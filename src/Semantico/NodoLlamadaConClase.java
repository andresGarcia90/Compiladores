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
public class NodoLlamadaConClase extends NodoPrimario {

    protected Token idClase;
    protected Argumentos args;

    public NodoLlamadaConClase(Token idClase, Token id, Argumentos args) {
        this.idClase = idClase;
        this.tok = id;
        this.args = args;
    }

    public Token getIdClase() {
        return idClase;
    }

    public void setIdClase(Token tok) {
        this.idClase = tok;
    }

    public Argumentos getArgs() {
        return args;
    }

    public void setArgs(Argumentos args) {
        this.args = args;
    }

    

    public void validarArgumentos(Metodo m) throws Exception {
        Argumentos actual = args;
        Map<String, Parametro> params = m.getParams();
        for (int i = 0; i < params.size(); i++) {
            if (actual == null) {
                throw new Exception("Cantidad invalida de parametros para el metodo " + m.getNombre() + " en la linea " + args.getExp().getTok().getLineNumber());
            }
            Parametro p = m.getParametro(i);
            if (actual.getExp() == null) {
                throw new Exception("Faltan parametros en la llamada al metodo " + m.getNombre() + " en la linea " + tok.getLineNumber());
            }
            
            TipoBase tipoActual = actual.getExp().check();

            if (!tipoActual.esCompatible(p.getTipoVar())) {
                throw new Exception("Los tipos de los parametros en la llamada al metodo " + m.getNombre() + " no se corresponden en la linea " + args.getExp().getTok().getLineNumber());
            }
            actual = args.getArgs();
        }
        if (actual != null) {
            if (actual.getExp() != null) {
                throw new Exception("Cantidad de parametros invalida para el metodo " + m.getNombre() + " en la linea " + args.getExp().getTok().getLineNumber());
            }
        }
    }
    @Override
    public TipoBase check() throws Exception {
        Clase c = analizadorsintactico.AnalizadorSintactico.getTs().getClase(idClase.getLexema());

        if (c == null) {
            throw new Exception("En la linea " + tok.getLineNumber() + " la clase " + idClase.getLexema() + " no existe");
        }

        Metodo m = c.getMetodos().get(tok.getLexema());
        if (m == null) {
            throw new Exception("En la linea " + tok.getLineNumber() + " el metodo " + tok.getLexema() + " no existe o no es visible para la clase");
        } else {
            validarArgumentos(m);
        }
        if (!m.getFormaMetodo().equals("static")) {
            throw new Exception("El metodo " + tok.getLexema() + " no es estatico en la clase " + c.getNombre());
        }
        
        
        /*****************************************GENCODE*******************************************/
	if(!m.getRetorno().getNombre().equals("void"))
            GenCode.gen().write("RMEM 1 # Reservo memoria para el retorno del metodo");
	
        GenCode.gen().write("PUSH "+m.getLabel()+" # Apilo la etiqueta del metodo");
	GenCode.gen().write("CALL # Llamo al metodo");
        /*************************************FIN GENCODE*******************************************/
		
        
        
        if (enca != null) {
            return enca.check(m.getRetorno());
        }

        return m.getRetorno();

    }

}
