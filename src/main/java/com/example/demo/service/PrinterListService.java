package com.example.demo.service;

import com.example.demo.module.web.printerlist.entity.PrinterListEntity;
import com.example.printerapp.until.Base64Converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@Service
public class PrinterListService {

    private static final String PRINTERS_JSON_PATH = "src/main/resources/printers.json";

    public List<PrinterListEntity> getAllPrinters() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(PRINTERS_JSON_PATH);
            if (jsonFile.exists()) {
                return objectMapper.readValue(jsonFile,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, PrinterListEntity.class));
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public PrinterListEntity addPrinter(PrinterListEntity printer) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(PRINTERS_JSON_PATH);
            List<PrinterListEntity> printers;
            if (jsonFile.exists()) {
                printers = objectMapper.readValue(jsonFile,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, PrinterListEntity.class));
            } else {
                printers = new ArrayList<>();
            }
            if (printer.getId() == null) {
                printer.setId((long) (printers.size() + 1)); // Simple ID assignment
            }
            printers.add(printer);
            objectMapper.writeValue(jsonFile, printers);
            return printer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<String> installDriver(MultipartFile file, String name) {
        try {
            // สร้างไฟล์ชั่วคราวเพื่อเก็บไฟล์ PPD ที่อัปโหลด
            File tempFile = File.createTempFile("ppd", ".ppd");
            file.transferTo(tempFile);

            // เข้ารหัสไฟล์ PPD เป็น Base64
            String base64Encoded = Base64Converter.encodeFileToBase64(tempFile.getAbsolutePath());

            // อ่านเครื่องพิมพ์ที่มีอยู่จาก JSON
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(PRINTERS_JSON_PATH);
            List<PrinterListEntity> printers;
            if (jsonFile.exists()) {
                printers = objectMapper.readValue(jsonFile,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, PrinterListEntity.class));
            } else {
                printers = new ArrayList<>();
            }

            boolean printerFound = false;
            // อัปเดตเครื่องพิมพ์ที่มีอยู่หรือลงทะเบียนเครื่องพิมพ์ใหม่
            for (PrinterListEntity existingPrinter : printers) {
                if (existingPrinter.getName().equals(name)) {
                    existingPrinter.setPpdBase64(base64Encoded);
                    printerFound = true;
                    break;
                }
            }

            if (!printerFound) {
                // สร้างเครื่องพิมพ์ใหม่หากยังไม่มี
                PrinterListEntity newPrinter = new PrinterListEntity();
                newPrinter.setName(name);
                newPrinter.setIp("172.16.22.241"); // IP ตัวอย่าง
                newPrinter.setDescription("เครื่องที่ไม่มีในฐานข้อมูล"); // คำอธิบายตัวอย่าง
                newPrinter.setPpdBase64(base64Encoded);
                printers.add(newPrinter);
            }

            // เขียนรายการที่อัปเดตกลับไปที่ JSON
            objectMapper.writeValue(jsonFile, printers);

            // ลบไฟล์ชั่วคราว
            tempFile.delete();

            return ResponseEntity.ok(printerFound ? "Printer updated successfully" : "Driver installed successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error installing driver");
        }
    }

    public List<String> getAllPrinterNames() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(PRINTERS_JSON_PATH);
            if (jsonFile.exists()) {
                List<PrinterListEntity> printers = objectMapper.readValue(jsonFile,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, PrinterListEntity.class));
                List<String> printerNames = new ArrayList<>();
                for (PrinterListEntity printer : printers) {
                    printerNames.add(printer.getName());
                }
                return printerNames;
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
