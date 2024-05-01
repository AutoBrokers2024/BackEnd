package com.fastporte.fastportewebservice.service;

import com.fastporte.fastportewebservice.entities.Comment;

import java.util.List;

public interface ICommentService extends CrudService<Comment> {

    List<Comment> findByDriverId(Long driverId) throws Exception;
    List<Comment> findByClientId(Long clientId) throws Exception;

}
