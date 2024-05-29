package com.proj.model;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "RevenueData") // cria tabela

public class RevenueData {

    public RevenueData() { }

    public RevenueData(UserData FKUser, Double revenueValue, Date revenueDate, String revenueReason) {
        this.user = FKUser;
        this.revenueValue = revenueValue;
        this.revenueDate = revenueDate;
        this.revenueReason = revenueReason;
    }
    
    @Id
    @GeneratedValue // gera o id sozinho 
    private Long IDRevenue;
    
    @ManyToOne
    @JoinColumn(name = "FKUser") // cria coluna
    private UserData user;

    @Column(name = "RevenueValue")
    private Double revenueValue;
    
    @Column(name = "RevenueDate")
    private Date revenueDate;
    
    @Column(name = "RevenueReason")
    private String revenueReason;
    
    public UserData getFKUser() {
        return user;
    }

    public void setFKUser(UserData fKUser) {
        user = fKUser;
    }
    
    public Double getRevenueValue() {
        return revenueValue;
    }

    public void setRevenueValue(Double revenueValue) {
        this.revenueValue = revenueValue;
    }

    public Date getRevenueDate() {
        return revenueDate;
    }

    public void setRevenueDate(Date revenueDate) {
        this.revenueDate = revenueDate;
    }

    public String getRevenueReason() {
        return revenueReason;
    }

    public void setRevenueReason(String revenueReason) {
        this.revenueReason = revenueReason;
    }
}
