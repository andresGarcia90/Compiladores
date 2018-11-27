/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import GCI.GenCode;
import Token.Token;

/**
 *
 * @author andi
 */
public class NodoExpUnaria extends NodoExp {

    private NodoExp e;

    public NodoExpUnaria(Token token, NodoExp e) {
        tok = token;
        this.e = e;
    }

    public NodoExp getE() {
        return e;
    }

    public void setE(NodoExp e) {
        this.e = e;
    }

    @Override
    public Tipo check() throws Exception {
        TipoBase tipoe = e.check();
        String operador = tok.getLexema();

        if (operador.equals("!")) {
            if (!tipoe.getNombre().equals("boolean")) {
                throw new Exception("Error: El operador no se puede aplicar a una expresion no booleana en linea " + tok.getLineNumber());
            }
            GenCode.gen().write("NOT # Suma");
            return new Bool(tok.getLineNumber(), tok.getColumNumber());
        } else {
            if (!tipoe.getNombre().equals("int")) {
                throw new Exception("Error: el operador no se puede aplicar a una expresion no entera en linea " + tok.getLineNumber());
            }
            GenCode.gen().write("NEG # Suma");
            return new Int(tok.getLineNumber(), tok.getColumNumber());
        }

    }

}
