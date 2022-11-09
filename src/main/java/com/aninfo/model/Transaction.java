package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {

    private Double amount;

    private Long cbu;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;

    public Transaction(){

    }

    public void setTransactionId(Long transactionId){
        this.transactionId = transactionId;
    }

    public Long getTransactionId(){
        return transactionId;
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
