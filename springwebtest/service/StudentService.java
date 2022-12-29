package com.maorzehavi.springwebtest.service;

import com.maorzehavi.springwebtest.model.dto.student.StudentGradesResponse;
import com.maorzehavi.springwebtest.model.dto.student.StudentRequest;
import com.maorzehavi.springwebtest.model.dto.student.StudentResponse;
import com.maorzehavi.springwebtest.model.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Optional<Boolean> isActivated(Long id);

    @Transactional
    @Modifying
    Optional<Boolean> activateStudent(Long id);

    @Transactional
    @Modifying
    Optional<Boolean> deactivateStudent(Long id);

    @Transactional
    @Modifying
    Optional<StudentResponse> createStudent(StudentRequest studentRequest);

    @Transactional
    @Modifying
    Optional<StudentResponse> updateStudent(Long id, StudentRequest studentRequest);

    Optional<StudentResponse> getStudent(Long id);

    Optional<StudentGradesResponse> getStudentWithGrades(Long id);

    @Transactional
    @Modifying
    Optional<StudentResponse> deleteStudent(Long id);

    Optional<Double> calculateAverageGrade(Long id);

    Optional<Student> getStudentEntity(Long id);

    Optional<List<StudentResponse>> getAllStudents();

    Optional<List<StudentResponse>> getAllStudentsByName(String name);

    Optional<List<StudentGradesResponse>> getAllStudentsWithGrades();

    // mapping methods
    Student mapToStudent(StudentRequest studentRequest);

    StudentResponse mapToStudentResponse(Student student);

    StudentGradesResponse mapToStudentGradesResponse(Student student);

}
