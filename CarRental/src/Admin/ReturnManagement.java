package Admin;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReturnManagement extends JFrame{
    private JTable returnTable;
    private JLabel customerIDLabel, carRegLabel, rentalDateLabel, dueDateLabel, returnDateLabel, delayLabel, fineLabel;
    private JTextField fineField;
    private JFormattedTextField customerIDField, carRegField, rentalDateField, returnDateField, dueDateField, delayField;
    private JPanel infoPanel;
    private ArrayList<Object[]> returnData;
    private JScrollPane returnPane;
    private JButton returnButton, modifyButton, cancelButton, deleteButton;

    String[] rentalColumns = {"Customer ID", "Car Registration No.", "Rental date", "Due Date", "Return date", "Delay", "Fine (RM)"};

    private JTable createTable(ArrayList<Object[]>data, String[] columns){
        DefaultTableModel model = new DefaultTableModel();
        JTable tempTable = new JTable(model);
        for (String col : columns){
            model.addColumn(col);
        }
        for (Object[] dt : data){
            model.addRow(dt);
        }
        tempTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));

        for(int i = 0; i < 7;i++){
            TableColumn column = tempTable.getColumnModel().getColumn(i);
            column.setMinWidth(100);
        }
        return tempTable;
    }
    public ReturnManagement() throws ParseException {
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(10, 2));
        Border gap = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        infoPanel.setBorder(gap);

        customerIDLabel = new JLabel(rentalColumns[0]);
        infoPanel.add(customerIDLabel);

        MaskFormatter formatter = new MaskFormatter("Customer_##");
        customerIDField = new JFormattedTextField(formatter);
        infoPanel.add(customerIDField);

        carRegLabel = new JLabel(rentalColumns[1]);
        infoPanel.add(carRegLabel);

        MaskFormatter formatter1 = new MaskFormatter("Car_##");
        carRegField = new JFormattedTextField(formatter1);
        infoPanel.add(carRegField);

        rentalDateLabel = new JLabel(rentalColumns[2]);
        infoPanel.add(rentalDateLabel);

        MaskFormatter formatter2 = new MaskFormatter("##-##-####");
        rentalDateField = new JFormattedTextField(formatter2);
        infoPanel.add(rentalDateField);

        returnDateLabel = new JLabel(rentalColumns[3]);
        infoPanel.add(returnDateLabel);

        MaskFormatter formatter3 = new MaskFormatter("##-##-####");
        returnDateField = new JFormattedTextField(formatter3);
        infoPanel.add(returnDateField);

        dueDateLabel = new JLabel(rentalColumns[4]);
        infoPanel.add(dueDateLabel);

        MaskFormatter formatter4 = new MaskFormatter("##-##-####");
        dueDateField = new JFormattedTextField(formatter4);
        infoPanel.add(dueDateField);

        delayLabel = new JLabel(rentalColumns[5]);
        infoPanel.add(delayLabel);

        delayField = new JFormattedTextField();
        delayField.setEditable(false);
        infoPanel.add(delayField);

        fineLabel = new JLabel(rentalColumns[6]);
        infoPanel.add(fineLabel);

        fineField = new JTextField();
        infoPanel.add(fineField);
        fineField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if((c < '0' || c > '9') && c != '.'){
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        infoPanel.add(new JSeparator());
        infoPanel.add(new JSeparator());
        returnButton = new JButton("Return");
        infoPanel.add(returnButton);
        modifyButton = new JButton("Modify");
        infoPanel.add(modifyButton);
        deleteButton = new JButton("Delete");
        infoPanel.add(deleteButton);
        cancelButton = new JButton("Cancel");
        infoPanel.add(cancelButton);

        returnData = new ArrayList<>();
        returnData.add(new Object[]{"Customer_01", "Car_02", "10-10-2020", "11-11-2020", "12-12-2020", 31, 31.00});
        returnData.add(new Object[]{"Customer_02", "Car_01", "02-02-2020", "04-04-2020", "06-06-2020", 62, 62.00});
        returnData.add(new Object[]{"Customer_03", "Car_03", "02-02-2020", "04-04-2020", "01-03-2020", 62, 0.00});
        returnTable = createTable(returnData, rentalColumns);
        returnTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        returnTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = returnTable.getSelectedRow();
                Object[] row = returnData.get(selectedIndex);

                customerIDField.setText(row[0].toString());
                carRegField.setText(row[1].toString());
                rentalDateField.setText(row[2].toString());
                returnDateField.setText(row[3].toString());
                dueDateField.setText(row[4].toString());

                Integer days = 0;
                try {
                    days = dateCount(row[3].toString(), row[4].toString());
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                delayField.setText(days.toString() + " days");
                Double fine = days * 50.00;
                fineField.setText(fine.toString());
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
        returnPane = new JScrollPane(returnTable);

        setLayout(new FlowLayout());
        add(returnPane);
        add(infoPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    protected static int dateCount(String returnDate, String dueDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date returnD = formatter.parse(returnDate);
        Date due = formatter.parse(dueDate);

        long diffInMil = returnD.getTime() - due.getTime();
        if (diffInMil > 0){
            return 0;
        }
        long diff = Math.abs(TimeUnit.DAYS.convert(diffInMil, TimeUnit.MILLISECONDS));
        return (int) diff;
    }

    public static void main(String[] args) throws ParseException {
        new ReturnManagement().setVisible(true);
//        new RentalManagement(null, null).setVisible(true);
    }
}
