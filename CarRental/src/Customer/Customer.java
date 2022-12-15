package Customer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Customer {
    private String username, password;
    private String approve;

    public Customer(String username, String password, String approve)
    {
        this.username = username;
        this.password = password;
        this.approve = approve;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getApprove(){
        return approve;
    }

    public int data_validity(Customer customer) {
        for(char a : customer.username.toCharArray()){
            if(a == ':')
                return 2;
        }

        for(char a : customer.password.toCharArray()){
            if(a == ':')
                return 2;
        }

        if(customer.username.length() < 3 || customer.password.length() < 3)
            return 3;

        if(customer.user_exist(customer.username))
            return 4;

        return 1;
    }

    public int user_login(Customer customer) {
        if(customer.user_exist(customer.username)){
            if(customer.password_check(customer)){
                customer.record_login_time(customer.username);
                return 1;
            }
            else{
                return 2;
            }
        }
        else{
            return 3;
        }
    }

    private boolean user_exist(String username)
    {
        LinkedList<String> username_list = new LinkedList<String>(this.get_userinfo("username"));
        boolean exist = username_list.contains(username);
        if(exist){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean password_check(Customer customer){
        LinkedList<String> username = new LinkedList<String>(get_userinfo("username"));
        LinkedList<String> password = new LinkedList<String>(get_userinfo("password"));
        int index = username.indexOf(customer.username);
        if(password.get(index).equals(customer.password)){
            return true;
        }
        return false;
    }

    public LinkedList<String> get_userinfo(String info)
    {
        LinkedList<String> username = new LinkedList<String>();
        FileReader file = null;
        BufferedReader buffReader = null;
        String line;
        int colon = 0;

        try
        {
            file = new FileReader("user.txt");
            buffReader = new BufferedReader(file);
            while(buffReader.ready())
            {
                line = buffReader.readLine();
                for(int i = 0; i < line.length();i++)
                {
                    if(line.charAt(i) == ':')
                    {
                        colon = i;
                    }
                }
                if(info.equals("username")){
                    username.add(line.substring(0, colon));
                }
                else if(info.equals("password")){
                    username.add(line.substring(colon + 1, line.length()));
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                file.close();
                buffReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return username;
    }

    private void record_login_time(String username){
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;
        String Login = "Login", data;
        try {
            fileWriter = new FileWriter("login_record.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);

            data = LocalDate.now() + "\t" + LocalTime.now() + "\t" + "user" + "\t" + username + "\t" + Login;
            printWriter.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                printWriter.close();
                bufferedWriter.close();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

