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
public abstract class TipoArreglo extends TipoReferencia{
    protected NodoExp exp;
    protected Encadenado enca;

    public NodoExp getExp() {
        return exp;
    }

    public void setExp(NodoExp exp) {
        this.exp = exp;
    }

    public Encadenado getEnca() {
        return enca;
    }

    public void setEnca(Encadenado enca) {
        this.enca = enca;
    }
    
    
    
    
}
