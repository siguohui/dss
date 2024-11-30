package com.xiaosi.rd.repository;

import com.xiaosi.rd.entity.Stu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StuRepository extends JpaRepository<Stu, Long> {
}
