package implComparator;

import model.Employee;

import java.util.Comparator;

public class EmployeeComparatorByName implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
    }
}
