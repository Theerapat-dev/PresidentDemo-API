package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.module.web.printerlist.entity.PrinterListEntity;
import org.springframework.stereotype.Service;

@Service
public class PrinterInstallerFromjson {

    // เส้นทางไฟล์ JSON ที่เก็บข้อมูลของเครื่องพิมพ์
    private static final String PRINTERS_JSON_PATH = "src/main/resources/printers.json";

    // เมธอดหลักที่ใช้ในการติดตั้งเครื่องพิมพ์จากไฟล์ JSON
    public void installPrinters() {
        try {
            // สร้าง ObjectMapper สำหรับการอ่านข้อมูลจาก JSON
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(PRINTERS_JSON_PATH);

            // ตรวจสอบว่าไฟล์ JSON มีอยู่หรือไม่
            if (jsonFile.exists()) {
                // อ่านข้อมูล JSON และแปลงเป็น List ของ PrinterListEntity
                List<PrinterListEntity> printers = objectMapper.readValue(jsonFile,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, PrinterListEntity.class));

                // วนลูปผ่านแต่ละเครื่องพิมพ์ในลิสต์
                for (PrinterListEntity printer : printers) {
                    // ตรวจสอบว่ามี PPD (PostScript Printer Description) ในรูปแบบ base64 หรือไม่
                    if (printer.getPpdBase64() != null && !printer.getPpdBase64().isEmpty()) {
                        // สร้างชื่อไฟล์ PPD จากชื่อของเครื่องพิมพ์
                        String ppdFileName = printer.getName() + ".ppd";
                        // ถอดรหัส base64 เป็นข้อมูล byte ของไฟล์ PPD
                        byte[] decodedPpd = Base64.getDecoder().decode(printer.getPpdBase64());

                        // เขียนข้อมูล PPD ลงในไฟล์
                        try (OutputStream os = new FileOutputStream(ppdFileName)) {
                            os.write(decodedPpd);
                        } catch (IOException e) {
                            // แสดงข้อผิดพลาดหากไม่สามารถเขียนไฟล์ได้
                            System.err.println("Error writing PPD file: " + e.getMessage());
                            continue; // ข้ามไปยังเครื่องพิมพ์ถัดไป
                        }

                        // คัดลอกไฟล์ PPD ไปยัง Docker container
                        if (!copyPPDFileToDocker(ppdFileName)) {
                            // แสดงข้อผิดพลาดหากไม่สามารถคัดลอกไฟล์ PPD ได้
                            System.err.println("Failed to copy PPD file to Docker container: " + ppdFileName);
                            // ลบไฟล์ PPD หลังจากคัดลอกไม่สำเร็จ
                            cleanUpPPDFile(ppdFileName);
                            continue; // ข้ามไปยังเครื่องพิมพ์ถัดไป
                        }

                        // เรียกใช้คำสั่ง CUPS ใน Docker container เพื่อติดตั้งเครื่องพิมพ์
                        String result = installPrinterInDocker(ppdFileName, printer.getName(), printer.getIp(),
                                printer.getDescription(),  printer.getLocation());
                        System.out.println(result); // แสดงผลลัพธ์ของการติดตั้งเครื่องพิมพ์

                        // ลบไฟล์ PPD หลังจากติดตั้งเสร็จสิ้น
                        cleanUpPPDFile(ppdFileName);
                    }
                }
            } else {
                // แสดงข้อผิดพลาดหากไม่พบไฟล์ JSON
                System.err.println("JSON file not found: " + PRINTERS_JSON_PATH);
            }
        } catch (IOException e) {
            e.printStackTrace(); // แสดงข้อผิดพลาดหากมีปัญหาในการอ่านไฟล์ JSON
        }
    }

    // เมธอดที่ใช้ในการคัดลอกไฟล์ PPD ไปยัง Docker container
    private static boolean copyPPDFileToDocker(String ppdFilePath) {
        try {
            // สร้าง ProcessBuilder สำหรับคำสั่ง Docker
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "cp", ppdFilePath, "cups-printer-president:/usr/share/cups/ppd-new/" + ppdFilePath);
            processBuilder.inheritIO(); // ส่งข้อมูลการป้อนข้อมูลและข้อมูลเอาท์พุตไปยังคอนโซล
            Process process = processBuilder.start();
            int exitCode = process.waitFor(); // รอให้คำสั่งเสร็จสิ้น
            return exitCode == 0; // คืนค่าความสำเร็จของการคัดลอก
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // แสดงข้อผิดพลาดหากมีปัญหาในการคัดลอกไฟล์
            return false;
        }
    }

    // เมธอดที่ใช้ในการติดตั้งเครื่องพิมพ์ใน Docker container
    private static String installPrinterInDocker(String ppdFilePath, String printerName, String printerIP,
            String description, String location) {
        try {
            // สร้าง ProcessBuilder สำหรับคำสั่ง CUPS
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "exec", "cups-printer-president", "lpadmin",
                    "-p", printerName, "-E", "-v", "socket://" + printerIP, "-P",
                    "/usr/share/cups/ppd-new/" + ppdFilePath, "-D", description, "-L", location);

            processBuilder.inheritIO(); // ส่งข้อมูลการป้อนข้อมูลและข้อมูลเอาท์พุตไปยังคอนโซล
            Process process = processBuilder.start();
            int exitCode = process.waitFor(); // รอให้คำสั่งเสร็จสิ้น

            if (exitCode == 0) {
                // คืนค่าข้อความสำเร็จหากการติดตั้งเสร็จสิ้น
                return "Printer installed successfully!";
            } else {
                // อ่านข้อผิดพลาดและคืนค่า
                String errorMessage = new String(process.getErrorStream().readAllBytes());
                return "Error installing printer: " + errorMessage;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // แสดงข้อผิดพลาดหากมีปัญหาในการติดตั้งเครื่องพิมพ์
            return "Error installing printer: " + e.getMessage();
        }
    }

    // ลบไฟล์ PPD หลังจากการติดตั้งเสร็จสิ้น
    private static void cleanUpPPDFile(String ppdFilePath) {
        File ppdFile = new File(ppdFilePath);
        if (ppdFile.exists()) {
            if (ppdFile.delete()) {
                System.out.println("PPD file deleted successfully.");
            } else {
                System.err.println("Failed to delete PPD file.");
            }
        }
    }
}
