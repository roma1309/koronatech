package service;

import model.Employee;
import model.Manager;

import java.util.List;
import java.util.Set;

public interface Output {
    public void outputConsole(Set<Manager> managerSet, Set<Employee> incorrectEmployee, List<String> incorrectData);

    public void outputFile();
}
