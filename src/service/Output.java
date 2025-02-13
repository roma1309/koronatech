package service;

import model.Employee;
import model.Manager;

import java.util.List;
import java.util.Set;

public interface Output {
    public void outputConsole(Set<Manager> managerSet, Set<Employee> incorrectEmployee, List<String> incorrectData, String sort, String order);

    public void outputFile(Set<Manager> managerSet, List<String> incorrectData, Set<Employee> incorrectEmployee, String fileName, String sort, String order);
}
