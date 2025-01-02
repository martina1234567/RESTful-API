package com.example.car_managment.dto;

public class YearMonthDto {
    private int year;
    private String month;
    private boolean leapYear;
    private int monthValue;

    public YearMonthDto(int year, String month, boolean leapYear, int monthValue) {
        this.year = year;
        this.month = month;
        this.leapYear = leapYear;
        this.monthValue = monthValue;
    }

    // Getters and setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public boolean isLeapYear() {
        return leapYear;
    }

    public void setLeapYear(boolean leapYear) {
        this.leapYear = leapYear;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(int monthValue) {
        this.monthValue = monthValue;
    }
}
