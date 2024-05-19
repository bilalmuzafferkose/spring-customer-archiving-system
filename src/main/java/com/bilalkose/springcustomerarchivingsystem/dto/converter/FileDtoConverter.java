package com.bilalkose.springcustomerarchivingsystem.dto.converter;

import com.bilalkose.springcustomerarchivingsystem.dto.FileDto;
import com.bilalkose.springcustomerarchivingsystem.model.File;
import org.springframework.stereotype.Component;

@Component
public class FileDtoConverter {
    public FileDto convert(File file) {
        return FileDto.builder()
                .name(file.getName())
                .url(file.getUrl())
                .build();
    }
}
