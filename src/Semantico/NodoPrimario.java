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
public abstract class NodoPrimario extends NodoOperando {

    protected Encadenado enca;

    public Encadenado getEnca() {
        return enca;
    }

    public void setEnca(Encadenado enca) {
        this.enca = enca;
    }

    @Override
    public abstract TipoBase check() throws Exception;

}
