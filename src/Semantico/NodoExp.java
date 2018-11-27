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
public abstract class NodoExp {
    protected Token tok;

    public Token getTok() {
        return tok;
    }

    public void setTok(Token tok) {
        this.tok = tok;
    }
    
    
    public abstract TipoBase check() throws Exception;
    
    
}
