package service.impl;

import model.Employee;
import model.Manager;
import service.Output;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class OutputImpl implements Output {
    @Override
    public void outputConsole(Set<Manager> managerSet, Set<Employee> incorrectEmployee, List<String> incorrectData) {
        List<Manager> managerList = managerSet.stream().sorted().toList();
        for (Manager manager : managerList) {
            System.out.println(manager.toString());

            manager.sortBySalary().stream().forEach(System.out::println);
        }
        System.out.println("Некорректные данные");

//        for (Employee employee : incorrectEmployee) {
//            writer.write(employee.toString());
//            writer.append('\n');
//        }
        incorrectEmployee.stream().forEach(System.out::println);
        incorrectData.stream().forEach(System.out::println);
//        for (String data : incorrectData) {
//            writer.write(data);
//            writer.append('\n');
//        }
    }

    @Override
    public void outputFile() {

    }
}
