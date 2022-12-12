package Admin;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Admin {
    File adminDataFile = new File("./CarRental/src/Data/Admin Data.txt");
    public boolean register(String username, String password)
    {
        init();
        if(!checkUsernameAvail(adminDataFile, username))
            return false;

        Pattern punct = Pattern.compile("\\p{Punct}");
        Matcher m = punct.matcher(username);
        while(m.find()){
            return false;
        }

        FileWriter user_input = null;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;

        try
        {
            user_input = new FileWriter(adminDataFile, true);
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

    private boolean checkUsernameAvail(File adminDataFile, String username){
        try {
            Scanner scanner = new Scanner(adminDataFile);
            while(scanner.hasNextLine()){
                String data = scanner.nextLine();
                String[] row = data.split(":", 2);
                if(row[0].equals(username)) {
                    return false;
                }
            }
            scanner.close();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Admin(){
        init();
    }

    private void init(){
        try{
            adminDataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
