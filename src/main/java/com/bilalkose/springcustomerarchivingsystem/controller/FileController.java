package com.bilalkose.springcustomerarchivingsystem.controller;

import com.bilalkose.springcustomerarchivingsystem.model.File;
import com.bilalkose.springcustomerarchivingsystem.repository.FileRepository;
import com.bilalkose.springcustomerarchivingsystem.service.FileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/file")
@CrossOrigin("*")
@SecurityRequirement(name = "bearerAuth")
public class FileController {

    private final FileService fileService;
    private final FileRepository fileRepository;

    public FileController(FileService fileService, FileRepository fileRepository) {
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<File>> getAllFiles() {
        List<File> fileInfos = fileService.getAllFiles().collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.getFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam("customerId") Long customerId) throws IOException {
        fileService.uploadFile(file, customerId);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @PutMapping("/update/{fileId}/{customerId}")
    public ResponseEntity<String> updateFile(@PathVariable Long fileId, @PathVariable Long customerId){
        fileService.updateFile(fileId, customerId);
        return ResponseEntity.ok("File updated successfully");
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId) throws IOException, URISyntaxException {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok("File deleted.");
    }
}
