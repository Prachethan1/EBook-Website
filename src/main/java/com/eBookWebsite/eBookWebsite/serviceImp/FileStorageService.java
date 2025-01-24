package com.eBookWebsite.eBookWebsite.serviceImp;

import com.eBookWebsite.eBookWebsite.Repository.FileRepo;
import com.eBookWebsite.eBookWebsite.entity.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageService {
    @Autowired
    private FileRepo fileRepo;

    public FileData store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileData FileDB = new FileData(fileName, file.getContentType(), file.getBytes());
        return fileRepo.save(FileDB);
    }

    public FileData getFile(String id) {
        return fileRepo.findById(id).get();
    }

    public Stream<FileData> getAllFiles() {
        return fileRepo.findAll().stream();
    }

    public byte[] getFileContent(String id) {
        FileData fileData = getFile(id);
        return fileData.getData();  // Assuming getData() returns the byte[] content of the file
    }
}

