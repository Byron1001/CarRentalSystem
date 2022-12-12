import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class tryclass {
    public tryclass() throws ParseException {
        System.out.println("dd-mm-yyyy");
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();
        Date date1=new SimpleDateFormat("ddMMyyyy").parse(date);
        System.out.println(date1);
    }
    public static void main(String[] args) throws ParseException {
        new tryclass();
    }
}
