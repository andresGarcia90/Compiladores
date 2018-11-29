/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import GCI.GenCode;
import Token.Token;

/**
 *
 * @author andi
 */
public class NodoCtorArreglo extends NodoCtor {

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

        TipoArreglo ret = null;
        GenCode.gen().write("RMEM 1 # Reservo lugar para el arreglo");

        if (!tamaño.check().esCompatible(new Int(0, 0))) {
            throw new Exception("La expresion de la linea " + tamaño.getTok().getLineNumber() + " no es de tipo entero");
        }

        GenCode.gen().write("PUSH lmalloc # Apilo la etiqueta del lmalloc");

        GenCode.gen().write("CALL # Llamo al arreglo");

        if (enca != null) {
            return enca.check(new TipoClase(tok.getLineNumber(), tok.getColumNumber(), tok.getLexema()));
        }

        if (tok.getLexema().equals("int")) {
            ret = new TipoArregloInt(tok);
        } else if (tok.getLexema().equals("char")) {
            ret = new TipoArregloChar(tok);
        } else if (tok.getLexema().equals("boolean")) {
            ret = new TipoArregloBool(tok);
        }

        return ret;
    }

}
