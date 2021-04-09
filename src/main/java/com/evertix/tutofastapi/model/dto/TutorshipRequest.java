package com.evertix.tutofastapi.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TutorshipRequest {
    private String studentName;
    private String teacherName;
    private String courseName;
    private String topic;
    private String status;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
