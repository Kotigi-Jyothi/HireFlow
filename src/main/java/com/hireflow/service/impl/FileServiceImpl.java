package com.hireflow.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hireflow.dto.response.FileUploadResponseDTO;
import com.hireflow.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public FileUploadResponseDTO uploadResume(MultipartFile file) throws IOException {

        return uploadFile(file, "uploads/resumes/", "Resume uploaded successfully");
    }

    @Override
    public FileUploadResponseDTO uploadLogo(MultipartFile file) throws IOException {

        return uploadFile(file, "uploads/logos/", "Logo uploaded successfully");
    }

    @Override
    public FileUploadResponseDTO uploadOffer(MultipartFile file) throws IOException {

        return uploadFile(file, "uploads/offers/", "Offer letter uploaded successfully");
    }

    private FileUploadResponseDTO uploadFile(
            MultipartFile file,
            String uploadDir,
            String message)
            throws IOException {

        String fileName =
                System.currentTimeMillis() + "_"
                        + file.getOriginalFilename();

        Path path = Paths.get(uploadDir + fileName);

        Files.createDirectories(path.getParent());

        Files.write(path, file.getBytes());

        FileUploadResponseDTO response = new FileUploadResponseDTO();

        response.setFileName(fileName);
        response.setMessage(message);

        return response;
    }
    
    @Override
    public Resource downloadResume(String fileName)
            throws MalformedURLException {

        Path path = Paths.get("uploads/resumes/")
                .resolve(fileName);

        return new UrlResource(path.toUri());
    }

    @Override
    public Resource downloadLogo(String fileName)
            throws MalformedURLException {

        Path path = Paths.get("uploads/logos/")
                .resolve(fileName);

        return new UrlResource(path.toUri());
    }

    @Override
    public Resource downloadOffer(String fileName)
            throws MalformedURLException {

        Path path = Paths.get("uploads/offers/")
                .resolve(fileName);

        return new UrlResource(path.toUri()); //convert:uploads/resumes/resume.pdf
        										//into resource obj that spring understand
    }
    

}