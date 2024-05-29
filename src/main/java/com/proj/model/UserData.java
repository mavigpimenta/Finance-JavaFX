package com.proj.model;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "UserData") // cria tabela
public class UserData {

    public UserData() { }

    public UserData(String username, Date userbirth, String useremail, String userpass) {
        this.username = username;
        this.userbirth = userbirth;
        this.useremail = useremail;
        this.userpass = userpass;
    }
    
    @Id
    @GeneratedValue // gera o id sozinho 
    private Long IDUser;
    
    public Long getIDUser() {
        return IDUser;
    }

    @Column(name = "UserName") // cria coluna
    private String username;

    @Column(name = "UserBirth")
    private Date userbirth;
    
    
    @Column(name = "UserEmail")
    private String useremail;
  
    
    @Column(name = "UserPass")
    private String userpass;
    

    public String getUserpass() {
        return userpass;
    }
    
    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }
    
    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }
    
    public Date getUserbirth() {
        return userbirth;
    }
    
    public void setUserbirth(Date userbirth) {
        this.userbirth = userbirth;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
