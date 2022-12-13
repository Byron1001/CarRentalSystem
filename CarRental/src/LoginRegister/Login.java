package LoginRegister;

import Admin.AdminMain;
import Customer.CustomerMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Login extends JFrame{
    private JPanel mainPanel;
    private JTextField UsernameField;
    private JTextField passwordField;
    private JButton registerButton;
    private JButton loginButton;
    String row;
    String[] credentials;

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

    public Login(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = UsernameField.getText();
                String password = passwordField.getText();
                int check = loginVerification(username, password);

                if (check == 2){
                    new AdminMain().setVisible(true);
                    dispose();
                } else if (check == 1) {
                    new CustomerMain().setVisible(true);
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
    }

}
