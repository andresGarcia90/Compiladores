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
public class NodoCtorArreglo extends NodoCtor{
    private NodoExp tamaño;
    private String tipoPrimitivo;

    public NodoCtorArreglo(Token id, NodoExp tamaño, String tipo) {
        this.tamaño = tamaño;
        this.tok = id;
        this.tipoPrimitivo = tipo;
    }

    public NodoExp getTamaño() {
        return tamaño;
    }

    public void setTamaño(NodoExp tamaño) {
        this.tamaño = tamaño;
    }

    public String getTipoPrimitivo() {
        return tipoPrimitivo;
    }

    public void setTipoPrimitivo(String tipoPrimitivo) {
        this.tipoPrimitivo = tipoPrimitivo;
    }
    
  

    @Override
    public TipoBase check() throws Exception {

        TipoArreglo ret;
        if(!tamaño.check().esCompatible(new Int(0,0))){
            throw new Exception("La expresion de la linea "+ tamaño.getTok().getLineNumber()+" no es de tipo entero");
        }
        
        if (enca != null) {
            return enca.check(new TipoClase(tok.getLineNumber(), tok.getColumNumber(), tok.getLexema()));
        }

        
        if(tok.getLexema().equals("int"))
            return new TipoArregloInt(tok);
        else if (tok.getLexema().equals("char"))
                return new TipoArregloChar(tok);
        else if(tok.getLexema().equals("boolean"))
            return new TipoArregloBool(tok);
        
        return null;
    }
    
    
}
