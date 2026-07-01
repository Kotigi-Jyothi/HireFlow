package com.hireflow.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;

import org.springframework.web.multipart.MultipartFile;

import com.hireflow.dto.response.FileUploadResponseDTO;

public interface FileService {

    FileUploadResponseDTO uploadResume(MultipartFile file) throws IOException;

    FileUploadResponseDTO uploadLogo(MultipartFile file) throws IOException;

    FileUploadResponseDTO uploadOffer(MultipartFile file) throws IOException;

    Resource downloadResume(String fileName) throws MalformedURLException;

    Resource downloadLogo(String fileName) throws MalformedURLException;

    Resource downloadOffer(String fileName) throws MalformedURLException;
}