// package com.example.demo.service.controller;

// import com.example.demo.service.PrinterInstallerFromjson;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/printersjson")
// public class PrinterInstallerFromjsonController {

//     @Autowired
//     private PrinterInstallerFromjson printerInstallerFromjson;

//     @PostMapping("/install")
//     public ResponseEntity<String> installPrintersFromJson() {
//         try {
//             printerInstallerFromjson.installPrinters();
//             return new ResponseEntity<>("Printers installation process completed", HttpStatus.OK);
//         } catch (Exception e) {
//             e.printStackTrace();
//             return new ResponseEntity<>("Error starting the installation process: " + e.getMessage(),
//                     HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//     }
// }
