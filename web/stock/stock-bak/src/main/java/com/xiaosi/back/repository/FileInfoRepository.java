package com.xiaosi.back.repository;

import com.xiaosi.back.entity.FileInfo;
import com.xiaosi.back.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo,Long>, JpaSpecificationExecutor<FileInfo> {
}
