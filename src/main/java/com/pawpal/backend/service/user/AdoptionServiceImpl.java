package com.pawpal.backend.service.user;

import com.pawpal.backend.dto.AdoptionRequestDto;
import com.pawpal.backend.dto.PetDto;
import com.pawpal.backend.entity.AdoptionRequest;
import com.pawpal.backend.entity.Pet;
import com.pawpal.backend.entity.User;
import com.pawpal.backend.enums.AdoptionStatus;
import com.pawpal.backend.enums.RequestStatus;
import com.pawpal.backend.repository.AdoptionRequestRepository;
import com.pawpal.backend.repository.PetRepository;
import com.pawpal.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdoptionServiceImpl implements AdoptionService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final AdoptionRequestRepository adoptionRequestRepository;

    @Override
    public List<PetDto> getAllAvailablePets() {
        return petRepository.findAll().stream()
                .filter(pet -> pet.getAdoptionStatus() == AdoptionStatus.AVAILABLE)
                .map(Pet::getDto).collect(Collectors.toList());
    }

    @Override
    public List<PetDto> searchPetByTitle(String name) {
        return petRepository.findAllByNameContaining(name).stream()
                .filter(pet -> pet.getAdoptionStatus() == AdoptionStatus.AVAILABLE)
                .map(Pet::getDto).collect(Collectors.toList());
    }

    @Override
    public AdoptionRequestDto requestAdoption(AdoptionRequestDto adoptionRequestDto) {
        Optional<Pet> optionalPet = petRepository.findById(adoptionRequestDto.getPetId());
        Optional<User> optionalUser = userRepository.findById(adoptionRequestDto.getUserId());

        if (optionalPet.isPresent() && optionalUser.isPresent()) {
            // Check if pet is available
            if (optionalPet.get().getAdoptionStatus() == AdoptionStatus.AVAILABLE) {
                Pet pet = optionalPet.get();
                pet.setAdoptionStatus(AdoptionStatus.PENDING); // Mark pet as pending
                petRepository.save(pet);

                AdoptionRequest adoptionRequest = new AdoptionRequest();
                adoptionRequest.setPet(pet);
                adoptionRequest.setUser(optionalUser.get());
                adoptionRequest.setRequestStatus(RequestStatus.PENDING);
                adoptionRequest.setRequestDate(new Date());

                AdoptionRequest savedRequest = adoptionRequestRepository.save(adoptionRequest);

                AdoptionRequestDto dto = new AdoptionRequestDto();
                dto.setId(savedRequest.getId());
                return dto;
            }
        }
        return null;
    }

    @Override
    public List<AdoptionRequestDto> getMyAdoptionRequests(Long userId) {
        return adoptionRequestRepository.findByUserId(userId).stream().map(request -> {
            AdoptionRequestDto dto = new AdoptionRequestDto();
            dto.setId(request.getId());
            dto.setPetId(request.getPet().getId());
            dto.setPetName(request.getPet().getName());
            dto.setRequestDate(request.getRequestDate());
            dto.setRequestStatus(request.getRequestStatus());
            return dto;
        }).collect(Collectors.toList());
    }
}