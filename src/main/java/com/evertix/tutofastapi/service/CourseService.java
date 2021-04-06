package com.evertix.tutofastapi.service;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {
    ResponseEntity<MessageResponse> getAllCoursesPaginated(String name, Pageable pageable) ;   //Course name is optional
    ResponseEntity<MessageResponse> getAllCourses(String name);   //Course name is optional


}
