public class Account {
   
    private String service;
    private String login;
    private String password;

    public Account(String service, String login, String password) {
      
        this.service = service;
        this.login = login;
        this.password = password;
    }


    public String getService() {
        return service;
    }

    
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    
}
