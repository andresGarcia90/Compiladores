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
public class NodoLiteral extends NodoOperando{

    public NodoLiteral(Token token) {
        tok = token;
    }

    
    
    @Override
    public Tipo check() throws Exception {
        switch(tok.getName()){
            case "entero": return new Int(tok.getLineNumber(), tok.getColumNumber());
            case "tString": return new TipoString(tok.getLineNumber(), tok.getColumNumber());
            case "caracter": return new Char(tok.getLineNumber(), tok.getColumNumber());
            case "null": return new TipoClase(tok.getLineNumber(), tok.getColumNumber(), tok.getLexema());
            case "true": return new Bool(tok.getLineNumber(), tok.getColumNumber());
            case "false": return new Bool(tok.getLineNumber(), tok.getColumNumber());
            default: return null;
        }

    }
    
}
