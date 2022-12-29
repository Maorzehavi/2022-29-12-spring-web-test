package com.maorzehavi.springwebtest.model.dto.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequest {

    private String name;

    private LocalDate birthDate;

}
