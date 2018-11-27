/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

/**
 *
 * @author andi
 */
public class Parametro extends VarMetodo {

    private int posicion;

    public Parametro(String nombre, int linea, int columna, int p, Tipo tipo) {
        this.linea = linea;
        this.columna = columna;
        posicion = p;
        this.nombre= nombre;
        this.tipoVar = tipo;
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

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    //TODO: PARAMETRO
    @Override
    public void controlDeclaraciones() throws Exception {
        if (!tipoVar.check()) {
            throw new Exception("Error, tipo de parametro '" + tipoVar.getNombre() + "' invalido en linea " + linea);
        }
    }

}
