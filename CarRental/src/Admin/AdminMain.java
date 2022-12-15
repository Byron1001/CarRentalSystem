package Admin;

import Customer.Customer;
import LoginRegister.Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class AdminMain extends JFrame {

    private JButton carRegistrationButton;
    private JButton customerButton;
    private JButton rentalButton;
    private JButton returnButton;
    private JButton logoutButton;
    private JPanel mainPanel;
    private JButton generateReportButton;
    private JButton customerRegistrationManagementButton;

    public AdminMain(){
        carRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CarRegistration().setVisible(true);
                dispose();
            }
        });
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerRegistration().setVisible(true);
                hide();
            }
        });
        rentalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RentalManagement reg = null;
                try {
                    reg = new RentalManagement();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                hide();
                reg.setVisible(true);
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ReturnManagement().setVisible(true);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                hide();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "See You Again.");
                dispose();
                new Login().setVisible(true);
            }
        });

        JScrollPane pane = new JScrollPane(mainPanel);
        setContentPane(pane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        customerRegistrationManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerVerify().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args){
        new AdminMain().setVisible(true);
    }
}
