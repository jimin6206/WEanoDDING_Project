package com.ssafy.db.repository;

import com.ssafy.db.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Integer> {

    Files findByFno(int fno);
    List<Files> findByFolderNameEquals(String folderName);

}
