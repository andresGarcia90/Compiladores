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
public class Argumentos {
    
    protected NodoExp exp;
    protected Argumentos args;

    public Argumentos(NodoExp exp, Argumentos args) {
        this.exp = exp;
        this.args = args;
    }

    public NodoExp getExp() {
        return exp;
    }

    public void setExp(NodoExp exp) {
        this.exp = exp;
    }

    public Argumentos getArgs() {
        return args;
    }

    public void setArgs(Argumentos args) {
        this.args = args;
    }
    
    
    
}
