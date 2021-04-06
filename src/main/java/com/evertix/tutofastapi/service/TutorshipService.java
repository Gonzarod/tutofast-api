package com.evertix.tutofastapi.service;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.model.Tutorship;
import com.evertix.tutofastapi.model.dto.SaveTutorshipRequest;
import com.evertix.tutofastapi.model.dto.TutorshipRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


public interface TutorshipService {
    ResponseEntity<MessageResponse> createTutorshipRequest(SaveTutorshipRequest request, Long studentId, Long courseId);

    ResponseEntity<MessageResponse> getAllTutorshipRequestFiltered(Long courseId);
}
