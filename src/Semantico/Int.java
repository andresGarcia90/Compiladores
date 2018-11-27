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
public class Int extends TipoPrimitivo {

    public Int(int l, int c) {
        linea = l;
        columna = c;
        nombre = "int";
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
        GenCode.gen().write("PUSH " + s + " # Apilo el valor " + s);
    }

}
