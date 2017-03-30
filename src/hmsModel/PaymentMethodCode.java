/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsModel;

/**
 *
 * @author mrkjse
 */
public enum PaymentMethodCode {
    VISA("VI"),
    MASTERCARD("MC"),
    AMERICANEXPRESS("AX"),
    JCB("JC"),
    CASH("CA");
    
    private String code;
    
    PaymentMethodCode(String code)
    {
        this.code = code;
    }

    public String code() 
    {
        return code;
    }
}
