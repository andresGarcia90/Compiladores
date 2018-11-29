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
public class ArregloEncadenado extends Encadenado {

    private NodoExp exp;

    public ArregloEncadenado(Token id, Encadenado cad, NodoExp exp) {
        this.id = id;
        this.exp = exp;
        this.cadena = cad;
        if(analizadorsintactico.AnalizadorSintactico.getTs().ladoIzquierdo){
            this.ladoIzq = true;
        }
    }

    public NodoExp getExp() {
        return exp;
    }

    public void setExp(NodoExp exp) {
        this.exp = exp;
    }

    @Override
    public TipoBase check(TipoBase tipo) throws Exception {

        TipoBase tipoExpr = exp.check();

        if (!tipoExpr.esCompatible(new Int(0, 0))) {
            throw new Exception("La expresion de la linea " + id.getLineNumber() + " no es de tipo entero");
        }

        GenCode.gen().write("ADD #Sumo el offset del Arreglo");

        if (this.ladoIzq && cadena == null) {
            GenCode.gen().write("SWAP");
            GenCode.gen().write("STOREREF 0 # Guardo en el offset ");
        } else {
            GenCode.gen().write("LOADREF 0 # Acceso Arreglo Encadenado ");

        }

        if (cadena != null) {
            return cadena.check(tipoExpr);
        }

        switch (tipo.getNombre()) {
            case "arregloInt": {
                return new Int(0, 0);
            }
            case "arregloBool": {
                return new Bool(0, 0);
            }
            case "arregloChar": {
                return new Char(0, 0);
            }
        }

        //System.out.println("ID: "+ tipo.getNombre());
        return new TipoArregloBool(id);

    }

}
