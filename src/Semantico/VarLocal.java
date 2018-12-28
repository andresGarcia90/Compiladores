/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Token.Token;

/**
 *
 * @author andi
 */
public class VarLocal extends VarMetodo{

    public VarLocal(Token tok, Tipo tipo) {
        this.linea= tok.getLineNumber();
        this.columna = tok.getColumNumber();
        this.nombre = tok.getLexema();
        this.tipoVar = tipo;
    }

    
    
    @Override
    public void controlDeclaraciones() throws Exception{
        if(!tipoVar.check()){
            throw new Exception("El tipo de la variable local "+ nombre +" declarada en la linea "+ linea +" no existe ("+tipoVar.getNombre()+")");
        }

    }
    
}
