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
public class LlamadaEncadenada extends Encadenado {

    protected Argumentos args;

    public LlamadaEncadenada(Token tok, Argumentos args, Encadenado enc) {

        this.id = tok;
        this.cadena = enc;
        this.args = args;
        if(analizadorsintactico.AnalizadorSintactico.getTs().isLadoIzquierdo())
            this.ladoIzq = true;
        else this.ladoIzq = false;
    }

    public Argumentos getArgs() {
        return args;
    }

    public void setArgs(Argumentos args) {
        this.args = args;
    }
    
    
    
    private void validarArgs(Metodo m) throws Exception{
		
		Argumentos actual = args;
		Map<String, Parametro> params = m.getParams();
		
		for (int i = 0; i < params.size(); i++) {
			if(actual == null) {
				throw new Exception("Cantidad de parametros invalida para el metodo "+m.getNombre()+" en la linea "+args.getExp().getTok().getLineNumber());
			}
			Parametro p = m.getParametro(i);
			if(actual.getExp() == null) {
				throw new Exception("Faltan parametros en la llamada al metodo "+m.getNombre()+" en la linea "+id.getLineNumber());
			}
			TipoBase tActual = actual.getExp().check();
			if(!tActual.esCompatible(p.getTipoVar())) {
				throw new Exception("Los tipos de los parametros en la llamada al metodo "+m.getNombre()+" no se corresponden en la linea "+args.getExp().getTok().getLineNumber());
			}
			actual = args.getArgs();
		}
		
		if(actual!=null)
			if(actual.getExp()!=null) {
				throw new Exception("Cantidad de parametros invalida para el metodo "+m.getNombre()+" en la linea "+args.getExp().getTok().getLineNumber());
			}
		
		
		
	}
    
    
    

    @Override
    public TipoBase check(TipoBase tipo) throws Exception {
        TipoBase ret;
        if (args == null) { //Variable o clase
            if (tipo.getNombre().equals("int") || tipo.getNombre().equals("boolean") || tipo.getNombre().equals("char") || tipo.getNombre().equals("String")) {
                throw new Exception("La variable " + id.getLexema() + " en la linea " + id.getLineNumber() + " no puede realizar llamadas ya que es de un tipo primitvo");
            }
            Clase c = analizadorsintactico.AnalizadorSintactico.getTs().getClase(tipo.getNombre());

            if (c.estaVariable(id.getLexema())) {
                Variable var = c.getVariables().get(id.getLexema());
                ret = var.getTipoVar();
            } else {
                throw new Exception("La variable " + id.getLexema() + " en la linea " + id.getLineNumber() + " no se encuentra declarada en la clase " + c.getNombre());
            }
        } else { //Llamada a un metodo
            Clase c = analizadorsintactico.AnalizadorSintactico.getTs().getClase(tipo.getNombre());

            if (c == null) {
                throw new Exception("La clase a la que se le esta queriendo pedir el metodo '" + id.getLexema() + "' en la linea " + id.getLineNumber() + " no existe");
            }

            if (c.estaMetodo(id.getLexema())) {
                Metodo m = c.getMetodos().get(id.getLexema());
                //chequear que metodo no sea privado
                validarArgs(m);
                if(cadena == null && this.isLadoIzq()){
                    throw new Exception("No se le puede asignar un valor a un metodo en la linea "+ id.getLineNumber());
                }
                ret = m.getRetorno();
            } else {
                throw new Exception("El metodo " + id.getLexema() + " en linea " + id.getLineNumber() + " no se encuentra declarado");
            }
        }
        
        if (cadena != null) {
            return cadena.check(ret);
        }
        return ret;

    }

}
