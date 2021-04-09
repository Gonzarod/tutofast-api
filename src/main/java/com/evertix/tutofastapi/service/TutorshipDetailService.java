package com.evertix.tutofastapi.service;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.model.dto.SaveTutorshipDetailRequest;
import com.evertix.tutofastapi.model.dto.SaveTutorshipRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface TutorshipDetailService {
    ResponseEntity<MessageResponse> createTutorshipDetailRequest(SaveTutorshipDetailRequest request, Long teacherId, Long tutorshipId);

    ResponseEntity<MessageResponse> searchAllTutorshipDetailRequest(Long tutorshipId);

    ResponseEntity<MessageResponse> searchAllTutorshipDetailRequestPaged(Long tutorshipId, Pageable pageable);
}
