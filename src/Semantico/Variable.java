
package Semantico;

/**
 *
 * @author andi
 */
public abstract class Variable {
    protected String nombre;
    protected Tipo tipoVar;
    protected int linea;
    protected int columna;
    protected int offset;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipoVar() {
        return tipoVar;
    }

    public void setTipoVar(Tipo tipoVar) {
        this.tipoVar = tipoVar;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    
        
    public abstract void controlDeclaraciones() throws Exception;
    
}
