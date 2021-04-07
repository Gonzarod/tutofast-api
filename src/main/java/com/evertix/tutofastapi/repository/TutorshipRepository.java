package com.evertix.tutofastapi.repository;

import com.evertix.tutofastapi.model.Tutorship;
import com.evertix.tutofastapi.model.enums.EStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TutorshipRepository extends JpaRepository<Tutorship,Long > {
    List<Tutorship> getAllByStatusEquals(EStatus status);
    List<Tutorship> getAllByStatusEqualsAndCourseId(EStatus status,Long courseId);
    List<Tutorship> getAllByStatusEqualsAndStartAtBetween(EStatus status,LocalDateTime date1,LocalDateTime date2);
    List<Tutorship> getAllByStatusEqualsAndCourseIdAndStartAtBetween(EStatus status, Long courseId,LocalDateTime date1,LocalDateTime date2);

    Page<Tutorship> getAllByStatusEquals(EStatus status,Pageable pageable);
    Page<Tutorship> getAllByStatusEqualsAndCourseId(EStatus status,Long courseId,Pageable pageable);
    Page<Tutorship> getAllByStatusEqualsAndStartAtBetween(EStatus status,LocalDateTime date1,LocalDateTime date2,Pageable pageable);
    Page<Tutorship> getAllByStatusEqualsAndCourseIdAndStartAtBetween(EStatus status, Long courseId,LocalDateTime date1,LocalDateTime date2,Pageable pageable);

    List<Tutorship> getAllByStudentIdAndStatusEquals(Long studentId, EStatus status);


}
