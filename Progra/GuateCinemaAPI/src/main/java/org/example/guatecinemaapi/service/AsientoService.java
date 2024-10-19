package org.example.guatecinemaapi.service;

import org.example.guatecinemaapi.model.Asiento;
import org.example.guatecinemaapi.repository.AsientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsientoService {

    @Autowired
    private AsientoRepository asientoRepository;

    public Asiento saveAsiento(Asiento asiento) {
        return asientoRepository.save(asiento);
    }

    public List<Asiento> findAllAsientos() {
        return asientoRepository.findAll();
    }

    public void deleteAsiento(Integer id) {
        asientoRepository.deleteById(id);
    }
}
