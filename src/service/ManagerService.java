package service;

import model.Employee;
import model.Statistics;

import java.util.List;
import java.util.Set;

public interface ManagerService {
    public void linkEmployeeWithManager(Set<Employee> employeeSet);

    public List<Employee> sortByNameEmployee(String sortOrder);
    public List<Employee> sortBySalaryEmployee(String sortOrder);


    public Statistics generateStatistics();
}