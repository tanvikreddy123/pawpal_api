package com.pawpal.backend.controller;

import com.pawpal.backend.dto.AdoptionRequestDto;
import com.pawpal.backend.dto.PetDto;
import com.pawpal.backend.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/pet")
    public ResponseEntity<PetDto> addPet(@ModelAttribute PetDto petDto) throws IOException {
        PetDto petDto1 = adminService.addPet(petDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(petDto1);
    }

    @GetMapping("/pets")
    public ResponseEntity<List<PetDto>> getAllPets() {
        List<PetDto> petDtos = adminService.getAllPets();
        return ResponseEntity.ok(petDtos);
    }

    @DeleteMapping("/pet/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {
        adminService.deletePet(petId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/pet/{petId}")
    public ResponseEntity<PetDto> getPetById(@PathVariable Long petId) {
        PetDto petDto = adminService.getPetById(petId);
        if (petDto != null) {
            return ResponseEntity.ok(petDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/pet/{petId}")
    public ResponseEntity<PetDto> updatePet(@PathVariable Long petId, @ModelAttribute PetDto petDto) throws IOException {
        PetDto updatedPet = adminService.updatePet(petId, petDto);
        if (updatedPet != null) {
            return ResponseEntity.ok(updatedPet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/adoption-requests")
    public ResponseEntity<List<AdoptionRequestDto>> getPlacedAdoptionRequests() {
        return ResponseEntity.ok(adminService.getPlacedAdoptionRequests());
    }

    @GetMapping("/adoption-request/{requestId}/{status}")
    public ResponseEntity<AdoptionRequestDto> changeRequestStatus(@PathVariable Long requestId, @PathVariable String status) {
        AdoptionRequestDto dto = adminService.changeRequestStatus(requestId, status);
        if (dto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dto);
    }
}