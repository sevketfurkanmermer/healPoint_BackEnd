package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoDoctorsFav {
    private String doctorName;
    private String doctorSurname;
    private String branch;
    private String city;
    private double avgPoint;
    private String email;
}