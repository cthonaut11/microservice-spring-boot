package com.example.vacation_pay_calculator.controller;

import com.example.vacation_pay_calculator.dto.VacationPayResponse;
import com.example.vacation_pay_calculator.service.VacationPayService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<VacationPayResponse> calculateVacationPay(
            @RequestParam double averageSalary,
            @RequestParam(required = false) Integer vacationDays,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){


        try{

            double result;
            String message;

            if(vacationDays != null){
                result = vacationPayService.calculateVacationPay(averageSalary, vacationDays);
                message = "Расчёт по количеству дней";
            } else if (startDate != null && endDate != null) {
                result = vacationPayService.calculateExactVacationPay(averageSalary, startDate, endDate);
                message = "Расчёт по выбранным датам с учётом праздников";
            }else {
                throw new IllegalArgumentException("Укажите количество дней либо дату начала отпуска с датой окончания");
            }

            VacationPayResponse response = new VacationPayResponse(result, message);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e){
            VacationPayResponse errorResponse = new VacationPayResponse(0.0, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
