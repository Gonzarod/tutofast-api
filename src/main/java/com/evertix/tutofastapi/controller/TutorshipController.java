package com.evertix.tutofastapi.controller;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.model.dto.SaveTutorshipRequest;
import com.evertix.tutofastapi.service.TutorshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin
@Tag(name = "Tutorship", description = "API is Ready")
@RequestMapping("api/tutorship")
@RestController
public class TutorshipController {

    @Autowired
    TutorshipService tutorshipService;

    @PostMapping("/student/{studentId}/course/{courseId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create Tutorship Request", description = "Create Tutorship Request",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Tutorship"})
    public ResponseEntity<MessageResponse> create(@RequestBody @Validated SaveTutorshipRequest tutorship,
                                                  @PathVariable Long studentId,
                                                  @PathVariable Long courseId) {
        return this.tutorshipService.createTutorshipRequest(tutorship,studentId,courseId);

    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search Tutorship Request", description = "Search Tutorship Request",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Tutorship"})
    public ResponseEntity<MessageResponse> getAllTutorshipRequestFiltered(@RequestParam(required = false) @Parameter(description = "is Optional") Long courseId) {
        return this.tutorshipService.getAllTutorshipRequestFiltered(courseId);

    }

}
