package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.Complemento;
import org.example.guatecinemaapi.repository.ComplementoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplementoService {

    @Autowired
    private ComplementoRepository complementoRepository;

    public List<Complemento> findAll() {
        return complementoRepository.findAll();
    }

    public Complemento save(Complemento complemento) {
        return complementoRepository.save(complemento);
    }
}
