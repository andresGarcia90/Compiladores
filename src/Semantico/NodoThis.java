/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;
import analizadorsintactico.AnalizadorSintactico;
import Token.Token;

/**
 *
 * @author andi
 */
public class NodoThis extends NodoAcceso {

    private Token tok;

    public NodoThis(Token tok) {
       // super(false);
        this.tok = tok;
    }

    public TipoBase check() throws Exception{
        if(AnalizadorSintactico.getTs().getUnidadActual() instanceof Metodo){
            Metodo m = (Metodo) AnalizadorSintactico.getTs().getUnidadActual();
            if(m.getFormaMetodo().equals("static")){
                throw new Exception("Error Semantico en la linea: "+ tok.getLineNumber()+ " no se puede ejecutar un this en un metodo static");
            }
        }
        if(this.enca != null){
            return this.enca.check(new TipoClase(tok.getLineNumber(), tok.getColumNumber(), AnalizadorSintactico.getTs().getClaseActual().getNombre()));
        }
        return new TipoClase(tok.getLineNumber(), tok.getColumNumber(), AnalizadorSintactico.getTs().getClaseActual().getNombre());
    }

}
