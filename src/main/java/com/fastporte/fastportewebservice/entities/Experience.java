package com.fastporte.fastportewebservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;


@Entity
@Table(name = "experience")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Experience implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //@MapsId
    @JsonIgnore
    private Driver driver;

    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "years", nullable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    private int years;
}
