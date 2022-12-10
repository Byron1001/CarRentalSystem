package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReturnManagement extends JFrame{
    private JTable returnTable;
    private JLabel customerIDLabel, carRegLabel, rentalDateLabel, dueDateLabel, returnDateLabel, delayLabel, fineLable;
    private JTextField fineField;
    private JFormattedTextField customerIDField, carRegField, rentalDateField, returnDateField, delayField;
    private JPanel infoPanel;

    String[] rentalColumns = {"Customer ID", "Car Registration No.", "Rental date", "Due Date", "Return date", "Delay", "Fine"};

    private JTable createTable(ArrayList<Object[]>data, String[] columns){
        DefaultTableModel model = new DefaultTableModel();
        JTable tempTable = new JTable(model);
        for (String col : columns){
            model.addColumn(col);
        }
        for (Object[] dt : data){
            model.addRow(dt);
        }
        tempTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        return tempTable;
    }
    public ReturnManagement() throws ParseException {
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(8, 2));

        customerIDLabel = new JLabel(rentalColumns[0]);
        infoPanel.add(customerIDLabel);

        MaskFormatter formatter = new MaskFormatter("Customer_##");
        customerIDField = new JFormattedTextField(formatter);
        infoPanel.add(customerIDField);

        carRegLabel = new JLabel(rentalColumns[1]);
        infoPanel.add(carRegLabel);

        MaskFormatter formatter1 = new MaskFormatter("Car_##");
        carRegField = new JFormattedTextField(formatter1);
        infoPanel.add(carRegField);

        rentalDateLabel = new JLabel(rentalColumns[2]);
        infoPanel.add(rentalDateLabel);

        MaskFormatter formatter2 = new MaskFormatter("##-##-####");
        rentalDateField = new JFormattedTextField(formatter2);
        infoPanel.add(rentalDateField);

        returnDateLabel = new JLabel("Return Date");
        infoPanel.add(returnDateLabel);

        MaskFormatter formatter3 = new MaskFormatter("##-##-####");
        returnDateField = new JFormattedTextField(formatter3);
        infoPanel.add(returnDateField);

        delayLabel = new JLabel("Delay");
        infoPanel.add(delayLabel);

        delayField.setEditable(false);
        infoPanel.add(delayField);

        JLabel fineLabel = new JLabel("Fine");
        infoPanel.add(fineLabel);

        MaskFormatter formatter7 = new MaskFormatter("RM ####.##");
        JFormattedTextField fineField = new JFormattedTextField(formatter7);
        infoPanel.add(fineField);

//        ArrayList<Object[]> rentalData = new ArrayList<>();
//        rentalData.add(new Object[]{"Customer_01", "Car_02", "10-10-2020", "11-11-2020", "12-12-2020", 31, "RM 31.00"});
//        rentalData.add(new Object[]{"Customer_02", "Car_01", "02-02-2020", "04-04-2020", "06-06-2020", 62, "RM 62.00"});
//        rentalTable = createTable(rentalData, rentalColumns);
//        rentalTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        rentalPane = new JScrollPane();
//        rentalPane.add(rentalTable);
    }

    protected static long dateCount(String date1, String date2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date calendarStart = format.parse(date1);
        Date calendarEnd = format.parse(date2);

        return 0;
    }

    public static void main(String[] args) throws ParseException {
//        new ReturnManagement().setVisible(true);
        System.out.println(dateCount("12-12-2022", "12-12-2022"));;
    }
}
