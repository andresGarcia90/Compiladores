/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Token;

/**
 * Clase responsable de guardar la informacion del token
 * @author Andres Garcia
 */
public class Token {
    private String name;
    private String lexema;
    private int lineNumber;
    private int columNumber;
    
    /**
     * Contructor de la clase
     * @param n valor del nombre
     * @param l valor del lexema
     * @param line valor de la linea
     * @param col  valor de la columna
     */
    public Token(String n, String l, int line, int col){
        name = n;
        lexema = l;
        lineNumber = line;
        columNumber = col;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getColumNumber() {
        return columNumber;
    }

    public void setColumNumber(int columNumber) {
        this.columNumber = columNumber;
    }
    
}
