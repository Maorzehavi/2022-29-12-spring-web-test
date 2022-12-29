package com.maorzehavi.springwebtest;

import com.maorzehavi.springwebtest.model.dto.grade.GradeRequest;
import com.maorzehavi.springwebtest.model.dto.grade.GradeResponse;
import com.maorzehavi.springwebtest.model.dto.student.StudentGradesResponse;
import com.maorzehavi.springwebtest.model.dto.student.StudentRequest;
import com.maorzehavi.springwebtest.model.dto.student.StudentResponse;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SchoolTesting {
    public static void main(String[] args) {
        //this is rest template class

        RestTemplate restTemplate = new RestTemplate();
        URI baseUri = URI.create("http://localhost:8080/students");

//        System.out.println(createStudent(restTemplate, baseUri, StudentRequest.builder()
//                .name("avi")
//                .birthDate(LocalDate.of(2000, 12, 14))
//                .build()));

//
//        System.out.println(getAllStudents(restTemplate, baseUri));
//
//        System.out.println(getStudent(restTemplate, baseUri, 2L));
//
//        System.out.println(updateStudent(restTemplate, baseUri, 2L, StudentRequest.builder()
//                .name("alon")
//                .birthDate(LocalDate.of(1998, 12, 14))
//                .build()));
//
//        System.out.println(deleteStudent(restTemplate, baseUri, 2L));

//        System.out.println(getAllStudentsWithGrades(restTemplate, baseUri));

//        System.out.println(createGrade(restTemplate, baseUri, GradeRequest.builder()
//                .grade(100)
//                .topic(Topic.MATH)
//                .studentId(3L)
//                .build()));

//        System.out.println(getAllGrades(restTemplate, baseUri));

//        System.out.println(getAllGradesByStudentId(restTemplate, baseUri, 3L));

//        System.out.println(getAverageGradeByStudentId(restTemplate, baseUri, 3L));

//        System.out.println(getAllStudentsByName(restTemplate, baseUri, "avi"));



    }


    private static StudentResponse createStudent(RestTemplate restTemplate, URI baseUri, StudentRequest studentRequest) {
        return restTemplate.postForObject(baseUri + "/post-student", studentRequest, StudentResponse.class);
    }

    private static StudentResponse getStudent(RestTemplate restTemplate, URI baseUri, Long id) {
        return restTemplate.getForObject(baseUri + "/get-student/" + id, StudentResponse.class);
    }

    private static StudentResponse updateStudent(RestTemplate restTemplate, URI baseUri, Long id, StudentRequest studentRequest) {
        restTemplate.put(baseUri + "/update-student/" + id, studentRequest);
        return getStudent(restTemplate, baseUri, id);
    }

    private static boolean deleteStudent(RestTemplate restTemplate, URI baseUri, Long id) {
        try {
            restTemplate.delete(baseUri + "/delete-student/" + id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static List<StudentResponse> getAllStudents(RestTemplate restTemplate, URI baseUri) {
        return Arrays.stream
                (Objects.requireNonNull(restTemplate.getForObject
                        (baseUri + "/get-all-students", StudentResponse[].class))).toList();
    }

    private static List<StudentResponse> getAllStudentsByName(RestTemplate restTemplate, URI baseUri, String name) {
        return Arrays.stream
                (Objects.requireNonNull(restTemplate.getForObject
                        (baseUri + "/get-all-students-by-name/" + name, StudentResponse[].class))).toList();
    }

    private static List<StudentGradesResponse> getAllStudentsWithGrades(RestTemplate restTemplate, URI baseUri) {
        return Arrays.stream
                (Objects.requireNonNull(restTemplate.getForObject
                        (baseUri + "/get-all-students-with-grades", StudentGradesResponse[].class))).toList();
    }

    private static GradeResponse createGrade(RestTemplate restTemplate, URI baseUri, GradeRequest gradeRequest) {
        return restTemplate.postForObject(baseUri + "/post-grade", gradeRequest, GradeResponse.class);
    }

    private static GradeResponse getGrade(RestTemplate restTemplate, URI baseUri, Long id) {
        return restTemplate.getForObject(baseUri + "/get-grade/" + id, GradeResponse.class);
    }

    private static GradeResponse updateGrade(RestTemplate restTemplate, URI baseUri, Long id, GradeRequest gradeRequest) {
        restTemplate.put(baseUri + "/update-grade/" + id, gradeRequest);
        return getGrade(restTemplate, baseUri, id);
    }

    private static boolean deleteGrade(RestTemplate restTemplate, URI baseUri, Long id) {
        try {
            restTemplate.delete(baseUri + "/delete-grade/" + id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static List<GradeResponse> getAllGrades(RestTemplate restTemplate, URI baseUri) {
        return Arrays.stream
                (Objects.requireNonNull(restTemplate.getForObject
                        (baseUri + "/get-all-grades", GradeResponse[].class))).toList();
    }

    private static List<GradeResponse> getAllGradesByStudentId(RestTemplate restTemplate, URI baseUri, Long studentId) {
        return Arrays.stream
                (Objects.requireNonNull(restTemplate.getForObject
                        (baseUri + "/get-all-grades-by-student-id/" + studentId, GradeResponse[].class))).toList();
    }

    private static Double getAverageGradeByStudentId(RestTemplate restTemplate, URI baseUri, Long studentId) {
        return restTemplate.getForObject(baseUri + "/get-student-average/" + studentId, Double.class);
    }
}
