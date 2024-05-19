package com.bilalkose.springcustomerarchivingsystem.config;

import com.bilalkose.springcustomerarchivingsystem.service.FileService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileServiceConfig {

    private final FileService fileService;

    public FileServiceConfig(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    public void initFileService() {
        fileService.initFileFolder();
    }
}
