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
public class Else extends If {

    private Sentencia sen2;

    public Else(Token tok, NodoExp exp, Sentencia sen, Sentencia sen2) {
        super(tok, exp, sen);
        this.sen2 = sen2;
    }

    public Sentencia getSen2() {
        return sen2;
    }

    public void setSen2(Sentencia sen2) {
        this.sen2 = sen2;
    }

    public void check() throws Exception {

        if (!exp.check().getNombre().equals("boolean")) {
            throw new Exception("El tipo de la expresion dentro del if en la linea " + linea + " no es de tipo boolean");
        }

        String finIf = "finIf" + GenCode.gen().genLabel();
        String lElse = "else" + GenCode.gen().genLabel();

        GenCode.gen().write("BF " + lElse + " # Salto si la sentencia es falsa");

        sen.check();

        GenCode.gen().write("JUMP " + finIf + " # Salto al fin del if para que no ejecute el else");
        GenCode.gen().write(lElse + ": NOP # Codigo del else");

        sen2.check();

        GenCode.gen().write(finIf + ": NOP # Etiqueta fin if");
        GenCode.gen().nl();
    }

}
