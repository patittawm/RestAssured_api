package API_Testing.Pojos;

/**
 * POJOS stand for Plain Old Java Object
 * A pojo class must be public and its variables must be private
 * A pojo can have a constructor with arguments, the variables should have getters and setters to access data.
 * Pojo classes are used for creating JSON and XML Payloads - (DATA) for API
 */

public class loginPojos {

    public loginPojos() {

    }

    private String userName;
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

}




