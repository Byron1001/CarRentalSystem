package Admin;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
        setSize(900, 500);

        finalData = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(carDataFile);
            while(scanner.hasNextLine()){
                String row = scanner.nextLine();
                Object[] carData = row.split(":", 4);
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
        setLocationRelativeTo(null);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carRegNoString =  carRegNoField.getText();
                carMakeInteger = Integer.parseInt(carMakeField.getText());
                carModelString = carModelField.getText();
                carAvailableString = carAvailableBox.getSelectedItem().toString();

                Object[] row = {carRegNoString, carMakeInteger, carModelString, carAvailableString};
                finalData.add(row);
                saveCarData(finalData);
                dispose();
                new CarRegistration().setVisible(true);
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
                Object[] row = {carRegNoString, carMakeInteger, carModelString, carAvailableString};
                finalData.set(selectedIndex, row);
                saveCarData(finalData);
                dispose();
                new CarRegistration().setVisible(true);
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

    }

    private void createUIComponents() {
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
            for(Object[] car : data){
                String row = car[0] + car[1].toString() + car[2] + car[3];
                printWriter.println(row);
            }
            printWriter.close();
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
