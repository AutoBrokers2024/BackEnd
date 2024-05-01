package com.fastporte.fastportewebservice.repository;

import com.fastporte.fastportewebservice.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByDriverId(Long driverId);

    List<Comment> findByClientId(Long clientId);

}