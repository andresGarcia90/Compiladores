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
        
        Tipo ret;
        switch(tok.getName()){
            case "entero":{ ret = new Int(tok.getLineNumber(), tok.getColumNumber()); break;}
            case "tString":{ ret = new TipoString(tok.getLineNumber(), tok.getColumNumber()); break;}
            case "caracter":{ ret = new Char(tok.getLineNumber(), tok.getColumNumber()); break;}
            case "null": {ret = new TipoClase(tok.getLineNumber(), tok.getColumNumber(), tok.getLexema()); break;}
            case "true": {ret = new Bool(tok.getLineNumber(), tok.getColumNumber()); break;}
            case "false":{ ret = new Bool(tok.getLineNumber(), tok.getColumNumber()); break;}
            default: ret = null;
        }
        
        ret.gen(tok.getLexema());
        return ret;
    }
    
}
