package com.evertix.tutofastapi.service;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.model.Tutorship;
import com.evertix.tutofastapi.model.dto.SaveTutorshipRequest;
import com.evertix.tutofastapi.model.dto.TutorshipRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Date;


public interface TutorshipService {
    ResponseEntity<MessageResponse> createTutorshipRequest(SaveTutorshipRequest request, Long studentId, Long courseId);

    ResponseEntity<MessageResponse> searchAllTutorshipRequest(Long courseId, Date date);

    ResponseEntity<MessageResponse> searchAllTutorshipRequestPaged(Long studentId, Long courseId, Date date, Pageable pageable);
}
