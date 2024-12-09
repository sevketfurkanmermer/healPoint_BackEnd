package com.proje.healpoint.controller.impl;


import com.proje.healpoint.controller.IPatientController;
import com.proje.healpoint.dto.DtoPatient;
import com.proje.healpoint.dto.DtoPatientIU;
import com.proje.healpoint.jwt.JwtService;
import com.proje.healpoint.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/patients")
public class PatientControllerImpl implements IPatientController {
    @Autowired
    private IPatientService patientService;
    @Autowired
    private JwtService jwtService;

    @PostMapping(path = "/create")
    @Override
    public ResponseEntity<String> createPatient(@RequestBody DtoPatientIU dtoPatientIU) {
        String response = patientService.createPatient(dtoPatientIU);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path = "/update")
    @Override
    public ResponseEntity<String> updatePatient(@RequestBody DtoPatientIU dtoPatientIU) {
        String Patient_tc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String response = patientService.updatePatient(dtoPatientIU);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<DtoPatient> getPatient() {

        String tc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DtoPatient dtoPatient = patientService.getPatientById(tc);
        return ResponseEntity.ok(dtoPatient);
    }
}
