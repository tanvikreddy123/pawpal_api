package com.pawpal.backend.service.admin;

import com.pawpal.backend.dto.AdoptionRequestDto;
import com.pawpal.backend.dto.PetDto;
import com.pawpal.backend.entity.AdoptionRequest;
import com.pawpal.backend.entity.Pet;
import com.pawpal.backend.enums.AdoptionStatus;
import com.pawpal.backend.enums.RequestStatus;
import com.pawpal.backend.repository.AdoptionRequestRepository;
import com.pawpal.backend.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final PetRepository petRepository;
    private final AdoptionRequestRepository adoptionRequestRepository;

    @Override
    public PetDto addPet(PetDto petDto) throws IOException {
        Pet pet = new Pet();
        pet.setName(petDto.getName());
        pet.setDescription(petDto.getDescription());
        pet.setSpecies(petDto.getSpecies());
        pet.setBreed(petDto.getBreed());
        pet.setGender(petDto.getGender());
        pet.setAge(petDto.getAge());
        pet.setImg(petDto.getImg().getBytes());
        pet.setAdoptionStatus(AdoptionStatus.AVAILABLE);
        return petRepository.save(pet).getDto();
    }

    @Override
    public List<PetDto> getAllPets() {
        return petRepository.findAll().stream().map(Pet::getDto).collect(Collectors.toList());
    }


    @Override
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public PetDto getPetById(Long petId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        return optionalPet.map(Pet::getDto).orElse(null);
    }

    @Override
    public PetDto updatePet(Long petId, PetDto petDto) throws IOException {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            pet.setName(petDto.getName());
            pet.setDescription(petDto.getDescription());
            pet.setSpecies(petDto.getSpecies());
            pet.setBreed(petDto.getBreed());
            pet.setGender(petDto.getGender());
            pet.setAge(petDto.getAge());
            if (petDto.getImg() != null) {
                pet.setImg(petDto.getImg().getBytes());
            }
            return petRepository.save(pet).getDto();
        } else {
            return null;
        }
    }

    @Override
    public List<AdoptionRequestDto> getPlacedAdoptionRequests() {
        return adoptionRequestRepository.findAll().stream().map(request -> {
            AdoptionRequestDto dto = new AdoptionRequestDto();
            dto.setId(request.getId());
            dto.setPetName(request.getPet().getName());
            dto.setUserName(request.getUser().getName());
            dto.setRequestDate(request.getRequestDate());
            dto.setRequestStatus(request.getRequestStatus());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public AdoptionRequestDto changeRequestStatus(Long requestId, String status) {
        Optional<AdoptionRequest> optionalRequest = adoptionRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            AdoptionRequest request = optionalRequest.get();
            if (Objects.equals(status, "Approve")) {
                request.setRequestStatus(RequestStatus.APPROVED);
                // Also update pet status
                Pet pet = request.getPet();
                pet.setAdoptionStatus(AdoptionStatus.ADOPTED);
                petRepository.save(pet);
            } else {
                request.setRequestStatus(RequestStatus.REJECTED);
                // Make pet available again if rejected
                Pet pet = request.getPet();
                pet.setAdoptionStatus(AdoptionStatus.AVAILABLE);
                petRepository.save(pet);
            }
            AdoptionRequest updatedRequest = adoptionRequestRepository.save(request);
            AdoptionRequestDto dto = new AdoptionRequestDto();
            dto.setId(updatedRequest.getId());
            return dto;
        }
        return null;
    }
}