package com.pawpal.backend.dto;

import com.pawpal.backend.enums.RequestStatus;
import lombok.Data;
import java.util.Date;

@Data
public class AdoptionRequestDto {
    private Long id;
    private Long userId;
    private Long petId;
    private String petName;
    private String userName;
    private Date requestDate;
    private RequestStatus requestStatus;
}