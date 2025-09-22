package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
//import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
public class PatientService {
    private PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;

    public PatientService(PatientRepository patientRepository,
                          BillingServiceGrpcClient billingServiceGrpcClient) {

        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
    }

    public List<PatientResponseDto> getPatients(){
        List<Patient> patients =patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toDto).toList();
    }

    public PatientResponseDto  createPatient (PatientRequestDto patient){
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exists "+
                    patient.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patient));
        billingServiceGrpcClient.createBillingAccount(String.valueOf(newPatient.getId()),newPatient.getName(), newPatient.getEmail());
        return PatientMapper.toDto(newPatient);
    }

    public PatientResponseDto updatePatient (UUID id, PatientRequestDto patient){
        Patient updatePatient=patientRepository.findById(id)
                .orElseThrow(()->new PatientNotFoundException("Patient Not Found With ID" + id));

        if (patientRepository.existsByEmailAndIdNot(patient.getEmail(),id)) {
//            if (!patient.getEmail().equals(updatePatient.getEmail()))
                throw new EmailAlreadyExistsException("A patient with this email already exists "+
                        patient.getEmail());

        }

        updatePatient.setName(patient.getName());
        updatePatient.setEmail(patient.getEmail());
        updatePatient.setAddress(patient.getAddress());
        updatePatient.setBirthDate(LocalDate.parse(patient.getBirthDate()));

        patientRepository.save(updatePatient);

        return PatientMapper.toDto(updatePatient);


    }

    public void deletePatient (UUID id){
//        Patient patient = patientRepository.findById(id)
//                .orElseThrow(()->new PatientNotFoundException("Patient Not Found With ID" + id));

        patientRepository.deleteById(id);

    }
}
