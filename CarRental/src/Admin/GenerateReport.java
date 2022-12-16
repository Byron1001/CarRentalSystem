package Admin;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GenerateReport{
    File adminDataFile = new File("./CarRental/src/Data/Admin Data.txt");
    File bookingHistoryFile = new File("./CarRental/src/Data/Booking History.txt");
    File carDataFile = new File("./CarRental/src/Data/Car Data.txt");
    File customerDataFile = new File("./CarRental/src/Data/Customer Data.txt");
    File paymentFile = new File("./CarRental/src/Data/Payment.txt");
    File returnHistoryFile = new File("./CarRental/src/Data/Return History.txt");
    File tempCustomerDataFile = new File("./CarRental/src/Data/Temp Customer Data.txt");
    private Scanner scanner;
    public GenerateReport(){
        init();
        int pendingCustomer = numberOfPendingCustomer();
        int returnFinished = numberOfReturnProcess();
        int paymentPending = numberOfPaymentPending();
        int paymentFinished = numberOfPaymentFinished();
        int customerNumber = numberOfCustomer();
        int carNumber = numberOfCar();
        int bookingProcess = numberOfBookingHistory();
        int adminNumber = numberOfAdmin();
        String message = "Car Mangement System Report:\nNumber of admin registered: " + adminNumber +
                "\nNumber of customer registered (Number of user): " + customerNumber +
                "\nNumber of customer waiting to be verified by admin: " + pendingCustomer +
                "\nNumber of car registered: " + carNumber + "\nBooking Process finished: " + bookingProcess +
                "\nPayment process finished: " + paymentFinished +
                "\nPayment pending: " + paymentPending +
                "\nCar return process finished: " + returnFinished;
        JOptionPane.showMessageDialog(null, message, "Car Management System Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private int numberOfPendingCustomer(){
        try {
            scanner = new Scanner(tempCustomerDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1) {
                count++;
            }
        }
        return count;
    }

    private int numberOfReturnProcess(){
        try {
            scanner = new Scanner(returnHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1) {
                count++;
            }
        }
        return count;
    }

    private int numberOfPaymentPending(){
        try {
            scanner = new Scanner(paymentFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] data = row.split(":");
            if (data.length > 1){
                if (data[data.length - 1].equals("No")){
                    count++;
                }
            }
        }
        return count;
    }

    private int numberOfPaymentFinished(){
        try {
            scanner = new Scanner(paymentFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] data = row.split(":");
            if (data.length > 1){
                if (data[data.length - 1].equals("Yes")) {
                    count++;
                }
            }
        }
        return count;
    }

    private int numberOfCustomer(){
        try {
            scanner = new Scanner(customerDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1)
                count++;
        }
        return count;
    }

    private int numberOfCar(){
        try {
            scanner = new Scanner(carDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1)
                count++;
        }
        return count;
    }

    private int numberOfBookingHistory(){
        try {
            scanner = new Scanner(bookingHistoryFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1)
                count++;
        }
        return count;
    }

    private int numberOfAdmin(){
        try {
            scanner = new Scanner(adminDataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int count = 0;
        while(scanner.hasNextLine()){
            String row = scanner.nextLine();
            String[] rowData = row.split(":");
            if (rowData.length > 1)
                count++;
        }
        return count;
    }

    private void init(){
        try {
            adminDataFile.createNewFile();
            bookingHistoryFile.createNewFile();
            carDataFile.createNewFile();
            customerDataFile.createNewFile();
            paymentFile.createNewFile();
            returnHistoryFile.createNewFile();
            tempCustomerDataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
