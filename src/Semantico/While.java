/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Token.Token;

/**
 *
 * @author andi
 */
public class While extends Sentencia{
    
    private Sentencia sen;
    private NodoExp exp;

    public While(Token tok, Sentencia sen, NodoExp exp) {
        linea = tok.getLineNumber();
        this.sen = sen;
        this.exp = exp;
    }

    public Sentencia getSen() {
        return sen;
    }

    public void setSen(Sentencia sen) {
        this.sen = sen;
    }

    public NodoExp getExp() {
        return exp;
    }

    public void setExp(NodoExp exp) {
        this.exp = exp;
    }
    
    
        
    @Override
    public void check() throws Exception {
        TipoBase tipoExp = exp.check();
        if(!tipoExp.getNombre().equals("boolean")){
            throw new Exception("El tipo de la expresion dentro del while en la linea "+ linea+ " no es de tipo boolean");
        }
        
        sen.check();
    }
    
}
