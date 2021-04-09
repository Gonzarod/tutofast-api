package com.evertix.tutofastapi.repository;

import com.evertix.tutofastapi.model.TutorshipDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorshipDetailRepository extends JpaRepository<TutorshipDetail, Long> {
    List<TutorshipDetail> getAllByTutorshipId(Long tutorshipId);
    Page<TutorshipDetail> getAllByTutorshipId(Long tutorshipId, Pageable pageable);
}
