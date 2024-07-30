package com.example.demo.service.impl;

import com.example.demo.module.web.printer.entity.PrinterEntity;
import com.example.demo.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/printers")
public class PrinterServiceController {

    @Autowired
    private PrinterService printerService;

    @GetMapping("/{name}")
    public PrinterEntity getPrinter(@PathVariable String name) {
        return printerService.getPrinterByName(name);
    }

    @GetMapping
    public List<PrinterEntity> getAllPrinters() {
        return printerService.getAllPrinters();
    }

    @GetMapping("/cups")
    public String getCupsPrinters() {
        return printerService.getCupsPrinters();
    }

    @PostMapping("/install")
    public String installPrinter(@RequestParam String name) {
        PrinterEntity printer = printerService.getPrinterByName(name);
        if (printer != null) {
            return printerService.installPrinterDriver(printer);
        } else {
            return "Printer not found";
        }
    }
}