package com.bilalkose.springcustomerarchivingsystem.repository;

import com.bilalkose.springcustomerarchivingsystem.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {

}
