package com.fastporte.fastportewebservice.repository;

import com.fastporte.fastportewebservice.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDriverRepository extends JpaRepository<Driver, Long> {

    Driver findByEmailAndPassword(String email, String password);
}
