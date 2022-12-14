package LoginRegister;

import Admin.AdminMain;
import Customer.CustomerMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class Login extends JFrame{
    private JPanel mainPanel;
    private JTextField UsernameField;
    private JTextField passwordField;
    private JButton registerButton;
    private JButton loginButton;
    String row;
    String[] credentials;
    File loginHistoryFile = new File("./CarRental/src/Data/Login History.txt");

    private void createUIComponents() {
        UsernameField = new JTextField();
        passwordField = new JPasswordField();
    }

    private int loginVerification(String username, String password){
        File customerDataFile = new File("./CarRental/src/Data/Customer Data.txt");
        File adminDataFile = new File("./CarRental/src/Data/Admin Data.txt");
        Scanner scanner;
        row = "";
        credentials = new String[]{};
        try {
            scanner = new Scanner(customerDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            row = scanner.nextLine();
            credentials = row.split(":", 2);
            if (credentials[0].equals(username) && credentials[1].equals(password))
                return 1;
        }
        try {
            scanner = new Scanner(adminDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanner.hasNextLine()){
            row = scanner.nextLine();
            credentials = row.split(":", 2);
            if (credentials[0].equals(username) && credentials[1].equals(password))
                return 2;
        }
        scanner.close();
        return 0;
    }

    private void init(){
        try {
            loginHistoryFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loginRecord(String username, int credentials){
        try {
            FileWriter writer = new FileWriter(loginHistoryFile);
            String record = LocalDate.now() + "|" + LocalTime.now() + "|" + username;
            if (credentials == 1)
                record = record + "|" + "User";
            else if (credentials == 2)
                record = record + "|" + "Admin";
            record += "\n";
            writer.write(record);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Login(){
        init();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = UsernameField.getText();
                String password = passwordField.getText();
                int check = loginVerification(username, password);

                if (check == 2){
                    new AdminMain().setVisible(true);
                    loginRecord(username, check);
                    dispose();
                } else if (check == 1) {
                    new CustomerMain().setVisible(true);
                    loginRecord(username, check);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Username or Password do not match.");
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new Register().setVisible(true);
                    dispose();
            }
        });

        setContentPane(mainPanel);
        setTitle("Welcome");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        UsernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    passwordField.requestFocus();
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER)
                    loginButton.doClick();
            }
        });
    }

}
