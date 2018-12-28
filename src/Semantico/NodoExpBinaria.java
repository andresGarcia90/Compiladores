
package Semantico;

import Token.Token;
import GCI.GenCode;

/**
 *
 * @author andi
 */
public class NodoExpBinaria extends NodoExp {

    private NodoExp e1;
    private NodoExp e2;

    public NodoExpBinaria(Token token, NodoExp e1, NodoExp e2) {
        tok = token;
        this.e1 = e1;
        this.e2 = e2;
    }

    public NodoExp getE1() {
        return e1;
    }

    public void setE1(NodoExp e1) {
        this.e1 = e1;
    }

    public NodoExp getE2() {
        return e2;
    }

    public void setE2(NodoExp e2) {
        this.e2 = e2;
    }

    @Override
    public Tipo check() throws Exception {
        String operador = tok.getLexema();
        TipoBase tipoI = e1.check();
        TipoBase tipoD = e2.check();

        switch (operador) {
            case "+":
            case "-":
            case "/":
            case "*": {
                if (!tipoI.getNombre().equals("int") || !tipoD.getNombre().equals("int")) {
                    throw new Exception("Error, los tipos en la expresion de la linea " + tok.getLineNumber() + " no son compatibles");
                }
                switch (operador) {
                    case "+": {
                        GenCode.gen().write("ADD # Suma");
                        break;
                    }
                    case "-": {
                        GenCode.gen().write("SUB # Resta");
                        break;
                    }
                    case "*": {
                        GenCode.gen().write("MUL # Multiplicacion");
                        break;
                    }
                    case "/": {
                        GenCode.gen().write("Div # Division");
                        break;
                    }
                    case "%": {
                        GenCode.gen().write("MOD # Modulo");
                        break;
                    }
                }
                return new Int(tok.getLineNumber(), tok.getColumNumber());
            }

            case "<":
            case ">":
            case "<=":
            case ">=": {
                if (!tipoI.getNombre().equals("int") || !tipoD.getNombre().equals("int")) {
                    throw new Exception("Error, los tipos en la expresion de la linea " + tok.getLineNumber() + " no son compatibles");
                }
                switch (operador) {
                    case "<": {
                        GenCode.gen().write("LT # Menor");
                        break;
                    }
                    case ">": {
                        GenCode.gen().write("GT # Mayor");
                        break;
                    }
                    case ">=": {
                        GenCode.gen().write("GE # Mayor o igual");
                        break;
                    }
                    case "<=": {
                        GenCode.gen().write("LE # Menor o igual");
                        break;
                    }
                }

                return new Bool(tok.getLineNumber(), tok.getColumNumber());
            }

            case "==":
            case "!=": {
                if (tipoI instanceof TipoClase && tipoI instanceof TipoClase) {
                    TipoClase claseA = (TipoClase) tipoI;
                    TipoClase claseB = (TipoClase) tipoD;
                    if (!claseA.esCompatibleBinario(claseB)) {
                        throw new Exception("Error, los tipos en la expresion de la linea " + tok.getLineNumber() + " no son compatibles");

                    }

                } else {

                    if (!tipoI.esCompatible(tipoD)) {
                        throw new Exception("Error, los tipos en la expresion de la linea " + tok.getLineNumber() + " no son compatibles");
                    }

                }
                switch (operador) {
                    case "==": {
                        GenCode.gen().write("EQ # Igual");
                        break;
                    }
                    case "!=": {
                        GenCode.gen().write("GT # Not Igual");
                        break;
                    }
                }

                return new Bool(tok.getLineNumber(), tok.getColumNumber());
            }

            default: {
                //Caso de && o ||
                if (!tipoI.getNombre().equals("boolean") || !tipoD.getNombre().equals("boolean")) {
                    throw new Exception("Error, los tipos en la expresion de la linea " + tok.getLineNumber() + " no son compatibles");
                }

                switch (operador) {
                    case "&&": {
                        GenCode.gen().write("AND # And");
                        break;
                    }
                    case "||": {
                        GenCode.gen().write("OR # Or");
                        break;
                    }
                }
                return new Bool(tok.getLineNumber(), tok.getColumNumber());
            }

        }

    }

}
