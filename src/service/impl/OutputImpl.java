package service.impl;

import model.Employee;
import model.Manager;
import service.Output;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutputImpl implements Output {
    static Logger LOGGER = Logger.getLogger(OutputImpl.class.getName());

    @Override
    public void outputConsole(Set<Manager> managerSet, Set<Employee> incorrectEmployee, List<String> incorrectData, String sort, String order) {
        List<Manager> managerList = managerSet.stream().sorted().toList();
        for (Manager manager : managerList) {
            System.out.println(manager.toString());
            if (sort == null) {
                manager.getEmployees().stream().forEach(System.out::println);
            } else if (sort.equals("name")) {
                manager.sortByNameEmployee(order).stream().forEach(System.out::println);
            } else if (sort.equals("salary")) {
                manager.sortBySalaryEmployee(order).stream().forEach(System.out::println);
            } else {
                manager.getEmployees().stream().forEach(System.out::println);
            }
            System.out.println(manager.generateStatistics());
        }

        System.out.println("Некорректные данные");
        incorrectEmployee.stream().forEach(x -> {
            System.out.println(x.printIncorrect());
        });
        incorrectData.stream().forEach(System.out::println);
    }

    @Override
    public void outputFile(Set<Manager> managerSet, List<String> incorrectData, Set<Employee> incorrectEmployee, String fileName, String sort, String order) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            List<Manager> managerList = managerSet.stream().sorted().toList();
            for (Manager manager : managerList) {
                writer.write(manager.toString());
                writer.append('\n');
                printOnFileEmployee(writer, manager, sort, order);
                writer.write(manager.generateStatistics().toString());
                writer.append('\n');
            }
            writer.write("Некорректные данные");
            writer.append('\n');
            for (Employee employee : incorrectEmployee) {
                writer.write(employee.printIncorrect());
                writer.append('\n');
            }
            for (String data : incorrectData) {
                writer.write(data);
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
    }

    private void printOnFileEmployee(FileWriter writer, Manager manager, String sort, String order) {
        if (sort == null) {
            manager.getEmployees().stream().forEach(x -> {
                try {
                    writer.write(x.toString());
                    writer.append('\n');
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else if (sort.equals("name")) {
            manager.sortByNameEmployee(order).stream().forEach(x -> {
                try {
                    writer.write(x.toString());
                    writer.append('\n');
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else if (sort.equals("salary")) {
            manager.sortBySalaryEmployee(order).stream().forEach(x -> {
                try {
                    writer.write(x.toString());
                    writer.append('\n');
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            manager.getEmployees().stream().forEach(x -> {
                try {
                    writer.write(x.toString());
                    writer.append('\n');
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
