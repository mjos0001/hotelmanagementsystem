/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmsModel;

import java.util.*;

/**
 *
 * @author mrkjse
 */
public class SearchRoomRequest {
    private Date checkInDate = null;
    private Date checkOutDate = null;
    private ArrayList<String> roomTypeCode = null;
    private int hotelId = -1;
    private double minPrice = -1L;
    private double maxPrice = -1L;
    
    public Date getCheckInDate()
    {
        return checkInDate;
    }
    
    public Date getCheckOutDate()
    {
        return checkOutDate;
    }
    
    public ArrayList<String> getRoomTypeCode()
    {
        return roomTypeCode;
    }
    
    public double getMinPrice()
    {
        return minPrice;
    }
    
    public double getMaxPrice()
    {
        return maxPrice;
    }
    
    public int getHotelId()
    {
        return hotelId;
    }
    
    public void setHotelId(int newId)
    {
        hotelId = newId;
    }
    
    public void setCheckInDate(Date value)
    {
        checkInDate = value;
    }
    
    public void setCheckOutDate(Date value)
    {
        checkOutDate = value;
    }
    
    public void setRoomTypeCode(ArrayList<String> value)
    {
        roomTypeCode = value;
    }
    
    public void setMinPrice(double value)
    {
        minPrice = value;
    }
    
    public void setMaxPrice(double value)
    {
        maxPrice = value;
    }
}

