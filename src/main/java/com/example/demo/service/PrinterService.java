package com.example.demo.service;

import com.example.demo.module.web.printer.entity.PrinterEntity;
import com.example.demo.module.web.printer.repository.PrinterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@Service
public class PrinterService {

    private final String CUPS_SERVER = "http://localhost:631";
    private final RestTemplate restTemplate;

    public PrinterService() {
        this.restTemplate = new RestTemplate();
    }

    @Autowired
    private PrinterRepository printerRepository;

    public PrinterEntity getPrinterByName(String name) {
        Optional<PrinterEntity> printer = printerRepository.findByPrinterName(name);
        return printer.orElse(null);
    }

    public List<PrinterEntity> getAllPrinters() {
        return printerRepository.findAll();
    }

    public String getCupsPrinters() {
        String url = CUPS_SERVER + "/printers/";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching printers";
        }
    }

    public String printFile(String printerName, MultipartFile file) {
        PrinterEntity printer = getPrinterByName(printerName);
        if (printer == null) {
            return "Printer not found";
        }
        String url = CUPS_SERVER + "/printers/" + printer.getPrinterName();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> requestEntity;
        try {
            requestEntity = new HttpEntity<>(file.getBytes(), headers);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error processing file";
        }

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "Print job submitted successfully!";
            } else {
                return "Failed to submit print job.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error submitting print job";
        }
    }

    public String getJobs() {
        String url = CUPS_SERVER + "/jobs/";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching jobs";
        }
    }

    public void cancelJob(String jobId) {
        String url = CUPS_SERVER + "/jobs/" + jobId;
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String installPrinterDriver(PrinterEntity printer) {
        String ppdURL = printer.getPpdURL();
        String printerName = printer.getPrinterName();
        String printerIP = printer.getPrinterIP();
        String ppdFilePath = "/usr/share/cups/ppd-new/" + printerName + ".ppd";

        try {
            downloadPPDFile(ppdURL, ppdFilePath);
            System.out.println("PPD file copied to: " + ppdFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error downloading PPD file: " + e.getMessage();
        }

        if (!checkWritePermission("/usr/share/cups/ppd-new")) {
            return "Write permission is not granted for directory: /usr/share/cups/ppd-new";
        }

        String url = CUPS_SERVER + "/admin/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("username", "password");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("op", "add-printer");
        body.add("printer_name", printerName);
        body.add("device_uri", "socket://" + printerIP);
        body.add("ppd_name", ppdFilePath);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            Process process = Runtime.getRuntime().exec(
                    new String[] { "docker", "exec", "cutp-printer-president", "lpadmin",
                            "-p", printerName, "-E", "-v", "socket://" + printerIP, "-P", ppdFilePath });
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String s;
            StringBuilder output = new StringBuilder();
            while ((s = stdInput.readLine()) != null) {
                output.append(s);
            }
            StringBuilder error = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                error.append(s);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "Printer driver installed successfully! Output: " + output;
            } else {
                return "Failed to install printer driver. Exit code: " + exitCode + ". Error: " + error;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error installing printer driver: " + e.getMessage();
        }
    }

    private void downloadPPDFile(String ppdURL, String destinationPath) throws IOException {
        System.out.println("Downloading PPD file from URL: " + ppdURL);
        byte[] ppdContent = restTemplate.getForObject(ppdURL, byte[].class);
        if (ppdContent != null && ppdContent.length > 0) {
            try (FileOutputStream outStream = new FileOutputStream(destinationPath)) {
                outStream.write(ppdContent);
                System.out.println("PPD file written to: " + destinationPath);

                // คัดลอกไฟล์ PPD เข้า Docker container
                Process process = Runtime.getRuntime().exec(
                        new String[] { "docker", "cp", destinationPath, "cutp-printer-president:" + destinationPath });
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                throw new IOException("Failed to save PPD file to path: " + destinationPath, e);
            }
        } else {
            throw new IOException("Failed to download PPD file from URL: " + ppdURL);
        }
    }

    public String installLocalPPDFile(MultipartFile file, String printerName, String printerIP) {
        String ppdFilePath = "/usr/share/cups/ppd-new/" + printerName + ".ppd";

        try {
            savePPDFile(file, ppdFilePath);
            System.out.println("PPD file copied to: " + ppdFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error saving PPD file: " + e.getMessage();
        }

        if (!checkWritePermission("/usr/share/cups/ppd-new")) {
            return "Write permission is not granted for directory: /usr/share/cups/ppd-new";
        }

        try {
            Process process = Runtime.getRuntime().exec(
                    new String[] { "docker", "exec", "cutp-printer-president", "lpadmin",
                            "-p", printerName, "-E", "-v", "socket://" + printerIP, "-P", ppdFilePath });
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String s;
            StringBuilder output = new StringBuilder();
            while ((s = stdInput.readLine()) != null) {
                output.append(s);
            }
            StringBuilder error = new StringBuilder();
            while ((s = stdError.readLine()) != null) {
                error.append(s);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "Printer driver installed successfully! Output: " + output;
            } else {
                return "Failed to install printer driver. Exit code: " + exitCode + ". Error: " + error;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error installing printer driver: " + e.getMessage();
        }
    }

    private void savePPDFile(MultipartFile file, String destinationPath) throws IOException {
        try (FileOutputStream outStream = new FileOutputStream(destinationPath)) {
            outStream.write(file.getBytes());
            System.out.println("PPD file written to: " + destinationPath);

            // คัดลอกไฟล์ PPD เข้า Docker container
            Process process = Runtime.getRuntime().exec(
                    new String[] { "docker", "cp", destinationPath, "cutp-printer-president:" + destinationPath });
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Failed to copy PPD file to Docker container. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            throw new IOException("Failed to save PPD file to path: " + destinationPath, e);
        }
    }

    public boolean checkWritePermission(String directoryPath) {
        File directory = new File(directoryPath);
        System.out.println("Checking write permission for directory: " + directoryPath);
        boolean canWrite = directory.exists() && directory.canWrite();
        System.out.println("Can write: " + canWrite);
        return canWrite;
    }

}
