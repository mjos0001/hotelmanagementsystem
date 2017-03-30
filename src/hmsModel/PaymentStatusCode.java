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
public enum PaymentStatusCode {
    PAID("PD"),
    UNPAID("UP");
    
    private String code;
    
    PaymentStatusCode(String code)
    {
        this.code = code;
    }

    public String code() 
    {
        return code;
    }
}
