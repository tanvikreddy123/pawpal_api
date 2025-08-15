package com.pawpal.backend.dto;

import com.pawpal.backend.enums.AdoptionStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PetDto {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private int age;
    private String gender;
    private String description;
    private byte[] returnedImg;
    private MultipartFile img;
    private AdoptionStatus adoptionStatus;
}