package com.maorzehavi.springwebtest.repository;

import com.maorzehavi.springwebtest.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s.isActive FROM Student s WHERE s.id = ?1")
    Optional<Boolean> isActivated(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Student s SET s.isActive = true WHERE s.id = ?1")
    void activateStudent(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Student s SET s.isActive = false WHERE s.id = ?1")
    void deactivateStudent(Long id);

    Optional<List<Student>> findAllByName(String name);

}