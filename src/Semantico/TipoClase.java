/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import analizadorsintactico.AnalizadorSintactico;
import java.util.ArrayList;

/**
 *
 * @author andi
 */
public class TipoClase extends TipoReferencia {

    public TipoClase(int l, int c, String n) {
        linea = l;
        columna = c;
        nombre = n;
    }

    @Override
    public boolean esCompatible(TipoBase tipo) throws Exception {

        if (!this.getNombre().equals("null")) {
            if (tipo.getNombre().equals("null")) {
                return true;
            }
            
            if (AnalizadorSintactico.getTs().estaClase(nombre)) {
                if (AnalizadorSintactico.getTs().estaClase(tipo.nombre)) {
                    Clase claseActual = AnalizadorSintactico.getTs().getClase(this.getNombre());
                    Clase claseThis = AnalizadorSintactico.getTs().getClase(tipo.getNombre());
                    if(claseActual.getNombre().equals("Object")){
                        return true;
                    }
                    while (!claseThis.getNombre().equals("Object")) {
                        if (claseThis.getNombre().equals(claseActual.getNombre())) {
                            return true;
                        }
                        claseThis = AnalizadorSintactico.getTs().getClase(claseThis.getHereda());
                    }
                    return false;
                } else {
                    return false;
                }
            } else {
                return false;

            }
        } else {
            return false;
        }
        

    }

    public boolean esCompatibleBinario(TipoClase tipo) {
        Clase actual = AnalizadorSintactico.getTs().getClase(nombre);
        if (this.getNombre().equals("null") || tipo.getNombre().equals("null")) {
            return true;
        }
        while (!actual.getNombre().equals("Object")) {
            Clase aux = AnalizadorSintactico.getTs().getClase(tipo.getNombre());
            while (!aux.getNombre().equals("Object")) {
                if (actual.getNombre().equals(aux.getNombre())) {
                    return true;
                }
                aux = AnalizadorSintactico.getTs().getClase(aux.getHereda());
            }
            actual = AnalizadorSintactico.getTs().getClase(actual.getHereda());
        }

        return false;
    }

    @Override
    public boolean check() {
        if (AnalizadorSintactico.getTs().estaClase(nombre)) {
            return true;
        }
        return false;
    }

    @Override
    public void gen(String s) {
    }

}
