import Admin.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{
    private JPanel mainPanel;
    private JTextField UsernameField;
    private JTextField passwordField;
    private JButton registerButton;
    private JButton loginButton;

    private void createUIComponents() {
        UsernameField = new JTextField();
        passwordField = new JPasswordField();
    }

    public Login(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = UsernameField.getText();
                String password = passwordField.getText();

                if (username.equals("john") && password.equals("123")){
                    Main main = new Main();
                    hide();
                    main.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Username or Password do not match.");
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new Register().setVisible(true);
            }
        });

        setContentPane(mainPanel);
        setTitle("Welcome");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        setResizable(false);
    }
}
