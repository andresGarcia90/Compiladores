/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

/**
 *
 * @author andi
 */
public class TipoString extends TipoReferencia {

    public TipoString(int l, int c) {
        nombre = "String";
        linea = l;
        columna = c;
    }

    @Override
    public boolean esCompatible(TipoBase t) throws Exception {
        if (t.getNombre().equals(this.nombre) || t.getNombre().equals("null")) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean check() {
        return true;
    }

}
