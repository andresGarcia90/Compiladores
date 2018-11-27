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
public class ArregloEncadenado extends Encadenado {

    private NodoExp exp;

    public ArregloEncadenado(Token id, Encadenado cad, NodoExp exp) {
        this.id = id;
        this.exp = exp;
        this.cadena = cad;
    }

    public NodoExp getExp() {
        return exp;
    }

    public void setExp(NodoExp exp) {
        this.exp = exp;
    }
    
    

    @Override
    public TipoBase check(TipoBase tipo) throws Exception {
        TipoBase tipoExpr = exp.check();
        if(!tipoExpr.esCompatible(new Int(0, 0))){
            throw new Exception("La expresion de la linea "+id.getLineNumber()+ " no es de tipo entero");
        }
        if (cadena != null){
            return cadena.check(tipoExpr);
        }
        
        switch(tipo.getNombre()){
            case "arregloInt": {return new Int(0, 0);}
            case "arregloBool": {return new Bool(0, 0);}
            case "arregloChar": {return new Char(0, 0);}
        }
        
        //System.out.println("ID: "+ tipo.getNombre());
        return new TipoArregloBool(id);
        
            
    }

}
