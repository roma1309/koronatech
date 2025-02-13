package util;

import model.Employee;
import model.Manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserFileUtil {
    static Logger LOGGER = Logger.getLogger(ParserFileUtil.class.getName());

    public static Set<Manager> parsingText(String fileName, Set<Manager> managerSet, List<String> incorrectData, Set<Employee> employeeSet) {
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
                    incorrectData.add(line.trim());
                }
            }
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
        return managerSet;
    }

    private static Manager createManager(String[] fields) {
        try {
            Manager manager = new Manager();
            manager.setId(Long.parseLong(fields[1].replaceAll("^\\s+|\\s+$", "")));
            manager.setName(fields[2].replaceAll("^\\s+|\\s+$", ""));
            manager.setSalary(Float.parseFloat(fields[3].replaceAll("^\\s+|\\s+$", "")));
            manager.setDepartment(fields[4]);
            return manager;

        } catch (NumberFormatException exception) {
            LOGGER.log(Level.WARNING, exception.getMessage());
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
            LOGGER.log(Level.WARNING, exception.getMessage());
        }
        return null;
    }
}
