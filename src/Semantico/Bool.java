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
public class Bool extends TipoPrimitivo {

    public Bool(int l, int c) {
        linea = l;
        columna = c;
        nombre = "boolean";
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

        if (s.equals("true")) {

            GenCode.gen().write("PUSH 1 # Apilo true");
        } else {
            GenCode.gen().write("PUSH 0 # Apilo false");
        }
    }

}
