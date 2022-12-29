package com.maorzehavi.springwebtest.model.dto.grade;

import com.maorzehavi.springwebtest.model.enums.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeRequest {

    private Topic topic;

    private Integer grade;

    private Long studentId;
}
