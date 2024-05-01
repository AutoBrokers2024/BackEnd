package com.fastporte.fastportewebservice.repository;

import com.fastporte.fastportewebservice.entities.CardDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardDriverRepository extends JpaRepository<CardDriver, Long> {

}
