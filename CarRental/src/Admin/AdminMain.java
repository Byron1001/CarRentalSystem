package Admin;

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
                CustomerRegistration reg = new CustomerRegistration(null);
                hide();
                reg.setVisible(true);
            }
        });
        rentalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RentalManagement reg = null;
                try {
                    reg = new RentalManagement(null, null);
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
                CarRegistration reg = new CarRegistration();
                hide();
                reg.setVisible(true);
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
    }

    public JFrame getMainFrame(){
        return this;
    }
}
