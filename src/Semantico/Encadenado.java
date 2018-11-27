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
public abstract class Encadenado {
    protected Token id;
    protected Encadenado cadena;
    protected boolean ladoIzq = false;

    public Token getTok() {
        return id;
    }

    public void setTok(Token tok) {
        this.id = tok;
    }

    public Encadenado getEnc() {
        return cadena;
    }

    public void setEnc(Encadenado enc) {
        this.cadena = enc;
    }

    public boolean isLadoIzq() {
        return ladoIzq;
    }

    public void setLadoIzq(boolean ladoIzq) {
        this.ladoIzq = ladoIzq;
    }
    
    
    
    public abstract TipoBase check(TipoBase tipo) throws Exception;
    
    
}
