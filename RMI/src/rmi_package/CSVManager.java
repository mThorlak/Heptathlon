package rmi_package;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
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
            List<String[]> bills = new ArrayList<>();
            // Reading Records One by One in a String array
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                bills.add(nextRecord);
            }
            return bills;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeNewBill(Bill bill) throws IOException {
        try (
                FileWriter writer = new FileWriter(BILL_PATH, true);
                CSVWriter csvWriter = new CSVWriter(writer,
                        SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
                )
        {
            List<Article> articleBought = bill.getArticles();
            List<String[]> listRandom = readLineByLine();
            String articleFormatted = "";
            String idBill;

            if (listRandom.isEmpty())
                idBill = "1";
            else {
                String[] lastLine = listRandom.get(listRandom.size() - 1);
                idBill = Integer.toString(Integer.parseInt(lastLine[1]) + 1);
            }

            int cpt = 0;
            for (Article article : articleBought) {
                cpt ++;
                articleFormatted = articleFormatted.concat("|" + article.getReference());
                if (cpt == articleBought.size()) {
                    articleFormatted = articleFormatted.concat("|" + article.getPrice() + "|;");
                }
                else
                    articleFormatted = articleFormatted.concat("|" + article.getPrice() + "|,");
            }

            csvWriter.writeNext(
                    new String[]{
                            bill.getDate(),
                            idBill,
                            Double.toString(bill.getTotal()),
                            bill.getPayment(),
                            articleFormatted
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
