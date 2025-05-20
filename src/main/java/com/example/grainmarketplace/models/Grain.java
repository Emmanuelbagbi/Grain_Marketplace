package com.example.grainmarketplace.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Grain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private double quantity;  // for example, in kg

    // Constructors, getters, setters

    public Grain() {
    }

    public Grain(String name, String type, double quantity) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
