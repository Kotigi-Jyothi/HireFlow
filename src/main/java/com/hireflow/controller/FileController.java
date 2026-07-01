package com.hireflow.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hireflow.dto.response.FileUploadResponseDTO;
import com.hireflow.service.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController {
	
	private final FileService fileService;

	public FileController(FileService fileService) {
	    this.fileService = fileService;
	}

	@PostMapping("/resume")
	public FileUploadResponseDTO uploadResume(
	        @RequestParam("file") MultipartFile file)
	        throws IOException {

	    return fileService.uploadResume(file);
	}

    
	@PostMapping("/logo")
	public FileUploadResponseDTO uploadLogo(
	        @RequestParam("file") MultipartFile file)
	        throws IOException {

	    return fileService.uploadLogo(file);
	}
    
    
	@PostMapping("/offer")
	public FileUploadResponseDTO uploadOffer(
	        @RequestParam("file") MultipartFile file)
	        throws IOException {

	    return fileService.uploadOffer(file);
	}
	
	@GetMapping("/resume/{fileName}")
	public ResponseEntity<Resource> downloadResume(
	        @PathVariable String fileName)
	        throws MalformedURLException {

	    Resource resource =
	            fileService.downloadResume(fileName);

	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                    "attachment; filename=\"" +
	                            resource.getFilename() + "\"")
	            .body(resource);
	}
	
	@GetMapping("/logo/{fileName}")
	public ResponseEntity<Resource> downloadLogo(
	        @PathVariable String fileName)
	        throws MalformedURLException {

	    Resource resource =
	            fileService.downloadLogo(fileName);

	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                    "attachment; filename=\"" +
	                            resource.getFilename() + "\"")
	            .body(resource); //this tells the browser
	    						 //"Download this file instead of displaying JSON."
	}							//Without it, the browser might try to display the file contents
	
	@GetMapping("/offer/{fileName}")
	public ResponseEntity<Resource> downloadOffer(
	        @PathVariable String fileName)
	        throws MalformedURLException {

	    Resource resource =
	            fileService.downloadOffer(fileName);

	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                    "attachment; filename=\"" +
	                            resource.getFilename() + "\"")
	            .body(resource);
	}
}