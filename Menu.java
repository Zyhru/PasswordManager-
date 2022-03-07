import java.util.*;
import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Menu {

    private Scanner menuInput = new Scanner(System.in);
    private GeneratePassword password = new GeneratePassword();
    private Account account = null;

    private int dbOption;
    private int id;

    private final String MASTER_PASSWORD = "root";

    // TODO add master password login


    private void masterPassword() {

        // 1. Prompt user to enter master password.
        // 2. Compare user input to MASTER_PASSWORD
        // 3. Check to see if they're equal, otherwise prompt again
        
        
        Console console = System.console();
        StringBuilder sb = new StringBuilder();
        char[] password = null;
        if(console != null)  {
             password = console.readPassword("Enter password: ");
            
            for(int i = 0; i < password.length; i++) {
                sb.append(password[i]);
            }
            

            System.out.println(sb.toString());

            // if input != master password then ask again 
            if(!(MASTER_PASSWORD.equals(sb.toString()))) {
                System.out.println("---------------------------------------");
                System.out.println("Incorrect Master Password. Please try again.\n\n");
                masterPassword();
            }

    
        }



    }


    public void startMenu() {


        masterPassword();


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
                        deleteAccount();
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

    // to delete an account
    // view the database
    // then ask the user which account they want to delete by specifying an id
    // number !
    private void deleteAccount() {
        dbOption = 1;
        connectToDB();

        System.out.println("To delete an account you must specify an account id.");
        System.out.println("Enter the account id: ");
        id = menuInput.nextInt();

        dbOption = 3;
        connectToDB();

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
        dbOption = 1;
        connectToDB();
    }

    private void accountMenu() {
        // 1. Ask for account info
        // 2. Insert querys to table in database
        System.out.println("To add an account you must provide\nLogin\nPassword\nService");

        System.out.println("Enter service: ");
        String service = menuInput.next();

        System.out.println("Enter login: ");
        String login = menuInput.next();

        System.out.println("Enter password: ");
        String p = menuInput.next();

        account = new Account(service, login, p);

        dbOption = 2;

        connectToDB();

    }

    private void connectToDB() {

        Connection c = null;
        Statement s = null;

        String url = "jdbc:postgresql://localhost:5432/testdb";
        String user = "postgres";
        String dbPass = "local";

        try {
            Class.forName("org.postgresql.Driver");

            c = DriverManager.getConnection(url, user, dbPass);

            if (c != null) {
                System.out.println("Connection: [OK]");
            } else {
                System.out.println("Connection: [FAILED]");
            }

            // Creating Statement object for sending SQL statements
            s = c.createStatement();

            // Views table from database
            switch (dbOption) {
                case 1:
                    System.out.println("Displaying Table: [OK]");
                    displayTable(s);
                    break;
                case 2:
                    System.out.println("Querying Table: [OK]");
                    insertQuery(s);
                    break;
                case 3:
                    System.out.println("Querying Table: [OK]");
                    deleteQuery(s, id);
                    break;
            }

            s.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println();

    }

    private void displayTable(Statement s) throws SQLException {
        ResultSet rs = s.executeQuery("SELECT * FROM manager;");
        System.out.println("===================================");
        while (rs.next()) {
            int id = rs.getInt("id");
            String srvce = rs.getString("service");
            String l = rs.getString("login");
            String p = rs.getString("password");
            System.out.printf("ID = %s , Service = %s , Login = %s, Password = %s ", id, srvce, l, p);
            System.out.println();
        }
        System.out.println("===================================");
        rs.close();

    }

    private void insertQuery(Statement s) throws SQLException {
        String sql = "INSERT INTO manager (service,login,password) " +
                "VALUES ('" + account.getService() + "', '" + account.getLogin() + "', '"
                + account.getPassword() + "');";
        // executes given SQL statement
        s.executeUpdate(sql);
    }

    private void deleteQuery(Statement s, int id) {
        String sql = "DELETE FROM manager WHERE id = " + id + ";";

        try {
            s.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
