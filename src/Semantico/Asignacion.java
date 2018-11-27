/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Token.Token;

/**
 *
 * @author andi
 */
public class Asignacion extends Sentencia {

    private NodoExp lder;
    private NodoAcceso lizq;

    public Asignacion(Token token, NodoExp expresion, NodoAcceso idIzquierdo) {
        this.lder = expresion;
        this.lizq = idIzquierdo;
        linea = token.getLineNumber();
        //System.out.println("ASIGNACION: "+lder.getTok().getName()+" "+ lizq.getTok().getName());
    }

    public NodoExp getExpresion() {
        return lder;
    }

    public void setExpresion(NodoExp expresion) {
        this.lder = expresion;
    }

    public NodoAcceso getIdIzquierdo() {
        return lizq;
    }

    public void setIdIzquierdo(NodoAcceso idIzquierdo) {
        this.lizq = idIzquierdo;
    }

    @Override
    public void check() throws Exception {
        TipoBase tipoExp = lder.check();

        if (lizq instanceof NodoThis && this.lizq.getEnca() == null) {
            throw new Exception("No se puede asignar nada a un this en la linea " + this.linea);
        }
/*
        if (lizq.getEnca() instanceof LlamadaEncadenada) {
            throw new Exception("No se le puede asignar un valor a una llamada a un metodo en la linea " + this.getLinea());
        }
*/
        TipoBase tipoLi = lizq.check();

        if (!tipoLi.esCompatible(tipoExp)) {
            throw new Exception("Los tipos " + tipoLi.getNombre() + " y " + tipoExp.getNombre() + " no son compatibles en la asignacion de la linea: " + linea);
        }

    }

}
