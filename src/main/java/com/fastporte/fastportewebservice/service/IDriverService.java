package com.fastporte.fastportewebservice.service;

import com.fastporte.fastportewebservice.entities.Driver;

public interface IDriverService extends CrudService<Driver> {

    Driver findByEmailAndPassword(String email, String password);
}
