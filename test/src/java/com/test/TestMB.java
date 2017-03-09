/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author ArcoSoft-Pc1
 */
@Named(value = "testMB")
@SessionScoped
public class TestMB implements Serializable {

    /**
     * Creates a new instance of TestMB
     */
    public TestMB() {
    }
    
    public String toDo(){
        return "otra";
    }
}
