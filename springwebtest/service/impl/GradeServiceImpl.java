package com.maorzehavi.springwebtest.service.impl;

import com.maorzehavi.springwebtest.exception.StudentSystemException;
import com.maorzehavi.springwebtest.model.dto.grade.GradeRequest;
import com.maorzehavi.springwebtest.model.dto.grade.GradeResponse;
import com.maorzehavi.springwebtest.model.entity.Grade;
import com.maorzehavi.springwebtest.repository.GradeRepository;
import com.maorzehavi.springwebtest.service.GradeService;
import com.maorzehavi.springwebtest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository,
                            @Lazy StudentService studentService) {
        this.gradeRepository = gradeRepository;
        this.studentService = studentService;
    }

    @Override
    @Transactional
    @Modifying
    public Optional<GradeResponse> createGrade(@Valid GradeRequest gradeRequest) {
        var student = studentService.getStudentEntity(gradeRequest.getStudentId())
                .orElseThrow(() -> new StudentSystemException("Student with id " + gradeRequest.getStudentId() + " not found"));
        if (studentService.isActivated(gradeRequest.getStudentId()).orElse(Boolean.FALSE)) {
            var grade = mapToGrade(gradeRequest);
            grade.setStudent(student);
            return Optional.of(mapToGradeResponse(gradeRepository.save(grade)));
        } else {
            throw new StudentSystemException("Student is not activated");
        }
    }

    @Override
    public Optional<GradeResponse> updateGrade(Long id, @Valid GradeRequest gradeRequest) {
        var grade = getGradeEntity(id);
        if (grade.isPresent()) {
            grade.get().setGrade(gradeRequest.getGrade());
            grade.get().setTopic(gradeRequest.getTopic());
            grade.get().setStudent(studentService.getStudentEntity(gradeRequest.getStudentId())
                    .orElseThrow(() -> new StudentSystemException("Could not find student with id: " + gradeRequest.getStudentId())));
            return Optional.of(mapToGradeResponse(gradeRepository.save(grade.get())));
        }
        throw new StudentSystemException("Could not find grade with id: " + id);
    }

    @Override
    public Optional<GradeResponse> getGrade(Long id) {
        var grade = gradeRepository.findById(id);
        return grade.map(this::mapToGradeResponse);
    }

    @Override
    public Optional<GradeResponse> deleteGrade(Long id) {
        var grade = getGradeEntity(id);
        if (grade.isPresent()) {
            gradeRepository.delete(grade.get());
            return Optional.of(mapToGradeResponse(grade.get()));
        }
        throw new StudentSystemException("Could not find grade with id: " + id);
    }

    @Override
    public Optional<Grade> getGradeEntity(Long id) {
        return Optional.of(gradeRepository.findById(id)
                .orElseThrow(() -> new StudentSystemException("Grade with id " + id + " not found")));
    }

    @Override
    public Optional<List<GradeResponse>> getAllGrades() {
        var grades = gradeRepository.findAll();
        var gradeResponses = grades.stream()
                .map(this::mapToGradeResponse)
                .toList();
        return Optional.of(gradeResponses);
    }

    @Override
    public Grade mapToGrade(GradeRequest gradeRequest) {
        return Grade.builder()
                .grade(gradeRequest.getGrade())
                .topic(gradeRequest.getTopic())
                .build();
    }

    @Override
    public GradeResponse mapToGradeResponse(Grade grade) {
        return GradeResponse.builder()
                .id(grade.getId())
                .topic(grade.getTopic())
                .grade(grade.getGrade())
                .build();
    }

    @Override
    public Optional<Double> calculateAverageGrade(Long studentId) {
        var grades = gradeRepository.findAllByStudentId(studentId);
        if (grades.isPresent()) {
            var average = grades.get().stream()
                    .mapToDouble(Grade::getGrade)
                    .average();
            return Optional.of(average.orElseThrow());
        }
        throw new StudentSystemException("Could not find grades for student with studentId: " + studentId);
    }

    @Override
    public Optional<List<GradeResponse>> getAllGradesByStudentId(Long studentId) {
        var grades = gradeRepository.findAllByStudentId(studentId);
        if (grades.isPresent()) {
            var gradeResponses = grades.get().stream()
                    .map(this::mapToGradeResponse)
                    .toList();
            return Optional.of(gradeResponses);
        }
        throw new StudentSystemException("Could not find grades for student with studentId: " + studentId);
    }
}
