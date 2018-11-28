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

        Clase c = AnalizadorSintactico.getTs().getClaseActual();
        Unidad u = AnalizadorSintactico.getTs().getUnidadActual();
        Metodo m = c.getMetodos().get(u.getNombre());

        String nombreVar = tok.getLexema();

        Tipo aux;
        Variable v;

        if (u.estaVar(nombreVar)) {
            v = u.getVars().get(nombreVar);
            aux = new TipoClase(v.getLinea(), v.getColumna(), v.getTipoVar().getNombre());
            if (enca != null) {
                GenCode.gen().write("LOAD " + v.getOffset() + " # Cargo " + v.getNombre());
            }
        } else if (c.estaVariable(nombreVar)) {
            v = c.getVariables().get(nombreVar);
            aux = new TipoClase(v.getLinea(), v.getColumna(), v.getTipoVar().getNombre());

            if (enca != null) {
                GenCode.gen().write("LOAD 3");
                GenCode.gen().write("LOADREF " + v.getOffset() + " # Cargo variable de instancia " + v.getNombre() + " de la clase " + c.getNombre());
            }
        } else {
            throw new Exception("La variable del lado izquierdo de la asignacion no pertence a las variables de instancia o a las variables locales del metodo " + m.getNombre() + " en la linea " + tok.getLineNumber());
        }

        if (enca != null) {

            return enca.check(aux);
        } else {
            if (u.estaVar(nombreVar)) { //Variable local
                GenCode.gen().write("STORE " + v.getOffset() + " # Guardo el valor en la variable " + v.getNombre());
            } else {
                GenCode.gen().write("LOAD 3");
                GenCode.gen().write("SWAP"); //Swap ya que storeRef utiliza los parametros en orden inverso (1ro CIR, 2do valor a almacenar)
                GenCode.gen().write("STOREREF " + v.getOffset() + " # Guardo el valor en la variable " + v.getNombre());
            }
        }
        return v.getTipoVar();

        /*
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
                
                GenCode.gen().write("LOAD " + v.getOffset() + " # Cargo el valor de la variable " + v.getNombre());

            } else {
                if (c.estaVariable(nombreVar)) {
                    if (!m.getFormaMetodo().equals("static")) {
                        v = c.getVariables().get(nombreVar);
                        ret = v.getTipoVar();

                        GenCode.gen().write("LOAD 3 # Cargo This");
                        GenCode.gen().write("LOADREF " + v.getOffset() + " # Cargo el valor de la variable");

                    } else {
                        throw new Exception("Se intento referenciar a una variable de instancia en la linea " + tok.getLineNumber());
                    }
                } else {
                    throw new Exception("La variable " + nombreVar + " en la linea " + tok.getLineNumber() + " no esta definida");
                }
            }

            if (enca != null) {
                ret = enca.check(ret);
            } else {
                if (this.ladoIzq) {
                    if (m.estaVar(nombreVar)) { //Variable local
                        GenCode.gen().write("STORE " + v.getOffset() + " # Guardo el valor en la variable " + v.getNombre());
                    } else {
                        GenCode.gen().write("LOAD 3");
                        GenCode.gen().write("SWAP"); //Swap ya que storeRef utiliza los parametros en orden inverso (1ro CIR, 2do valor a almacenar)
                        GenCode.gen().write("STOREREF " + v.getOffset() + " # Guardo el valor en la variable " + v.getNombre());
                    }
                }

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
        
        
        
         */
    }

}
