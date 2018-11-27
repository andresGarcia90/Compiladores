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
public class Return extends Sentencia {

    private NodoExp exp;

    public Return(Token tok, NodoExp exp) {
        linea = tok.getLineNumber();
        this.exp = exp;
    }

    public NodoExp getExp() {
        return exp;
    }

    public void setExp(NodoExp exp) {
        this.exp = exp;
    }

    @Override
    public void check() throws Exception {
        if (analizadorsintactico.AnalizadorSintactico.getTs().getUnidadActual() instanceof Metodo) {
            Metodo m = (Metodo) analizadorsintactico.AnalizadorSintactico.getTs().getUnidadActual();

            if (exp != null) {
                TipoBase tipoExp = exp.check();
                if (!m.getRetorno().esCompatible(tipoExp)) {
                    throw new Exception("Se intenta retornar algo de tipo " + tipoExp.getNombre() + " pero el metodo " + m.getNombre() + " deberia retornar " + m.getRetorno().getNombre() + " en la linea " + this.linea);
                } else {
                    if (m.getFormaMetodo().equals("void")) {
                        throw new Exception("El metodo " + m.getNombre() + " en la linea " + m.getLinea() + " deberia retornar algo de tipo " + m.getRetorno().getNombre() + " pero es void");
                    }

                    if (m.getFormaMetodo().equals("static")) {
                        GenCode.gen().write("STORE " + (3 + m.getParams().size()) + " # Guardo valor de retorno del metodo " + m.getNombre());
                    } else {
                        GenCode.gen().write("STORE " + (4 + m.getParams().size()) + " # Guardo valor de retorno del metodo " + m.getNombre());
                    }

                    if (m.getVars().size() - m.getParams().size() > 0) {
                        GenCode.gen().write("FMEM " + (m.getVars().size() - m.getParams().size()) + " # Libero espacio de variable locales al metodo " + m.getNombre());
                    }

                    GenCode.gen().write("STOREFP");

                    if (m.getFormaMetodo().equals("static")) { //Si es estatico
                        GenCode.gen().write("RET " + m.getParams().size() + " # Retorno y libero espacio de los parametros del metodo " + m.getNombre());
                    } else { //Si es dinamico
                        GenCode.gen().write("RET " + (m.getParams().size() + 1) + " # Retorno y libero espacio de los parametros del metodo y del THIS " + m.getNombre());
                    }

                }
                m.setTieneReturn(true);

            } else {
                if (m.getTieneReturn()) {
                    throw new Exception("El return del metodo " + m.getNombre() + " no puede ser vacio");
                }
            }

        } else {
            throw new Exception("El constructor no puede retornar.");
        }

    }

}
