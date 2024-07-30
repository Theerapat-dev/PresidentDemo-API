package com.example.demo.module.web.printer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Printer")
public class PrinterEntity {

    @Id
    @Column(name = "printerName")
    private String printerName;

    @Column(name = "printerIP")
    private String printerIP;

    @Column(name = "driverPrinter")
    private String driverPrinter;

    @Column(name = "ppdURL")
    private String ppdURL;

    // Getters and Setters
    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterIP() {
        return printerIP;
    }

    public void setPrinterIP(String printerIP) {
        this.printerIP = printerIP;
    }

    public String getDriverPrinter() {
        return driverPrinter;
    }

    public void setDriverPrinter(String driverPrinter) {
        this.driverPrinter = driverPrinter;
    }

    public String getPpdURL() {
        return ppdURL;
    }

    public void setPpdURL(String ppdURL) {
        this.ppdURL = ppdURL;
    }
}
