package com.example.vacation_pay_calculator.controller;

import com.example.vacation_pay_calculator.service.VacationPayService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class VacationPayController {

    private final VacationPayService vacationPayService;

    public VacationPayController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("/calculate")
    public double calculateVacationPay(
            @RequestParam double averageSalary,
            @RequestParam(required = false) Integer vacationDays,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        if(vacationDays != null){
            return vacationPayService.calculateVacationPay(averageSalary, vacationDays);
        } else if (startDate != null && endDate != null) {
            return vacationPayService.calculateExactVacationPay(averageSalary, startDate, endDate);
        }else {
            throw new IllegalArgumentException("Укажите количество дней либо дату начала отпуска с датой окончания");
        }
    }
}
