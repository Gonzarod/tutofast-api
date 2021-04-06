package com.evertix.tutofastapi.repository;

import com.evertix.tutofastapi.model.Tutorship;
import com.evertix.tutofastapi.model.enums.EStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorshipRepository extends JpaRepository<Tutorship,Long > {

    List<Tutorship> getAllByStudentIdAndStatusEquals(Long studentId, EStatus status);
    List<Tutorship> getAllByStatusEquals(EStatus status);

    Page<Tutorship> getAllByStatusEquals(EStatus status, Pageable pageable);

}
