package com.evertix.tutofastapi.service.impl;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.controller.constants.ResponseConstants;
import com.evertix.tutofastapi.model.Course;
import com.evertix.tutofastapi.model.Tutorship;
import com.evertix.tutofastapi.model.User;
import com.evertix.tutofastapi.model.dto.SaveTutorshipRequest;
import com.evertix.tutofastapi.model.dto.TutorshipRequest;
import com.evertix.tutofastapi.model.enums.EStatus;
import com.evertix.tutofastapi.repository.CourseRepository;
import com.evertix.tutofastapi.repository.TutorshipRepository;
import com.evertix.tutofastapi.repository.UserRepository;
import com.evertix.tutofastapi.service.TutorshipService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public ResponseEntity<MessageResponse> selectTeacherTutorship(Long id, Long teacherId) {
        try {
            //Validate if Tutorship Exists
            Tutorship tutorship = this.tutorshipRepository.findById(id).orElse(null);
            if (tutorship == null){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("No existe tutorship con ID: "+id)
                                .build()
                        );
            }
            // Validate if Teacher Exists
            User teacher = this.userRepository.findById(teacherId).orElse(null);
            if (teacher == null){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("No existe usuario teacher con ID: "+teacherId)
                                .build()
                        );
            }
            //Validation Completed
            tutorship.setStatus(EStatus.CLOSED);
            tutorship.setTeacher(teacher);
            tutorship = tutorshipRepository.save(tutorship);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.SUCCESS_CODE)
                            .message("Éxito al completar Tutorship")
                            .data(this.convertToResource(tutorship))
                            .build());

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.ERROR_CODE)
                            .message("Error Interno: "+sw.toString())
                            .build()
                    );
        }
    }

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

    @Override
    public ResponseEntity<MessageResponse> searchAllTutorshipRequest(Long courseId, Date date) {


        try {
            LocalDateTime date1=null;
            LocalDateTime date2=null;
            if(date!=null){
                date1=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
                date2=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(23, 59, 59);

                System.out.println(date);
            }
            //TODO: USE PREDICATES
            List<Tutorship> tutorshipList;
            if (courseId==null && date==null){
                tutorshipList=this.tutorshipRepository.getAllByStatusEquals(EStatus.OPEN);
            }else {
                if (courseId!=null && date==null){
                    tutorshipList=this.tutorshipRepository.getAllByStatusEqualsAndCourseId(EStatus.OPEN,courseId);
                }else if(courseId==null){
                    tutorshipList=this.tutorshipRepository.getAllByStatusEqualsAndStartAtBetween(EStatus.OPEN,date1,date2);
                }else{
                    tutorshipList=this.tutorshipRepository.getAllByStatusEqualsAndCourseIdAndStartAtBetween(EStatus.OPEN,courseId,date1,date2);
                }
            }
            /*
            *else if (courseId!=null){
                    tutorshipList=this.tutorshipRepository.getAllByStatusEqualsAndCourseId(EStatus.OPEN,date);
            * */

            if(tutorshipList == null || tutorshipList.isEmpty()){
                return this.getNoTutorshipContentResponse();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(tutorshipList)
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
    public ResponseEntity<MessageResponse> searchAllTutorshipRequestPaged(Long studentId, Long courseId, Date date, Pageable pageable) {
        try {
            LocalDateTime date1 = null;
            LocalDateTime date2 = null;
            if(date!=null){
                date1=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
                date2=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(23, 59, 59);
                System.out.println(date);
            }
            //TODO: USE PREDICATES
            Page<Tutorship> tutorshipList;
            if (studentId==null && courseId==null && date==null){
                tutorshipList=this.tutorshipRepository.getAllByStatusEquals(EStatus.OPEN,pageable);
            }else {
                if (studentId != null) {
                    tutorshipList=this.tutorshipRepository.getAllByStudentId(studentId,pageable);
                }
                else if (courseId!=null && date==null){
                    tutorshipList=this.tutorshipRepository.getAllByStatusEqualsAndCourseId(EStatus.OPEN,courseId,pageable);
                }else if(courseId==null){
                    tutorshipList=this.tutorshipRepository.getAllByStatusEqualsAndStartAtBetween(EStatus.OPEN,date1,date2,pageable);
                }else{
                    tutorshipList=this.tutorshipRepository.getAllByStatusEqualsAndCourseIdAndStartAtBetween(EStatus.OPEN,courseId,date1,date2,pageable);
                }
            }
            /*
            *else if (courseId!=null){
                    tutorshipList=this.tutorshipRepository.getAllByStatusEqualsAndCourseId(EStatus.OPEN,date);
            * */

            if(tutorshipList == null || tutorshipList.isEmpty()){
                return this.getNoTutorshipContentResponse();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(tutorshipList)
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


    private Tutorship convertToEntity(SaveTutorshipRequest object){return mapper.map(object, Tutorship.class);}

    private TutorshipRequest convertToResource(Tutorship entity){
        TutorshipRequest resource = mapper.map(entity, TutorshipRequest.class);
        resource.setStudentName(entity.getStudent().getName()+" "+entity.getStudent().getLastName());
        //resource.setTeacherName(entity.getTeacher().getName()+" "+entity.getTeacher().getLastName()); //Null Exception
        resource.setStatus(entity.getStatus().toString());

        return resource;
    }


    private ResponseEntity<MessageResponse> getNoTutorshipContentResponse(){
        return ResponseEntity.status(HttpStatus.OK).body(
                MessageResponse
                        .builder()
                        .code(ResponseConstants.WARNING_CODE)
                        .message(ResponseConstants.MSG_WARNING_CONS+" No hay solicitudes de tutorias")
                        .data(null)
                        .build()
        );
    }

}
