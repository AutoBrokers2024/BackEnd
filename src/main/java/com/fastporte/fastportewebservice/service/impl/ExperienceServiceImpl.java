package com.fastporte.fastportewebservice.service.impl;

import com.fastporte.fastportewebservice.entities.Experience;
import com.fastporte.fastportewebservice.repository.IExperienceRepository;
import com.fastporte.fastportewebservice.service.IExperienceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ExperienceServiceImpl implements IExperienceService {

    private final IExperienceRepository experienceRepository;
    public ExperienceServiceImpl(IExperienceRepository experienceRepository){this.experienceRepository = experienceRepository;}


    @Override
    @Transactional
    public Experience save(Experience experience) throws Exception {
        return experienceRepository.save(experience);
    }
    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        experienceRepository.deleteById(id);
    }
    @Override
    public List<Experience> findByDriverId(Long driverId) throws Exception {
        return experienceRepository.findByDriverId(driverId);
    }
    @Override
    public List<Experience> getAll() throws Exception {
        return experienceRepository.findAll();
    }
    @Override
    public Optional<Experience> getById(Long id) throws Exception {
        return experienceRepository.findById(id);
    }
}
