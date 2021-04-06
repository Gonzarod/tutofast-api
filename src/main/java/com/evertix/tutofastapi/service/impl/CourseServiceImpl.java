package com.evertix.tutofastapi.service.impl;

import com.evertix.tutofastapi.model.Course;
import com.evertix.tutofastapi.model.User;
import com.evertix.tutofastapi.model.dto.CourseDetail;
import com.evertix.tutofastapi.model.dto.UserDetail;
import com.evertix.tutofastapi.repository.CourseRepository;
import com.evertix.tutofastapi.repository.UserRepository;
import com.evertix.tutofastapi.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    ModelMapper mapper;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public CourseDetail findCourseById(Long id) {
        return this.courseRepository.findById(id).map(this::convertToResource).orElse(null);
    }

    private CourseDetail convertToResource(Course entity){return mapper.map(entity, CourseDetail.class);}
}
