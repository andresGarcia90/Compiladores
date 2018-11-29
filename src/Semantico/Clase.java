/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import GCI.GenCode;
import Token.Token;
import java.util.HashMap;
import java.util.Map;
import analizadorsintactico.*;

/**
 *
 * @author andi
 */
public class Clase {

    private Token token;
    private String nombre;
    private String hereda;
    private Map<String, VarInstancia> variables;
    private Map<String, Metodo> metodos;
    private boolean tieneConst = false;
    private boolean visitado, actualizado;
    private Ctor constructor;
    private boolean constructorPredefinido;
    private int linea, columna;
    private boolean mainDeclarado = false;
    private int cantMetDyn = 0;

    public Clase(Token t) {
        this.token = t;
        nombre = t.getLexema();
        linea = t.getLineNumber();
        columna = t.getColumNumber();
        hereda = "Object";
        variables = new HashMap<String, VarInstancia>();
        metodos = new HashMap<String, Metodo>();
        visitado = false;
        actualizado = false;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHereda() {
        return hereda;
    }

    public void setHereda(String hereda) {
        this.hereda = hereda;
    }

    public Map<String, VarInstancia> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, VarInstancia> variables) {
        this.variables = variables;
    }

    public boolean estaVariable(String n) {
        return variables.containsKey(n);
    }

    public Map<String, Metodo> getMetodos() {
        return metodos;
    }

    public void setMetodos(Map<String, Metodo> metodos) {
        this.metodos = metodos;
    }

    public boolean getTieneConst() {
        return tieneConst;
    }

    public void setTieneConst(boolean tieneConst) {
        this.tieneConst = tieneConst;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public boolean isActualizado() {
        return actualizado;
    }

    public void setActualizado(boolean actualizado) {
        this.actualizado = actualizado;
    }

    public Ctor getConstructor() {
        return constructor;
    }

    public void setConstructor(Ctor constructor) {
        this.constructor = constructor;
    }

    public boolean isConstructorPredefinido() {
        return constructorPredefinido;
    }

    public void setConstructorPredefinido(boolean constructorPredefinido) {
        this.constructorPredefinido = constructorPredefinido;
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

    public int getCantMetDyn() {
        return cantMetDyn;
    }

    public void setCantMetDyn(int cantMetDyn) {
        this.cantMetDyn = cantMetDyn;
    }

    public void agregarMetodo(Metodo m) throws Exception {
        //TODO: Aca chequear si el metodo tiene cantidades distintas de parametros.. entonces si se puede agregar.
        if (!metodos.containsKey(m.getNombre())) {
            metodos.put(m.getNombre(), m);
            if (m.getFormaMetodo().equals("dynamic")) {
                cantMetDyn++;
            }
        } else {
            m.getLinea();
            m.getColumna();
            metodos.get(m.getNombre()).getLinea();
            throw new Exception("Error Semantico: En linea " + m.getLinea() + " el metodo " + m.getNombre() + " ya fue creado en linea " + metodos.get(m.getNombre()).getLinea());
        }
    }

    private void agregarMetodo(String s, Metodo m) throws Exception {
        if (!metodos.containsKey(m.getNombre())) {
            metodos.put(s, m);
            if (m.getFormaMetodo().equals("dynamic")) {
                cantMetDyn++;
            }
        } else {
            throw new Exception("Error Semantico: El metodo ya estaba declarado en " + metodos.get(m.getNombre()).getLinea());
        }
    }

    public void agregarVariable(Token token, String visibilidad, Tipo tipo) throws Exception {
        if (!variables.containsKey(token.getLexema())) {
            variables.put(token.getLexema(), new VarInstancia(token, visibilidad, tipo));
        } else {
            throw new Exception("Error Semantico: La variable " + token.getLexema() + " ya existente en " + variables.get(token.getLexema()).getLinea());
        }
    }

    public void agregarVariable(String k, VarInstancia v) throws Exception {
        if (!variables.containsKey(v.getNombre())) {
            //System.out.println(v.getNombre());
            variables.put(k, v);
        } else {
            throw new Exception("Error Semantico: La variable " + v.getNombre() + " ya existente en la linea " + variables.get(v.getNombre()).getLinea());

        }
    }

    public Ctor agregarConstructor(Token token) {
        Ctor c = new Ctor(token);
        c.setDeclaradoEn(AnalizadorSintactico.getTs().getClaseActual());
        tieneConst = true;
        constructor = c;
        return c;
    }

    private boolean estaMetodo(Metodo m) {
        return metodos.containsKey(m.getNombre());
    }

    public boolean estaMetodo(String s) {
        return metodos.containsKey(s);
    }

    private Clase getPadre() {
        return AnalizadorSintactico.getTs().getClase(hereda);
    }

    //CHEQUEO DE HERENCIA
    public void chequearHerencia() throws Exception {
        if (!getNombre().equals("Object") && !getNombre().equals("System")) {
            if (!AnalizadorSintactico.getTs().estaClase(hereda)) {
                throw new Exception("La clase " + hereda + " en la linea " + linea + " de la que quiere heredar " + nombre + " no existe");
            }
            chequearCircular();
        }
    }

    private void chequearCircular() throws Exception {
        if (!actualizado) {
            if (!visitado) {
                visitado = true;
                Clase padre = getPadre();
                padre.chequearHerencia();

                setOffsets();

                for (VarInstancia v : padre.getVariables().values()) {
                    String nv = v.getNombre();
                    //   System.out.println("nombre: "+v.nombre+ " cant "+v.linea);
                    while (estaVariable(nv)) {
                        nv = "#" + nv;
                    }

                    if (v.getVisibilidad().equals("private")) {
                        nv = "@" + nv;
                    }

                    agregarVariable(nv, v);
                }

                //Agrego los metodos de mi padre y controlo en caso de que redefini.
                for (Metodo m : padre.getMetodos().values()) {

                    //si esta quiere decir que lo estoy redefiniendo.
                    if (estaMetodo(m)) {
                        Metodo actual = metodos.get(m.getNombre());

                        //Forma de metodo igual
                        if (m.getFormaMetodo().equals(actual.getFormaMetodo())) {

                            //Cantidad y tipo de Argumentos
                            if (igualesParametros(m.getParams(), actual.getParams())) {

                                //Tipo de salida
                                if (!m.getRetorno().esCompatible(actual.getRetorno())) {
                                    throw new Exception("Error Semantico: El metodo " + m.getNombre() + " esta mal redefinido (Su retorno no coincide)");
                                }else{
                                    actual.setOffset(m.getOffset());
                                }

                            } else {
                                throw new Exception("Error Semantico: el metodo " + m.getNombre() + " esta mal redefinido (Sus argumentos son distinos)");
                            }

                        } else {
                            throw new Exception("Error Semantico: El metodo " + m.getNombre() + " esta mal redefinido (FormaMetodo)");
                        }

                    } else {
                        //En caso de que no este redefinido lo agrego al metodo hijo
                        //Si es el constructor no lo agrego
                        if (!m.getNombre().equals(padre.getNombre())) {

                            //TODO: OJOOOO CON ESTO, el comentado es de la anterior entrega.
                            // agregarMetodo("@" + m.getNombre(), m);
                            agregarMetodo(m.getNombre(), m);
                        }
                    }
                }
                setOffsetsMetodos();
                actualizado = true;
                visitado = false;

            } else {
                throw new Exception("En la jerarquia de herencia de la clase " + nombre + " hay herencia circular");

            }
        }
    }

    private void setOffsetsMetodos() {

        Clase padre = AnalizadorSintactico.getTs().getClase(hereda);

        int cantMetodosPadre = AnalizadorSintactico.getTs().getClase(hereda).getCantMetDyn();

        //System.out.println("Clase "+nombre+" cant de metodos de mi padre "+cantMetodosPadre);
        for (String s : metodos.keySet()) {
            if (s.charAt(0) != '@') {
                Metodo m = metodos.get(s);
                if (!m.getNombre().equals(nombre) && !m.getFormaMetodo().equals("static")) {
                    if (!padre.estaMetodo(m)) {
                        m.setOffset(cantMetodosPadre++);
                        //System.out.println("Clase "+nombre);
                    }
                }
            }
        }
    }

    private boolean igualesParametros(Map<String, Parametro> p1, Map<String, Parametro> p2) throws Exception {
        if (p1.size() != p2.size()) {
            return false;
        } else {
            for (Parametro a : p1.values()) {
                for (Parametro b : p2.values()) {
                    if (a.getPosicion() == b.getPosicion()) {
                        if (!a.getTipoVar().esCompatible(b.getTipoVar())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    //Chequeo de variables
    public void chequearVariables() throws Exception {
        for (Variable v : variables.values()) {
            if (metodos.containsKey(v.getNombre())) {
                throw new Exception("En la linea " + v.getLinea() + " la variable " + v.getNombre() + " tiene el mismo nombre que el metodo de la linea " + metodos.get(v.getNombre()).getLinea());
            }
            if (!v.getTipoVar().check()) {
                throw new Exception("Error: la variable '" + v.getNombre() + "' en la linea " + v.getLinea() + " esta declarada de un tipo inexistente (" + v.getTipoVar().getNombre() + ").");

            }
        }
    }

    private void chequearMetodos() throws Exception {
        for (Metodo m : metodos.values()) {
            //m.chequearSentencia();
            m.controlDeclaraciones();
            if (m.getNombre().equals("main")) {
                if (!m.getFormaMetodo().equals("static")) {
                    throw new Exception("El metodo main en la linea " + m.getLinea() + " de la clase " + m.getDeclaradoEn().getNombre() + " debe ser estatico");
                }
                if (m.getParams().size() == 0) {
                    AnalizadorSintactico.getTs().setMainDeclarado(true);
                    AnalizadorSintactico.getTs().setMain(m);
                } else {
                    throw new Exception("El Metodo main en la linea " + m.getLinea() + " tiene parametros");
                }
            }
        }
    }

    private void chequearCtor() throws Exception {
        if (!constructorPredefinido) {
            AnalizadorSintactico.getTs().setUnidadActual(constructor);
            constructor.controlDeclaraciones();
        }
    }

    public void chequearDeclaraciones() throws Exception {
        chequearMetodos();
        chequearHerencia();
        chequearVariables();
        chequearCtor();
    }

    public void chequearSentencias() throws Exception {
        GenCode.gen().write("# Clase " + nombre);
        GenCode.gen().write("# Creo la VTable");
        GenCode.gen().nl();
        GenCode.gen().nl();

        GenCode.gen().write(".DATA");

        String ls = "DW ";
        
                        System.out.println(nombre+": Cantidad de metodos "+cantMetDyn);

        for (int i = 0; i < cantMetDyn; i++) {
            for (Metodo m : metodos.values()) {
                    System.out.println("met: "+m.getLabel()+ " off: "+m.getOffset());
                if (m.getOffset() == i) {
                    ls += m.getLabel() + ",";
                }
            }
        }

        if (cantMetDyn > 0) {
            ls = ls.substring(0, ls.length() - 1); //Elimino la ultima coma
            GenCode.gen().write("VT_" + nombre + ": " + ls);
        } else {
            GenCode.gen().write("VT_" + nombre + ": DW 0");
        }
        GenCode.gen().nl();
        GenCode.gen().nl();

        GenCode.gen().write(".CODE");
        AnalizadorSintactico.getTs().setUnidadActual(constructor);
        constructor.chequearSentencia();

        for (Metodo m : metodos.values()) {
            analizadorsintactico.AnalizadorSintactico.getTs().setUnidadActual(m);
            m.chequearSentencia();
           // m.controlDeclaraciones();
            if (!m.getRetorno().getNombre().equals("void") && !m.getTieneReturn()) {
                throw new Exception("El metodo " + m.getNombre() + " debe retornar algo de tipo " + m.getRetorno().getNombre() + " en la linea " + m.getLinea());
            }
        }
        
        
        analizadorsintactico.AnalizadorSintactico.getTs().setUnidadActual(constructor);
    }

    private void setOffsets() {

        Clase padre = AnalizadorSintactico.getTs().getClase(hereda);
        int cantAtrPadre = padre.getVariables().size();

        for (Variable v : variables.values()) {
            //cantAtrPadre++;
            v.setOffset(++cantAtrPadre);

        }
    }

}
