package com.evertix.tutofastapi.controller;

import com.evertix.tutofastapi.controller.commons.MessageResponse;
import com.evertix.tutofastapi.service.CourseService;
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

import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Tag(name = "Course", description = "API is Ready")
@RequestMapping("/api/course")
@RestController
public class CourseController  {

    @Autowired
    private CourseService courseService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Courses", description = "Get All Courses. Can be filter by name.", tags = {"Course"},security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<MessageResponse> getAllCourse(@RequestParam(required = false) @Parameter(description = "is Optional") String name){
        return this.courseService.getAllCourses(name);
    }


    @GetMapping("/paged")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get All Courses paginated by name", description = "Get All Courses paginated by name. Can filter by name (param optional)", tags = {"Course"},
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
            },security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<MessageResponse> getAllPaginated(@PageableDefault @Parameter(hidden = true) Pageable pageable, @RequestParam(required = false) @Parameter(description = "is Optional") String name ){
        return this.courseService.getAllCoursesPaginated(name,pageable);
    }
/*
    @GetMapping("/{id}")
    //@PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get Course by Id", description = "Get Course by Id",
            security = @SecurityRequirement(name = "bearerAuth"),tags = {"Course"})
    public ResponseEntity<MessageResponse> findById(@PathVariable Long id) {
        if (id <= 0) {
            String json = "id debe ser positivo";

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.ERROR_CODE)
                    .message(ResponseConstants.MSG_ERROR_CONS)
                    .data(json)
                    .build();

            return ResponseEntity.ok(response);

        }

        try {
            Course course = this.courseService.findById(Course.builder().id(id).build()).orElse(null);
            if (course == null) {
                return super.getNotContentResponseEntity();
            }

            MessageResponse response = MessageResponse
                    .builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(course)
                    .build();

            return ResponseEntity.ok(response);

        } catch (ServiceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


 */









}
