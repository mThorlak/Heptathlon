package rmi_package;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class CSVManager {

    /**
     * OpenCSV CSVReader Example, Read line by line
     */
    public void OpenCSVReaderLineByLine() throws IOException, CsvValidationException {

        CSVReader reader = new CSVReader(new FileReader("RMI/resources/bill.csv"));

        // List<Employee> emps = new ArrayList<Employee>();

        // read line by line
        String[] record = null;

        while ((record = reader.readNext()) != null) {
            System.out.println(record[0]);
//            Employee emp = new Employee();
//            emp.setId(record[0]);
//            emp.setName(record[1]);
//            emp.setAge(record[2]);
//            emp.setCountry(record[3]);
//            emps.add(emp);
        }

        reader.close();
    }
}
