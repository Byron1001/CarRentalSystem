package Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CarReturn extends JFrame{
    private JFormattedTextField carIDField;
    private JButton returnButton;
    private JButton cancelButton;
    private JPanel infoPanel = new JPanel();
    private JLabel carIDLabel;
    private JTable rentTable;
    private JScrollPane rentPane;
    private Scanner scanner;
    File carDataFile = new File("./CarRental/src/Data/Car Data.txt");
    File bookingHistoryFile = new File("./CarRental/src/Data/Booking History.txt");
    File returnHistoryFile = new File("./CarRental/src/Data/Return History.txt");
    File paymentFile = new File("./CarRental/src/Data/Payment.txt");
    File loginHistoryFile = new File("./CarRental/src/Data/Login History.txt");
    String[] rentalColumns = {"Customer ID", "Car Registration No.", "Rental date", "Due Date", "Return Date"};
    String username;

    private static JTable createTable(ArrayList<Object[]> data, String[] columns){
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int columns){
                return false;
            }
        };
        JTable tempTable = new JTable(model);

        for (String column : columns) {
            model.addColumn(column);
        }
        for (Object[] datum : data) {
            model.addRow(datum);
        }
        TableColumnModel col = tempTable.getColumnModel();
        col.removeColumn(col.getColumn(0));
        tempTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tempTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        return tempTable;
    }
    public CarReturn() throws ParseException {

        init();
        infoPanel.setLayout(new GridLayout(10, 2));
        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        username = getUsername(loginHistoryFile);

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

        JLabel returnDateLabel = new JLabel("Return date");
        infoPanel.add(returnDateLabel);

        MaskFormatter formatter5 = new MaskFormatter("##-##-####");
        JFormattedTextField returnDateField = new JFormattedTextField(formatter5);
        infoPanel.add(returnDateField);

        ArrayList<Object[]> carData = getData(carDataFile);
        ArrayList<Object[]> rentData = getData(bookingHistoryFile);
        ArrayList<Object[]> returnData = getData(returnHistoryFile);

        rentTable = createTable(rentData, rentalColumns);

        rentPane = new JScrollPane(rentTable);
        rentTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = rentTable.getSelectedRow();
                carIDField.setText(rentTable.getValueAt(selectedIndex, 0).toString());
                rentalDateField.setText(rentTable.getValueAt(selectedIndex, 1).toString());
                dueDateField.setText(rentTable.getValueAt(selectedIndex, 2).toString());

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Calendar calendar = Calendar.getInstance();
                returnDateField.setText(format.format(calendar.getTime()));
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

        returnButton = new JButton("Return");
        infoPanel.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedCarIndex = getSelectedCarIndex(username, carIDField.getText(), rentalDateField.getText(), rentData);
                Object[] rent = {username, carIDField.getText(), rentalDateField.getText(), dueDateField.getText(), returnDateField.getText(), "Yes", "No"};
                int carIndex = getCarIndex(carIDField.getText(), carData);
                Object[] car = carData.get(carIndex);
                car[3] = "Yes";
                carData.remove(carIndex);
                carData.add(car);

                rentData.remove(selectedCarIndex);
                returnData.add(rent);
                saveData(rentData, bookingHistoryFile);
                saveData(carData, carDataFile);
                saveData(returnData, returnHistoryFile);
                try {
                    int delay = fineDateCount(returnDateField.getText(), dueDateField.getText());
                    int rentDay = rentalDateCount(rentalDateField.getText(), returnDateField.getText());
                    Object[] payment = {username, carIDField.getText(), returnDateField.getText(), rentDay, delay, rentDay*20, delay*50};
                    String message = "Car ID: " + carIDField.getText() + "\n" +
                            "Rental date: " + rentalDateField.getText() + "\n" +
                            "Return date: " + returnDateField.getText() + "\n" +
                            "Due date: " + dueDateField.getText() + "\n" +
                            "Your rental payment:" + payment[5] + "\n" +
                            "Your fine payment:" + payment[6] + "\n";
                    addPayment(payment);
                    JOptionPane.showMessageDialog(null, message, "Rental summary", JOptionPane.INFORMATION_MESSAGE);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }

                CarReturn rental;
                try {
                    rental = new CarReturn();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                rental.setVisible(true);
                dispose();
            }
        });

        cancelButton = new JButton("Cancel");
        infoPanel.add(cancelButton);
        infoPanel.add(new JSeparator());
        infoPanel.add(new JSeparator());

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerMain().setVisible(true);
                saveData(rentData, bookingHistoryFile);
                saveData(carData, carDataFile);
                dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3, 20, 20));
        panel.add(rentPane);
        panel.add(infoPanel);

        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(900, 500));
        setTitle("Customer Car return");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        try {
            carDataFile.createNewFile();
            bookingHistoryFile.createNewFile();
            loginHistoryFile.createNewFile();
            returnHistoryFile.createNewFile();
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
            if (file == bookingHistoryFile || file == returnHistoryFile){
                String[] data = row.split(":", 7);
                if (data.length > 1){
                    if (file == bookingHistoryFile){
                        Object[] da = {data[0], data[1], data[2], data[3], data[4], data[5], data[6]};
                        tempData.add(da);
                    }
                    if (file == returnHistoryFile){
                        Object[] da = {data[0], data[1], data[2], data[3], data[4], data[5], data[6]};
                        tempData.add(da);
                    }
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
                String whole = ob[0] + ":" + ob[1] + ":" + ob[2] + ":" + ob[3];
                if (file == bookingHistoryFile)
                    whole += ":" + ob[4] + ":" + ob[5] + ":" + ob[6];
                if (file == returnHistoryFile)
                    whole += ":" + ob[4] + ":" + ob[5] + ":" + ob[6];
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

    private void addPayment(Object[] payment){
        try {
            FileWriter writer = new FileWriter(paymentFile, true);
            String whole = payment[0].toString();
            for(int i = 1;i < payment.length;i++){
                whole = whole + ":" + payment[i].toString();
            }
            whole += ":No\n";
            writer.write(whole);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getCarIndex(String carID, ArrayList<Object[]> carData){
        for (Object[] d : carData){
            if (carID.equals(d[0].toString()))
                return carData.indexOf(d);
        }
        return -1;
    }
    protected static int fineDateCount(String returnDate, String dueDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date returnD = formatter.parse(returnDate);
        Date due = formatter.parse(dueDate);

        long diffInMil = returnD.getTime() - due.getTime();
        System.out.println(diffInMil);
        if (diffInMil < 0)
            return 0;
        long diff = Math.abs(TimeUnit.DAYS.convert(diffInMil, TimeUnit.MILLISECONDS));
        return (int) diff;
    }

    protected static int rentalDateCount(String returnDate, String dueDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date returnD = formatter.parse(returnDate);
        Date due = formatter.parse(dueDate);

        long diffInMil = returnD.getTime() - due.getTime();
        if (diffInMil > 0)
            return 0;
        long diff = Math.abs(TimeUnit.DAYS.convert(diffInMil, TimeUnit.MILLISECONDS));
        return (int) diff;
    }

    private String getUsername(File loginHistoryFile){
        String username = null;
        Scanner scanner1;
        try {
            scanner1 = new Scanner(loginHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner1.hasNextLine()){
            String row = scanner1.nextLine();
            String[] record = row.split("/");
            username = record[2];
        }
        return username;
    }

    private int getSelectedCarIndex(String username, String carID, String rentalDate, ArrayList<Object[]> rentData){
        for(Object[] dd : rentData){
            if (username.equals(dd[0].toString()) && carID.equals(dd[1].toString()) && rentalDate.equals(dd[2].toString()))
                return rentData.indexOf(dd);
        }
        return -1;
    }


}
