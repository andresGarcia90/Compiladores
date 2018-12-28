
package Semantico;

import GCI.GenCode;
import java.util.ArrayList;

/**
 *
 * @author andi
 */
public class DecVars extends Sentencia {

    private ArrayList<Variable> variables;
    private Tipo tipo;

    public DecVars(ArrayList<Variable> variables, Tipo tipo) {
        this.variables = variables;
        this.tipo = tipo;
    }

    @Override
    public void check() throws Exception {

        Unidad m = analizadorsintactico.AnalizadorSintactico.getTs().getUnidadActual();
        for (Variable v : variables) {
            m.agregarVariable(v);

            /*OFFSET*/
            v.setOffset(m.getOffVar());
            m.setOffVar(m.getOffVar() - 1);

            v.controlDeclaraciones();
        }

        GenCode.gen().write("RMEM " + variables.size() + " # Reservo espacio para variables locales");
    }

}
