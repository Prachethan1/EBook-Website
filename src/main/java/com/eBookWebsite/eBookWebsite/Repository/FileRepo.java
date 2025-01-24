package com.eBookWebsite.eBookWebsite.Repository;

import com.eBookWebsite.eBookWebsite.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<FileData, String> {

}

