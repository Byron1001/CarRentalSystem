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

public class CustomerPayment extends JFrame{
    private JTable paymentTable;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton paymentButton, backButton;
    private Scanner scanner;
    File paymentFile = new File("./CarRental/src/Data/Payment.txt");
    File returnHistoryFile = new File("./CarRental/src/Data/Return History.txt");
    File loginHistoryFile = new File("./CarRental/src/Data/Login History.txt");
    String[] paymentColumns = {"Username", "Car ID", "Return date", "Rental days", "Delay days", "Rental fees", "Delay fees", "Payment"};
    String username, yes;
    ArrayList<Object[]> paymentData, returnHistoryData;
    public CustomerPayment(){
        init();
        username = getUsername(loginHistoryFile);
        yes = "Yes";

        paymentData = getData(paymentFile, username);
        paymentTable = createTable(paymentData, paymentColumns, username);
        returnHistoryData = getData(returnHistoryFile, username);

        paymentButton = new JButton("Make Payment");
        backButton = new JButton("Back to Main menu");

        scrollPane = new JScrollPane(paymentTable);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(scrollPane);
        panel.add(paymentButton);
        panel.add(backButton);

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = paymentTable.getSelectedRow();
                if (selectedIndex == -1){
                    JOptionPane.showMessageDialog(null, "Please select the payment you want to make", "Payment select error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    Object[] paymentRow = paymentData.get(selectedIndex);
                    Integer pay = Integer.parseInt(paymentRow[5].toString()) + Integer.parseInt(paymentRow[6].toString());
                    double payFloat = Double.parseDouble(pay.toString());
                    String message = "Are you sure to make payment?\nYou will need to pay RM " + payFloat;

                    int choose = JOptionPane.showConfirmDialog(null, message, "Payment confirmation", JOptionPane.YES_NO_OPTION);
                    if (choose == JOptionPane.YES_OPTION){
                        paymentRow[paymentRow.length - 1] = yes;
                        paymentData.remove(selectedIndex);
                        paymentData.add(paymentRow);

                        String carID = paymentRow[1].toString();
                        String returnDate = paymentRow[2].toString();
                        int returnHistoryIndex = getReturnHistoryIndex(returnHistoryData, username, carID, returnDate);
                        Object[] returnData = returnHistoryData.get(returnHistoryIndex);
                        returnData[returnData.length - 1] = yes;
                        returnHistoryData.remove(returnHistoryIndex);
                        returnHistoryData.add(returnData);

                        saveUserData(paymentData, paymentFile, false);
                        saveUserData(returnHistoryData, returnHistoryFile, false);
                        new CustomerPayment().setVisible(true);
                        dispose();
                        JOptionPane.showMessageDialog(null, "Payment success", "Payment notice", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Payment cancelled", "Payment Cancelled", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerMain().setVisible(true);
                dispose();
            }
        });

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        try {
            loginHistoryFile.createNewFile();
            paymentFile.createNewFile();
            returnHistoryFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    private static JTable createTable(ArrayList<Object[]> data, String[] columns, String username){
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
        for (Object[] i : data) {
            if (i[0].equals(username))
                model.addRow(i);
        }
        tempTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tempTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        return tempTable;
    }

    private ArrayList<Object[]> getData(File file, String username){
        ArrayList<Object[]> tempDataUser = new ArrayList<>();
        try {
            scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String row = scanner.nextLine();
                String[] datarow = row.split(":");
                if (datarow.length > 1){
                    tempDataUser.add(datarow);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.close();

        return tempDataUser;
    }

    private void saveUserData(ArrayList<Object[]> data, File file, boolean append){
        try {
            FileWriter writer = new FileWriter(file, append);
            for(Object[] ob:data){
                String whole = ob[0] + ":" + ob[1] + ":" + ob[2] + ":" + ob[3];
                if (file == paymentFile)
                    whole += ":" + ob[4] + ":" + ob[5] + ":" + ob[6] + ":" + ob[7];
                if (file == returnHistoryFile)
                    whole += ":" + ob[4] + ":" + ob[5] + ":" + ob[6];
                whole += "\n";
                if(ob.length == data.indexOf(ob) + 1)
                    whole += "\n";
                if (!append)
                    writer.write(whole);
                else
                    writer.append(whole);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getReturnHistoryIndex(ArrayList<Object[]> returnHistoryData, String username, String carID, String returnDate){
        for (Object[] dd : returnHistoryData){
            if(dd[0].toString().equals(username) && dd[1].toString().equals(carID) && dd[4].toString().equals(returnDate))
                return returnHistoryData.indexOf(dd);
            }
        return -1;
    }

    public static void main(String[] args) {
        new CustomerPayment().setVisible(true);
    }

}
