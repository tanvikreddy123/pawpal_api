package com.pawpal.backend.entity;

import com.pawpal.backend.dto.PetDto;
import com.pawpal.backend.enums.AdoptionStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pets")
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String species;
    private String breed;
    private int age;
    private String gender;
    private String description;
    
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    private AdoptionStatus adoptionStatus;

    public PetDto getDto() {
        PetDto petDto = new PetDto();
        petDto.setId(id);
        petDto.setName(name);
        petDto.setSpecies(species);
        petDto.setBreed(breed);
        petDto.setAge(age);
        petDto.setGender(gender);
        petDto.setDescription(description);
        petDto.setReturnedImg(img);
        petDto.setAdoptionStatus(adoptionStatus);
        return petDto;
    }
}