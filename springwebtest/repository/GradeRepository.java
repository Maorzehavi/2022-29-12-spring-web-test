package com.maorzehavi.springwebtest.repository;

import com.maorzehavi.springwebtest.model.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Optional<List<Grade>> findAllByStudentId(Long id);
}