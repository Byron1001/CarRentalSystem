package Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckLoginRecord extends JFrame {
    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton backButton;
    private Scanner scanner;
    private ArrayList<Object[]> loginData;
    File loginHistoryFile = new File("./CarRental/src/Data/Login History.txt");
    public CheckLoginRecord(){
        init();
        loginData = getLoginHistory(loginHistoryFile);

        backButton = new JButton("Back to main menu");

        panel = new JPanel();
        panel.setLayout(new GridLayout(100, 1));
        for (Object[] data :loginData){
            String row1 = "Date: " + data[0];
            String row2 = "Login Time: " + data[1];
            String row3 = "Username: " + data[2];
            String row4 = "Character: " + data[3];
            panel.add(new JLabel(row1));
            panel.add(new JLabel(row2));
            panel.add(new JLabel(row3));
            panel.add(new JLabel(row4));
            panel.add(new JLabel());
        }
        panel.add(new JLabel("Login History print finished."), JScrollPane.CENTER_ALIGNMENT);
        panel.add(new JSeparator());
        panel.add(backButton);

        scrollPane = new JScrollPane(panel);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminMain().setVisible(true);
                dispose();
            }
        });

        setContentPane(scrollPane);
        setSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        setTitle("Login Record Review for admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void init(){
        try {
            loginHistoryFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Object[]> getLoginHistory(File loginHistoryFile){
        ArrayList<Object[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(loginHistoryFile);
            while (scanner.hasNextLine()){
                String row = scanner.nextLine();
                String[] rowData = row.split("/", 4);
                if (rowData.length > 1){
                    Object[] forAdded = {rowData[0], rowData[1], rowData[2], rowData[3]};
                    tempData.add(forAdded);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.close();
        return tempData;
    }

}
