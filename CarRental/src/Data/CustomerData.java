package Data;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerData {
    File customerDataFile = new File("./CarRental/src/Data/Customer Data.txt");
    File adminDataFile = new File("./CarRental/src/Data/Admin Data.txt");
    public boolean register(String username, String password) {
        init();
        if(!checkUsernameAvail(customerDataFile, username))
            return false;
        if(!checkUsernameAvail(adminDataFile, username))
            return false;
        Pattern punct = Pattern.compile("\\p{Punct}");
        Matcher m = punct.matcher(username);
        while(m.find()){
            return false;
        }
        Matcher m2 = punct.matcher(password);
        while(m2.find()){
            return false;
        }

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

    private boolean checkUsernameAvail(File customerDataFile, String username){
        try {
            Scanner scanner = new Scanner(customerDataFile);
            while(scanner.hasNextLine()){
                String data = scanner.nextLine();
                String[] row = data.split(":", 2);
                if(row[0].equals(username))
                    return false;
            }
            scanner.close();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
