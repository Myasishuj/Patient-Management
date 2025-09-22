package com.pm.patientservice.dto;

import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.spi.ManagedEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDto {
    @NotBlank
    @Size(max=100,message="name cannot exceed 100 chars")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email should be valid")
    private String email;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "birth date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String birthDate;


    @NotBlank(groups = CreatePatientValidationGroup.class
            ,message = "registered date is required")
    private String registerDate;
}
