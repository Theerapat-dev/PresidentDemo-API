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

//import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import org.springframework.web.client.RestTemplate;

@Service
public class PrinterService {

    private final String CUPS_SERVER = "http://localhost:631";
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PrinterRepository printerRepository;

    // ดึงข้อมูลเครื่องพิมพ์จากฐานข้อมูล
    public PrinterEntity getPrinterByName(String name) {
        Optional<PrinterEntity> printer = printerRepository.findByPrinterName(name);
        return printer.orElse(null);
    }

    public List<PrinterEntity> getAllPrinters() {
        return printerRepository.findAll();
    }

    // ดึงข้อมูลเครื่องพิมพ์จาก CUPS
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

    // ส่งงานพิมพ์ไปยัง CUPS
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

    // ดึงข้อมูลงานพิมพ์จาก CUPS
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

    // ยกเลิกงานพิมพ์
    public void cancelJob(String jobId) {
        String url = CUPS_SERVER + "/jobs/" + jobId;
        try {
            restTemplate.delete(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ฟังก์ชันสำหรับคัดลอกไฟล์ PPD
    public String copyPPDFile(String ppdURL, String printerName) throws IOException {
        // ตรวจสอบและสร้างไดเรกทอรีถ้ายังไม่มี
        File targetDir = new File("/etc/cups/ppd");
        if (!targetDir.exists()) {
            if (!targetDir.mkdirs()) {
                throw new IOException("Failed to create directory: " + targetDir.getAbsolutePath());
            }
        }

        // สร้างไฟล์ในไดเรกทอรีที่กำหนด
        File targetFile = new File(targetDir, printerName + ".ppd");
        try (FileOutputStream outStream = new FileOutputStream(targetFile)) {
            // ดาวน์โหลดไฟล์จาก URL และเขียนลงไฟล์
            outStream.write(restTemplate.getForObject(ppdURL, byte[].class));
        } catch (IOException e) {
            throw new IOException("Failed to copy PPD file from URL: " + ppdURL, e);
        }

        return targetFile.getAbsolutePath();
    }

    // ฟังก์ชันสำหรับติดตั้งไดร์เวอร์เครื่องพิมพ์โดยใช้ lpadmin แก้ใหม่ ใช้ API ของ
    // CUPS แทน lpadmin
    public String installPrinterDriver(PrinterEntity printer) {
        String ppdURL = printer.getPpdURL();
        String printerName = printer.getPrinterName();
        String printerIP = printer.getPrinterIP();

        // สร้าง URL สำหรับติดตั้งเครื่องพิมพ์
        String url = String.format("http://localhost:631/printers/%s", printerName);

        // การกำหนด headers และ body ของคำขอ
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("device-uri", "socket://" + printerIP);
        body.add("ppd", ppdURL);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // ส่งคำขอ POST ไปยัง CUPS API
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return "Printer driver installed successfully! :)";
            } else {
                return "Failed to install printer driver. Response: " + response.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error installing printer driver T_T: " + e.getMessage();
        }
    }
}