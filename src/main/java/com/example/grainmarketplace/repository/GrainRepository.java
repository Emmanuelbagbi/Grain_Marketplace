package com.example.grainmarketplace.repository;

import com.example.grainmarketplace.models.Grain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrainRepository extends JpaRepository<Grain, Long> {
}
