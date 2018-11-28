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
 * @author Meltman
 */
public class NodoLlamadaVar extends NodoPrimario {

    public NodoLlamadaVar(Token token, Encadenado e) {
        this.enca = e;
        this.tok = token;
    }

    @Override
    public TipoBase check() throws Exception {
        Clase c = AnalizadorSintactico.getTs().getClaseActual();
        Unidad u = AnalizadorSintactico.getTs().getUnidadActual();
        String nombreVar = tok.getLexema();
        Variable v;
        TipoBase ret;

        Metodo m = c.getMetodos().get(u.getNombre());
        if(m == null){
            throw new Exception("NUEVO ERROR caso de constructor?");
        }
        if (m.estaVar(nombreVar)) {
            v = m.getVars().get(nombreVar);
            ret = v.getTipoVar();
            GenCode.gen().write("LOAD " + v.getOffset() + " # Cargo el valor de la variable " + v.getNombre());
        } else if (c.estaVariable(nombreVar)) {
            if (!m.getFormaMetodo().equals("static")) {
                v = c.getVariables().get(nombreVar);
                ret = v.getTipoVar();
                GenCode.gen().write("LOAD 3 # Cargo This");
                GenCode.gen().write("LOADREF " + v.getOffset() + " # Cargo el valor de la variable");
            } else {
                throw new Exception("Se intento referenciar a una variable de instancia en la linea " + tok.getLineNumber() + " desde un metodo estatico en linea " + tok.getLineNumber());
            }
        } else {
            throw new Exception("La variable " + nombreVar + " en la linea " + tok.getLineNumber() + " no esta definida");
        }

        if (enca != null) {
            return enca.check(ret);
        }
        return ret;

    }

}
