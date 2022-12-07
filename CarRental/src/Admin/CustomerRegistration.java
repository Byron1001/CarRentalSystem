package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CustomerRegistration extends JFrame implements MouseListener {
    private JTextField customerIDField;
    private JTextField customerNameField;
    private JComboBox customerPaymentMethodBox;
    private JComboBox customerGenderBox;
    private JButton addButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JButton cancelButton;
    private JPanel customerRegPanel;
    private JLabel customerIDLabel;
    private JLabel customerNameLabel;
    private JLabel customerPaymentMethodLabel;
    private JLabel customerGenderLabel;
    private JFormattedTextField customerMobileNumberField;
    private JLabel customerMobileNumberLabel;

    private JScrollPane scrollPane;
    private String customerIDString, customerPaymentMethodString, customerNameString, customerGenderString;

    private JTable table1, overload_table;
    private String customerMobileNumberString;
    ArrayList<Object[]> finalData;

    private Object[] columns = {"Customer ID", "Name", "Mobile number", "Payment Method", "Gender"};

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

    public CustomerRegistration(ArrayList<Object[]> data){
        customerGenderBox.addItem("Male");
        customerGenderBox.addItem("Female");
        customerGenderBox.addItem("Prefer not to say");

        customerPaymentMethodBox.addItem("TNG ewallet");
        customerPaymentMethodBox.addItem("Online banking");
        customerPaymentMethodBox.addItem("Debit card/Bank card");


        this.setLayout(new GridLayout(2, 1));
        this.setSize(900, 500);

        if(data == null){
            data = new ArrayList<Object[]>();
            data.add(new Object[]{"Customer_01", "Jason Ong", "01222223333", "TNG ewallet", "Male"});
            data.add(new Object[]{"Customer_02", "Ella Chen", "12312332232", "Online banking", "Female"});

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
        this.add(customerRegPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        finalData = data;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerIDString =  customerIDField.getText();
                customerNameString = customerNameField.getText();
                customerMobileNumberString = customerMobileNumberField.getText().toString();
                customerPaymentMethodString = customerPaymentMethodBox.getSelectedItem().toString();
                customerGenderString = customerGenderBox.getSelectedItem().toString();

                Object[] row = {customerIDString, customerNameString, customerMobileNumberString, customerPaymentMethodString, customerGenderString};
                finalData.add(row);
                dispose();
                CustomerRegistration new_reg = new CustomerRegistration(finalData);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();

                customerIDString = customerIDField.getText();
                customerNameString = customerNameField.getText();
                customerMobileNumberString = customerMobileNumberField.getText();
                customerPaymentMethodString = customerPaymentMethodBox.getSelectedItem().toString();
                customerGenderString = customerGenderBox.getSelectedItem().toString();
                Object[] row = {customerIDString, customerNameString, customerMobileNumberString, customerPaymentMethodString, customerGenderString};
                finalData.set(selectedIndex, row);
                dispose();
                CustomerRegistration new_reg = new CustomerRegistration(finalData);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();
                finalData.remove(selectedIndex);
                dispose();
                CustomerRegistration new_reg = new CustomerRegistration(finalData);
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
        customerMobileNumberField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c  = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_MINUS) || (c == '+'))){
                    JOptionPane.showMessageDialog(null, "Please enter valid character!");
                    e.consume();
                }
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
        customerIDField.setText((String) model.getValueAt(selectedIndex, 0));
        customerNameField.setText(model.getValueAt(selectedIndex, 1).toString());
        customerMobileNumberField.setText(model.getValueAt(selectedIndex, 2).toString());
        String value = model.getValueAt(selectedIndex, 3).toString();
        customerPaymentMethodBox.setSelectedIndex(
                (value.equals("TNG ewallet") ? 0 :
                        (value.equals("Online banking") ? 1 :
                                (value.equals("Debit card/Bank card") ? 2 :
                                        -1)
                        )
                )
        );
        value = model.getValueAt(selectedIndex, 4).toString();
        customerGenderBox.setSelectedIndex(
                (value.equals("Male") ? 0 :
                        (value.equals("Female") ? 1 :
                                (value.equals("Prefer not to say") ? 2 :
                                        -1)
                        )
                )
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
