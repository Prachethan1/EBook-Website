package com.eBookWebsite.eBookWebsite.controller;

import com.eBookWebsite.eBookWebsite.entity.FileData;
import com.eBookWebsite.eBookWebsite.response.ResponseFile;
import com.eBookWebsite.eBookWebsite.response.ResponseMessage;
import com.eBookWebsite.eBookWebsite.serviceImp.FileStorageService;
//import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "http://localhost:5173")
public class FileController {
    @Autowired
    private FileStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.store(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }


    @GetMapping("/getfiles")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/getfiles/")
                    .path(dbFile.getId().toString())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName().toString(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/getfile/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileData file = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }



//    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewFile(@PathVariable String id) {
//        byte[] fileContent = storageService.getFileContent(id);
//        ByteArrayResource resource = new ByteArrayResource(fileContent);
//
//        // Assuming the content type is application/pdf, adjust for other types if necessary
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)  // Change this if necessary (e.g., application/pdf, image/png)
//                .body(resource);

        FileData fileData = storageService.getFile(id);
        byte[] fileContent = fileData.getData();
        String fileType = fileData.getType();  // Assuming you store the MIME type of the file

        // Convert byte array to resource
        ByteArrayResource resource = new ByteArrayResource(fileContent);

        // Return response with appropriate content type
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileType))  // Dynamically set content type
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileData.getName() + "\"")
                .body(resource);
    }

    // Endpoint for downloading files
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id) {
        FileData fileData = storageService.getFile(id);
        ByteArrayResource resource = new ByteArrayResource(fileData.getData());

        // Setting the content disposition for download
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileData.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // Adjust for file types (e.g., application/pdf, image/png)
                .body(resource);
    }

}
