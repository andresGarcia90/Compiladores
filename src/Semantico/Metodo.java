/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import GCI.GenCode;
import Token.Token;
import analizadorsintactico.*;
import java.util.HashMap;

/**
 *
 * @author andi
 */
public class Metodo extends Unidad {

    private String formaMetodo;
    private TipoBase retorno;
    private boolean checkeado;
    private boolean tieneReturn;

    public Metodo(Token token, String fm, TipoBase retorno) {
        nombre = token.getLexema();
        linea = token.getLineNumber();
        columna = token.getColumNumber();
        params = new HashMap<String, Parametro>();
        vars = new HashMap<String, Variable>();
        cuerpo = null;
        formaMetodo = fm;
        this.retorno = retorno;
        tieneReturn = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarParametro(Token token, Tipo tipo) throws Exception {
        if (!params.containsKey(token.getName())) {
            params.put(token.getLexema(), new Parametro(token.getLexema(), token.getLineNumber(), token.getColumNumber(), params.size(), tipo));
        } else {
            Unidad m = AnalizadorSintactico.getTs().getUnidadActual();
            throw new Exception("Error Semantico: Ya existe un parametro con ese nombre en : " + m.getLinea());
        }
    }

    public void agregarVariable(Variable v) throws Exception {
        if (!vars.containsKey(v.getNombre())) {
            vars.put(v.getNombre(), v);
        } else {
            throw new Exception("La variable " + v.getNombre() + " de la linea " + v.getLinea() + " ya fue creada en " + vars.get(v.getNombre()).getLinea());
        }
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    /*
    public void eliminarVar(String v) {
        vars.remove(v);
    }
     */
    public String getFormaMetodo() {
        return formaMetodo;
    }

    public void setFormaMetodo(String formaMetodo) {
        this.formaMetodo = formaMetodo;
    }

    public TipoBase getRetorno() {
        return retorno;
    }

    public void setRetorno(TipoBase retorno) {
        this.retorno = retorno;
    }

    public boolean isCheckeado() {
        return checkeado;
    }

    public void setCheckeado(boolean checkeado) {
        this.checkeado = checkeado;
    }

    public boolean getTieneReturn() {
        return tieneReturn;
    }

    public void setTieneReturn(boolean tieneReturn) {
        this.tieneReturn = tieneReturn;
    }

    public Parametro getParametro(int nro) {
        for (Parametro p : params.values()) {
            if (p.getPosicion() == nro) {
                return p;
            }
        }
        return null;
    }

    public Parametro getParametro(String s) {
        return params.get(s);
    }

    @Override
    public void controlDeclaraciones() throws Exception {
        if (!retorno.check()) {
            throw new Exception("Error Semantico: Tipo invalido en el metodo " + nombre);
        }
        for (Parametro p : params.values()) {
            p.controlDeclaraciones();

            if (formaMetodo.equals("static")) {
                p.setOffset(2 + cantParams() - p.getPosicion()); //3+cantParams-nroParam
            } else {
                p.setOffset(3 + cantParams() - p.getPosicion());
            }

            vars.put(p.getNombre(), p);

        }

    }

    public void chequearSentencia() throws Exception {
        if (analizadorsintactico.AnalizadorSintactico.getTs().getClaseActual().getNombre().equals(this.declaradoEn.getNombre())) {

            /**********************GenCode****************************/
          
          
          
            GenCode.gen().write(getLabel() + ": NOP # METODO " + nombre);
            GenCode.gen().inicioUnidad();

            /************************Fin GenCode****************************/
            /************************Chequeo Sentencias****************************/
            //System.out.println("Metodo "+nombre+" offvar "+offVar);
            setOffVar(0);//???????????????

            if (cuerpo != null) {
                cuerpo.check();
            }

            /**
             * *******************Fin Chequeo Sentencias***************************
             */
            /**
             * ********************GenCode***************************
             */
            GenCode.gen().write("STOREFP # Restablezco el contexto");

            if (formaMetodo.equals("static") && !nombre.equals(declaradoEn.getNombre())) { //Si es estatico y no es el constructor

                GenCode.gen().write("RET " + params.size() + " # Retorno y libero espacio de los parametros del metodo " + nombre);
            } else { //Si es dinamico o es constructor

                GenCode.gen().write("RET " + (params.size() + 1) + " # Retorno y libero espacio de los parametros del metodo y del THIS " + nombre);
            }

            GenCode.gen().nl();

        }
    }

    private int cantParams() {
        return params.size();
    }
}
