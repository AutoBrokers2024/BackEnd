package com.fastporte.fastportewebservice.repository;

import com.fastporte.fastportewebservice.entities.StatusContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusContractRepository extends JpaRepository<StatusContract, Long> {


}
