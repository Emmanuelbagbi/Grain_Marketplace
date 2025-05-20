package com.example.grainmarketplace.Controller;

import com.example.grainmarketplace.models.Grain;
import com.example.grainmarketplace.service.GrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grains")
public class GrainController {

    @Autowired
    private GrainService grainService;

    // Get all grains
    @GetMapping
    public ResponseEntity<List<Grain>> getAllGrains() {
        return ResponseEntity.ok(grainService.findAll());
    }

    // Get grain by id
    @GetMapping("/{id}")
    public ResponseEntity<Grain> getGrainById(@PathVariable Long id) {
        Grain grain = grainService.findById(id);
        if (grain != null) {
            return ResponseEntity.ok(grain);
        }
        return ResponseEntity.notFound().build();
    }

    // Create new grain
    @PostMapping
    public ResponseEntity<Grain> createGrain(@RequestBody Grain grain) {
        Grain created = grainService.saveGrain(grain);
        return ResponseEntity.ok(created);
    }

    // Update grain
    @PutMapping("/{id}")
    public ResponseEntity<Grain> updateGrain(@PathVariable Long id, @RequestBody Grain grain) {
        Grain updated = grainService.updateGrain(id, grain);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete grain
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrain(@PathVariable Long id) {
        boolean deleted = grainService.deleteGrain(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
