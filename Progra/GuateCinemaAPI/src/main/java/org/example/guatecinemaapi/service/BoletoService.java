package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.Boleto;
import org.example.guatecinemaapi.repository.BoletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoletoService {

    @Autowired
    private BoletoRepository boletoRepository;

    public List<Boleto> findAll() {
        return boletoRepository.findAll();
    }

    public Boleto save(Boleto boleto) {
        return boletoRepository.save(boleto);
    }
}
