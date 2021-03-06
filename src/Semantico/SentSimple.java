
package Semantico;

import GCI.GenCode;
import Token.Token;

/**
 *
 * @author andi
 */
public class SentSimple extends Sentencia {

    private NodoExp exp;

    public SentSimple(Token token, NodoExp e) {
        linea = token.getLineNumber();
        this.exp = e;
    }

    public NodoExp getE() {
        return exp;
    }

    public void setE(NodoExp e) {
        this.exp = e;
    }

    @Override
    public void check() throws Exception {
        TipoBase tm = exp.check();
        if (!tm.getNombre().equals("void")) {
            GenCode.gen().write("POP");
        }
    }

}
