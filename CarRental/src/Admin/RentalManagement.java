package Admin;

import javax.print.attribute.standard.JobKOctetsProcessed;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

public class RentalManagement extends JFrame{
    private JTextField customerIDField;
    private JFormattedTextField carIDField;
    private JButton modifyButton;
    private JButton rentButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JPanel buttonPanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JLabel customerIDLabel;
    private JLabel carIDLabel;
    private JTable availableTable, rentTable;
    private JScrollPane availablePane, rentPane;
    private Scanner scanner;
    File carDataFile = new File("./CarRental/src/Data/Car Data.txt");
    File bookingHistoryFile = new File("./CarRental/src/Data/Booking History.txt");
    String[] carColumns = {"Car Registration No", "Make", "Model", "Available"};
    String[] rentalColumns = {"Customer ID", "Car Registration No.", "Rental date", "Due Date", "Return Date" + "Return" + "Payment"};

    private static JTable createTable(ArrayList<Object[]> data, String[] columns){
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int columns){
                return false;
            }
        };
        JTable tempTable = new JTable(model);

        for (int i = 0; i < columns.length;i++){
            model.addColumn(columns[i]);
        }
        for (Object[] i : data) {
            model.addRow(i);
        }
        tempTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tempTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        return tempTable;
    }
    public RentalManagement() throws ParseException {

        init();
        infoPanel.setLayout(new GridLayout(9, 2));
        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        customerIDLabel = new JLabel("Customer ID");
        infoPanel.add(customerIDLabel);

        customerIDField = new JTextField();
        infoPanel.add(customerIDField);

        carIDLabel = new JLabel("Car Registration No.");
        infoPanel.add(carIDLabel);

        MaskFormatter formatter2 = new MaskFormatter("Car_##");
        carIDField = new JFormattedTextField(formatter2);
        infoPanel.add(carIDField);

        JLabel rentalDateLabel = new JLabel("Rental date");
        infoPanel.add(rentalDateLabel);

        MaskFormatter formatter3 = new MaskFormatter("##-##-####");
        JFormattedTextField rentalDateField = new JFormattedTextField(formatter3);
        infoPanel.add(rentalDateField);

        JLabel dueDateLabel = new JLabel("Due date");
        infoPanel.add(dueDateLabel);

        MaskFormatter formatter4 = new MaskFormatter("##-##-####");
        JFormattedTextField dueDateField = new JFormattedTextField(formatter4);
        infoPanel.add(dueDateField);

        setLayout(new GridLayout(1, 3, 10, 0));

        ArrayList<Object[]> carData = getData(carDataFile);

        ArrayList<Object[]> rentData = getData(bookingHistoryFile);

        availableTable = createTable(carData, carColumns);
        rentTable = createTable(rentData, rentalColumns);

        availablePane = new JScrollPane(availableTable);
        rentPane = new JScrollPane(rentTable);

        availableTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = availableTable.getSelectedRow();
                carIDField.setValue(availableTable.getValueAt(selectedIndex, 0));

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Calendar calendar = Calendar.getInstance();
                rentalDateField.setText(format.format(calendar.getTime()));

                calendar.add(Calendar.DATE, 1);
                dueDateField.setText(format.format(calendar.getTime()));

                customerIDField.setText(null);
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        rentTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = rentTable.getSelectedRow();
                customerIDField.setText(rentTable.getValueAt(selectedIndex, 0).toString());
                carIDField.setText(rentTable.getValueAt(selectedIndex, 1).toString());
                rentalDateField.setText(rentTable.getValueAt(selectedIndex, 2).toString());
                dueDateField.setText(rentTable.getValueAt(selectedIndex, 3).toString());
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        modifyButton = new JButton("Modify");
        infoPanel.add(modifyButton);

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = rentTable.getSelectedRow();
                if (selectedIndex == -1){
                    JOptionPane.showMessageDialog(null, "Please choose the car!", "error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    Object[] data = rentData.get(selectedIndex);
                    data[0] = customerIDField.getText();
                    data[1] = carIDField.getText();
                    data[2] = rentalDateField.getText();
                    data[3] = dueDateField.getText();

                    boolean check = checkUserAvail(data[0].toString());
                    boolean check2 = checkCarAvail(data[1].toString());
                    if (check && check2){
                        rentData.remove(selectedIndex);
                        rentData.add(data);
                        saveData(rentData, bookingHistoryFile);

                        RentalManagement reg = null;
                        try {
                            new RentalManagement().setVisible(true);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        JOptionPane.showMessageDialog(null, "Rental information modified.", "Rental information modification", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Car ID or Customer ID not existed", "Input error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        rentButton = new JButton("Rent");
        infoPanel.add(rentButton);
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = availableTable.getSelectedRow();
                if (selectedIndex == -1){
                    JOptionPane.showMessageDialog(null, "Please choose the rental record!", "error", JOptionPane.ERROR_MESSAGE);
                }
                else if(carData.get(selectedIndex)[3] == "No"){
                    JOptionPane.showMessageDialog(null, "Please choose the available car!", "Car choosing error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    Object[] rent = {customerIDField.getText(), carIDField.getText(), rentalDateField.getText(), dueDateField.getText(), "00-00-0000", "No", "No"};
                    boolean check = checkUserAvail(rent[0].toString());
                    if (check){
                        Object[] car = carData.get(selectedIndex);
                        rentData.add(rent);
                        car[3] = "No";
                        carData.remove(selectedIndex);
                        carData.add(car);
                        saveData(rentData, bookingHistoryFile);
                        saveData(carData, carDataFile);
                        RentalManagement rental = null;
                        try {
                            rental = new RentalManagement();
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        rental.setVisible(true);
                        JOptionPane.showMessageDialog(null, "Car rental success.\nPlease ask customer to make payment before getting the car.", "Car rental successful", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Username error", "Username error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deleteButton = new JButton("Delete");
        infoPanel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = rentTable.getSelectedRow();
                if (selectedIndex == -1 || customerIDField.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please choose the rental record!", "error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    String carID = rentData.get(selectedIndex)[1].toString();
                    for (Object[] car : carData) {
                        if (car[0].equals(carID)) {
                            car[3] = "Yes";
                            rentData.remove(selectedIndex);
                        }
                    }
                    saveData(rentData, bookingHistoryFile);
                    saveData(carData, carDataFile);
                    try {
                        new RentalManagement().setVisible(true);
                        JOptionPane.showMessageDialog(null, "Rental record deleted", "Rental record delete", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        cancelButton = new JButton("Cancel");
        infoPanel.add(cancelButton);
        infoPanel.add(new JSeparator());
        infoPanel.add(new JSeparator());

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminMain().setVisible(true);
                saveData(rentData, bookingHistoryFile);
                saveData(carData, carDataFile);
                dispose();
            }
        });

        add(availablePane);
        add(rentPane);
        add(infoPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(900, 500));
        setTitle("Admin Rental Management");
        setLocationRelativeTo(null);
    }

    private void init(){
        try {
            carDataFile.createNewFile();
            bookingHistoryFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Object[]> getData(File file){
        ArrayList<Object[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            if (file == bookingHistoryFile){
                String[] data = row.split(":", 7);
                if (data.length > 1){
                    Object[] da = {data[0], data[1], data[2], data[3], data[4], data[5], data[6]};
                    tempData.add(da);
                }
            }
            else {
                String[] data = row.split(":", 4);
                if (data.length > 1){
                    Object[] da = {data[0], data[1], data[2], data[3]};
                    tempData.add(da);
                }
            }
        }
        scanner.close();
        return tempData;
    }

    private void saveData(ArrayList<Object[]> data, File file){
        try {
            FileWriter writer = new FileWriter(file, false);
            for(Object[] ob:data){
//                System.out.println(Arrays.toString(ob));
                String whole = ob[0] + ":" + ob[1] + ":" + ob[2] + ":" + ob[3];
                if (file == bookingHistoryFile){
                    whole += ":" + ob[4] + ":" + ob[5] + ":" + ob[6];
                }
                whole += "\n";
                if(ob.length == data.indexOf(ob) + 1){
                    whole += "\n";
                }
                writer.write(whole);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkUserAvail(String username){
        File customerDataFile = new File("./CarRental/src/Data/Customer Data.txt");
        try {
            scanner = new Scanner(customerDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] data = row.split(":", 2);
            if (data[0].equals(username))
                return true;
        }
        scanner.close();
        return false;
    }

    private boolean checkCarAvail(String carID){
        File customerDataFile = new File("./CarRental/src/Data/Car Data.txt");
        try {
            scanner = new Scanner(customerDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] data = row.split(":", 4);
            if (data[0].equals(carID))
                return true;
        }
        scanner.close();
        return false;
    }

    public static void main(String[] args) throws ParseException {
        new RentalManagement().setVisible(true);
    }

}
