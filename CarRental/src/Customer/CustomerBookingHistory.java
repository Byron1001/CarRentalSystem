package Customer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerBookingHistory extends JFrame{
    private String username;
    private Scanner scanner;
    private ArrayList<String> bookingHistoryData, returnHistoryData, paymentHistoryData;
    File loginHistoryFile = new File("./CarRental/src/Data/Login History.txt");
    File paymentFile = new File("./CarRental/src/Data/Payment.txt");
    File returnHistoryFile = new File("./CarRental/src/Data/Return History.txt");
    File bookingHistoryFile = new File("./CarRental/src/Data/Booking History.txt");
    private String getUsername(File loginHistoryFile){
        String username = null;
        Scanner scanner1 = null;
        try {
            scanner1 = new Scanner(loginHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String row = scanner1.nextLine();
        String[] record = row.split("/");
        username = record[2];
        return username;
    }
    public CustomerBookingHistory(){
        username = getUsername(loginHistoryFile);
        bookingHistoryData = getBookingHistory(username);
        returnHistoryData = getReturnHistory(username);
        paymentHistoryData = getPaymentHistory(username);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1000, 1));

        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel("Car Booking History"));
        for(String data : bookingHistoryData){
            mainPanel.add(new JLabel(data));
            mainPanel.add(new JLabel());
        }
        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel("Car Return History"));

        for(String data : returnHistoryData){
            mainPanel.add(new JLabel(data));
            mainPanel.add(new JLabel());
        }

        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel("Payment made History"));
        for(String data : paymentHistoryData){
            mainPanel.add(new JLabel(data));
            mainPanel.add(new JLabel());
        }
        mainPanel.add(new JLabel("History print finish."), JPanel.CENTER_ALIGNMENT);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        setContentPane(scrollPane);
        setSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    private ArrayList<String> getBookingHistory(String username){
        ArrayList<String> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(bookingHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1 && username.equals(rowData[0])){
                String labelData = "Car ID: " + rowData[1] + "\n" + "Rental date: " + rowData[2] + "\n"
                        + "Return date: " + rowData[3] + "\n" + "Return process finished: " + rowData[4] + "\n"
                        + "Payment made: " + rowData[5] + "\n";
                tempData.add(labelData);
            }
        }
        scanner.close();
        return tempData;
    }

    private ArrayList<String> getReturnHistory(String username){
        ArrayList<String> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(returnHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (username.equals(rowData[0]) && rowData.length > 1){
                String labelData = "Car ID :" + rowData[1] + "\n" + "Rental date: " + rowData[2] + "\n" +
                        "Due Date: " + rowData[3] + "\n" + "Return Date: " + rowData[4] + "\n" +
                        "Return process finished: " + rowData[5] + "\n" + "Payment made: " + rowData[6] + "\n";
                tempData.add(labelData);
            }
        }
        scanner.close();
        return tempData;
    }

    private ArrayList<String> getPaymentHistory(String username){
        ArrayList<String> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(paymentFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (username.equals(rowData[0]) && rowData.length > 1){
                String labelData = "Rented Car ID: " + rowData[1] + "\n" + "Return date: " + rowData[2] + "\n" +
                        "Rental days: " + rowData[3] + "\n" + "Delay days: " + rowData[4] + "\n" +
                        "Rental fees: " + rowData[5] + "\n" + "Return delay fine: " + rowData[6] + "\n" + "Payment made" + rowData[7] + "\n";
                tempData.add(labelData);
            }

        }
        scanner.close();
        return tempData;
    }
    public static void main(String[] args){
        new CustomerBookingHistory().setVisible(true);
    }

}
