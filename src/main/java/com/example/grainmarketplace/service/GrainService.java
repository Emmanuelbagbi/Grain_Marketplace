package com.example.grainmarketplace.service;


import com.example.grainmarketplace.models.Grain;
import java.util.List;

public interface GrainService {

    Grain saveGrain(Grain grain);

    Grain findById(Long id);

    List<Grain> findAll();

    Grain updateGrain(Long id, Grain grain);

    boolean deleteGrain(Long id);
}
