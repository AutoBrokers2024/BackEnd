package com.fastporte.fastportewebservice.repository;

import com.fastporte.fastportewebservice.entities.CardClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardClientRepository extends JpaRepository<CardClient, Long> {

}

