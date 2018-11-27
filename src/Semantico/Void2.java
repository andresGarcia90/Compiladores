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
public class Void2 extends TipoBase{

    public Void2() {
        setNombre("void");
    }

    @Override
    public boolean esCompatible(TipoBase t) {
        if (t.getNombre().equals(this.getNombre())) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean check() {
        return true;
    }

    @Override
    public void gen(String s) {
    }
    
}
