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

public class LatestNotification extends JFrame {
    File loginHistoryFile = new File("./CarRental/src/Data/Login History.txt");
    File bookingHistoryFile = new File("./CarRental/src/Data/Booking History.txt");
    File paymentHistoryFile = new File("./CarRental/src/Data/Payment.txt");
    File returnHistoryFile = new File("./CarRental/src/Data/Return History.txt");
    ArrayList<Object[]> userBookingHistoryData, userLoginHistoryData, userPaymentHistoryData, userReturnHistoryData;

    private String username;
    private Scanner scanner;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JButton backButton;
    public LatestNotification(){
        username = getUsername(loginHistoryFile);
        userBookingHistoryData = getUserBookingHistory(username);
        userLoginHistoryData = getUserLoginHistory(username);
        userPaymentHistoryData = getUserPaymentHistory(username);
        userReturnHistoryData = getUserReturnHistory(username);

        panel = new JPanel();
        panel.setLayout(new GridLayout(200, 1));

        panel.add(new JLabel("Booking notifications"));
        panel.add(new JLabel());
        for(Object[] dd : userBookingHistoryData){
            panel.add(new JLabel("Your new Booking details:\nCar ID: " + dd[1]));
            panel.add(new JLabel("Rental date: " + dd[2]));
            panel.add(new JLabel("Due date: " + dd[3]));
            panel.add(new JLabel());
        }
        panel.add(new JLabel());

        panel.add(new JLabel("Login notification"));
        panel.add(new JLabel());
        for(Object[] dd : userLoginHistoryData){
            panel.add(new JLabel("You have new Login:\nLogin date: " + dd[0]));
            panel.add(new JLabel("Login time: " + dd[1]));
            panel.add(new JLabel());
        }
        panel.add(new JLabel());

        panel.add(new JLabel("Payment notification"));
        panel.add(new JLabel());
        for(Object[] dd : userPaymentHistoryData){
            panel.add(new JLabel("You just make payment for your rental:\nCar ID: " + dd[1]));
            panel.add(new JLabel("Return date: " + dd[2]));
            panel.add(new JLabel("Rental for: " + dd[3] + "days"));
            panel.add(new JLabel("Return delay: " + dd[4] + "days"));
            panel.add(new JLabel("Rental payment: RM " + dd[5]));
            panel.add(new JLabel("Return delay payment: RM " + dd[6]));
            Integer total = Integer.parseInt(dd[5].toString()) + Integer.parseInt(dd[6].toString());
            panel.add(new JLabel("Total payment needed to be made: RM " + total.toString()));
            panel.add(new JLabel("Payment finished: " + dd[7]));
            panel.add(new JLabel());
        }
        panel.add(new JLabel());

        panel.add(new JLabel("Car return notification"));
        panel.add(new JLabel());
        for(Object[] dd : userReturnHistoryData){
            panel.add(new JLabel("You have Return your rental car:\nCar ID: " + dd[1]));
            panel.add(new JLabel("Rental date: " + dd[2]));
            panel.add(new JLabel("Due date: " + dd[3]));
            panel.add(new JLabel("Return date: " + dd[4]));
            panel.add(new JLabel("Return process finished: " + dd[5]));
            panel.add(new JLabel("Rental and delay payment made: " + dd[6]));
            panel.add(new JLabel());
        }
        panel.add(new JLabel("That's all your notification"));
        panel.add(new JLabel());
        backButton = new JButton("Back to main menu");
        panel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerMain().setVisible(true);
                dispose();
            }
        });

        scrollPane = new JScrollPane(panel);
        setContentPane(scrollPane);
        setSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Latest notification");
        setVisible(true);

    }

    private String getUsername(File loginHistoryFile){
        String username = null;
        Scanner scanner1;
        try {
            scanner1 = new Scanner(loginHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanner1.hasNextLine()){
            String row = scanner1.nextLine();
            String[] record = row.split("/");
            username = record[2];
        }
        return username;
    }

    private ArrayList<Object[]> getUserBookingHistory(String username){
        ArrayList<Object[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(bookingHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1 && rowData[0].toString().equals(username)){
                tempData.add(rowData);
            }
        }
        return tempData;
    }
    private ArrayList<Object[]> getUserLoginHistory(String username){
        ArrayList<Object[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(loginHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1 && rowData[0].toString().equals(username)){
                tempData.add(rowData);
            }
        }
        return tempData;
    }
    private ArrayList<Object[]> getUserPaymentHistory(String username){
        ArrayList<Object[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(paymentHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1 && rowData[0].toString().equals(username)){
                tempData.add(rowData);
            }
        }
        return tempData;
    }

    private ArrayList<Object[]> getUserReturnHistory(String username){
        ArrayList<Object[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(returnHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1 && rowData[0].toString().equals(username)){
                tempData.add(rowData);
            }
        }
        return tempData;
    }

}
