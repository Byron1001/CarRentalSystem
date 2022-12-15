package Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CustomerBookingHistory extends JFrame{
    private String username;
    private Scanner scanner;
    private ArrayList<String[]> bookingHistoryData, returnHistoryData, paymentHistoryData, loginHistoryData;
    File loginHistoryFile = new File("./CarRental/src/Data/Login History.txt");
    File paymentFile = new File("./CarRental/src/Data/Payment.txt");
    File returnHistoryFile = new File("./CarRental/src/Data/Return History.txt");
    File bookingHistoryFile = new File("./CarRental/src/Data/Booking History.txt");
    private String getUsername(File loginHistoryFile){
        String username = null;
        Scanner scanner1;
        try {
            scanner1 = new Scanner(loginHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner1.hasNextLine()){
            String row = scanner1.nextLine();
            String[] record = row.split("/");
            username = record[2];
        }
        return username;
    }
    public CustomerBookingHistory(){
        username = getUsername(loginHistoryFile);
        bookingHistoryData = getBookingHistory(username);
        returnHistoryData = getReturnHistory(username);
        paymentHistoryData = getPaymentHistory(username);
        loginHistoryData = getLoginHistory(username);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(100, 1));

        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel("Car Booking History"));
        for(String[] data : bookingHistoryData){
            for (int i = 1; i < data.length;i++){
                mainPanel.add(new JLabel(data[i]));
            }
            mainPanel.add(new JLabel());
        }
        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel("Car Return History"));

        for(String[] data : returnHistoryData){
            for (int i = 1; i < data.length;i++){
                mainPanel.add(new JLabel(data[i]));
            }
            mainPanel.add(new JLabel());
        }

        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel("Payment made History"));
        for(String[] data : paymentHistoryData){
            for (int i = 1; i < data.length;i++){
                mainPanel.add(new JLabel(data[i]));
            }
            mainPanel.add(new JLabel());
        }

        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel("Login History"));
        for(String[] data : paymentHistoryData){
            for (int i = 1; i < data.length;i++){
                mainPanel.add(new JLabel(data[i]));
            }
            mainPanel.add(new JLabel());
        }

        mainPanel.add(new JLabel("History print finish."), JPanel.CENTER_ALIGNMENT);
        JButton backButton = new JButton("Back to main menu");
        mainPanel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerMain().setVisible(true);
                dispose();
            }
        });
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        setContentPane(scrollPane);
        setSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    private ArrayList<String[]> getBookingHistory(String username){
        ArrayList<String[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(bookingHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1 && username.equals(rowData[0])){
                String[] labelData = {"Car ID: " + rowData[1],
                "Rental date: " + rowData[2],
                        "Due date: " + rowData[3],
                        "Return process finished: " + rowData[5],
                        "Payment made: " + rowData[6]};
                tempData.add(labelData);
            }
        }
        scanner.close();
        return tempData;
    }

    private ArrayList<String[]> getReturnHistory(String username){
        ArrayList<String[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(returnHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (username.equals(rowData[0]) && rowData.length > 1){
                String[] labelData = {"Car ID :" + rowData[1],
                "Rental date: " + rowData[2],
                        "Due Date: " + rowData[3],
                        "Return Date: " + rowData[4],
                        "Return process finished: " + rowData[5],
                        "Payment made: " + rowData[6]};
                tempData.add(labelData);
            }
        }
        scanner.close();
        return tempData;
    }

    private ArrayList<String[]> getPaymentHistory(String username){
        ArrayList<String[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(paymentFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (username.equals(rowData[0]) && rowData.length > 1){
                String[] labelData = {"Rented Car ID: " + rowData[1],
                        "Return date: " + rowData[2],
                        "Rental days: " + rowData[3],
                        "Delay days: " + rowData[4],
                        "Rental fees: " + rowData[5],
                        "Return delay fine: " + rowData[6],
                        "Payment made" + rowData[7]};
                tempData.add(labelData);
            }

        }
        scanner.close();
        return tempData;
    }
    private ArrayList<String[]> getLoginHistory(String username){
        ArrayList<String[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(loginHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (username.equals(rowData[0]) && rowData.length > 1){
                String[] labelData = {"Rented Car ID: " + rowData[1],
                        "Return date: " + rowData[2],
                        "Rental days: " + rowData[3],
                        "Delay days: " + rowData[4],
                        "Rental fees: " + rowData[5],
                        "Return delay fine: " + rowData[6],
                        "Payment made" + rowData[7]};
                tempData.add(labelData);
            }

        }
        scanner.close();
        return tempData;
    }

}
