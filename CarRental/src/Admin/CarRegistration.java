package Admin;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CarRegistration extends JFrame implements MouseListener {

    private JPanel carRegPanel;
    private JTextField carRegNoField;
    private JTextField carMakeField;
    private JTextField carModelField;
    private JTable table1, overload_table;
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

    private Object[] columns = {"Car Registration No.", "Make", "Model", "Available"};

    private JTable createTable(ArrayList<Object[]> data){
            DefaultTableModel model = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int columns){
                    return false;
                }
            };
            JTable temp_table = new JTable(model);

            for(int i = 0;i < columns.length;i++){
                model.addColumn(columns[i]);
            }

            for(int i = 0; i < data.size();i++){
                model.addRow(data.get(i));
            }

            temp_table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));

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
            table1.addMouseListener(this);
            scrollPane = new JScrollPane(table1);
        }
        else{
            overload_table = createTable(data);
            overload_table.addMouseListener(this);
            scrollPane = new JScrollPane(overload_table);
        }

        this.add(scrollPane);
        this.add(carRegPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        finalData = data;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carRegNoString =  carRegNoField.getText();
                carMakeInteger = Integer.parseInt(carMakeField.getText());
                carModelString = carModelField.getText();
                carAvailableString = carAvailableBox.getSelectedItem().toString();

                Object[] row = {carRegNoString, carMakeInteger, carModelString, carAvailableString};
                finalData.add(row);
                dispose();
                CarRegistration new_reg = new CarRegistration(finalData);
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
                dispose();
                CarRegistration new_reg = new CarRegistration(finalData);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();
                finalData.remove(selectedIndex);
                dispose();
                CarRegistration new_reg = new CarRegistration(finalData);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main new_main = new Main();
                new_main.setVisible(true);
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
        else if(e.getSource() == overload_table){
            model = (DefaultTableModel) overload_table.getModel();
            selectedIndex = overload_table.getSelectedRow();
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
}
