package Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CarReturn extends JFrame{
    File historyData = new File("./CarRental/src/Data/History.txt");
    private JPanel buttonPanel;
    private ScrollPane tablePane;
    private JButton returnButton, cancelButton;
    private JTable table1;
    private Scanner scanner;
    private ArrayList<Object[]> data;
    private String[] columns = {"Customer ID", "Car ID", "Rental Date", "Due Date", "Return Date", "Delay", "Fine", "Payment"};
    private void init(){
        try {
            historyData.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private JTable createTable(ArrayList<Object[]> data, String[] columns){
        DefaultTableModel model = new DefaultTableModel();
        JTable tempTable = new JTable(model);
        return tempTable;
    }
    private ArrayList<Object[]> getData(File historyData){
        ArrayList<Object[]> tempData = new ArrayList<>();
        try {
            scanner = new Scanner(historyData);
            while(scanner.hasNextLine()){
                String row = scanner.nextLine();
                String[] row2 = row.split(":", columns.length);
                tempData.add(row2);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return tempData;
    }
    public CarReturn(){
        init();
        data = getData(historyData);
        table1 = createTable(data, columns);
        tablePane = new ScrollPane();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
    }
}
