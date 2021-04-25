package com.evertix.tutofastapi.service.impl;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.controller.constants.ResponseConstants;
import com.evertix.tutofastapi.model.Course;
import com.evertix.tutofastapi.model.dto.CourseDetail;
import com.evertix.tutofastapi.model.dto.SaveCourseRequest;
import com.evertix.tutofastapi.repository.CourseRepository;
import com.evertix.tutofastapi.service.CourseService;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CourseRepository courseRepository;
/*
    @Override
    public List<Course> getAll() throws ServiceException {
        return this.courseRepository.findAll();
    }

    @Override
    public Optional<Course> findById(Course course) throws ServiceException {

        return this.courseRepository.findById(course.getId());

    }

    @Override
    public Course insert(Course course) throws ServiceException {
        return this.courseRepository.save(course);

    }

    @Override
    public Course update(Course course) throws ServiceException {
        Course updatedCourse = this.findById(course).orElse(null);
        if (updatedCourse == null) {
            return null;
        }
        BeanUtils.copyProperties(course, updatedCourse);

        return this.courseRepository.save(updatedCourse);
    }

    @Override
    public Course delete(Course course) throws ServiceException {
        Course deletedCourse = this.findById(course).orElse(null);
        if (deletedCourse == null) {
            throw new ServiceException("Id no valido");
        }
        this.courseRepository.delete(deletedCourse);
        return course;
    }
    */

    @Override
    public ResponseEntity<MessageResponse> getAllCoursesPaginated(String name, Pageable pageable) {

        try {
            Page<Course> coursePage;
            if (name == null) {
                coursePage = this.courseRepository.findAll(pageable);
            } else {
                coursePage = this.courseRepository.findByNameContaining(name,pageable);
            }

            if(coursePage == null || coursePage.isEmpty()){
                return this.getNoCourseContent();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(coursePage)
                    .build();

            return ResponseEntity.ok(response);

        } catch (ServiceException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(

                            MessageResponse.builder()
                                    .code(ResponseConstants.ERROR_CODE)
                                    .message("Error Interno: "+sw.toString())
                                    .build()
                    );
        }

    }

    @Override
    public ResponseEntity<MessageResponse> getAllCourses(String name) {

        try {
            List<Course> courses;
            if (name==null){
                courses=this.courseRepository.findAll();
            }else{
                courses=this.courseRepository.findByNameContaining(name);
            }

            if(courses == null || courses.isEmpty()){
                return this.getNoCourseContent();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(courses)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(

                            MessageResponse.builder()
                                    .code(ResponseConstants.ERROR_CODE)
                                    .message("Error Interno: "+sw.toString())
                                    .build()
                    );
        }
    }

    @Override
    public ResponseEntity<MessageResponse> updateCourse(SaveCourseRequest course, Long courseId) {
        try {
            Course saveCourse = this.courseRepository.findById(courseId).orElse(null);
            if (course == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Don't exists course with ID: " + courseId)
                                .build());
            }
            saveCourse.setName(course.getName());
            saveCourse.setDescription(course.getDescription());
            saveCourse = courseRepository.save(saveCourse);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.SUCCESS_CODE)
                            .message("Successful update")
                            .data(this.convertToResource(saveCourse))
                            .build());
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.ERROR_CODE)
                            .message("Internal Error: " + sw.toString())
                            .build());
        }
    }

    @Override
    public ResponseEntity<MessageResponse> deleteCourse(Long courseId) {
        try {
            Course course = this.courseRepository.findById(courseId).orElse(null);
            if (course == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Don't exists course with ID: " + courseId)
                                .build());
            }
            courseRepository.delete(course);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.SUCCESS_CODE)
                            .message("Successful delete")
                            .data(this.convertToResource(course))
                            .build());
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.ERROR_CODE)
                            .message("Internal Error: " + sw.toString())
                            .build());
        }
    }

    private CourseDetail convertToResource(Course course) { return modelMapper.map(course, CourseDetail.class); }

    public ResponseEntity<MessageResponse> getNoCourseContent(){
        return ResponseEntity.status(HttpStatus.OK).body(
                MessageResponse
                        .builder()
                        .code(ResponseConstants.WARNING_CODE)
                        .message(ResponseConstants.MSG_WARNING_CONS+" No hay cursos")
                        .data(null)
                        .build()
        );
    }
}
