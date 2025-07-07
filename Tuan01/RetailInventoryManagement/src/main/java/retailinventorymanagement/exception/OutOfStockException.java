/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retailinventorymanagement.exception;

import java.io.Serializable;

/**
 *
 * @author dtnhn
 */
public class OutOfStockException extends Exception implements Serializable{
    public OutOfStockException(String message) {
        super(message);
    }
}

