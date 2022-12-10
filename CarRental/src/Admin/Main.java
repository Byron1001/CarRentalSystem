package Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class Main extends JFrame {

    private JButton carRegistrationButton;
    private JButton customerButton;
    private JButton rentalButton;
    private JButton returnButton;
    private JButton logoutButton;
    private JPanel mainPanel;

    public Main(){

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        carRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CarRegistration reg = new CarRegistration(null);
                hide();
                reg.setVisible(true);
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
                CarRegistration reg = new CarRegistration(null);
                hide();
                reg.setVisible(true);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "See You Again.");
                dispose();
                Login new_login = new Login();
                new_login.setVisible(true);
            }
        });
    }

    public JFrame getMainFrame(){
        return this;
    }
}
