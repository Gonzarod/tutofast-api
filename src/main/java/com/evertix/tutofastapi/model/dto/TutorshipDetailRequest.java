package com.evertix.tutofastapi.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TutorshipDetailRequest {
    private String tutorshipTopic;
    private String teacherName;
}
