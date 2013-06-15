/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataejbopi.vo;

/**
 *
 * @author Camilo
 */
public class TransactionVo {
    private Long originAccount;
    private Long destinyAccount;
    private double value;

    public Long getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(Long originAccount) {
        this.originAccount = originAccount;
    }

    public Long getDestinyAccount() {
        return destinyAccount;
    }

    public void setDestinyAccount(Long destinyAccount) {
        this.destinyAccount = destinyAccount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
}
