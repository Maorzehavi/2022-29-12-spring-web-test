package com.maorzehavi.springwebtest.service.impl;

import com.maorzehavi.springwebtest.exception.StudentSystemException;
import com.maorzehavi.springwebtest.model.dto.student.StudentGradesResponse;
import com.maorzehavi.springwebtest.model.dto.student.StudentRequest;
import com.maorzehavi.springwebtest.model.dto.student.StudentResponse;
import com.maorzehavi.springwebtest.model.entity.Student;
import com.maorzehavi.springwebtest.repository.StudentRepository;
import com.maorzehavi.springwebtest.service.GradeService;
import com.maorzehavi.springwebtest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final GradeService gradeService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, GradeService gradeService) {
        this.studentRepository = studentRepository;
        this.gradeService = gradeService;
    }

    @Override
    public Optional<Boolean> isActivated(Long id) {
        return studentRepository.isActivated(id);
    }

    @Transactional
    @Modifying
    @Override
    public Optional<Boolean> activateStudent(Long id) {
        try {
            studentRepository.activateStudent(id);
            return Optional.of(Boolean.TRUE);
        } catch (Exception e) {
            return Optional.of(Boolean.FALSE);
        }
    }

    @Transactional
    @Modifying
    @Override
    public Optional<Boolean> deactivateStudent(Long id) {
        try {
            studentRepository.deactivateStudent(id);
            return Optional.of(Boolean.TRUE);
        } catch (Exception e) {
            return Optional.of(Boolean.FALSE);
        }
    }

    @Transactional
    @Modifying
    @Override
    public Optional<StudentResponse> createStudent(@Valid StudentRequest studentRequest) {
        var student = mapToStudent(studentRequest);
        student.setIsActive(Boolean.TRUE);
        return Optional.of(mapToStudentResponse(studentRepository.save(student)));
    }

    @Transactional
    @Modifying
    @Override
    public Optional<StudentResponse> updateStudent(Long id, @Valid StudentRequest studentRequest) {
        var student = studentRepository.findById(id);
        if (student.isPresent()) {
            student.get().setName(studentRequest.getName());
            student.get().setBirthDate(studentRequest.getBirthDate());
            return Optional.of(mapToStudentResponse(studentRepository.save(student.get())));
        }
        throw new StudentSystemException("Student with id: " + id + " not found");
    }

    @Override
    public Optional<StudentResponse> getStudent(Long id) {
        return studentRepository.findById(id).map(this::mapToStudentResponse);
    }

    @Override
    @Transactional
    public Optional<StudentGradesResponse> getStudentWithGrades(Long id) {
        return studentRepository.findById(id).map(this::mapToStudentGradesResponse);
    }

    @Transactional
    @Modifying
    @Override
    public Optional<StudentResponse> deleteStudent(Long id) {
        var student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.deleteById(id);
            return Optional.of(mapToStudentResponse(student.get()));
        }
        throw new StudentSystemException("Student with id: " + id + " not found");
    }

    @Override
    public Optional<Double> calculateAverageGrade(Long id) {
        return gradeService.calculateAverageGrade(id);
    }

    @Override
    public Optional<Student> getStudentEntity(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Optional<List<StudentResponse>> getAllStudents() {
        var students = studentRepository.findAll();
        var studentResponses = students.stream().map(this::mapToStudentResponse).toList();
        return Optional.of(studentResponses);
    }

    @Override
    public Optional<List<StudentResponse>> getAllStudentsByName(String name) {
        var students = studentRepository.findAllByName(name);
        if (students.isPresent() && !students.get().isEmpty()) {
            students.get();
            var studentResponses = students.get().stream().map(this::mapToStudentResponse).toList();
            return Optional.of(studentResponses);
        }
        throw new StudentSystemException("No one of the students has the name: " + name);
    }

    @Transactional
    @Modifying
    @Override
    public Optional<List<StudentGradesResponse>> getAllStudentsWithGrades() {
        var students = studentRepository.findAll();
        var studentGradesResponses = students.stream().map(this::mapToStudentGradesResponse).toList();
        return Optional.of(studentGradesResponses);
    }

    @Override
    public Student mapToStudent(StudentRequest studentRequest) {
        return Student.builder()
                .name(studentRequest.getName())
                .birthDate(studentRequest.getBirthDate())
                .build();
    }

    @Override
    public StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .birthDate(student.getBirthDate())
                .isActive(student.getIsActive())
                .build();
    }

    @Override
    public StudentGradesResponse mapToStudentGradesResponse(Student student) {
        var grades = student.getGrades().stream().map(gradeService::mapToGradeResponse).toList();
        return StudentGradesResponse.builder()
                .student(mapToStudentResponse(student))
                .grades(grades)
                .build();

    }
}
