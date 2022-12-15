package Admin;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class CarRegistration extends JFrame implements MouseListener {

    private JPanel carRegPanel;
    private JTextField carRegNoField;
    private JTextField carMakeField;
    private JTextField carModelField;
    private JTable table1;
    private JLabel carRegNoLabel;
    private JLabel carMakeLabel;
    private JLabel carModelLabel;
    private JLabel carAvailableLabel;
    private JComboBox carAvailableBox;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton cancelButton;

    private JScrollPane scrollPane;
    private String carRegNoString, carModelString;
    private int carMakeInteger;
    private String carAvailableString;
    ArrayList<Object[]> finalData;
    File carDataFile = new File("./CarRental/src/Data/Car Data.txt");

    private Object[] columns = {"Car Registration No.", "Make", "Model", "Available"};

    private JTable createTable(ArrayList<Object[]> data){
        DefaultTableModel model = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int columns){
                    return false;
                }
            };
        JTable temp_table = new JTable(model);
        for (Object column : columns)
            model.addColumn(column);
        for (Object[] datum : data)
            model.addRow(datum);
        temp_table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        return temp_table;
    }

    public CarRegistration(){
        init();
        carAvailableBox.addItem("Yes");
        carAvailableBox.addItem("No");

        setLayout(new GridLayout(2, 1));

        finalData = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(carDataFile);
            while(scanner.hasNextLine()){
                String row = scanner.nextLine();
                Object[] carData = row.split(":", 4);
                if (carData.length > 1)
                    finalData.add(carData);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        table1 = createTable(finalData);
        table1.addMouseListener(this);
        scrollPane = new JScrollPane(table1);

        add(scrollPane);
        add(carRegPanel);
        setVisible(true);
        setSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        setTitle("Admin Car Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carRegNoString =  carRegNoField.getText();
                carMakeInteger = Integer.parseInt(carMakeField.getText());
                carModelString = carModelField.getText();
                carAvailableString = carAvailableBox.getSelectedItem().toString();

                if (checkCarIDAvail(carRegNoString)){
                    JOptionPane.showMessageDialog(null, "Car Registration No already exists!", "Car Registration No error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    Object[] row = {carRegNoString, carMakeInteger, carModelString, carAvailableString};
                    finalData.add(row);
                    saveCarData(finalData);
                    new CarRegistration().setVisible(true);
                    dispose();
                }
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();

                carRegNoString = carRegNoField.getText();
                carMakeInteger = Integer.parseInt(carMakeField.getText());
                carModelString = carModelField.getText();
                carAvailableString = carAvailableBox.getSelectedItem().toString();
                if (checkCarIDAvail(carRegNoString)){
                    JOptionPane.showMessageDialog(null, "Car Registration No already exist!", "Car registration No error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    Object[] row = {carRegNoString, carMakeInteger, carModelString, carAvailableString};
                    finalData.set(selectedIndex, row);
                    saveCarData(finalData);
                    new CarRegistration().setVisible(true);
                    dispose();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();
                finalData.remove(selectedIndex);
                saveCarData(finalData);
                dispose();
                new CarRegistration().setVisible(true);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminMain().setVisible(true);
            }
        });

        carMakeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    carModelField.requestFocus();
                else if (e.getKeyChar() > '9' || e.getKeyChar() < '0')
                    e.consume();
            }
        });
        carRegNoField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    carMakeField.requestFocus();
                }
            }
        });
        carModelField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    carAvailableBox.requestFocus();
                }
            }
        });
    }

    private void createUIComponents() {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("Car_##");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        carRegNoField = new JFormattedTextField(formatter);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedIndex = 0;
        DefaultTableModel model = new DefaultTableModel();
        if (e.getSource() == table1){
            model = (DefaultTableModel) table1.getModel();
            selectedIndex = table1.getSelectedRow();
        }
        carRegNoField.setText((String) model.getValueAt(selectedIndex, 0));
        carMakeField.setText(model.getValueAt(selectedIndex, 1).toString());
        carModelField.setText((String) model.getValueAt(selectedIndex, 2));
        carAvailableBox.setSelectedIndex(
                (model.getValueAt(selectedIndex, 3).equals("Yes"))? 0 : ((model.getValueAt(selectedIndex, 3).equals("No")) ? 1 : -1)
        );
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

    private void init(){
        try {
            carDataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveCarData(ArrayList<Object[]> data){
        FileWriter writer;
        try {
            writer = new FileWriter(carDataFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            int count = 0;
            for(Object[] car : data){
                String row = car[0] + ":" + car[1].toString() + ":" + car[2] + ":" + car[3];
                if (count == data.size() - 1)
                    row += "\n";
                printWriter.println(row);
                count++;
            }
            printWriter.close();
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkCarIDAvail(String carID){
        for (Object[] data : finalData){
            if(data[0].toString().equals(carID))
                return false;
        }
        return true;
    }
}
