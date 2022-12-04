package Rent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarRegistration extends JFrame{
    private JTextField carRegField = new JTextField();
    private JTextField carMakeField = new JTextField();
    private JTextField carModelField = new JTextField();
    private JTextField carAvailableField = new JTextField();
    private JButton addButton = new JButton();
    private JButton editButton = new JButton();
    private JButton deleteButton = new JButton();
    private JButton cancelButton = new JButton();
    private JTable table1;
    private JPanel carRegPanel = new JPanel();
    private JScrollPane scrollPanel1 = new JScrollPane();

    private void createTable(){
        String[] columns = {"Car Registration No.", "Make", "Model", "Available"};

        String[][] data = {
                {"Car_01", "2002", "AMG", "Yes"},
                {"Car_02", "2012", "AMG", "No"}
        };

        DefaultTableModel model = new DefaultTableModel();
        table1 = new JTable(model);
        for(int i = 0;i < columns.length;i++){
            model.addColumn(columns[i]);
        }
        for(int i = 0; i < data.length;i++){
            model.addRow(data[i]);
        }

    }

    public CarRegistration(){
        createTable();
        setSize(900, 500);
        setContentPane(carRegPanel);
        setVisible(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() {

    }

    public static void main(String[] args){
        CarRegistration reg = new CarRegistration();
    }
}
