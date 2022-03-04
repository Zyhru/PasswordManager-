import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Menu {

    private Scanner menuInput = new Scanner(System.in);
    private GeneratePassword password = new GeneratePassword();

    public Menu() {

    }

    // TODO add master password login 
    
    public void startMenu() {
        System.out.println();
        while (true) {
            System.out.println("Welcome to PasswordManager");
            System.out.println("1. Generate a password");
            System.out.println("2. Add an account");
            System.out.println("3. Delete an account");
            System.out.println("4. View accounts");
            System.out.println("5. Quit");

            int option = menuInput.nextInt();
            switch (option) {
                case 1:
                    // password
                    // password menu
                    System.out.println("Entering password menu");
                    passwordMenu();
                    break;
                case 2:
                    // add an account
                    accountMenu();
                    break;
                case 3:
                    // delete an account

                    break;
                case 4:
                    // view database
                    viewDatabase();
                    break;
                case 5: 
                    // quit
                    System.out.println("Thank you for using PasswordManager.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error.");
                    // menu();
            }

        }

    }

    private void passwordMenu() {

        System.out.println("Enter the length of desired password: [Length must be > 8 and < 12]");
        int passwordLength = menuInput.nextInt();

        if (password.isLengthAppropiate(passwordLength)) {
            System.out.println(passwordLength);
            String pass = password.generatePassword(passwordLength);
            System.out.println(pass);
            return;
        } else {
            System.out.println("Invalid Length!");
            passwordMenu();
        }

    }

    private void viewDatabase() {
        connectToDB();
    
    }

    private void accountMenu() {

        // connect to db
        // 1. Ask for login
        // 2. Ask for password
        // 3. Ask for what service
        // 4. Insert querys to table in database
        System.out.println("To add an account you must provide\nLogin\nPassword\nService");
    
        System.out.println("Enter service: ");
        String service = menuInput.next();

        System.out.println("Enter login: ");
        String login = menuInput.next();

        System.out.println("Enter password: ");
        String p = menuInput.next();

        // connectToDB(service, login, p);

    }

    private void connectToDB() {
        
        Connection c = null;
        Statement  s = null;
        String url = "jdbc:postgresql://localhost:5432/testdb";
        String user = "postgres";
        String dbPass = "local";
        try {
            Class.forName("org.postgresql.Driver");
            
            c = DriverManager.getConnection(url, user, dbPass);

            if(c != null) {
                System.out.println("Connection: [OK]");
            } else {
                System.out.println("Connection: [FAILED]");
            }

            // insert values to table
            s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM manager;");
            System.out.println("===================================");
            while(rs.next()) {
                String srvce = rs.getString("service");
                String l = rs.getString("login");
                String p = rs.getString("password");
                System.out.printf( "Service = %s , Login = %s, Password = %s ", srvce,l,p);
                System.out.println();
            }
            System.out.println("===================================");
            // String sql = "INSERT INTO manager (service,login,password) " + 
            // "VALUES ('" + service + "', '" + login + "', '" + pw + "');";



            // executes given SQL statement
            // s.executeUpdate(sql);
            s.close();
            c.close();
            rs.close();




        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println();


    }

}
