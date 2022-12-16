package Customer;

import LoginRegister.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class CustomerMain extends JFrame{
    private JLabel advertisementLabel;
    private JPanel advertisementPanel;
    private JLabel advertisementLabel3;
    private JLabel advertisementLabel2;
    private JButton latestNotificationButton;
    private JLabel welcomeBackLabel;
    private JButton bookYourCarButton;
    private JButton makePaymentButton;
    private JButton bookingHistoryButton;
    private JButton returnTheCarButton;
    private JButton logoutButton;
    private JPanel notificationPanel;
    private JPanel buttonPanel;
    private JPanel customerMainPanel;

    public CustomerMain(){
        bookYourCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CarBooking().setVisible(true);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
        returnTheCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CarReturn().setVisible(true);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

        setVisible(true);
        setSize(new Dimension(900, 500));
        setContentPane(customerMainPanel);
        setLocationRelativeTo(null);
        makePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerPayment().setVisible(true);
                dispose();
            }
        });
        bookingHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerBookingHistory().setVisible(true);
                dispose();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "See you again.", "Logout", JOptionPane.INFORMATION_MESSAGE);
                new Login().setVisible(true);
                dispose();
            }
        });
        latestNotificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LatestNotification().setVisible(true);
                dispose();
            }
        });
    }

}
