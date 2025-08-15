package com.pawpal.backend.service.user;

import com.pawpal.backend.dto.AdoptionRequestDto;
import com.pawpal.backend.dto.PetDto;
import java.util.List;

public interface AdoptionService {
    List<PetDto> getAllAvailablePets();
    List<PetDto> searchPetByTitle(String name);
    AdoptionRequestDto requestAdoption(AdoptionRequestDto adoptionRequestDto);
    List<AdoptionRequestDto> getMyAdoptionRequests(Long userId);
}