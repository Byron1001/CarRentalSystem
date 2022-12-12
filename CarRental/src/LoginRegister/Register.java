package LoginRegister;

import Data.CustomerData;
import Admin.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Register extends JFrame{
    private JTextField usernameField;
    private JPasswordField confirmPasswordField;
    private JPasswordField passwordField;
    private JLabel logoLabel;
    private JPanel registerPanel;
    private JPanel rightPanel;
    private JLabel usernameLabel;
    private JLabel confirmPasswordLabel;
    private JLabel passwordLabel;
    private JButton registerButton;
    private JButton adminRegistrationButton;
    String username, password;

    public Register(){

        confirmPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    registerButton.doClick();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                if (Arrays.toString(passwordField.getPassword()).equals(Arrays.toString(confirmPasswordField.getPassword()))){
                    password = String.valueOf(passwordField.getPassword());
                    CustomerData cust = new CustomerData();
                    boolean success = cust.register(username, password);
                    if (success){
                        JOptionPane.showMessageDialog(null, "Registration success.\nPlease login with your username and password.");
                        new Login().setVisible(true);
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Password or username error. Reason: username includes punctuation or username exists.", "Password error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Password or username error.", "Password error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    passwordField.requestFocus();
                }
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    confirmPasswordField.requestFocus();
                }
            }
        });

        setContentPane(registerPanel);
        setTitle("New Customer Registration");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        adminRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = JOptionPane.showInputDialog(null, "Please enter the admin secret code.");
                if(code.equals("////")){
                    username = usernameField.getText();
                    if (Arrays.toString(passwordField.getPassword()).equals(Arrays.toString(confirmPasswordField.getPassword()))){
                        password = String.valueOf(passwordField.getPassword());
                        Admin admin = new Admin();
                        boolean success = admin.register(username, password);
                        if (success){
                            JOptionPane.showMessageDialog(null, "Registration success.\nPlease login with your username and password.");
                            new Login().setVisible(true);
                            dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Password or username error. Reason: username includes punctuation or username exists.", "Password error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Password or username error.", "Password error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error secret code.\n Please get it from authorities!", "Secret code error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args){
        new Register().setVisible(true);
    }
}
