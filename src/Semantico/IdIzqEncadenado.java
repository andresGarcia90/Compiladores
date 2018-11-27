/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import GCI.GenCode;
import Token.Token;
import analizadorsintactico.AnalizadorSintactico;

/**
 *
 * @author andi
 */
class IdIzqEncadenado {

    private Token id;
    private IdIzqEncadenado enc;

    public IdIzqEncadenado(Token id, IdIzqEncadenado enc) {
        this.id = id;
        this.enc = enc;
    }

    public Token getId() {
        return id;
    }

    public void setId(Token id) {
        this.id = id;
    }

    public IdIzqEncadenado getEnc() {
        return enc;
    }

    public void setEnc(IdIzqEncadenado enc) {
        this.enc = enc;
    }

    public TipoBase check(TipoBase tipo) throws Exception {

        Clase c = AnalizadorSintactico.getTs().getClaseActual();
        String nombreVar = id.getLexema();
        VarInstancia v;
        TipoBase aux;

        if (tipo.getNombre().equals("boolean") || tipo.getNombre().equals("int") || tipo.getNombre().equals("char") || tipo.getNombre().equals("String")) {
            throw new Exception("La variable " + tipo.getNombre() + " de la linea " + tipo.getLinea() + " no puede hacer llamados porque es de un tipo primitivo");
        }

        //Sin encadenado
        if (enc == null) {
            if (c.getVariables().containsKey(nombreVar)) {
                v = c.getVariables().get(nombreVar);
                if (v.getVisibilidad().equals("private")) {
                    throw new Exception("No se puede acceder a la variable " + v.getNombre() + " en la linea " + v.getLinea() + " porque es privada");
                }
                GenCode.gen().write("SWAP");
                GenCode.gen().write("STOREREF " + v.getOffset() + " # Guardo el valor en la variable " + v.getNombre());
            } else {
                throw new Exception("No se encontro la variable " + nombreVar + " de la linea " + id.getLineNumber());
            }

            return v.getTipoVar();
        } else {//Con Encadenado

            if (c.estaVariable(nombreVar)) {
                v = c.getVariables().get(nombreVar);
                aux = v.getTipoVar();
                IdIzqEncadenado id2 = enc;

                GenCode.gen().write("LOADREF " + v.getOffset() + " # Cargo variable de instancia " + v.getNombre() + " de la clase " + c.getNombre());

                return id2.check(aux);
            } else {
                throw new Exception("No se encontro la variable " + nombreVar + " de la linea " + id.getLineNumber());
            }
        }

    }

}
