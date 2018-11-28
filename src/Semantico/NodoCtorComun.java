/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import GCI.GenCode;
import Token.Token;
import java.util.Map;

/**
 *
 * @author andi
 */
public class NodoCtorComun extends NodoCtor {

    private Argumentos args;

    public NodoCtorComun(Token id, Argumentos args) {
        this.tok = id;
        this.args = args;
    }

    public Argumentos getArgs() {
        return args;
    }

    public void setArgs(Argumentos args) {
        this.args = args;
    }

    public void validarArgs(Ctor c) throws Exception {

        Argumentos actual = args;
        Map<String, Parametro> params = c.getParams();

        for (int i = 0; i < params.size(); i++) {
            if (actual == null) {
                throw new Exception("Cantidad de parametros invalido para el ctor " + c.getNombre() + " en la linea " + args.getExp().getTok().getLineNumber());
            }

            Parametro p = c.getParametro(i);
            if (actual.getExp() == null) {
                throw new Exception("Faltan parametros en la llamada al ctor " + c.getNombre() + " en la linea " + tok.getLineNumber());
            }
            TipoBase tActual = actual.getExp().check();

            if (!p.getTipoVar().esCompatible(tActual)) {
                throw new Exception("Los tipos de los parametros en la llamada al ctor " + c.getNombre() + " no se corresponden en la linea " + args.getExp().getTok().getLineNumber());
            }

            actual = actual.getArgs();
            GenCode.gen().write("SWAP");

            //actual = args.getArgs();
        }

        if (actual != null) {
            if (actual.getExp() != null) {
                //System.out.println("ESTOY ACA");
                throw new Exception("Cantidad de parametros invalida para el ctor " + c.getNombre() + " en la linea " + args.getExp().getTok().getLineNumber());
            }
        }

    }

    @Override
    public TipoBase check() throws Exception {
        Clase c = analizadorsintactico.AnalizadorSintactico.getTs().getClase(tok.getLexema());

        if (c == null) {
            throw new Exception("En la linea " + tok.getLineNumber() + " la clase " + tok.getLexema() + " no existe");
        }

        GenCode.gen().write("RMEM 1 # Reservo lugar para el constructor de " + c.getNombre());
        GenCode.gen().write("PUSH " + (c.getVariables().size() + 1) + " # Reservo lugar para variables de instancia y VT de la clase " + c.getNombre());
        GenCode.gen().write("PUSH lmalloc # Apilo la etiqueta del lmalloc");
        GenCode.gen().write("CALL # Llamada al metodo malloc");
        GenCode.gen().write("DUP");
        GenCode.gen().write("PUSH VT_" + c.getNombre() + " # Apilo direccion de la VTable de la clase " + c.getNombre());
        GenCode.gen().write("STOREREF 0 # Guardamos la Referencia a la VT en el CIR que creamos");
        GenCode.gen().write("DUP");

        Ctor ctor = c.getConstructor();

        validarArgs(ctor);

        // System.out.println("TIpo tok: "+tok.getLexema());
        if (enca != null) {
            return enca.check(new TipoClase(tok.getLineNumber(), tok.getColumNumber(), tok.getLexema()));
        }

        GenCode.gen().write("PUSH " + ctor.getLabel() + " # Apilo etiqueta del constructor");
        GenCode.gen().write("CALL # Llamo al constructor");

        return new TipoClase(tok.getLineNumber(), tok.getColumNumber(), tok.getLexema());
    }

}
