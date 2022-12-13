package Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CarBooking extends JFrame{
    private JTable table1;
    private JButton bookTheCarButton;
    private JButton cancelButton;
    private JScrollPane carTablePanel;
    private JPanel buttonPanel;
    private JPanel carBookingPanel;
    private String[] columns = {"Car Registration No.", "Make", "Model", "Available"};
    private ArrayList<Object[]> data = null;
    private Scanner scanner;
    File carDataFile = new File("./CarRental/src/Data/Car Data.txt");
    File bookingHistoryFile = new File("./CarRental/src/Data/Booking History.txt");

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
                if (row2.length > 1){
                    tempData.add(row2);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }

        return tempData;
    }
    CarBooking(){
        init();
        data = getData(carDataFile);
        table1 = createTable(data, columns);
        bookTheCarButton = new JButton("Book the car");
        cancelButton = new JButton("Cancel");

        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 3, true));
        buttonPanel.setLayout(new GridLayout(2, 1));
        bookTheCarButton.setBounds(0, 0, 400, 16);
        cancelButton.setBounds(0, 0, 400, 16);
        buttonPanel.add(bookTheCarButton);
        buttonPanel.add(cancelButton);

        carTablePanel = new JScrollPane(table1);

        carBookingPanel = new JPanel();
        carBookingPanel.setLayout(new FlowLayout());
        carBookingPanel.add(carTablePanel);
        carBookingPanel.add(new JSeparator());
        carBookingPanel.add(buttonPanel);

        bookTheCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();
                table1.setValueAt("No", selectedIndex, 3);
//                saveData(data);
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

    public static void main(String[] args){
        new CarBooking().setVisible(true);
    }

    private void init(){
        try {
            carDataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveData(ArrayList<Object[]> data){
        try {
            FileWriter writer = new FileWriter(carDataFile, false);
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

    private void saveBookingHistory(){}

}
