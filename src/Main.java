import model.Employee;
import model.Manager;
import service.Output;
import service.impl.OutputImpl;
import util.ParserFileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        String sortBy = null;
        String sortOrder = null;
        String outputType = "console";
        String filePath = null;

        for (String arg : args) {
            if (arg.startsWith("--sort=") || arg.startsWith("-s=")) {
                sortBy = arg.split("=")[1];
            } else if (arg.startsWith("--order=")) {
                sortOrder = arg.split("=")[1];
            } else if (arg.startsWith("--output=")) {
                outputType = arg.split("=")[1];
            } else if (arg.startsWith("-o=")) {
                String value = arg.split("=")[1];
                if (value.equals("file")) {
                    outputType = value;
                } else if (value.equals("asc") || value.equals("desc")) {
                    sortOrder = value;
                }
            } else if (arg.startsWith("--path=") && outputType.equals("file")) {
                filePath = arg.split("=")[1];
            }
        }

        if (sortBy != null && !sortBy.equals("name") && !sortBy.equals("salary")) {
            LOGGER.log(Level.WARNING, "Ошибка: неверный параметр сортировки. Сортировка выполняться не будет");
            sortBy = null;
        }
        if (sortOrder != null && !sortOrder.equals("asc") && !sortOrder.equals("desc")) {
            LOGGER.log(Level.WARNING, "Ошибка: неверный порядок сортировки. Сортировка будет иметь прямой порядок");
            sortOrder = null;
        }
        if (sortOrder != null && sortBy == null) {
            LOGGER.log(Level.WARNING, "Ошибка: неверный порядок сортировки. Сортировка выполняться не будет");
            sortOrder = null;
        }
        if (outputType.equals("file") && filePath == null ) {
            LOGGER.log(Level.WARNING, "Ошибка: путь к выходному файлу не указан. Будет вывод в консоль");
            outputType = "console";
        }else if (outputType.equals("file") && filePath.isEmpty()){
            LOGGER.log(Level.WARNING, "Ошибка: путь к выходному файлу пустой. Будет вывод в консоль");
            outputType = "console";
        }

        List<String> incorrectData = new ArrayList<>();
        Set<Manager> managerSet = new HashSet<>();
        Set<Employee> employeeSet = new LinkedHashSet<>();
        ParserFileUtil.parsingText("INPUT.txt", managerSet, incorrectData, employeeSet);
        managerSet.stream().forEach(x -> x.linkEmployeeWithManager(employeeSet));

        Output output = new OutputImpl();
        if (outputType.equals("console")) {
            output.outputConsole(managerSet, employeeSet, incorrectData, sortBy, sortOrder);
        } else {
            output.outputFile(managerSet, incorrectData, employeeSet, filePath, sortBy, sortOrder);
        }
    }
}
