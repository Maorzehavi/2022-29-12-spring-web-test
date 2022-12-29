package com.maorzehavi.springwebtest.service;

import com.maorzehavi.springwebtest.model.dto.grade.GradeRequest;
import com.maorzehavi.springwebtest.model.dto.grade.GradeResponse;
import com.maorzehavi.springwebtest.model.entity.Grade;

import java.util.List;
import java.util.Optional;

public interface GradeService {
    Optional<GradeResponse> createGrade(GradeRequest gradeRequest);

    Optional<GradeResponse> updateGrade(Long id, GradeRequest gradeRequest);

    Optional<GradeResponse> getGrade(Long id);

    Optional<GradeResponse> deleteGrade(Long id);

    Optional<Grade> getGradeEntity(Long id);

    Optional<List<GradeResponse>> getAllGrades();


    // mapping methods
    Grade mapToGrade(GradeRequest gradeRequest);

    GradeResponse mapToGradeResponse(Grade grade);

    Optional<Double> calculateAverageGrade(Long studentId);

    Optional<List<GradeResponse>> getAllGradesByStudentId(Long studentId);
}
