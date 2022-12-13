package Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerMain extends JFrame{
    private JLabel advertisementLabel;
    private JPanel advertisementPanel;
    private JLabel advertisementLabel3;
    private JLabel advertisementLabel2;
    private JButton latestNotificationButton;
    private JLabel welcomeBackLabel;
    private JPanel notificationPanel;
    private JButton bookYourCarButton;
    private JButton makePaymentButton;
    private JButton bookingHistoryButton;
    private JButton returnTheCarButton;
    private JButton logoutButton;
    private JPanel buttonPanel;
    private JPanel customerMainPanel;

    public CustomerMain(){
        bookYourCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CarBooking().setVisible(true);
                dispose();
            }
        });
        returnTheCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CarReturn().setVisible(true);
                dispose();
            }
        });
        setVisible(true);
        setSize(new Dimension(900, 500));
        setContentPane(customerMainPanel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args){
        new CustomerMain().setVisible(true);
    }
}
