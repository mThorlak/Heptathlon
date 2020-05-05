package model_table;

import rmi_siege.tables.Bill;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableBillSiege extends AbstractTableModel {

        private List<Bill> bills = new ArrayList<>();

        private final String[] header = {"ID Bill", "Shop", "Date", "Total", "Payment", "Paid"};

        public TableBillSiege(List<Bill> bills) {
            super();
            this.bills = bills;
        }

        public int getRowCount() {
            return bills.size();
        }

        public int getColumnCount() {
            return header.length;
        }

        public String getColumnName(int columnIndex) {
            return header[columnIndex];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0)
                return bills.get(rowIndex).getId();
            else if (columnIndex == 1)
                return bills.get(rowIndex).getShop();
            else if (columnIndex == 2)
                return bills.get(rowIndex).getDate();
            else if (columnIndex == 3)
                return bills.get(rowIndex).getTotal();
            else if (columnIndex == 4)
                return bills.get(rowIndex).getPayment();
            else if (columnIndex == 5)
                return bills.get(rowIndex).isPaid();
            else
                return null;
        }

    }
