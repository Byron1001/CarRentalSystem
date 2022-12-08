package Admin;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.PrivilegedAction;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class RentalManagement extends JFrame{
    private JFormattedTextField customerIDField;
    private JFormattedTextField carIDField;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JPanel buttonPanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JLabel customerIDLabel;
    private JLabel carIDLabel;
    private JTable availableTable, rentalTable;
    private JScrollPane availablePane, rentalPane;
    String[] carColumns = {"Car Registration No", "Make", "Model", "Available"};
    String[] rentalColumns = {"Customer ID", "Car Registration No.", "Rental date", "Due Date", "Return date", "Delay", "Fine"};

    private static JTable createTable(ArrayList<Object[]> data, String[] columns){
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int columns){
                return false;
            }
        };
        JTable tempTable = new JTable(model);

        for (int i = 0; i < columns.length;i++){
            model.addColumn(columns[i]);
        }
        for(int i = 0; i < data.size();i++){
            model.addRow(data.get(i));
        }

        return tempTable;
    }
    public RentalManagement() throws ParseException {
        infoPanel.setLayout(new GridLayout(7, 2));

        customerIDLabel = new JLabel("Customer ID");
        infoPanel.add(customerIDLabel);

        MaskFormatter formatter = new MaskFormatter("Customer_##");
        customerIDField = new JFormattedTextField(formatter);
        infoPanel.add(customerIDField);

        carIDLabel = new JLabel("Car Registration No.");
        infoPanel.add(carIDLabel);

        MaskFormatter formatter2 = new MaskFormatter("Car_##");
        carIDField = new JFormattedTextField(formatter2);
        infoPanel.add(carIDField);

        JLabel rentalDateLabel = new JLabel("Rental date");
        infoPanel.add(rentalDateLabel);

        MaskFormatter formatter3 = new MaskFormatter("##-##-####");
        JFormattedTextField rentalDateField = new JFormattedTextField(formatter3);
        infoPanel.add(rentalDateField);

        JLabel dueDateLabel = new JLabel("Due date");
        infoPanel.add(dueDateLabel);

        MaskFormatter formatter4 = new MaskFormatter("##-##-####");
        JFormattedTextField dueDateField = new JFormattedTextField(formatter4);
        infoPanel.add(dueDateField);

        JLabel returnDateLabel = new JLabel("Return Date");
        infoPanel.add(returnDateLabel);

        MaskFormatter formatter5 = new MaskFormatter("##-##-####");
        JFormattedTextField returnDateField = new JFormattedTextField(formatter5);
        infoPanel.add(returnDateField);

        JLabel delayLabel = new JLabel("Delay");
        infoPanel.add(delayLabel);

        MaskFormatter formatter6 = new MaskFormatter("###");
        JFormattedTextField delayField = new JFormattedTextField(formatter6);
        infoPanel.add(delayField);

        JLabel fineLabel = new JLabel("Fine");
        infoPanel.add(fineLabel);

        MaskFormatter formatter7 = new MaskFormatter("RM ####.##");
        JFormattedTextField fineField = new JFormattedTextField(formatter7);
        infoPanel.add(fineField);

        this.setLayout(new GridLayout(3, 1));

        ArrayList<Object[]> carData = new ArrayList<>();
        carData.add(new Object[]{"Car_01", 2002, "AMG", "Yes"});
        carData.add(new Object[]{"Car_02", 2012, "AMG", "No"});
        availableTable = createTable(carData, carColumns);
        availablePane = new JScrollPane(availableTable);
        this.add(availablePane);

        ArrayList<Object[]> rentalData = new ArrayList<>();
        rentalData.add(new Object[]{"Customer_01", "Car_02", "10-10-2020", "11-11-2020", "12-12-2020", 31, "RM 31.00"});
        rentalData.add(new Object[]{"Customer_02", "Car_01", "02-02-2020", "04-04-2020", "06-06-2020", 62, "RM 62.00"});
        rentalTable = createTable(rentalData, rentalColumns);

        rentalPane = new JScrollPane();
        rentalPane.add(rentalTable);
        JOptionPane.showMessageDialog(null, rentalPane);
        this.add(rentalTable);

        this.add(infoPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) throws ParseException {
        new RentalManagement().setVisible(true);
    }
}
