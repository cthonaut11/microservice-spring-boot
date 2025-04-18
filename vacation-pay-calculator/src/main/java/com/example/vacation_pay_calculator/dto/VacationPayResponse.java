package com.example.vacation_pay_calculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Data
public class VacationPayResponse{

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private double vacationPay;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String message;


    public VacationPayResponse(double vacationPay, String message) {
        this.vacationPay = vacationPay;
        this.message = message;
    }
}