package com.nihar.dto;

public class AdminDashboardCounterDTO {

    private long employeeCount;
    private long presentEmployeeCount;
    private long absentEmployeeCount;
    private double salaryProgress;

    // Getters and Setters

    public long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(long employeeCount) {
        this.employeeCount = employeeCount;
    }

    public long getPresentEmployeeCount() {
        return presentEmployeeCount;
    }

    public void setPresentEmployeeCount(long presentEmployeeCount) {
        this.presentEmployeeCount = presentEmployeeCount;
    }

    public long getAbsentEmployeeCount() {
        return absentEmployeeCount;
    }

    public void setAbsentEmployeeCount(long absentEmployeeCount) {
        this.absentEmployeeCount = absentEmployeeCount;
    }

    public double getSalaryProgress() {
        return salaryProgress;
    }

    public void setSalaryProgress(double salaryProgress) {
        this.salaryProgress = salaryProgress;
    }
}
