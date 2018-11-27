/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import GCI.GenCode;
import Semantico.NodoPrimario;
import Token.Token;
import java.util.Map;

/**
 *
 * @author andi
 */
public class NodoLlamadaDirecta extends NodoPrimario {

    protected Argumentos args;

    public NodoLlamadaDirecta(Token id, Argumentos args, Encadenado enca) {
        this.tok = id;
        this.enca = enca;
        this.args = args;

    }

    public Argumentos getArgs() {
        return args;
    }

    public void setArgs(Argumentos args) {
        this.args = args;
    }

    public void validarArgs(Metodo m) throws Exception {
        Argumentos actual = args;
        Map<String, Parametro> params = m.getParams();

        for (int i = 0; i < params.size(); i++) {
            if (actual == null) {
                throw new Exception("Cantidad de parametros invalida para el metodo " + m.getNombre() + " en la linea " + args.getExp().getTok().getLineNumber());
            }
            Parametro p = m.getParametro(i);
            if (actual.getExp() == null) {
                throw new Exception("Faltan parametros en la llamada al metodo " + m.getNombre() + " en la linea " + tok.getLineNumber());
            }
            TipoBase tActual = actual.getExp().check();

            /*        
            if (!tActual.esCompatible(p.getTipoVar())) {
                throw new Exception("Los tipos de los parametros en la llamada al metodo " + m.getNombre() + " no se corresponden en la linea " + args.getExp().getTok().getLineNumber());
            }
             */
            if (!p.getTipoVar().esCompatible(tActual)) {
                throw new Exception("Los tipos de los parametros en la llamada al metodo " + m.getNombre() + " no se corresponden en la linea " + args.getExp().getTok().getLineNumber());
            }

            if (m.getFormaMetodo().equals("dynamic")) {
                GenCode.gen().write("SWAP");
            }

            actual = actual.getArgs();
        }
        if (actual != null) {
            if (actual.getExp() != null) {
                throw new Exception("Cantidad de parametros invalida para el metodo " + m.getNombre() + " en la linea " + args.getExp().getTok().getLineNumber());
            }
        }
    }

    public TipoBase check() throws Exception {
        Clase c = analizadorsintactico.AnalizadorSintactico.getTs().getClaseActual();
        String nombreMet = tok.getLexema();
        TipoBase retorno;

        if (c.estaMetodo(nombreMet)) {
            Metodo m;
            m = c.getMetodos().get(nombreMet);

            if (analizadorsintactico.AnalizadorSintactico.getTs().getUnidadActual() instanceof Metodo) {
                Metodo actual = (Metodo) analizadorsintactico.AnalizadorSintactico.getTs().getUnidadActual();

                if (actual.getFormaMetodo().equals("static")) {
                    if (!m.getFormaMetodo().equals("static")) {
                        throw new Exception("En la linea " + tok.getLineNumber() + " se llama a un metodo dinamico desde un metodo estatico");
                    }
                }

                if (!m.getRetorno().getNombre().equals("void")) {
                    GenCode.gen().write("RMEM 1 # Reservo lugar para el retorno del metodo");
                    if (m.getFormaMetodo().equals("dynamic")) {
                        GenCode.gen().write("SWAP");
                    }
                }

            }

            validarArgs(m);

            if (m.getFormaMetodo().equals("static")) {
                GenCode.gen().write("PUSH " + m.getLabel() + " # Apilo la etiqueta del metodo");
                GenCode.gen().write("CALL # Llamo al metodo");
            } else {
                GenCode.gen().write("LOAD 3 # Cargo THIS");
                GenCode.gen().write("DUP");
                GenCode.gen().write("LOADREF 0 # Cargo la VTable");
                GenCode.gen().write("LOADREF " + m.getOffset() + " # Cargo el metodo " + m.getNombre());
                GenCode.gen().write("CALL # Llamo al metodo");
            }

            retorno = m.getRetorno();
        } else {
            throw new Exception("El metodo " + tok.getLexema() + " no pertence a la clase " + c.getNombre() + " en la linea " + tok.getLineNumber());
        }
        if (enca != null) {
            return enca.check(retorno);
        }
        return retorno;
    }

    public void imprimirArgs(Metodo m) {
        System.out.println("Metodo: " + m.getNombre());
        System.out.print("Argumentos: (");
        Argumentos actual = args;
        while (actual != null) {
            if (actual.getExp() != null) {
                System.out.print(actual.getExp().getTok().getLexema() + ", ");
            }
            actual = actual.getArgs();
        }
        System.out.println(")");

        Map<String, Parametro> params = m.getParams();

        System.out.print("Parametros: (");
        for (int i = 0; i < params.size(); i++) {
            System.out.print(m.getParametro(i).getNombre() + ",");
        }
        System.out.println();
    }

}
