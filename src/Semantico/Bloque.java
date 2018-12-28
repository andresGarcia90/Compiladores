
package Semantico;

import GCI.GenCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import analizadorsintactico.AnalizadorSintactico;

/**
 *
 * @author andi
 */
public class Bloque extends Sentencia {

    protected Map<String, Variable> variables;
    protected ArrayList<Sentencia> sentencias;
    private Bloque padre;

    public Bloque() {
        variables = new HashMap<String, Variable>();
        sentencias = new ArrayList<Sentencia>();

    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Variable> variables) {
        this.variables = variables;
    }

    public ArrayList<Sentencia> getSentencias() {
        return sentencias;
    }

    public void setSentencias(ArrayList<Sentencia> sentencias) {
        this.sentencias = sentencias;
    }

    public void agregarSentencia(Sentencia s) {
        sentencias.add(s);
    }

    public Bloque getPadre() {
        return padre;
    }

    public void setPadre(Bloque padre) {
        this.padre = padre;
    }

    public void agregarVariable(Variable var) throws Exception {
        if (!variables.containsKey(var.getNombre())) {
            variables.put(var.getNombre(), var);
        } else {
            throw new Exception("La variable " + var.getNombre() + " en la linea " + var.getLinea() + " ya fue declarada en " + variables.get(var.getNombre()).getLinea());
        }
    }

    @Override
    public void check() throws Exception {
        AnalizadorSintactico.getTs().setBloqueActual(this);
        for (Sentencia s : sentencias) {
            s.check();
        }

        //FMEM DE LA CANT DE VARS LOCALES
        GenCode.gen().write("FMEM " + variables.size() + " # Libero espacio de variables locales al bloque");
        //CAMBIAR OFF VARS LOCALES
        AnalizadorSintactico.getTs().getMain().setOffVar(AnalizadorSintactico.getTs().getMain().getOffVar() - variables.size());

        for (Variable v : variables.values()) {
            AnalizadorSintactico.getTs().getUnidadActual().eliminarVar(v.getNombre());
        }

        AnalizadorSintactico.getTs().setBloqueActual(padre);

    }

}
