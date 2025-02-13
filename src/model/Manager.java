package model;

import implComparator.EmployeeComparatorByName;
import implComparator.EmployeeComparatorBySalary;
import service.ManagerService;

import java.util.*;
import java.util.stream.Collectors;

public class Manager implements ManagerService, Comparable<Manager> {
    private Long id;
    private String name;
    private float salary;
    private String department;
    private List<Employee> employees = new ArrayList<>();

    public Manager() {
    }

    public Manager(Long id, String name, float salary, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        if (salary > 0) {
            this.salary = (float) (Math.round(salary * 100.0) / 100.0);
        } else {
            throw new NumberFormatException("Зарплата меньше нуля");
        }
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(id, manager.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getDepartment() + "\n" + "Manager," +
                id +
                "," + name +
                "," + salary;
    }

    @Override
    public void linkEmployeeWithManager(Set<Employee> employeeSet) {
        List<Employee> employeesForManager = employeeSet.stream().filter(x -> x.getManagerId() == getId()).collect(Collectors.toList());
        setEmployees(employeesForManager);
        employeeSet.removeAll(employeesForManager);
    }


    @Override
    public List<Employee> sortByNameEmployee(String sortOrder) {
        EmployeeComparatorByName employeeComparatorByName = new EmployeeComparatorByName();
        if (sortOrder != null) {
            if (sortOrder.equals("desc")) {
                return getEmployees().stream().sorted(employeeComparatorByName.reversed()).collect(Collectors.toList());
            }
        }
        return getEmployees().stream().sorted(employeeComparatorByName).collect(Collectors.toList());
    }

    @Override
    public List<Employee> sortBySalaryEmployee(String sortOrder) {
        EmployeeComparatorBySalary employeeComparatorBySalary = new EmployeeComparatorBySalary();
        if (sortOrder != null) {
            if (sortOrder.equals("desc")) {
                return getEmployees().stream().sorted(employeeComparatorBySalary.reversed()).collect(Collectors.toList());
            }
        }
        return getEmployees().stream().sorted(employeeComparatorBySalary).collect(Collectors.toList());
    }

    @Override
    public Statistics generateStatistics() {
        Statistics statistics = new Statistics();
        statistics.setCountEmployee((long) (1 + employees.size()));
        double sum = employees.stream().mapToDouble(p -> p.getSalary()).sum();
        double average = ((sum + getSalary()) / statistics.getCountEmployee());
        statistics.setAverageSalary(average);
        return statistics;
    }

    @Override
    public int compareTo(Manager m) {
        return this.department.toLowerCase().compareTo(m.getDepartment().toLowerCase());
    }
}

