package com.evertix.tutofastapi.service.impl;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.controller.constants.ResponseConstants;
import com.evertix.tutofastapi.model.Course;
import com.evertix.tutofastapi.model.Tutorship;
import com.evertix.tutofastapi.model.User;
import com.evertix.tutofastapi.model.dto.CourseDetail;
import com.evertix.tutofastapi.model.dto.SaveTutorshipRequest;
import com.evertix.tutofastapi.model.dto.TutorshipRequest;
import com.evertix.tutofastapi.model.dto.UserDetail;
import com.evertix.tutofastapi.model.enums.EStatus;
import com.evertix.tutofastapi.repository.CourseRepository;
import com.evertix.tutofastapi.repository.TutorshipRepository;
import com.evertix.tutofastapi.repository.UserRepository;
import com.evertix.tutofastapi.service.CourseService;
import com.evertix.tutofastapi.service.TutorshipService;
import com.evertix.tutofastapi.service.UserService;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

@Service
public class TutorshipServiceImpl implements TutorshipService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    TutorshipRepository tutorshipRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;


    @Override
    public ResponseEntity<MessageResponse> createTutorshipRequest(SaveTutorshipRequest request, Long studentId, Long courseId) {

        try {
            //Validate if Student Exists
            User student = this.userRepository.findById(studentId).orElse(null);

            if(student == null){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(
                                MessageResponse.builder()
                                        .code(ResponseConstants.ERROR_CODE)
                                        .message("No existe usuario estudiante con ID: "+studentId)
                                        .build()
                        );
            }

            //Validate if Course Exists
            Course course = this.courseRepository.findById(courseId).orElse(null);

            if(course == null){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(
                                MessageResponse.builder()
                                        .code(ResponseConstants.ERROR_CODE)
                                        .message("No existe curso con ID: "+courseId)
                                        .build()
                        );
            }
            //Validation Completed
            Tutorship saveTutorship = this.convertToEntity(request);
            //Tutorship Request initial status is OPEN
            saveTutorship.setStatus(EStatus.OPEN);
            //Set Student and Course
            saveTutorship.setStudent(student);
            saveTutorship.setCourse(course);

            tutorshipRepository.save(saveTutorship);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            MessageResponse.builder()
                                    .code(ResponseConstants.SUCCESS_CODE)
                                    .message("Éxito al crear la solicitud")
                                    .data(this.convertToResource(saveTutorship))
                                    .build());


        }catch (Exception e){
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



    private Tutorship convertToEntity(SaveTutorshipRequest object){return mapper.map(object, Tutorship.class);}

    private TutorshipRequest convertToResource(Tutorship entity){
        TutorshipRequest resource = mapper.map(entity, TutorshipRequest.class);
        resource.setStudentName(entity.getStudent().getName()+" "+entity.getStudent().getLastName());
        //resource.setTeacherName(entity.getTeacher().getName()+" "+entity.getTeacher().getLastName()); //Null Exception
        resource.setStatus(entity.getStatus().toString());

        return resource;
    }

}
