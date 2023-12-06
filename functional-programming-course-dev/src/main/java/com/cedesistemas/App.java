package com.cedesistemas;

import com.cedesistemas.factory.EmployeeFactory;
import com.cedesistemas.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class App {
    static List<Employee> employeeList = new ArrayList<>();

    public static void main(String[] args) {
        EmployeeFactory employeeFactory = new EmployeeFactory();
        employeeList = employeeFactory.getAllEmployee();

        //TODO: Implement questions ...

    }
}
