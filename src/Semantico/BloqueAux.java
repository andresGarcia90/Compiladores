/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import GCI.GenCode;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Meltman
 */
public class BloqueAux extends Bloque{

    
	String print;

	public BloqueAux(String S)
	{	
		print = S;	
		sentencias = new ArrayList<Sentencia>();
		variables= new HashMap<String, Variable>();
	}
	
	public void check() {
		String[] inst = print.split("\n");
		
		for (String s : inst) {
			GenCode.gen().write(s);
		}
				
	}
}
