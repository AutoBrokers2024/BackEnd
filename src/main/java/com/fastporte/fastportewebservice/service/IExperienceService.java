package com.fastporte.fastportewebservice.service;

import com.fastporte.fastportewebservice.entities.Experience;

import java.util.List;

public interface IExperienceService extends CrudService<Experience>{
    List<Experience> findByDriverId(Long driverId) throws Exception;
}
