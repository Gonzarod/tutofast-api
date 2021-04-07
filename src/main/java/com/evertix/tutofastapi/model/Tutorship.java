package com.evertix.tutofastapi.model;

import com.evertix.tutofastapi.model.enums.EStatus;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tutorship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tutorship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Topic cannot be null")
    @NotBlank(message = "Topic cannot be blank")
    @Size(max = 150)
    private String topic;

    @Column(nullable = false, updatable = false)
    private LocalDateTime startAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime endAt;

    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private User student;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "teacher_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private User teacher;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private Course course;

    public Tutorship(LocalDateTime startAt,LocalDateTime endAt,EStatus status,String topic,User student,Course course){
        this.startAt=startAt;
        this.endAt=endAt;
        this.status=status;
        this.topic=topic;
        this.student=student;
        this.course=course;
    }

}
