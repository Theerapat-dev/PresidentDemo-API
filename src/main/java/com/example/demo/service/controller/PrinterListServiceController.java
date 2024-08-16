package com.example.demo.service.controller;

import com.example.demo.module.web.printerlist.entity.PrinterListEntity;
import com.example.demo.service.PrinterListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class PrinterListServiceController {

    @Autowired
    private PrinterListService printerListService;

    @GetMapping
    public List<PrinterListEntity> getAllPrinters() {
        return printerListService.getAllPrinters();
    }

    // เพิ่มเครื่องพิมพ์ใหม่เข้าสู่ระบบ
    @PostMapping("/add")
    public ResponseEntity<PrinterListEntity> addPrinter(@RequestBody PrinterListEntity printer) {
        // เรียกใช้บริการเพื่อเพิ่มเครื่องพิมพ์ใหม่
        PrinterListEntity addedPrinter = printerListService.addPrinter(printer);

        // ตรวจสอบว่าเครื่องพิมพ์ถูกเพิ่มสำเร็จหรือไม่
        if (addedPrinter != null) {
            // ส่งกลับเครื่องพิมพ์ที่ถูกเพิ่มพร้อมสถานะ 200 OK
            return ResponseEntity.ok(addedPrinter);
        } else {
            // ส่งกลับสถานะ 500 Internal Server Error ถ้าเพิ่มไม่สำเร็จ
            return ResponseEntity.status(500).body(null);
        }
    }

    // ติดตั้งไดรเวอร์สำหรับเครื่องพิมพ์โดยการอัปโหลดไฟล์
    @PostMapping("/install")
    public ResponseEntity<String> installDriver(
            @RequestParam("file") MultipartFile file, // รับไฟล์ไดรเวอร์ที่ถูกอัปโหลด
            @RequestParam("name") String name, // รับชื่อของเครื่องพิมพ์
            @RequestParam("location") String location) { // รับ location ของเครื่องพิมพ์
        // ตรวจสอบว่าไฟล์ไม่ว่างเปล่า
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File parameter is missing or empty");
        }
        // ตรวจสอบว่าชื่อเครื่องพิมพ์ไม่ว่างเปล่า
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name parameter is missing or empty");
        }
        // ตรวจสอบว่า location ไม่ว่างเปล่า
        if (location == null || location.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Location parameter is missing or empty");
        }
        try {
            // ติดตั้งไดรเวอร์โดยใช้บริการที่ถูกเรียกใช้
            return printerListService.installDriver(file, name, location);
        } catch (Exception e) {
            // ถ้ามีข้อผิดพลาดเกิดขึ้น จะพิมพ์ stack trace และส่งกลับสถานะ 500 Internal Server Error
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error installing driver");
        }
    }

    // ลบเครื่องพิมพ์ตาม ID ที่ระบุ
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePrinter(@PathVariable("id") Long id) {
        // เรียกใช้บริการเพื่อลบเครื่องพิมพ์
        boolean removed = printerListService.deletePrinter(id);

        // ตรวจสอบว่าลบสำเร็จหรือไม่
        if (removed) {
            // ส่งกลับสถานะ 200 OK ถ้าลบสำเร็จ
            return ResponseEntity.ok("Printer deleted successfully");
        } else {
            // ส่งกลับสถานะ 404 Not Found ถ้าไม่พบเครื่องพิมพ์ที่ต้องการลบ
            return ResponseEntity.status(404).body("Printer not found");
        }
    }

    // ดึงชื่อของเครื่องพิมพ์ทั้งหมด
    @GetMapping("/names")
    public List<String> getAllPrinterNames() {
        // เรียกใช้บริการเพื่อดึงชื่อเครื่องพิมพ์ทั้งหมด
        return printerListService.getAllPrinterNames();
    }
}
