package com.evertix.tutofastapi.model.dto;

import com.evertix.tutofastapi.model.Course;
import com.evertix.tutofastapi.model.User;
import com.evertix.tutofastapi.model.enums.EStatus;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class SaveTutorshipRequest {

    @NotNull(message = "Topic cannot be null")
    @NotBlank(message = "Topic cannot be blank")
    @Size(max = 150)
    private String topic;

    @Column(nullable = false, updatable = false)
    private LocalDateTime start_at;

    @Column(nullable = false, updatable = false)
    private LocalDateTime end_at;

}
