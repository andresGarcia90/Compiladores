/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import GCI.GenCode;

/**
 *
 * @author andi
 */
public class Cadena extends Tipo {

    public Cadena(int lineNumber, int columNumber) {
        this.nombre = "String";
        this.columna = columNumber;
        this.linea = lineNumber;
    }

    @Override
    public boolean esCompatible(TipoBase t) throws Exception {
        if (t.getNombre().equals(this.nombre)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean check() {
        return true;
    }

    public void gen(String s) {

        GenCode.gen().write(".DATA");

        String et = GenCode.gen().genLabel();

        GenCode.gen().write("l_str" + et + ": DW " + s + ",0");
        GenCode.gen().write(".CODE");
        GenCode.gen().write("PUSH l_str" + et + " # Apilo etiqueta del String");

    }

}
