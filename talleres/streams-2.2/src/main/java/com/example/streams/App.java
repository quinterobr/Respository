package com.example.streams;

import com.example.streams.model.Employee;
import com.example.streams.factory.EmployeeFactory;
import com.example.streams.model.Project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class App {
    static List<Employee> employeeList = new ArrayList<>();

    public static void main(String[] args) {
        EmployeeFactory employeeFactory = new EmployeeFactory();
        employeeList = employeeFactory.getAllEmployee();

        //#1
        List<Project> distinctProjects = employeeList.stream()
                .flatMap(employee -> employee.getProjects().stream())
                .distinct()
                .sorted((project1, project2) -> project2.getName().compareTo(project1.getName()))
                .collect(Collectors.toList());

        System.out.println("Proyectos distintos en orden descendente: " + distinctProjects);

        //#2
        employeeList.stream()
                .filter(employee -> employee.getFirstName().startsWith("A"))
                .map(employee -> employee.getFirstName() + " " + employee.getLastName())
                .forEach(System.out::println);

        //#3
        employeeList.stream()
                .filter(employee -> employee.getId().startsWith("2023"))
                .forEach(System.out::println);

        //#4
        List<Employee> sortedEmployees = employeeList.stream()
                .sorted(Comparator.comparing(Employee::getFirstName)
                        .thenComparingInt(Employee::getSalary))
                .collect(Collectors.toList());

        System.out.println("Empleados ordenados por primer nombre y salario: " + sortedEmployees);

        //#5
        int n = 3; //cambiar n al número que desees, por ejemplo, 3 para el tercer salario más alto

        List<String> employeesWithNthHighestSalary = employeeList.stream()
                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                .limit(n)
                .map(employee -> employee.getFirstName() + " " + employee.getLastName())
                .collect(Collectors.toList());

        System.out.println("Empleados con el " + n + "° salario más alto: " + employeesWithNthHighestSalary);

        //#6
        Optional<Employee> employeeWithLowestSalary = employeeList.stream()
                .min(Comparator.comparingInt(Employee::getSalary));

        employeeWithLowestSalary.ifPresent(employee -> {
            System.out.println("Empleado con el menor salario: " + employee.getFirstName() + " " + employee.getLastName());
            System.out.println("Salario: " + employee.getSalary());
        });

        //#7
        int minSalary = employeeList.stream()
                .mapToInt(Employee::getSalary)
                .min()
                .orElse(0);

        List<Employee> employeesWithMinSalary = employeeList.stream()
                .filter(employee -> employee.getSalary() == minSalary)
                .collect(Collectors.toList());

        System.out.println("Empleados con el menor salario:");

        for (Employee employee : employeesWithMinSalary) {
            System.out.println(employee.getFirstName() + " " + employee.getLastName() + " - Salario: " + employee.getSalary());
        }

        //#8
        List<Employee> employeesWithMoreThanTwoProjects = employeeList.stream()
                .filter(employee -> employee.getProjects().size() > 2)
                .collect(Collectors.toList());

        System.out.println("Personas que trabajan en más de dos proyectos:");

        for (Employee employee : employeesWithMoreThanTwoProjects) {
            System.out.println(employee.getFirstName() + " " + employee.getLastName());
        }

        //#9
        int totalLaptopsAssigned = employeeList.stream()
                .mapToInt(Employee::getTotalLaptopsAssigned)
                .sum();

        System.out.println("Total de laptops asignados a los empleados: " + totalLaptopsAssigned);

        //#10
        long projectsManagedByRDJ = employeeList.stream()
                .flatMap(employee -> employee.getProjects().stream())
                .filter(project -> "Robert Downey Jr".equals(project.getProjectManager()))
                .count();

        System.out.println("Número de proyectos gestionados por Robert Downey Jr: " + projectsManagedByRDJ);



    }
}
