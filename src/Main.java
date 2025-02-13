import model.Employee;
import model.Manager;
import service.Output;
import service.impl.OutputImpl;
import util.FileUtil;

import java.util.*;

public class Main {
    public static void main(String[] args) {

//        if (args.length == 0) {
//            System.out.println("Ошибка: отсутствуют параметры.");
//            return;
//        }

        String sortBy = "name";
        String sortOrder = "desc";
        String outputType = "console";
        String filePath = null;

        for (String arg : args) {
            System.out.println(arg);
            if (arg.startsWith("--sort=") || arg.startsWith("-s=")) {
                sortBy = arg.split("=")[1];
            } else if (arg.startsWith("--order=") || arg.startsWith("-o=")) {
                sortOrder = arg.split("=")[1];
            } else if (arg.startsWith("--output=")) {
                outputType = arg.split("=")[1];
            } else if (arg.startsWith("-o=")) {
                outputType = arg.split("=")[1];
            } else if (arg.startsWith("--path=")) {
                filePath = arg.split("=")[1];
            }
        }

        if (sortBy != null && !sortBy.equals("name") && !sortBy.equals("salary")) {
            System.out.println("Ошибка: неверный параметр сортировки. Сортировка выполняться не будет");
            sortBy = null;
        }
        if (sortOrder != null && !sortOrder.equals("asc") && !sortOrder.equals("desc")) {
            System.out.println("Ошибка: неверный порядок сортировки. Сортировка выполняться не будет");
            sortOrder = null;
        }
        if (sortOrder != null && sortBy == null) {
            System.out.println("Ошибка: неверный порядок сортировки. Сортировка выполняться не будет");
            sortOrder = null;
        }
        if (outputType.equals("file") && (filePath == null || filePath.isEmpty())) {
            System.out.println("Ошибка: путь к выходному файлу не указан. Будет вывод в консоль");
            outputType = "console";
        }

        if (outputType == null && (filePath != null || !filePath.isEmpty())) {
            System.out.println("Задание пути без указания --output=file считается неправильным. Будет вывод в консоль");
            outputType = "console";
        }

        List<String> incorrectData = new ArrayList<>();
        Set<Manager> managerSet = new HashSet<>();
        Set<Employee> employeeSet = new HashSet<>();
        FileUtil.getManager("INPUT.txt", managerSet, incorrectData, employeeSet);
        managerSet.stream().forEach(x -> x.linkEmployeeWithManager(employeeSet));

        Output output = new OutputImpl();
        if (outputType == null || outputType.equals("console")) {
            output.outputConsole(managerSet, employeeSet, incorrectData, sortBy, sortOrder);
        } else {
            output.outputFile(managerSet, incorrectData, employeeSet, filePath, sortBy, sortOrder);
        }
    }
}
