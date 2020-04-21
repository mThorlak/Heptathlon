package rmi_package;

import au.com.bytecode.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVManager {

    private final static String BILL_PATH = "Server/resources/bill.csv";
    private final static char SEPARATOR = ';';

    public List<String[]> readLineByLine() {
    try (
            Reader reader = Files.newBufferedReader(Paths.get(BILL_PATH));
            CSVReader csvReader = new CSVReader(reader, SEPARATOR);
        ) {
        List<String[]> bill = new ArrayList<>();
        // Reading Records One by One in a String array
        String[] nextRecord;
        while ((nextRecord = csvReader.readNext()) != null) {
            System.out.println("Name : " + nextRecord[0]);
            System.out.println("Email : " + nextRecord[1]);
            bill.add(nextRecord);
        }
        return bill;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
    }
}
