package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Employee {
    private Long id;
    private String name;
    private float salary;
    private Long managerId;

    public Employee() {
    }

    public Employee(Long id, String name, float salary, Long managerId) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.managerId = managerId;
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
            throw new NumberFormatException();
        }
    }

    public Long getManagerId() {
        return managerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Employee," +
                 id +
                "," + name +
                "," + salary;
    }
}
