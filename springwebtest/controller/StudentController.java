package com.maorzehavi.springwebtest.controller;

import com.maorzehavi.springwebtest.model.dto.grade.GradeRequest;
import com.maorzehavi.springwebtest.model.dto.grade.GradeResponse;
import com.maorzehavi.springwebtest.model.dto.student.StudentGradesResponse;
import com.maorzehavi.springwebtest.model.dto.student.StudentRequest;
import com.maorzehavi.springwebtest.model.dto.student.StudentResponse;
import com.maorzehavi.springwebtest.service.GradeService;
import com.maorzehavi.springwebtest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    private final GradeService gradeService;

    @Autowired
    public StudentController(StudentService studentService,
                                GradeService gradeService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    @PostMapping("post-student")
    public StudentResponse postStudent(@RequestBody StudentRequest studentRequest) {
        return studentService.createStudent(studentRequest)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student could not be created"));
    }

    @GetMapping("get-student/{id}")
    public StudentResponse getStudent(@PathVariable Long id) {
        return studentService.getStudent(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    @PutMapping("update-student/{id}")
    public StudentResponse updateStudent(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student could not be updated"));
    }

    @DeleteMapping("delete-student/{id}")
    public StudentResponse deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student could not be deleted"));
    }

    @GetMapping("get-student-with-grades/{id}")
    public StudentGradesResponse getStudentWithGrades(@PathVariable Long id) {
        return studentService.getStudentWithGrades(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    @GetMapping("get-all-students")
    public Iterable<StudentResponse> getAllStudents() {
        return studentService.getAllStudents()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found"));
    }

    @GetMapping("get-all-students-by-name/{name}")
    public Iterable<StudentResponse> getAllStudentsByName(@PathVariable String name) {
        return studentService.getAllStudentsByName(name)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found"));
    }

    @GetMapping("get-all-students-with-grades")
    public Iterable<StudentGradesResponse> getAllStudentsWithGrades() {
        return studentService.getAllStudentsWithGrades()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found"));
    }

    @PostMapping("post-grade")
    public GradeResponse postGrade(@RequestBody GradeRequest gradeRequest) {
        return gradeService.createGrade(gradeRequest)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Grade could not be created"));
    }

    @GetMapping("get-grade/{id}")
public GradeResponse getGrade(@PathVariable Long id) {
        return gradeService.getGrade(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grade not found"));
    }

    @PutMapping("update-grade/{id}")
    public GradeResponse updateGrade(@PathVariable Long id, @RequestBody GradeRequest gradeRequest) {
        return gradeService.updateGrade(id, gradeRequest)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Grade could not be updated"));
    }

    @DeleteMapping("delete-grade/{id}")
    public GradeResponse deleteGrade(@PathVariable Long id) {
        return gradeService.deleteGrade(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Grade could not be deleted"));
    }

    @GetMapping("get-all-grades")
    public Iterable<GradeResponse> getAllGrades() {
        return gradeService.getAllGrades()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No grades found"));
    }

    @GetMapping("get-all-grades-by-student-id/{id}")
    public Iterable<GradeResponse> getAllGradesByStudentId(@PathVariable Long id) {
        return gradeService.getAllGradesByStudentId(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No grades found"));
    }

    @GetMapping("get-student-average/{id}")
    public Double getStudentAverage(@PathVariable Long id) {
        return gradeService.calculateAverageGrade(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No grades found"));
    }

}
