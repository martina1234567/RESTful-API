package com.example.car_managment.dto;

import java.time.Month;

public class MonthlyRequestsReportDto {
    private YearMonthDto yearMonth;
    private long requests;

    public MonthlyRequestsReportDto(YearMonthDto yearMonth, long requests) {
        this.yearMonth = yearMonth;
        this.requests = requests;
    }

    // Getters and setters
    public YearMonthDto getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonthDto yearMonth) {
        this.yearMonth = yearMonth;
    }

    public long getRequests() {
        return requests;
    }

    public void setRequests(long requests) {
        this.requests = requests;
    }
}



