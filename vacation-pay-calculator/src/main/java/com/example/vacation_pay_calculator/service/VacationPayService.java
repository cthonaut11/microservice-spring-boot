package com.example.vacation_pay_calculator.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class VacationPayService {

    public final double AVERAGE_DAYS_IN_MONTH = 29.3;

    private double calculateAverageDailyEarnings(double averageSalary) {
        return averageSalary / AVERAGE_DAYS_IN_MONTH;
    }

    public double calculateVacationPay(double averageSalary, int vacationDays){
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

    private Set<LocalDate> getHolidays(int year) {
        Set<LocalDate> holidays = new HashSet<>();
        holidays.add(LocalDate.of(year, 1, 1));
        holidays.add(LocalDate.of(year, 1, 2));
        holidays.add(LocalDate.of(year, 1, 3));
        holidays.add(LocalDate.of(year, 1, 4));
        holidays.add(LocalDate.of(year, 1, 5));
        holidays.add(LocalDate.of(year, 1, 6));
        holidays.add(LocalDate.of(year, 1, 7));
        holidays.add(LocalDate.of(year, 1, 8));

        holidays.add(LocalDate.of(year, 2, 23));
        holidays.add(LocalDate.of(year, 3, 8));
        holidays.add(LocalDate.of(year, 5, 1));
        holidays.add(LocalDate.of(year, 5, 9));
        holidays.add(LocalDate.of(year, 6, 12));
        holidays.add(LocalDate.of(year, 11, 4));

        return holidays;
    }

    private boolean isHoliday(LocalDate date) {
        return getHolidays(date.getYear()).contains(date) ||
               (date.getYear() != date.plusYears(1).getYear() &&
                getHolidays(date.getYear() + 1).contains(date));
    }
}
