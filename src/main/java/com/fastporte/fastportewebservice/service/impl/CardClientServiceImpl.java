package com.fastporte.fastportewebservice.service.impl;

import com.fastporte.fastportewebservice.entities.CardClient;
import com.fastporte.fastportewebservice.repository.ICardClientRepository;
import com.fastporte.fastportewebservice.service.ICardClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CardClientServiceImpl implements ICardClientService {
    private ICardClientRepository cardClientRepository;

    public CardClientServiceImpl(ICardClientRepository cardClientRepository) {
        this.cardClientRepository = cardClientRepository;
    }

    @Override
    @Transactional
    public CardClient save(CardClient entity) throws Exception {
        return cardClientRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        cardClientRepository.deleteById(id);
    }

    @Override
    public List<CardClient> getAll() throws Exception {
        return cardClientRepository.findAll();
    }

    @Override
    public Optional<CardClient> getById(Long id) throws Exception {
        return cardClientRepository.findById(id);
    }

}
