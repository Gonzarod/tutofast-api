package com.evertix.tutofastapi.controller;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.model.dto.SaveTutorshipDetailRequest;
import com.evertix.tutofastapi.service.TutorshipDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@Tag(name = "TutorshipDetail", description = "API is Ready")
@RequestMapping("api/tutorshipDetail")
@RestController
public class TutorshipDetailController {

    @Autowired
    TutorshipDetailService tutorshipDetailService;

    @PostMapping("/teacher/{teacherId}/tutorship/{tutorshipId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create TutorshipDetail Request", description = "Create TutorshipDetail Request",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"TutorshipDetail"})
    public ResponseEntity<MessageResponse> create(@RequestBody @Validated SaveTutorshipDetailRequest tutorship,
                                                  @PathVariable Long teacherId,
                                                  @PathVariable Long tutorshipId) {
        return this.tutorshipDetailService.createTutorshipDetailRequest(tutorship,teacherId,tutorshipId);

    }

    @GetMapping("/search")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search TutorshipDetail Request", description = "Search TutorshipDetail Request",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"TutorshipDetail"})
    public ResponseEntity<MessageResponse> searchAllTutorshipDetailRequest(@RequestParam(required = false) @Parameter(description = "is Optional") Long tutorshipId) {
        return this.tutorshipDetailService.searchAllTutorshipDetailRequest(tutorshipId);

    }

    @GetMapping("/search/paged")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search TutorshipDetail Request with Pagination", description = "Search TutorshipDetail Request with Pagination",
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
            },security = @SecurityRequirement(name = "bearerAuth"),tags = {"TutorshipDetail"})
    public ResponseEntity<MessageResponse> searchAllTutorshipDetailRequestPaged(@PageableDefault @Parameter(hidden = true) Pageable pageable,
                                                                                @RequestParam(required = false) @Parameter(description = "is Optional") Long tutorshipId) {
        return this.tutorshipDetailService.searchAllTutorshipDetailRequestPaged(tutorshipId, pageable);
    }
}
