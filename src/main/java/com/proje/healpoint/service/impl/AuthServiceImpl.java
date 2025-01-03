package com.proje.healpoint.service.impl;

import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.jwt.AuthRequest;
import com.proje.healpoint.jwt.AuthResponse;
import com.proje.healpoint.jwt.JwtService;
import com.proje.healpoint.model.Admin;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.model.Patients;
import com.proje.healpoint.repository.AdminRepository;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.repository.PatientRepository;
import com.proje.healpoint.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    AuthenticationManager authenticationManager;



    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            authenticationProvider.authenticate(auth);

            String token;
            if (authRequest.getUserType().equalsIgnoreCase("PATIENT")) {
                // Hasta için doğrulama
                Optional<Patients> optionalPatient = patientRepository.findById(authRequest.getUsername());
                if (optionalPatient.isEmpty()) {
                    throw new BaseException(
                            new ErrorMessage(MessageType.NO_RECORD_EXIST, "Hasta bulunamadı: " + authRequest.getUsername()));
                }
                token = jwtService.generateToken(authRequest.getUsername(), "PATIENT");

            } else if (authRequest.getUserType().equalsIgnoreCase("DOCTOR")) {
                // Doktor için doğrulama
                Optional<Doctors> optionalDoctor = doctorRepository.findById(authRequest.getUsername());
                if (optionalDoctor.isEmpty()) {
                    throw new BaseException(
                            new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı: " + authRequest.getUsername()));
                }
                token = jwtService.generateToken(authRequest.getUsername(), "DOCTOR");

            } else if (authRequest.getUserType().equalsIgnoreCase("ADMIN")) {
                // Admin için doğrulama
                Optional<Admin> optionalAdmin = adminRepository.findByUsername(authRequest.getUsername());
                if (optionalAdmin.isEmpty()) {
                    throw new BaseException(
                            new ErrorMessage(MessageType.NO_RECORD_EXIST, "Admin bulunamadı: " + authRequest.getUsername()));
                }
                token = jwtService.generateToken(authRequest.getUsername(), "ADMIN");

            } else {
                throw new BaseException(
                        new ErrorMessage(MessageType.INVALID_USER_TYPE, authRequest.getUserType()));
            }

            return new AuthResponse(token);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(
                    new ErrorMessage(MessageType.AUTHENTICATION_FAILED, authRequest.getUsername()));
        }
    }
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );


            Patients patient = patientRepository.findById(username)
                    .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Hasta bulunamadı")));

            return jwtService.generateToken(patient.getTc(), "PATIENT");
        } catch (Exception ex) {
            throw new BaseException(new ErrorMessage(MessageType.AUTHENTICATION_FAILED, "Geçersiz TC veya şifre"));
        }
    }
    @Override
    public String loginAsDoctor(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            Doctors doctor = doctorRepository.findById(username)
                    .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));

            return jwtService.generateToken(doctor.getTc(), "DOCTOR");
        } catch (Exception ex) {
            throw new BaseException(new ErrorMessage(MessageType.AUTHENTICATION_FAILED, "Geçersiz TC veya şifre"));
        }
    }
}

