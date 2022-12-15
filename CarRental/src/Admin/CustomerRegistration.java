package Admin;

import Customer.Customer;
import Data.CustomerData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerRegistration extends JFrame implements MouseListener {
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField, passwordField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JButton cancelButton;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private String usernameString, passwordString;
    private JTable table1;
    private Scanner scanner;
    ArrayList<Customer> data;
    private String[] columns = {"Username", "Password"};
    File customerDataFile = new File("./CarRental/src/Data/Customer Data.txt");

    private JTable createTable(ArrayList<Customer> data){
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int columns){
                return false;
            }
        };
        JTable temp_table = new JTable(model);

        for (String col : columns){
            model.addColumn(col);
        }

        for(int i = 0; i < data.size();i++){
            model.addRow(new String[] {data.get(i).getUsername(), data.get(i).getPassword()});
        }
        temp_table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        return temp_table;
    }

    public CustomerRegistration(){

        init();
        data = getData(customerDataFile);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        table1 = createTable(data);
        table1.addMouseListener(this);
        scrollPane = new JScrollPane(table1);
        panel.add(scrollPane);

        usernameLabel = new JLabel("Username");
        usernameField = new JTextField();
        passwordLabel = new JLabel("Password");
        passwordField = new JTextField();
        addButton = new JButton("Add");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));
        buttonPanel.add(new JSeparator());
        buttonPanel.add(new JSeparator());
        buttonPanel.add(usernameLabel);
        buttonPanel.add(usernameField);
        buttonPanel.add(passwordLabel);
        buttonPanel.add(passwordField);
        buttonPanel.add(new JSeparator());
        buttonPanel.add(new JSeparator());
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);

        setContentPane(panel);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setTitle("Admin Customer Registration and Authorization Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameString =  usernameField.getText();
                passwordString = passwordField.getText();
                boolean check = checkUserAvail(usernameString);
                if (check){
                    data.add(new Customer(usernameString, passwordString, null));
                    saveData(data);
                    new CustomerRegistration().setVisible(true);
                    JOptionPane.showMessageDialog(null, "Customer Adding success", "Customer Adding", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "No repeated Username allowed!", "Username repeated", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();

                usernameString = usernameField.getText();
                passwordString = passwordField.getText();
                data.set(selectedIndex, new Customer(usernameString, passwordString, null));
                saveData(data);
                new CustomerRegistration().setVisible(true);
                JOptionPane.showMessageDialog(null, "Customer Information modified.", "Customer information modification", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();
                data.remove(selectedIndex);
                saveData(data);
                new CustomerRegistration().setVisible(true);
                JOptionPane.showMessageDialog(null, "Customer Deleted", "Customer Delete", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData(data);
                new AdminMain().setVisible(true);
                dispose();
            }
        });
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Character c  = e.getKeyChar();
                Pattern punct = Pattern.compile("\\p{Punct}");
                Matcher m = punct.matcher(c.toString());
                if (m.find()){
                    JOptionPane.showMessageDialog(null, "Please enter valid character!");
                    e.consume();
                }
                if (c == KeyEvent.VK_ENTER)
                    passwordField.requestFocus();
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedIndex = 0;
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) table1.getModel();
        selectedIndex = table1.getSelectedRow();

        usernameField.setText((String) model.getValueAt(selectedIndex, 0));
        passwordField.setText(model.getValueAt(selectedIndex, 1).toString());
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
    private ArrayList<Customer> getData(File customerDataFile){
        ArrayList<Customer> tempData = new ArrayList<Customer>();
        try {
            scanner = new Scanner(customerDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] datarow = row.split(":");
            if (datarow.length != 1){
                Customer cust = new Customer(datarow[0], datarow[1], null);
                tempData.add(cust);
            }
        }
        scanner.close();

        return tempData;
    }
    private void init(){
        try {
            customerDataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveData(ArrayList<Customer> data){
        try {
            FileWriter writer = new FileWriter(customerDataFile, false);
            int count = 1;
            for (Customer cust : data){
                String row = cust.getUsername() + ":" + cust.getPassword() + "\n";
                if (count == data.size())
                    row += "\n";
                writer.write(row);
                count++;
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private boolean checkUserAvail(String username){
        for (Customer cust : data){
            if (cust.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

}
