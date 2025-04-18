package com.example.vacation_pay_calculator;

import com.example.vacation_pay_calculator.service.VacationPayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VacationPayServiceTest {

    private VacationPayService vacationPayService;
    private static final double TEST_SALARY = 100_000;
    private static final int TEST_DAYS = 14;
    private final double AVERAGE_DAYS_IN_MONTH = 29.3;

    @BeforeEach
    void setUp() {
        vacationPayService = new VacationPayService();
    }

    @Test
    void calculateVacationPay_ValidInput_CorrectResponse(){
        double expected = TEST_SALARY / AVERAGE_DAYS_IN_MONTH * TEST_DAYS;
        double actual = vacationPayService.calculateVacationPay(TEST_SALARY, TEST_DAYS);
        assertEquals(expected, actual, 0.01);
    }

    @Test
    void calculateExactVacationPay_ValidInput_CorrectResponse(){
        LocalDate startDate = LocalDate.of(2025, 4, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 14); //14 дней

        double expected = TEST_SALARY / AVERAGE_DAYS_IN_MONTH * 14;
        double actual = vacationPayService.calculateExactVacationPay(TEST_SALARY, startDate, endDate);

        assertEquals(expected, actual, 0.01);
    }
}
