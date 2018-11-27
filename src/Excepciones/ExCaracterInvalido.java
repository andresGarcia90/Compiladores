/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 * Excepcion que muestra que el caracter no es valido.
 * @author andi
 */
public class ExCaracterInvalido extends Exception{
    
    public ExCaracterInvalido(String str){
        super(str);
    }
}
