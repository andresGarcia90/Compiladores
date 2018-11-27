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

            //System.out.println("Tiene return? "+m.getTieneReturn());
            if (exp != null) {
                TipoBase tipoExp = exp.check();
                if (!m.getRetorno().esCompatible(tipoExp)) {
                    throw new Exception("Se intenta retornar algo de tipo " + tipoExp.getNombre() + " pero el metodo " + m.getNombre() + " deberia retornar " + m.getRetorno().getNombre() + " en la linea " +this.linea);
                } 
                //TODO: [IMPORTANTE] CHEQUEAR ESTO DE NUEVO
                else {
                    if (m.getFormaMetodo().equals("void")) {
                        throw new Exception("El metodo " + m.getNombre() + " en la linea " + m.getLinea() + " deberia retornar algo de tipo " + m.getRetorno().getNombre() + " pero es void");
                    }
                }
                m.setTieneReturn(true);

            } else {
                if(m.getTieneReturn())
                    throw new Exception("El return del metodo " + m.getNombre() + " no puede ser vacio");
            }

        } else {
            throw new Exception("El constructor no puede retornar.");
        }

    }

}
