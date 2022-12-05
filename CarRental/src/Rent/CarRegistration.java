package Rent;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CarRegistration extends JFrame{

    private JPanel carRegPanel;
    private JTextField carRegField;
    private JTextField carMakeField;
    private JTextField carModelField;
    private JTable table1, overload_table;
    private JLabel carRegNo;
    private JLabel carMake;
    private JLabel carModel;
    private JLabel carAvailable;
    private JComboBox carAvailableBox;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton cancelButton;

    private JScrollPane scrollPane;
    private String carRegNoString, carModelString;
    private int carMakeInteger;
    private String carAvailableString;

    private Object[] columns = {"Car Registration No.", "Make", "Model", "Available"};

    private JTable createTable(ArrayList<Object[]> data){
            DefaultTableModel model = new DefaultTableModel();
            JTable temp_table = new JTable(model);

            for(int i = 0;i < columns.length;i++){
                model.addColumn(columns[i]);
            }

            for(int i = 0; i < data.size();i++){
                model.addRow(data.get(i));
            }

            return temp_table;
        }

    public CarRegistration(ArrayList<Object[]> data){
        carAvailableBox.addItem("Yes");
        carAvailableBox.addItem("No");

        this.setLayout(new GridLayout(2, 1));
        this.setSize(900, 500);

        if(data == null){
            data = new ArrayList<Object[]>();
            data.add(new Object[]{"Car_01", 2002, "AMG", "Yes"});
            data.add(new Object[]{"Car_02", 2012, "AMG", "No"});

            table1 = createTable(data);
            scrollPane = new JScrollPane(table1);
        }
        else{
            overload_table = createTable(data);
            scrollPane = new JScrollPane(overload_table);
        }

        this.add(scrollPane);
        this.add(carRegPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        ArrayList<Object[]> finalData = data;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carRegNoString =  carRegField.getText();
                carMakeInteger = Integer.parseInt(carMakeField.getText());
                carModelString = carModelField.getText();
                carAvailableString = carAvailableBox.getSelectedItem().toString();

                Object[] row = {carRegNoString, carMakeInteger, carModelString, carAvailableString};
                finalData.add(row);
                dispose();
                CarRegistration new_reg = new CarRegistration(finalData);
            }
        });
    }

    private void createUIComponents() {
    }

    public static void main(String[] args){
        CarRegistration reg = new CarRegistration(null);
    }
}
