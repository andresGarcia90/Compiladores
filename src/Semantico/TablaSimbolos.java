/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Token.Token;
import java.util.HashMap;
import java.util.Map;
import GCI.GenCode;

/**
 *
 * @author andi
 */
public class TablaSimbolos {

    private Clase claseActual;
    private Unidad unidadActual;
    private Bloque bloqueActual;
    boolean mainDeclarado = false;
    boolean ladoIzquierdo = false;
    private Metodo main;

    private static Map<String, Clase> clases;

    public TablaSimbolos() {
        clases = new HashMap<String, Clase>();
        agregarObject();
    }

    public void agregarSystem1() {
        try {
            agregarSystem();
        } catch (Exception ex) {
            System.out.println("Error Semantico: No se pudo crear la clase System error " + ex.getMessage());
        }
    }

    private void agregarObject() {
        Clase c = new Clase(new Token("idClase", "Object", 0, 0));
        clases.put("Object", c);
        c.setActualizado(true);
        c.setVisitado(true);
    }

    private void agregarSystem() throws Exception {

        Clase c = new Clase(new Token("idClase", "System", 0, 0));
        clases.put("System", c);
        claseActual = c;
        c.setHereda("Object");
        c.agregarConstructor(new Token("idClase", "System", 0, 0));

        //Metodos de la clase System.
        Metodo m = new Metodo(new Token("idMetVar", "read", 0, 0), "static", new Int(0, 0));
        m.setDeclaradoEn(c);
        m.setOffset(0);
        String read = "READ\nPUSH 48\nSUB\nSTORE 3\nSTOREFP\nRET 0";
        m.setCuerpo(new BloqueAux(read));
        m.setTieneReturn(true);
        c.agregarMetodo(m);

        m = new Metodo(new Token("idMetVar", "printB", 0, 0), "static", new Void2());
        m.agregarParametro(new Token("", "b", 0, 0), new Bool(0, 0));
        m.setDeclaradoEn(c);
        m.setOffset(1);
        String printB = "LOAD 3\nBPRINT";
        m.setCuerpo(new BloqueAux(printB));
        m.setOffParam(1);
        c.agregarMetodo(m);

        m = new Metodo(new Token("idMetVar", "printC", 0, 0), "static", new Void2());
        m.agregarParametro(new Token("", "c", 0, 0), new Char(0, 0));
        m.setDeclaradoEn(c);
        m.setOffset(2);
        String printC = "LOAD 3\nCPRINT";
        m.setCuerpo(new BloqueAux(printC));
        m.setOffParam(1);
        c.agregarMetodo(m);

        m = new Metodo(new Token("idMetVar", "printI", 0, 0), "static", new Void2());
        m.agregarParametro(new Token("", "i", 0, 0), new Int(0, 0));
        m.setDeclaradoEn(c);
        m.setOffset(3);
        String printI = "LOAD 3\nIPRINT";
        m.setCuerpo(new BloqueAux(printI));
        m.setOffParam(1);
        c.agregarMetodo(m);

        m = new Metodo(new Token("idMetVar", "printS", 0, 0), "static", new Void2());
        m.agregarParametro(new Token("", "s", 0, 0), new TipoString(0, 0));
        m.setDeclaradoEn(c);
        m.setOffset(4);
        String printS = "LOAD 3\nSPRINT";
        m.setCuerpo(new BloqueAux(printS));
        m.setOffParam(1);
        c.agregarMetodo(m);

        m = new Metodo(new Token("idMetVar", "println", 0, 0), "static", new Void2());
        m.setDeclaradoEn(c);
        m.setOffset(5);
        String println = "PRNLN";
        m.setCuerpo(new BloqueAux(println));
        m.setOffParam(0);
        c.agregarMetodo(m);

        m = new Metodo(new Token("idMetVar", "printBln", 0, 0), "static", new Void2());
        m.agregarParametro(new Token("", "b", 0, 0), new Bool(0, 0));
        m.setDeclaradoEn(c);
        m.setOffset(6);
        String printBln = printB + '\n' + println;
        m.setCuerpo(new BloqueAux(printBln));
        m.setOffParam(1);
        c.agregarMetodo(m);

        m = new Metodo(new Token("idMetVar", "printCln", 0, 0), "static", new Void2());
        m.agregarParametro(new Token("", "c", 0, 0), new Char(0, 0));
        m.setDeclaradoEn(c);
        m.setOffset(7);
        String printCln = printC + '\n' + println;
        m.setCuerpo(new BloqueAux(printCln));
        m.setOffParam(1);
        c.agregarMetodo(m);

        m = new Metodo(new Token("idMetVar", "printIln", 0, 0), "static", new Void2());
        m.agregarParametro(new Token("", "i", 0, 0), new Int(0, 0));
        m.setDeclaradoEn(c);
        m.setOffset(8);
        String printIln = printI + '\n' + println;
        m.setCuerpo(new BloqueAux(printIln));
        m.setOffParam(1);
        c.agregarMetodo(m);

        m = new Metodo(new Token("idMetVar", "printSln", 0, 0), "static", new Void2());
        m.agregarParametro(new Token("", "s", 0, 0), new TipoString(0, 0));
        m.setDeclaradoEn(c);
        m.setOffset(9);
        String printSln = printS + '\n' + println;
        m.setCuerpo(new BloqueAux(printSln));
        m.setOffParam(1);
        c.agregarMetodo(m);

        c.setActualizado(true);
        c.setVisitado(true);
    }

    public boolean isLadoIzquierdo() {
        return ladoIzquierdo;
    }

    public void setLadoIzquierdo(boolean ladoIzquierdo) {
        this.ladoIzquierdo = ladoIzquierdo;
    }

    public boolean estaClase(String clase) {
        return clases.containsKey(clase);
    }

    public void agregarClase(Clase c) {
        clases.put(c.getNombre(), c);
        claseActual = c;
    }

    public Clase getClaseActual() {
        return claseActual;
    }

    public void setClaseActual(Clase claseActual) {
        this.claseActual = claseActual;
    }

    public Unidad getUnidadActual() {
        return unidadActual;
    }

    public void setUnidadActual(Unidad unidadActual) {
        this.unidadActual = unidadActual;
    }

    public Clase getClase(String clase) {
        return clases.get(clase);
    }

    public boolean isMainDeclarado() {
        return mainDeclarado;
    }

    public void setMainDeclarado(boolean mainDeclarado) {
        this.mainDeclarado = mainDeclarado;
    }

    public Metodo getMain() {
        return main;
    }

    public void setMain(Metodo main) {
        this.main = main;
    }

    public static Map<String, Clase> getClases() {
        return clases;
    }

    public static void setClases(Map<String, Clase> clases) {
        TablaSimbolos.clases = clases;
    }

    public Bloque getBloqueActual() {
        return bloqueActual;
    }

    public void setBloqueActual(Bloque bloqueActual) {
        this.bloqueActual = bloqueActual;
    }

    public void chequearDeclaraciones() throws Exception {
        for (Clase c : clases.values()) {
            if (!c.getNombre().equals("Object") && !c.getNombre().equals("System")) {
                c.chequearDeclaraciones();
            }
        }
        if (!isMainDeclarado()) {
            throw new Exception("Error Semantico: No hay ninguna clase con un main declarado");
        }
    }

    public void chequearSentencias() throws Exception {
        GenCode.gen().comment("<<<<< Inicializacion >>>>");
        GenCode.gen().nl();
        GenCode.gen().write(".CODE");
        GenCode.gen().write("PUSH lheap_init");
        GenCode.gen().write("CALL");
        GenCode.gen().write("PUSH " + main.getLabel() + " # Metodo Main");
        GenCode.gen().write("CALL");
        GenCode.gen().write("HALT");
        GenCode.gen().nl();
        GenCode.gen().nl();

        GenCode.gen().write("lmalloc: LOADFP # Inicialización unidad");
        GenCode.gen().write("LOADSP");
        GenCode.gen().write("STOREFP # Finaliza inicialización del RA");
        GenCode.gen().write("LOADHL # hl");
        GenCode.gen().write("DUP # hl");
        GenCode.gen().write("PUSH 1 # 1");
        GenCode.gen().write("ADD # hl + 1");
        GenCode.gen().write("STORE 4 # Guarda resultado (puntero a base del bloque)");
        GenCode.gen().write("LOAD 3 # Carga cantidad de celdas a alojar (par´ametro)");
        GenCode.gen().write("ADD");
        GenCode.gen().write("STOREHL # Mueve el heap limit (hl)");
        GenCode.gen().write("STOREFP");
        GenCode.gen().write("RET 1 # Retorna eliminando el parámetro");
        GenCode.gen().nl();
        GenCode.gen().nl();

        GenCode.gen().write("lheap_init: RET 0 # Inicialización simplificada del .heap");
        GenCode.gen().nl();
        GenCode.gen().nl();

        GenCode.gen().write("# ---------GCI DEL CODIGO FUENTE---------------");

        for (Clase c : clases.values()) {
            if (!c.getNombre().equals("Object")) {
                claseActual = c;
                c.chequearSentencias();
            }
        }
        
        
        GenCode.gen().close();
    }

}
