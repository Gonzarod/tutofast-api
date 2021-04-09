package com.evertix.tutofastapi.controller;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.model.dto.SaveTutorshipRequest;
import com.evertix.tutofastapi.service.TutorshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@Tag(name = "Tutorship", description = "API is Ready")
@RequestMapping("api/tutorship")
@RestController
public class TutorshipController {

    @Autowired
    TutorshipService tutorshipService;

    @PutMapping("/{id}/teacher/{teacherId}")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Set Teacher", description = "Set Teacher",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Tutorship"})
    public ResponseEntity<MessageResponse> setTeacher(@PathVariable Long id, @PathVariable Long teacherId) {
        return this.tutorshipService.selectTeacherTutorship(id, teacherId);
    }

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
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search Tutorship Request", description = "Search Tutorship Request",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Tutorship"})
    public ResponseEntity<MessageResponse> searchAllTutorshipRequest(@RequestParam(required = false) @Parameter(description = "is Optional") Long courseId,
                                                                     @RequestParam(required = false)  @DateTimeFormat(pattern="yyyy-MM-dd") @Parameter(description = "is Optional") Date date) {
        return this.tutorshipService.searchAllTutorshipRequest(courseId,date);

    }

    @GetMapping("/search/paged")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search Tutorship Request with Pagination", description = "Search Tutorship Request with Pagination",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Page you want to retrieve (0..N)"
                            , name = "page"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Number of records per page."
                            , name = "size"
                            , content = @Content(schema = @Schema(type = "integer", defaultValue = "20"))),
                    @Parameter(in = ParameterIn.QUERY
                            , description = "Sorting criteria in the format: property(,asc|desc). "
                            + "Default sort order is ascending. " + "Multiple sort criteria are supported."
                            , name = "sort"
                            , content = @Content(array = @ArraySchema(schema = @Schema(type = "string"))))
            },security = @SecurityRequirement(name = "bearerAuth"),tags = {"Tutorship"})
    public ResponseEntity<MessageResponse> searchAllTutorshipRequestPaged(@PageableDefault @Parameter(hidden = true) Pageable pageable,
                                                                          @RequestParam(required = false) @Parameter(description = "is Optional") Long studentId,
                                                                          @RequestParam(required = false) @Parameter(description = "is Optional") Long courseId,
                                                                          @RequestParam(required = false)  @DateTimeFormat(pattern="yyyy-MM-dd") @Parameter(description = "is Optional") Date date) {
        return this.tutorshipService.searchAllTutorshipRequestPaged(studentId, courseId, date, pageable);

    }

}
