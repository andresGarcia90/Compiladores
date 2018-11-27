/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

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
        //TODO: CAMBIE LA PALABRA METODO POR CTOR
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
            
            //TODO: POR LAS DUDAS TOQUE ACA
            actual = actual.getArgs();
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

        Ctor ctor = c.getConstructor();

        validarArgs(ctor);

        // System.out.println("TIpo tok: "+tok.getLexema());
        if (enca != null) {
            return enca.check(new TipoClase(tok.getLineNumber(), tok.getColumNumber(), tok.getLexema()));
        }

        return new TipoClase(tok.getLineNumber(), tok.getColumNumber(), tok.getLexema());
    }

}
