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
public enum HotelTypeCode {
       FIVESTAR("5S"),
       FOURSTAR("4S"),
       THREESTAR("3S"),
       TWOSTAR("2S");
       
       private String code;
       
       HotelTypeCode(String code)
       {
           this.code = code;
       }
       
       public String code() 
       {
           return code;
       }
}
