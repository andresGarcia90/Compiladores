/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Semantico.Encadenado;
import Semantico.NodoExp;
import Token.Token;

/**
 *
 * @author andi
 */
public class NodoExpParentizada extends NodoPrimario{

    private NodoExp exp;
    
    
    public NodoExpParentizada(Token token, NodoExp exp, Encadenado enca) {
        super.tok = token;
        this.exp = exp;
        this.enca = enca;
    }

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

    
    
    @Override
    public TipoBase check() throws Exception {
        TipoBase tipoExpresion = exp.check();
        
        if(enca != null){
            return enca.check(tipoExpresion);
        }

        return tipoExpresion;
    }
    
}
