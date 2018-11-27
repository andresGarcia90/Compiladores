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
public abstract class NodoAcceso extends NodoPrimario{
    protected boolean ladoIzq;

    
    public boolean isLadoIzq() {
        return ladoIzq;
    }

    public void setLadoIzq(boolean ladoIzq) {
        this.ladoIzq = ladoIzq;
    }
    
     
}
