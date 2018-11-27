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
public class If extends Sentencia {

    protected NodoExp exp;
    protected Sentencia sen;

    public If(Token tok, NodoExp exp, Sentencia sen) {
        linea = tok.getLineNumber();
        this.exp = exp;
        this.sen = sen;
    }

    public NodoExp getExp() {
        return exp;
    }

    public void setExp(NodoExp exp) {
        this.exp = exp;
    }

    public Sentencia getSen() {
        return sen;
    }

    public void setSen(Sentencia sen) {
        this.sen = sen;
    }

    @Override
    public void check() throws Exception {

        if (!exp.check().getNombre().equals("boolean")) {
            throw new Exception("El tipo de la expresion dentro del if en la linea " + linea + " no es de tipo boolean");
        }

        String finIf = "finIf" + GenCode.gen().genLabel();

        GenCode.gen().write("BF " + finIf + " # Salto si la sentencia es falsa");

        sen.check();

        GenCode.gen().write(finIf + ": NOP # Etiqueta fin if");
        GenCode.gen().nl();

    }

}
