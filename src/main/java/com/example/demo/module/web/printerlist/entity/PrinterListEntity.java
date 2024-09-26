package com.example.demo.module.web.printerlist.entity;

public class PrinterListEntity {

    private Long id;
    private String name;
    private String ip;
    private String description;
    private String location;
    private String ppdBase64;

    // Getter และ Setter อื่นๆ
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPpdBase64() {
        return ppdBase64;
    }

    public void setPpdBase64(String ppdBase64) {
        this.ppdBase64 = ppdBase64;
    }
}
