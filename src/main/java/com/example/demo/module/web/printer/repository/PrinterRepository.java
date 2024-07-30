package com.example.demo.module.web.printer.repository;

import com.example.demo.module.web.printer.entity.PrinterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PrinterRepository extends JpaRepository<PrinterEntity, String> {
    Optional<PrinterEntity> findByPrinterName(String printerName);
}
