package com.proj.model;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "SpendingData") // cria tabela

public class SpendingData {

    public SpendingData() { }

    public SpendingData(UserData FKUser, Double spendingValue, Date spendingDate, String spendingReason) {
        this.user = FKUser;
        this.spendingValue = spendingValue;
        this.spendingDate = spendingDate;
        this.spendingReason = spendingReason;
    }
    
    @Id
    @GeneratedValue // gera o id sozinho 
    private Long IDSpending;
    
    @ManyToOne
    @JoinColumn(name = "FKUser") // cria coluna
    private UserData user;
    
    @Column(name = "SpendingValue")
    private Double spendingValue;
    
    @Column(name = "SpendingDate")
    private Date spendingDate;
    
    @Column(name = "SpendingReason")
    private String spendingReason;
    
    public UserData getFKUser() {
        return user;
    }

    public void setFKUser(UserData fKUser) {
        user = fKUser;
    }

    public Double getSpendingValue() {
        return spendingValue;
    }

    public void setSpendingValue(Double spendingValue) {
        this.spendingValue = spendingValue;
    }

    public Date getSpendingDate() {
        return spendingDate;
    }

    public void setSpendingDate(Date spendingDate) {
        this.spendingDate = spendingDate;
    }

    public String getSpendingReason() {
        return spendingReason;
    }

    public void setSpendingReason(String spendingReason) {
        this.spendingReason = spendingReason;
    }
}
