package com.fastporte.fastportewebservice.service;

import com.fastporte.fastportewebservice.entities.Client;

public interface IClientService extends CrudService<Client> {

    Client findByEmailAndPassword(String email, String password) throws Exception;
}
