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
public class VarInstancia extends Variable{
    private String visibilidad;
    
    public VarInstancia(Token token, String visibilidad, Tipo tipo) {
        this.visibilidad = visibilidad;
        nombre = token.getLexema();
        linea = token.getLineNumber();
        columna = token.getColumNumber();
        tipoVar = tipo;
        
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }
    
    //TODO: VARINSTANCIA
    @Override
    public void controlDeclaraciones() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
