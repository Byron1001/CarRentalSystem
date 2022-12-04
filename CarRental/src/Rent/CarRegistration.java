package Rent;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CarRegistration extends JFrame{
    private JTextField carRegField;
    private JTextField carMakeFieldTextField = new JTextField();
    private JTextField carModelField = new JTextField();
    private JTextField carAvailableField = new JTextField();
    private JButton addButton = new JButton();
    private JButton editButton = new JButton();
    private JButton deleteButton = new JButton();
    private JButton cancelButton = new JButton();
    private JTable table1;
    private JPanel carRegPanel = new JPanel();
    private JScrollPane scrollPanel1 = new JScrollPane();

//    private void createTable(){
//        String[] columns = {"Car Registration No.", "Make", "Model", "Available"};
//
//        String[][] data = {
//                {"Car_01", "2002", "AMG", "Yes"},
//                {"Car_02", "2012", "AMG", "No"}
//        };
//
//        table1 = new JTable(data, columns){
//            public boolean isCellEditable(int data, int columns){
//                return false;
//            }
//            public Component prepareComponentRenderer(TableCellRenderer r, int data, int columns){
//                Component c = super.prepareRenderer(r, data, columns);
//                if(data % 2 == 0)
//                {
//                    c.setBackground(Color.white);
//                }
//                else {
//                    c.setBackground(Color.lightGray);
//                }
//                if(isCellSelected(data, columns)){
//                    c.setBackground(Color.blue);
//                }
//                return c;
//            }
//        };
//        table1.setPreferredScrollableViewportSize(new Dimension(450, 63));
//        table1.setFillsViewportHeight(true);
//        scrollPanel1.add(table1);
//        carRegPanel.add(scrollPanel1);
//        add(carRegPanel);
//    }

    public CarRegistration(){
        setSize(900, 500);
        setContentPane(carRegPanel);
        setVisible(true);
    }

    private void createUIComponents() {

    }
}
