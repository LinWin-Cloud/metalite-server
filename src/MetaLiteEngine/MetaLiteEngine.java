package MetaLiteEngine;

public class MetaLiteEngine {
    private String username;
    private String password;
    private String RunMessage = "";
    public void setUserName(String userName) {
        this.username = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void exec(String code) throws Exception {

    }

    public String getRunMessage() {
        return this.RunMessage;
    }
}
