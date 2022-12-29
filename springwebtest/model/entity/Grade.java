package com.maorzehavi.springwebtest.model.entity;

import com.maorzehavi.springwebtest.model.enums.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Topic topic;

    private Integer grade;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
