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
public class IdEncadenado extends Encadenado {

    public IdEncadenado(Token id, Encadenado e) {
        this.id = id;
        this.cadena = e;
        if (analizadorsintactico.AnalizadorSintactico.getTs().isLadoIzquierdo()) {
            this.ladoIzq = true;
        } else {
            this.ladoIzq = false;
        }
    }

    @Override
    public TipoBase check(TipoBase tipo) throws Exception {

        // System.out.println("ID ENCADENADO: "+ tipo.getNombre());
        Clase c = analizadorsintactico.AnalizadorSintactico.getTs().getClase(tipo.getNombre());
        String nombreVar = id.getLexema();
        VarInstancia v;

        if (tipo.getNombre().equals("int") || tipo.getNombre().equals("boolean") || tipo.getNombre().equals("char") || tipo.getNombre().equals("String")) {
            throw new Exception("La variable " + id.getLexema() + " en la linea " + id.getLineNumber() + " no puede llamar metodos ya que es de tipo primitivo");
        }
        if (c == null) {
            throw new Exception("Se trata de acceder a un metodo void en la linea " + id.getLineNumber());
        }

        if (cadena == null) {

            if (c.getVariables().containsKey(nombreVar)) {
                v = c.getVariables().get(nombreVar);
                if (v.getVisibilidad().equals("private")) {
                    throw new Exception("No se puede acceder a la variable " + v.getNombre() + " de la linea " + v.getLinea() + " debido a que su visibilidad es privada");
                }
                //TODO: 29/11/18: 1722
                if (this.ladoIzq) {
                    GenCode.gen().write("SWAP");
                    GenCode.gen().write("STOREREF " + v.getOffset() + " # Guardo el valor en la variable " + v.getNombre());

                } else {
                    GenCode.gen().write("LOADREF " + v.getOffset() + " # Cargo variable de instancia " + v.getNombre() + " de la clase " + c.getNombre());

                }

            } else {
                throw new Exception("No se encontro la variable " + nombreVar + " de la linea " + id.getLineNumber() + " en la clase " + c.getNombre());
            }

            return v.getTipoVar();
        } else { //con encadenado
            if (c.getVariables().containsKey(nombreVar)) {
                v = c.getVariables().get(nombreVar);
                if (v.getVisibilidad().equals("private")) {
                    throw new Exception("No se puede acceder a la variable " + v.getNombre() + " de la linea " + v.getLinea() + " debido a que su visibilidad es privada");
                }

                GenCode.gen().write("LOADREF " + v.getOffset() + " # Cargo variable de instancia " + v.getNombre() + " de la clase " + c.getNombre());

                Tipo aux = v.getTipoVar();

                return cadena.check(aux);
            } else {
                throw new Exception("No se encontro la variable " + nombreVar + " de la linea " + id.getLineNumber() + " en la clase " + c.getNombre());
            }
        }
    }

    /* CHECK VIEJO
    
    
    public TipoBase check(TipoBase tipo) throws Exception {

        // System.out.println("ID ENCADENADO: "+ tipo.getNombre());
        Clase c = analizadorsintactico.AnalizadorSintactico.getTs().getClase(tipo.getNombre());
        String nombreVar = id.getLexema();
        VarInstancia v;

        if (tipo.getNombre().equals("int") || tipo.getNombre().equals("boolean") || tipo.getNombre().equals("char") || tipo.getNombre().equals("String")) {
            throw new Exception("La variable " + id.getLexema() + " en la linea " + id.getLineNumber() + " no puede llamar metodos ya que es de tipo primitivo");
        }
        if (c == null) {
            throw new Exception("Se trata de acceder a un metodo void en la linea " + id.getLineNumber());
        }

        if (cadena == null) {

            if (c.getVariables().containsKey(nombreVar)) {
                v = c.getVariables().get(nombreVar);

                GenCode.gen().write("LOADREF " + v.getOffset() + " # Cargo variable de instancia " + v.getNombre() + " de la clase " + c.getNombre());

            } else {
                throw new Exception("No se encontro la variable " + nombreVar + " de la linea " + id.getLineNumber() + " en la clase " + c.getNombre());
            }

            return v.getTipoVar();
        } else {
            if (c.getVariables().containsKey(nombreVar)) {
                v = c.getVariables().get(nombreVar);
                if (v.getVisibilidad().equals("private")) {
                    throw new Exception("No se puede acceder a la variable " + v.getNombre() + " de la linea " + v.getLinea() + " debido a que su visibilidad es privada");
                }

                GenCode.gen().write("LOADREF " + v.getOffset() + " # Cargo variable de instancia " + v.getNombre() + " de la clase " + c.getNombre());

                Tipo aux = v.getTipoVar();

                return cadena.check(aux);
            } else {
                throw new Exception("No se encontro la variable " + nombreVar + " de la linea " + id.getLineNumber() + " en la clase " + c.getNombre());
            }
        }
    }*/
}
