package Rent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private JButton carRegistrationButton;
    private JButton customerButton;
    private JButton rentalButton;
    private JButton returnButton;
    private JButton logoutButton;
    private JPanel mainPanel;

    public Main(){
        carRegistrationButton = new JButton();
        customerButton = new JButton();
        rentalButton = new JButton();
        returnButton = new JButton();
        logoutButton = new JButton();

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        carRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello World");
                CarRegistration reg = new CarRegistration();
                dispose();
                reg.setVisible(true);
            }
        });
    }
}
