package com.maorzehavi.springwebtest.model.dto.student;

import com.maorzehavi.springwebtest.model.dto.grade.GradeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentGradesResponse {

    private StudentResponse student;

    private List<GradeResponse> grades;
}
