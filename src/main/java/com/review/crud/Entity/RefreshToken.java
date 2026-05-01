package com.review.crud.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "refresh_token")


public class RefreshToken {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String r_token;
    @Column(name = "username")
    private String userName;
    private Date expiryDate;

    public RefreshToken() {}
    public RefreshToken(String r_token, String userName, Date expiryDate){
        this.r_token = r_token;
        this.userName = userName;
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return r_token;
    }

    public void setToken(String r_token) {
        this.r_token = r_token;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }




}
