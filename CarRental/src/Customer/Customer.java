package Customer;

public class Customer {
    private String username, password, approve;

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
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setApprove(String approve){
        this.approve = approve;
    }

}

