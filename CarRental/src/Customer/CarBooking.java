package Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class CarBooking extends JFrame{
    private JTable table1;
    private JButton bookTheCarButton, cancelButton;
    private JScrollPane carTablePanel;
    private JPanel buttonPanel, carBookingPanel;
    private MaskFormatter formatter, formatter1;
    private JLabel fromLabel, toLabel, titleLabel;
    private JFormattedTextField fromField, toField;
    private String[] columns = {"Car Registration No.", "Make", "Model", "Available"};
    private ArrayList<Object[]> data = null;
    private Scanner scanner;
    File carDataFile = new File("./CarRental/src/Data/Car Data.txt");
    File bookingHistoryFile = new File("./CarRental/src/Data/Booking History.txt");
    File loginHistoryFile = new File("./CarRental/src/Data/Login History.txt");

    private JTable createTable(ArrayList<Object[]> data, String[] columns){
        DefaultTableModel model = new DefaultTableModel();
        JTable tempTable = new JTable(model);
        tempTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        for(String col : columns){
            model.addColumn(col);
        }
        for (Object[] row : data){
            model.addRow(row);
        }
        tempTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        return tempTable;
    }

    private ArrayList<Object[]> getData(File carDataFile){
        ArrayList<Object[]> tempData = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(carDataFile);
            while(scanner.hasNextLine()){
                String row = scanner.nextLine();
                String[] row2 = row.split(":");
                if (row2.length > 1)
                    tempData.add(row2);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
        return tempData;
    }
    CarBooking() throws ParseException {
        init();
        data = getData(carDataFile);
        table1 = createTable(data, columns);
        bookTheCarButton = new JButton("Book the car");
        cancelButton = new JButton("Cancel");
        titleLabel = new JLabel("Car Booking");
        fromLabel = new JLabel("From");
        toLabel = new JLabel("To");

        formatter = new MaskFormatter("##-##-####");
        formatter1 = new MaskFormatter("##-##-####");
        fromField = new JFormattedTextField(formatter);
        toField = new JFormattedTextField(formatter1);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1));

        buttonPanel.add(new JSeparator());
        buttonPanel.add(new JSeparator());
        buttonPanel.add(titleLabel);
        buttonPanel.add(new JLabel());
        buttonPanel.add(fromLabel);
        buttonPanel.add(fromField);
        buttonPanel.add(toLabel);
        buttonPanel.add(toField);
        bookTheCarButton.setBounds(0, 0, 400, 16);
        cancelButton.setBounds(0, 0, 400, 16);
        buttonPanel.add(bookTheCarButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(new JSeparator());
        buttonPanel.add(new JSeparator());

        carTablePanel = new JScrollPane(table1);

        carBookingPanel = new JPanel();
        carBookingPanel.setLayout(new GridLayout(1, 2, 30, 10));
        carBookingPanel.add(carTablePanel);
        carBookingPanel.add(buttonPanel, JPanel.CENTER_ALIGNMENT);

        bookTheCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();
                Object[] change = data.get(selectedIndex);
//                change[change.length - 1] = "No";
                data.remove(selectedIndex);
                data.add(change);
                saveData(data, carDataFile);
                String username = getUsername(loginHistoryFile);
//                System.out.println(username);
                String prefix = fromField.getText() + toField.getText();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerMain().setVisible(true);
                dispose();
            }
        });

        setContentPane(carBookingPanel);
        setSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) throws ParseException {
        new CarBooking().setVisible(true);
    }

    private void init(){
        try {
            carDataFile.createNewFile();
            bookingHistoryFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveData(ArrayList<Object[]> data, File file){
        try {
            FileWriter writer = new FileWriter(file, false);
            for(Object[] ob:data){
                String whole = ob[0] + ":" + ob[1] + ":" + ob[2] + ":" + ob[3] + "\n";
                if(ob.length == data.indexOf(ob) + 1){
                    whole = whole + "\n";
                }
                writer.write(whole);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUsername(File loginHistoryFile){
        String username = null;
        try {
            scanner = new Scanner(loginHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String row = scanner.nextLine();
        String[] record = row.split("|", 4);
        username = record[2];
        return username;
    }

    private void saveBookingHistory(){
        String suffix = ":00-00-0000:No:No";
    }

}
