package com.proj.model;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "InvestmentData") // cria tabela
public class InvestmentData {

    public InvestmentData() { }

    public InvestmentData(UserData FKUser, Double investmentValue, Date investmenDate, String investmentReason) {
        this.user = FKUser;
        this.investmentValue = investmentValue;
        this.investmentDate = investmenDate;
        this.investmentReason = investmentReason;
    }
    
    @Id
    @GeneratedValue // gera o id sozinho 
    private Long IDInvestment;
    
    @ManyToOne
    @JoinColumn(name = "FKUser") // cria coluna
    private UserData user;
    
    @Column(name = "InvestmentReason")
    private String investmentReason;
    
    @Column(name = "InvestmentValue")
    private Double investmentValue;
    
    @Column(name = "InvestmentDate")
    private Date investmentDate;
    
    public Double getInvestmentValue() {
        return investmentValue;
    }
    
    public void setInvestmentValue(Double investmentValue) {
        this.investmentValue = investmentValue;
    }
    
    public UserData getUser() {
        return user;
    }
    
    public void setUser(UserData fKUser) {
        user = fKUser;
    }
    
    public String getInvestmentReason() {
        return investmentReason;
    }
    
    public void setInvestmentReason(String investmentReason) {
        this.investmentReason = investmentReason;
    }
    
    public Date getInvestmentDate() {
        return investmentDate;
    }
    
    public void setInvestmentDate(Date investmentDate) {
        this.investmentDate = investmentDate;
    }
}
