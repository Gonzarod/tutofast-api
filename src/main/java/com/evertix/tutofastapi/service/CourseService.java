package com.evertix.tutofastapi.service;

import com.evertix.tutofastapi.model.dto.CourseDetail;
import com.evertix.tutofastapi.model.dto.UserDetail;

public interface CourseService {
    CourseDetail findCourseById(Long id);
}
