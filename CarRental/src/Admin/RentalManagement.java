package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RentalManagement extends JFrame{
    private JFormattedTextField customerIDField;
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
    String[] carColumns = {"Car Registration No", "Make", "Model", "Available"};
    String[] rentalColumns = {"Customer ID", "Car Registration No.", "Rental date", "Due Date"};

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

        tempTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
        return tempTable;
    }
    public RentalManagement(ArrayList<Object[]> overloadCarData,ArrayList<Object[]> overloadRentData) throws ParseException {
        infoPanel.setLayout(new GridLayout(8, 2));
        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        infoPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        customerIDLabel = new JLabel("Customer ID");
        infoPanel.add(customerIDLabel);

        MaskFormatter formatter = new MaskFormatter("Customer_##");
        customerIDField = new JFormattedTextField(formatter);
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

        this.setLayout(new GridBagLayout());

        ArrayList<Object[]> carData = new ArrayList<>();
        carData.add(new Object[]{"Car_01", 2002, "AMG", "Yes"});
        carData.add(new Object[]{"Car_02", 2012, "AMG", "Yes"});
        carData.add(new Object[]{"Car_03", 2018, "Coupe", "Yes"});

        ArrayList<Object[]> rentData = new ArrayList<>();
        rentData.add(new Object[] {"Customer_01", "Car_02", "01-01-2022", "10-10-2022"});
        rentData.add(new Object[] {"Customer_02", "Car_01", "09-09-2022", "12-12-2022"});

        if(overloadCarData == null && overloadRentData == null){
            availableTable = createTable(carData, carColumns);
            rentTable = createTable(rentData, rentalColumns);
        }
        else {
            availableTable = createTable(overloadCarData, carColumns);
            rentTable = createTable(overloadRentData, rentalColumns);
        }

        availableTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        availablePane = new JScrollPane(availableTable);

        rentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
                Object[] data = rentData.get(selectedIndex);
                data[0] = customerIDField.getText();
                data[1] = carIDField.getText();
                data[2] = rentalDateField.getText();
                data[3] = dueDateField.getText();
                rentData.remove(selectedIndex);
                rentData.add(data);
                RentalManagement reg = null;
                try {
                    reg = new RentalManagement(carData, rentData);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
                reg.setVisible(true);
            }
        });

        rentButton = new JButton("Rent");
        infoPanel.add(rentButton);
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] data = {customerIDField.getText(), carIDField.getText(), rentalDateField.getText(), dueDateField.getText()};
                int selectedIndex = availableTable.getSelectedRow();
                rentData.add(data);
                carData.remove(selectedIndex);
                RentalManagement rental = null;
                try {
                    rental = new RentalManagement(carData, rentData);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
                rental.setVisible(true);
            }
        });

        deleteButton = new JButton("Delete");
        infoPanel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = rentTable.getSelectedRow();
                Object carID = rentData.get(selectedIndex)[1];
                for (Object[] car : carData) {
                    if (car[0].equals(carID)) {
                        car[3] = "Yes";
                        rentData.remove(selectedIndex);
                    }
                }
                try {
                    new RentalManagement(carData, rentData).setVisible(true);
                    dispose();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelButton = new JButton("Cancel");
        infoPanel.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame[] frame = Main.getFrames();
                for (Frame f : frame){
                    f.setVisible(true);
                }
                dispose();
            }
        });

        add(new JSeparator(JSeparator.VERTICAL));
        add(availablePane);
        add(new JSeparator(JSeparator.VERTICAL));
        add(rentPane);
        add(new JSeparator(JSeparator.VERTICAL));
        add(infoPanel);
        add(new JSeparator(JSeparator.VERTICAL));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}
