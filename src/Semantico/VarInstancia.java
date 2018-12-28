
package Semantico;

import Token.Token;

/**
 *
 * @author andi
 */
public class VarInstancia extends Variable {

    private String visibilidad;

    public VarInstancia(Token token, String visibilidad, Tipo tipo) {
        this.visibilidad = visibilidad;
        nombre = token.getLexema();
        linea = token.getLineNumber();
        columna = token.getColumNumber();
        tipoVar = tipo;

    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    @Override
    public void controlDeclaraciones() throws Exception {

        if (!tipoVar.check()) {
            throw new Exception("Error: la variable de instancia '" + nombre + "' en la linea " + linea + " esta declarada de un tipo inexistente (" + tipoVar.getNombre() + ").");
        }
    }

}
