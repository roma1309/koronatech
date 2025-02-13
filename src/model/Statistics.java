package model;

public class Statistics {
    private Long countEmployee;
    private double averageSalary;

    public Statistics() {


    }

    public Long getCountEmployee() {
        return countEmployee;
    }

    public void setCountEmployee(Long countEmployee) {
        this.countEmployee = countEmployee;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = (Math.round(averageSalary * 100.0) / 100.0);
    }

    @Override
    public String toString() {
        return countEmployee + ", " + averageSalary;
    }

}
