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
public class NodoVar extends NodoAcceso {

    // private Token id;
    public NodoVar(Token id) {
        //this.id = id;
        this.tok = id;
    }

    public NodoVar(Token id, Encadenado enca) {
        //super(false);
        this.tok = id;
        this.enca = enca;
    }

    public Token getId() {
        return tok;
    }

    public void setId(Token id) {
        this.tok = id;
    }

    @Override
    public TipoBase check() throws Exception {
        //System.out.println("NodoVar: Tenemos " + tok.getName());
        Clase c = analizadorsintactico.AnalizadorSintactico.getTs().getClaseActual();
        Unidad u = analizadorsintactico.AnalizadorSintactico.getTs().getUnidadActual();
        String nombreVar = tok.getLexema();
        Variable v;
        TipoBase ret;

        Metodo m = c.getMetodos().get(u.getNombre());

        if (m != null) {
            if (m.estaVar(nombreVar)) {
                v = m.getVars().get(nombreVar);
                ret = v.getTipoVar();
            } else {
                if (c.estaVariable(nombreVar)) {
                    if (!m.getFormaMetodo().equals("static")) {
                        v = c.getVariables().get(nombreVar);
                        ret = v.getTipoVar();
                    } else {
                        throw new Exception("Se intento referenciar a una variable de instancia en la linea " + tok.getLineNumber());
                    }
                } else {
                    throw new Exception("La variable " + nombreVar + " en la linea " + tok.getLineNumber() + " no esta definida");
                }
            }

            if (enca != null) {
                ret = enca.check(ret);
            }

            if (ret instanceof TipoArreglo && v.getTipoVar() instanceof TipoClase) {
                throw new Exception("No se puede acceder a un tipo clase como arreglo en la linea " + tok.getLineNumber());
            }

        } else {
            ret = null;
            Ctor ctor = c.getConstructor();
            if (ctor.estaVar(nombreVar)) {
                v = ctor.getVars().get(nombreVar);
                ret = v.getTipoVar();
            } else {
                if (ctor.estaParametro(nombreVar)) {

                    for (Parametro p : ctor.getParams().values()) {
                        if (p.getNombre().equals(nombreVar)) {
                            ret = p.getTipoVar();
                        }
                    }

                } else {

                    if (c.estaVariable(nombreVar)) {
                        ret = c.getVariables().get(nombreVar).getTipoVar();
                        if (enca != null) {
                            ret = enca.check(ret);
                        }
                    } else {

                        throw new Exception("No se encuentra la variable " + nombreVar + " en la linea " + tok.getLineNumber());
                    }

                }
            }
        }

        return ret;
    }

}
