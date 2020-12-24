package mr.elhadj.tramwaytransport;

import androidx.annotation.NonNull;

public class Agent {
    private String id;
    private String ag_username;
    private String ag_password;
    private String ag_fullname;

    public Agent(String id,String ag_username, String ag_password, String ag_fullname) {
        this.id = id;
        this.ag_username = ag_username;
        this.ag_password = ag_password;
        this.ag_fullname = ag_fullname;
    }
    public Agent(String ag_username, String ag_password, String ag_fullname) {
        this.ag_username = ag_username;
        this.ag_password = ag_password;
        this.ag_fullname = ag_fullname;
    }

    public String getId() {
        return id;
    }

    public String getAg_username() {
        return ag_username;
    }

    public void setAg_username(String ag_username) {
        this.ag_username = ag_username;
    }

    public String getAg_password() {
        return ag_password;
    }

    public void setAg_password(String ag_password) {
        this.ag_password = ag_password;
    }

    public String getAg_fullname() {
        return ag_fullname;
    }

    public void setAg_fullname(String ag_fullname) {
        this.ag_fullname = ag_fullname;
    }

    @NonNull
    @Override
    public String toString() {
        return ag_username;
    }
}
