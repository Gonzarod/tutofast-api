package com.evertix.tutofastapi.service.impl;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.controller.constants.ResponseConstants;
import com.evertix.tutofastapi.model.Tutorship;
import com.evertix.tutofastapi.model.TutorshipDetail;
import com.evertix.tutofastapi.model.User;
import com.evertix.tutofastapi.model.dto.SaveTutorshipDetailRequest;
import com.evertix.tutofastapi.model.dto.SaveTutorshipRequest;
import com.evertix.tutofastapi.model.dto.TutorshipDetailRequest;
import com.evertix.tutofastapi.model.dto.TutorshipRequest;
import com.evertix.tutofastapi.repository.TutorshipDetailRepository;
import com.evertix.tutofastapi.repository.TutorshipRepository;
import com.evertix.tutofastapi.repository.UserRepository;
import com.evertix.tutofastapi.service.TutorshipDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Service
public class TutorshipDetailServiceImpl implements TutorshipDetailService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    TutorshipDetailRepository tutorshipDetailRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TutorshipRepository tutorshipRepository;

    @Override
    public ResponseEntity<MessageResponse> createTutorshipDetailRequest(SaveTutorshipDetailRequest request, Long teacherId, Long tutorshipId) {
        try {
            //Validate if Teacher Exists
            User teacher = this.userRepository.findById(teacherId).orElse(null);
            if (teacher == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("No existe usuario profesor con ID: "+teacherId)
                                .build()
                        );
            }
            //Validate if Course Exits
            Tutorship tutorship = this.tutorshipRepository.findById(tutorshipId).orElse(null);
            if (tutorship == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("No existe tutorship con ID: "+tutorshipId)
                                .build()
                        );
            }
            //Validate Completed
            TutorshipDetail saveTutorshipDetail = this.convertToEntity(request);
            //Set Teacher and Tutorship
            saveTutorshipDetail.setTeacher(teacher);
            saveTutorshipDetail.setTutorship(tutorship);
            tutorshipDetailRepository.save(saveTutorshipDetail);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.SUCCESS_CODE)
                            .message("Éxito al crear la solicitud")
                            .data(this.convertToResource(saveTutorshipDetail))
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
    public ResponseEntity<MessageResponse> searchAllTutorshipDetailRequest(Long tutorshipId) {
        try {
            //TODO: USE PREDICATES
            List<TutorshipDetail> tutorshipDetailList;
            tutorshipDetailList = tutorshipDetailRepository.getAllByTutorshipId(tutorshipId);
            if (tutorshipDetailList == null || tutorshipDetailList.isEmpty()) {
                return this.getNoTutorshipContentResponse();
            }
            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(tutorshipDetailList)
                    .build();

            return ResponseEntity.ok(response);
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
    public ResponseEntity<MessageResponse> searchAllTutorshipDetailRequestPaged(Long tutorshipId, Pageable pageable) {
        try {
            //TODO: USE PREDICATES
            Page<TutorshipDetail> tutorshipDetailPage;
            tutorshipDetailPage = tutorshipDetailRepository.getAllByTutorshipId(tutorshipId, pageable);
            if (tutorshipDetailPage == null || tutorshipDetailPage.isEmpty()) {
                return this.getNoTutorshipContentResponse();
            }
            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(tutorshipDetailPage)
                    .build();

            return ResponseEntity.ok(response);
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

    private TutorshipDetail convertToEntity(SaveTutorshipDetailRequest object){return mapper.map(object, TutorshipDetail.class);}

    private TutorshipDetailRequest convertToResource(TutorshipDetail entity){
        TutorshipDetailRequest resource = mapper.map(entity, TutorshipDetailRequest.class);
        resource.setTeacherName(entity.getTeacher().getName()+" "+entity.getTeacher().getLastName());
        resource.setTutorshipTopic(entity.getTutorship().getStatus().toString());
        return resource;
    }

    private ResponseEntity<MessageResponse> getNoTutorshipContentResponse(){
        return ResponseEntity.status(HttpStatus.OK).body(
                MessageResponse
                        .builder()
                        .code(ResponseConstants.WARNING_CODE)
                        .message(ResponseConstants.MSG_WARNING_CONS+" No hay solicitudes de tutorship-detail")
                        .data(null)
                        .build()
        );
    }
}
