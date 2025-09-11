package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name="Patient",description = "API for managing patients")
@AllArgsConstructor
public class PatientController {
    private PatientService patientService;

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDto>> getAll(){
        List<PatientResponseDto> patients = patientService.getPatients();
        return ResponseEntity.ok(patients);
    }

    @PostMapping
    @Operation(summary = "Create Patients")
    public ResponseEntity<PatientResponseDto> createPatient(
            @Validated({Default.class, CreatePatientValidationGroup.class})
            @RequestBody PatientRequestDto patient){
        PatientResponseDto patientResponseDto = patientService.createPatient(patient);

        return ResponseEntity.ok(patientResponseDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Patients")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @Validated({Default.class}) @RequestBody PatientRequestDto patient
            ,@PathVariable UUID id){


        PatientResponseDto patientResponseDto = patientService.updatePatient(id, patient);
        return ResponseEntity.ok(patientResponseDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patients")
    public ResponseEntity<PatientResponseDto> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
