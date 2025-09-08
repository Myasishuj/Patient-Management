package com.pm.patientservice.mapper;


import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;


public class PatientMapper {
    public static PatientResponseDto toDto(Patient patient){
        PatientResponseDto patientDto = new PatientResponseDto();
        patientDto.setId(String.valueOf(patient.getId()));
        patientDto.setName(patient.getName());
        patientDto.setEmail(patient.getEmail());
        patientDto.setBirthDate(String.valueOf(patient.getBirthDate()));
        patientDto.setAddress(patient.getAddress());
        return patientDto;
    }

    public static Patient toModel(PatientRequestDto patient){
        Patient newPatient = new Patient();
        newPatient.setName(patient.getName());
        newPatient.setEmail(patient.getEmail());
        newPatient.setBirthDate(LocalDate.parse(patient.getBirthDate()));
        newPatient.setAddress(patient.getAddress());
        newPatient.setRegisterDate(LocalDate.parse(patient.getRegisterDate()));

        return newPatient;
    }
}
