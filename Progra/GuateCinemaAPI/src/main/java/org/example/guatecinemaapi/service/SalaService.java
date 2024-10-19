package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.Sala;
import org.example.guatecinemaapi.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    public Sala save(Sala sala) {
        return salaRepository.save(sala);
    }
}
