package com.example.vacation_pay_calculator.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Set;

@Service
public class VacationPayService {

    private final int YEAR = Calendar.getInstance().get(Calendar.YEAR);

    private final Set<LocalDate> HOLIDAYS = Set.of(

            LocalDate.of(YEAR, 1, 1), LocalDate.of(YEAR, 1, 2), LocalDate.of(YEAR, 1, 3),
            LocalDate.of(YEAR, 1, 4), LocalDate.of(YEAR, 1, 5), LocalDate.of(YEAR, 1, 6),
            LocalDate.of(YEAR, 1, 7), LocalDate.of(YEAR, 1, 8),

            LocalDate.of(YEAR, 2, 23), LocalDate.of(YEAR, 3, 8),
            LocalDate.of(YEAR, 5, 1), LocalDate.of(YEAR, 5, 9),
            LocalDate.of(YEAR, 6, 12), LocalDate.of(YEAR, 11, 4)
    );
    public final double AVERAGE_DAYS_IN_MONTH = 29.3;

    public double calculateVacationPay(int vacationDays, double averageSalary){
        if(vacationDays <= 0 || averageSalary <= 0){
            throw new IllegalArgumentException("Количество дней и зарплата должны быть положительными");
        }

        double averageDailyEarnings = calculateAverageDailyEarnings(averageSalary);
        return vacationDays * averageDailyEarnings;
    }

    public double calculateExactVacationPay(double averageSalary, LocalDate startDate, LocalDate endDate){
        if(averageSalary <= 0){
            throw new IllegalArgumentException("Зарплата должна быть положительной");
        }

        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("Некорректное окно отпуска");
        }

        double averageDailyEarnings = calculateAverageDailyEarnings(averageSalary);
        int vacationDays = countDays(startDate, endDate);
        return vacationDays * averageDailyEarnings;
    }

    private int countDays(LocalDate startDate, LocalDate endDate){
        int days = 0;
        LocalDate date = startDate;

        while(!date.isAfter(endDate)){
            if(!isHoliday(date)){
                days++;
            }
            date = date.plusDays(1);
        }
        return days;
    }

    private double calculateAverageDailyEarnings(double averageSalary) {
        return averageSalary / AVERAGE_DAYS_IN_MONTH;
    }

    private boolean isHoliday(LocalDate date) {
        return HOLIDAYS.contains(date);
    }
}
