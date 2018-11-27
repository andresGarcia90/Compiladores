/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import GCI.GenCode;
import Token.Token;
import java.util.HashMap;

/**
 *
 * @author andi
 */
public class Ctor extends Unidad {

    private boolean predefinido;

    public Ctor(Token token) {
        nombre = token.getLexema();
        linea = token.getLineNumber();
        columna = token.getColumNumber();
        params = new HashMap<String, Parametro>();
        vars = new HashMap<String, Variable>();
        predefinido = true;
    }

    public boolean isPredefinido() {
        return predefinido;
    }

    public void setPredefinido(boolean predefinido) {
        this.predefinido = predefinido;
    }

    public boolean estaVar(String nombreVar) {
        return vars.containsKey(nombreVar);
    }

    @Override
    public void controlDeclaraciones() throws Exception {

        for (Parametro p : params.values()) {
            p.controlDeclaraciones();

            p.setOffset(2 + cantParams() - p.getPosicion()); //3+cantParams-nroParam

        }

    }

    public void chequearSentencias() throws Exception {

        /**
         * ********************GenCode***************************
         */
        GenCode.gen().write(getLabel() + ": NOP # CONSTRUCTOR " + nombre);

        GenCode.gen().inicioUnidad();

        /**
         * **********************Fin GenCode***************************
         */
        /**
         * **********************Chequeo Sentencias***************************
         */
        //System.out.println("Metodo "+nombre+" offvar "+offVar);
        setOffVar(0);//???????????????

        if (cuerpo != null) {
            cuerpo.check();
        }
        /**
         * *******************Fin Chequeo Sentencias***************************
         */

        GenCode.gen().write("STOREFP # Restablezco el contexto");

        GenCode.gen().write("RET " + (params.size() + 1) + " # Retorno y libero espacio de los parametros del metodo y del THIS " + nombre);

        GenCode.gen().nl();

    }

    private int cantParams() {
        return this.getParams().size();
    }

    @Override
    public void chequearSentencia() throws Exception {
        
        /**
         * ********************GenCode***************************
         */
        GenCode.gen().write(getLabel() + ": NOP # CONSTRUCTOR " + nombre);

        GenCode.gen().inicioUnidad();

        /**
         * **********************Fin GenCode***************************
         */
        /**
         * **********************Chequeo Sentencias***************************
         */
        //System.out.println("Metodo "+nombre+" offvar "+offVar);
        setOffVar(0);//???????????????

        if (cuerpo != null) {
            cuerpo.check();
        }
        /**
         * *******************Fin Chequeo Sentencias***************************
         */

        GenCode.gen().write("STOREFP # Restablezco el contexto");

        GenCode.gen().write("RET " + (params.size() + 1) + " # Retorno y libero espacio de los parametros del metodo y del THIS " + nombre);

        GenCode.gen().nl();

    }

}
