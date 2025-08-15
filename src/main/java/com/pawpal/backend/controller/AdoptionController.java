package com.pawpal.backend.controller;

import com.pawpal.backend.dto.AdoptionRequestDto;
import com.pawpal.backend.dto.PetDto;
import com.pawpal.backend.service.user.AdoptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService adoptionService;

    @GetMapping("/pets")
    public ResponseEntity<List<PetDto>> getAllAvailablePets() {
        return ResponseEntity.ok(adoptionService.getAllAvailablePets());
    }
    
    @GetMapping("/search/{name}")
    public ResponseEntity<List<PetDto>> searchPetByTitle(@PathVariable String name) {
        return ResponseEntity.ok(adoptionService.searchPetByTitle(name));
    }

    @PostMapping("/request-adoption")
    public ResponseEntity<?> requestAdoption(@RequestBody AdoptionRequestDto adoptionRequestDto) {
        AdoptionRequestDto placedRequest = adoptionService.requestAdoption(adoptionRequestDto);
        if (placedRequest != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(placedRequest);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to place request. Pet may not be available.");
    }

    @GetMapping("/my-requests/{userId}")
    public ResponseEntity<List<AdoptionRequestDto>> getMyAdoptionRequests(@PathVariable Long userId) {
        return ResponseEntity.ok(adoptionService.getMyAdoptionRequests(userId));
    }
}