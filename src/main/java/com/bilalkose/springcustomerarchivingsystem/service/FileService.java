package com.bilalkose.springcustomerarchivingsystem.service;

import com.bilalkose.springcustomerarchivingsystem.controller.FileController;
import com.bilalkose.springcustomerarchivingsystem.exception.*;
import com.bilalkose.springcustomerarchivingsystem.model.Customer;
import com.bilalkose.springcustomerarchivingsystem.model.File;
import com.bilalkose.springcustomerarchivingsystem.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private final CustomerService customerService;
    private final Path root = Paths.get("uploads");

    public FileService(FileRepository fileDBRepository, CustomerService customerService) {
        this.fileRepository = fileDBRepository;
        this.customerService = customerService;
    }

    public void initFileFolder() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new InitializeFolderException("Could not initialize folder for upload!");
        }
    }

    public Stream<File> getAllFiles() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(path -> {
                        String filename = path.getFileName().toString();
                        String url = MvcUriComponentsBuilder
                                .fromMethodName(FileController.class, "getFile", filename).build().toString();
                        return File.builder().name(filename).url(url).build();
                    });
        } catch (IOException e) {
            throw new LoadFileException("Could not load the files!");
        }
    }

    public Resource getFile(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ReadFileException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new FileUrlException("Error: " + e.getMessage());
        }
    }

    public void uploadFile(MultipartFile fileReq, Long customerId) throws IOException {
        this.checkIfFileIsExists(fileReq.getOriginalFilename());
        Customer customer = customerService.getCustomer(customerId);
        Files.copy(fileReq.getInputStream(), this.root.resolve(Objects.requireNonNull(fileReq.getOriginalFilename())));
        String fileUrl = this.root.resolve(fileReq.getOriginalFilename()).toUri().toString();
        File file = new File(fileReq.getOriginalFilename(), fileUrl, customer);
        log.info("File Uploaded: " + fileReq.getOriginalFilename() + " | " + customerId);
        fileRepository.save(file);
    }

    public void updateFile(Long fileId, Long customerId) {
        File existingFile = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found by id: " + fileId));
        Customer newCustomer = customerService.getCustomer(customerId);
        existingFile.setCustomer(newCustomer);
        log.info("File Updated: " + " | " + customerId);
        fileRepository.save(existingFile);
    }

    public void deleteFile(Long fileId) throws IOException {
        File existingFile = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found by id: " + fileId));
        Files.deleteIfExists(root.resolve(existingFile.getName()));
        log.info("File Deleted: " + existingFile.getName());
        fileRepository.delete(existingFile);
    }

    void checkIfFileIsExists(String fileName) throws MalformedURLException {
        Path file = root.resolve(fileName);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists()) {
            throw new AlreadyFileExistsException("This file already exists " + fileName);
        }
    }
}

