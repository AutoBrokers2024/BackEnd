package com.fastporte.fastportewebservice.repository;

import com.fastporte.fastportewebservice.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    //Notification findByClient(Long id);

}
