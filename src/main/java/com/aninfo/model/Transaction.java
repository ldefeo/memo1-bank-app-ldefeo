package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {

    private Double amount;

    private Long cbu;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionID;

    public Transaction(){

    }

    public void setTransactionId(Long transactionID){
        this.transactionID = transactionID;
    }

    public Long getTransactionId(){
        return transactionID;
    }

    public void setAmount(Double amount){
        this.amount = amount;
    }

    public Double getAmount(){
        return amount;
    }

    public void setCbu(Long cbu){
        this.cbu = cbu;
    }

    public Long getCbu(){
        return cbu;
    }


}
