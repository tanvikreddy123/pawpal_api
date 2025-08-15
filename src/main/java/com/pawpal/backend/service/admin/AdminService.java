package com.pawpal.backend.service.admin;

import com.pawpal.backend.dto.AdoptionRequestDto;
import com.pawpal.backend.dto.PetDto;
import java.io.IOException;
import java.util.List;

public interface AdminService {
    PetDto addPet(PetDto petDto) throws IOException;
    List<PetDto> getAllPets();
    void deletePet(Long id);
    PetDto getPetById(Long petId);
    PetDto updatePet(Long petId, PetDto petDto) throws IOException;
    List<AdoptionRequestDto> getPlacedAdoptionRequests();
    AdoptionRequestDto changeRequestStatus(Long requestId, String status);
}