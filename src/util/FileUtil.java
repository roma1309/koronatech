package util;

import model.Employee;
import model.Manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class FileUtil {

    public static Set<Manager> getManager(String fileName, Set<Manager> managerSet, List<String> incorrectData, Set<Employee> employeeSet) {
//        Set<Manager> managerList = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));) {
            String line;
            while ((line = reader.readLine()) != null) {
                Manager manager = null;
                Employee employee = null;

                String[] dates = line.split(",");
                if (dates.length != 5) {
                    incorrectData.add(line);
                    continue;
                }
                if (dates[0].replaceAll("^\\s+|\\s+$", "").equals("Manager")) {
                    manager = createManager(dates);
                } else if (dates[0].replaceAll("^\\s+|\\s+$", "").equals("Employee")) {
                    employee = createEmployee(dates);
                }
                if (manager != null) {
                    boolean isAdd = managerSet.add(manager);
                    if (!isAdd) {
                        incorrectData.add(line);
                    }
                } else if (employee != null) {
                    boolean isAdd = employeeSet.add(employee);
                    if (!isAdd) {
                        incorrectData.add(line);
                    }
                } else {
                    incorrectData.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return managerSet;
    }

    public static void writeFile(Set<Manager> managerSet, List<String> incorrectData, Set<Employee> incorrectEmployee) {
        try (FileWriter writer = new FileWriter("OUTPUT.txt", false)) {
            List<Manager> managerList = managerSet.stream().sorted().toList();
            for (Manager manager : managerList) {
                writer.write(manager.toString());
                writer.append('\n');
                manager.sortBySalary().stream().forEach(x -> {
                    try {
                        writer.write(x.toString());
                        writer.append('\n');
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.write(manager.generateStatistics().toString());
                writer.append('\n');
                writer.flush();
            }
            writer.write("Некорректные данные");
            writer.append('\n');
            for (Employee employee : incorrectEmployee) {
                writer.write(employee.toString());
                writer.append('\n');
            }
            for (String data : incorrectData) {
                writer.write(data);
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    private static Manager createManager(String[] fields) {
        try {
            Manager manager = new Manager();
            if (fields[0].equals("Manager")) {
                manager.setId(Long.parseLong(fields[1].replaceAll("^\\s+|\\s+$", "")));
                manager.setName(fields[2].replaceAll("^\\s+|\\s+$", ""));
                manager.setSalary(Float.parseFloat(fields[3].replaceAll("^\\s+|\\s+$", "")));
                manager.setDepartment(fields[4]);
                return manager;
            }
        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage() + "  2 catch");
        }
        return null;
    }

    private static Employee createEmployee(String[] fields) {
        try {
            Employee employee = new Employee();
            employee.setId(Long.parseLong(fields[1].replaceAll("^\\s+|\\s+$", "")));
            employee.setName(fields[2].replaceAll("^\\s+|\\s+$", ""));
            employee.setSalary(Float.parseFloat(fields[3].replaceAll("^\\s+|\\s+$", "")));
            employee.setManagerId(Long.parseLong(fields[4].replaceAll("^\\s+|\\s+$", "")));
            return employee;
        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage() + "  3 catch");
        }
        return null;
    }
}
