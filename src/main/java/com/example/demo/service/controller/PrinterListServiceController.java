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

    @PostMapping("/add")
    public ResponseEntity<PrinterListEntity> addPrinter(@RequestBody PrinterListEntity printer) {
        PrinterListEntity addedPrinter = printerListService.addPrinter(printer);
        if (addedPrinter != null) {
            return ResponseEntity.ok(addedPrinter);
        } else {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/install")
    public ResponseEntity<String> installDriver(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File parameter is missing or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name parameter is missing or empty");
        }
        try {
            return printerListService.installDriver(file, name);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error installing driver");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePrinter(@PathVariable("id") Long id) {
        boolean removed = printerListService.deletePrinter(id);
        if (removed) {
            return ResponseEntity.ok("Printer deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Printer not found");
        }
    }

    @GetMapping("/names")
    public List<String> getAllPrinterNames() {
        return printerListService.getAllPrinterNames();
    }
}
