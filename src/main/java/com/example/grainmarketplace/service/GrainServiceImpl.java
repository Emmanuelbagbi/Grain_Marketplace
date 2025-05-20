package com.example.grainmarketplace.service;


import com.example.grainmarketplace.models.Grain;
import com.example.grainmarketplace.repository.GrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrainServiceImpl implements GrainService {

    private final GrainRepository grainRepository;

    public GrainServiceImpl(GrainRepository grainRepository) {
        this.grainRepository = grainRepository;
    }

    @Override
    public Grain saveGrain(Grain grain) {
        return grainRepository.save(grain);
    }

    @Override
    public Grain findById(Long id) {
        return grainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grain not found with id " + id));
    }

    @Override
    public List<Grain> findAll() {
        return grainRepository.findAll();
    }

    @Override
    public Grain updateGrain(Long id, Grain grain) {
        Grain existingGrain = findById(id);
        existingGrain.setName(grain.getName());
        existingGrain.setType(grain.getType());
        existingGrain.setQuantity(grain.getQuantity());
        return grainRepository.save(existingGrain);
    }

    @Override
    public boolean deleteGrain(Long id) {
        grainRepository.deleteById(id);
        return false;
    }
}
