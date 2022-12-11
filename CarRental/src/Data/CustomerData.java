package Data;

import javax.swing.*;
import java.io.*;

public class CustomerData {
    File customerDataFile = new File("./CarRental/src/Data/Customer Data.txt");
    public boolean register(String username, String password)
    {
        FileWriter user_input = null;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;
        try
        {
            user_input = new FileWriter(customerDataFile, true);
            bufferedWriter = new BufferedWriter(user_input);
            printWriter = new PrintWriter(bufferedWriter);

            printWriter.println(username + ":" + password);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        finally
        {
            try {
                printWriter.close();
                bufferedWriter.close();
                user_input.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public CustomerData(){
        init();

    }

    private void init(){
        try{
            customerDataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        new CustomerData();
    }
}
