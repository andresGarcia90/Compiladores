/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Token.Token;
import analizadorsintactico.AnalizadorSintactico;
import java.util.Map;

/**
 *
 * @author andi
 */
public abstract class Unidad {

    protected String nombre;
    protected Clase declaradoEn;
    protected Map<String, Parametro> params;
    protected Map<String, Variable> vars;
    protected int linea;
    protected int columna;
    protected Bloque cuerpo;
    protected String label;
    protected int offset = 99;
    protected int offParam;
    protected int offVar;
    protected boolean esConstructor = false;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Clase getDeclaradoEn() {
        return declaradoEn;
    }

    public void setDeclaradoEn(Clase declaradoEn) {
        this.declaradoEn = declaradoEn;
    }

    public Map<String, Parametro> getParams() {
        return params;
    }

    public void setParams(Map<String, Parametro> params) {
        this.params = params;
    }

    public Map<String, Variable> getVars() {
        return vars;
    }

    public void setVars(Map<String, Variable> vars) {
        this.vars = vars;
    }

    public void agregarParam(Token aux, Tipo tipo) throws Exception {
        //TODO: CHEQUEAR AGREGAR PARAM EN UNIDAD
        if (!params.containsKey(aux.getLexema())) {
            Parametro p = new Parametro(aux.getLexema(), aux.getLineNumber(), aux.getColumNumber(), params.size(), tipo);
            //p.setTipoVar(tipo);
            params.put(aux.getLexema(), p);

        } else {
            Unidad m = AnalizadorSintactico.getTs().getUnidadActual();
            throw new Exception("Hay 2 parametros con el mismo nombre en el metodo '" + m.getNombre() + "' en la linea " + m.getLinea());
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

    public Bloque getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(Bloque cuerpo) {
        this.cuerpo = cuerpo;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffParam() {
        return offParam;
    }

    public void setOffParam(int offParam) {
        this.offParam = offParam;
    }

    public int getOffVar() {
        return offVar;
    }

    public void setOffVar(int offVar) {
        this.offVar = offVar;
    }

    public String getLabel() {
        if (label == null) {
            label = "" + nombre + "_" + declaradoEn.getNombre();
        }
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isEsConstructor() {
        return esConstructor;
    }

    public void setEsConstructor(boolean esConstructor) {
        this.esConstructor = esConstructor;
    }
    
    public Parametro getParametro(int i) {
        for (Parametro p : params.values()) {
            if (p.getPosicion() == i) {
                return p;
            }
        }
        return null;
    }

    public boolean estaVar(String nombreVar) {
        return vars.containsKey(nombreVar);
    }

    public boolean estaParametro(String nombreVar) {
        return params.containsKey(nombreVar);
    }

    public void eliminarVar(String s) {
        vars.remove(s);
    }

    public void agregarVariable(Variable v) throws Exception {
        if (!vars.containsKey(v.getNombre())) {
            vars.put(v.getNombre(), v);
        } else {
            throw new Exception("La variable " + v.getNombre() + " de la linea " + v.getLinea() + " ya fue creada en " + vars.get(v.getNombre()).getLinea());
        }
    }

    /*
    public void chequearSentencia() throws Exception {

        if (analizadorsintactico.AnalizadorSintactico.getTs().getClaseActual().getNombre().equals(declaradoEn.getNombre())) {
            if (cuerpo != null) {
                cuerpo.check();
            }
        }
    }
*/
    
    public abstract void chequearSentencia() throws Exception;

    public abstract void controlDeclaraciones() throws Exception;

}
