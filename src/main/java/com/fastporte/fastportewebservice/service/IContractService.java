package com.fastporte.fastportewebservice.service;

import com.fastporte.fastportewebservice.entities.Contract;

import java.util.List;

public interface IContractService extends CrudService<Contract> {

    List<Contract> findByDriverId(Long driverId) throws Exception;
    List<Contract> findByClientId(Long clientId) throws Exception;
    List<Contract> findByDriverIdAndClientId(Long driverId, Long clientId) throws Exception;
}
