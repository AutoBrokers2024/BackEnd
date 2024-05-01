package com.fastporte.fastportewebservice.service.impl;

import com.fastporte.fastportewebservice.entities.Driver;
import com.fastporte.fastportewebservice.repository.IDriverRepository;
import com.fastporte.fastportewebservice.service.IDriverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // Solo servicios de lectura
public class DriverServiceImpl implements IDriverService {

    private final IDriverRepository driverRepository;

    public DriverServiceImpl(IDriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    @Transactional
    public Driver save(Driver driver) throws Exception {
        return driverRepository.save(driver);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        driverRepository.deleteById(id);
    }

    @Override
    public List<Driver> getAll() throws Exception {
        return driverRepository.findAll();
    }

    @Override
    public Optional<Driver> getById(Long id) throws Exception {
        return driverRepository.findById(id);
    }

    @Override
    public Driver findByEmailAndPassword(String email, String password) {
        return driverRepository.findByEmailAndPassword(email, password);
    }
}
