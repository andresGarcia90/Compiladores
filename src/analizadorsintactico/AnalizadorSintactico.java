/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorsintactico;

import AnalizadorLexico.ALex;
import GCI.GenCode;
import Semantico.*;
import Token.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author andi
 */
public class AnalizadorSintactico {

    private static Map<String, Set<String>> primeros;
    private static Map<String, Set<String>> siguientes;
    private static Token t;
    private static ALex alex;
    private static TablaSimbolos ts;
    //private static boolean procesandoAsignacion;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        t = new Token("", "", 0, 0);

        alex = new ALex(args[0]);
        //alex = new ALex("C:\\Users\\Meltman\\Desktop\\inout\\prueba.java");
        //GenCode.path = "C:\\Users\\Meltman\\Desktop\\inout\\out.ceiasm";

        //alex = new ALex("/home/andi/ejemplo");
        //GenCode.path = "/home/andi/out.ceiasm";
        if (args.length == 2) {
            GenCode.path = args[1];
        } else {
            GenCode.path = System.getProperty("user.dir") + "/out.ceiasm";
        }
        inicializar();
        try {
            t = alex.getToken();

            inicial();
            ts.chequearDeclaraciones();
            ts.chequearSentencias();
            System.out.println("La sintaxis y la semantica es correcta");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void inicializar() {
        ts = new TablaSimbolos();
        ts.agregarSystem1();

        primeros = new HashMap<String, Set<String>>();
        siguientes = new HashMap<String, Set<String>>();

        //Agrego los primeros.
        Set<String> primClase = new HashSet<String>();
        primClase.add("class");
        primeros.put("clase", primClase);

        Set<String> primInicial = new HashSet<String>();
        primInicial.addAll(primeros.get("clase"));
        primeros.put("inicial", primInicial);

        Set<String> primC = new HashSet<String>();
        primC.addAll(primeros.get("inicial"));
        primeros.put("c", primC);

        Set<String> primHerencia = new HashSet<String>();
        primHerencia.add("extends");
        primeros.put("herencia", primHerencia);

        Set<String> primH = new HashSet<String>();
        primH.addAll(primeros.get("herencia"));
        primeros.put("h", primH);

        Set<String> primVisibilidad = new HashSet<String>();
        primVisibilidad.add("public");
        primVisibilidad.add("private");
        primeros.put("visibilidad", primVisibilidad);

        Set<String> primAtributo = new HashSet<String>();
        primAtributo.addAll(primeros.get("visibilidad"));
        primeros.put("atributo", primAtributo);

        Set<String> primFormaMetodo = new HashSet<String>();
        primFormaMetodo.add("static");
        primFormaMetodo.add("dynamic");
        primeros.put("formaMetodo", primFormaMetodo);

        Set<String> primMetodo = new HashSet<String>();
        primMetodo.addAll(primeros.get("formaMetodo"));
        primeros.put("metodo", primMetodo);

        Set<String> primCtor = new HashSet<String>();
        primCtor.add("idClase");
        primeros.put("ctor", primCtor);

        Set<String> primMiembro = new HashSet<String>();
        primMiembro.addAll(primeros.get("atributo"));
        primMiembro.addAll(primeros.get("ctor"));
        primMiembro.addAll(primeros.get("metodo"));
        primeros.put("miembro", primMiembro);

        Set<String> primMiembros = new HashSet<String>();
        primMiembros.addAll(primeros.get("miembro"));
        primeros.put("miembros", primMiembros);

        Set<String> primArgsFormales = new HashSet<String>();
        primArgsFormales.add("parentesisAbre");
        primeros.put("argsFormales", primArgsFormales);

        Set<String> primTipo = new HashSet<String>();
        primTipo.add("boolean");
        primTipo.add("char");
        primTipo.add("int");
        primTipo.add("idClase");
        primTipo.add("String");
        primeros.put("tipo", primTipo);

        Set<String> primArgFormal = new HashSet<String>();
        primArgFormal.addAll(primeros.get("tipo"));
        primeros.put("argFormal", primArgFormal);

        Set<String> primListaArgsFormales = new HashSet<String>();
        primListaArgsFormales.addAll(primeros.get("argFormal"));
        primeros.put("listaArgsFormales", primListaArgsFormales);

        Set<String> primLAF = new HashSet<String>();
        primLAF.addAll(primeros.get("listaArgsFormales"));
        primeros.put("laf", primArgsFormales);

        Set<String> primLAFS = new HashSet<String>();
        primLAFS.add("coma");
        primeros.put("lafs", primLAFS);

        Set<String> primTipoMetodo = new HashSet<String>();
        primTipoMetodo.addAll(primeros.get("tipo"));
        primeros.put("tipoMetodo", primTipoMetodo);

        Set<String> primBool = new HashSet<String>();
        primBool.add("llaveAbre");
        primeros.put("bool", primBool);

        Set<String> primCh = new HashSet<String>();
        primCh.add("llaveAbre");
        primeros.put("ch", primCh);

        Set<String> primI = new HashSet<String>();
        primI.add("llaveAbre");
        primeros.put("i", primI);

        Set<String> primTipoPrimitivo = new HashSet<String>();
        primTipoPrimitivo.add("boolean");
        primTipoPrimitivo.add("char");
        primTipoPrimitivo.add("int");
        primeros.put("tipoPrimitivo", primTipoPrimitivo);

        Set<String> primTipoReferencia = new HashSet<String>();
        primTipoReferencia.add("idClase");
        primTipoReferencia.add("String");
        primTipoReferencia.addAll(primeros.get("tipoPrimitivo"));
        primeros.put("tipoReferencia", primTipoReferencia);

        Set<String> primListaDecVars = new HashSet<String>();
        primListaDecVars.add("idMetVar");
        primeros.put("listaDecVars", primListaDecVars);

        Set<String> primLDVFact = new HashSet<String>();
        primLDVFact.add("coma");
        primeros.put("lDVFact", primLDVFact);

        Set<String> primBloque = new HashSet<String>();
        primBloque.add("llaveAbre");
        primeros.put("bloque", primBloque);

        Set<String> primAccesoVar = new HashSet<String>();
        primAccesoVar.add("idMetVar");
        primeros.put("accesoVar", primAccesoVar);

        Set<String> primAccesoThis = new HashSet<String>();
        primAccesoThis.add("this");
        primeros.put("accesoThis", primAccesoThis);

        Set<String> primAsignacion = new HashSet<String>();
        primAsignacion.addAll(primeros.get("accesoVar"));
        primAsignacion.addAll(primeros.get("accesoThis"));
        primeros.put("asignacion", primAsignacion);

        Set<String> primSentenciaLlamada = new HashSet<String>();
        primSentenciaLlamada.add("parentesisAbre");
        primeros.put("sentenciaLlamada", primSentenciaLlamada);

        Set<String> primSentencia = new HashSet<String>();
        primSentencia.add("puntoYComa");
        primSentencia.addAll(primeros.get("asignacion"));
        primSentencia.addAll(primeros.get("sentenciaLlamada"));
        primSentencia.addAll(primeros.get("tipo"));
        primSentencia.add("if");
        primSentencia.add("while");
        primSentencia.addAll(primeros.get("bloque"));
        primSentencia.add("return");
        primeros.put("sentencia", primSentencia);

        Set<String> primS = new HashSet<String>();
        primS.addAll(primeros.get("sentencia"));
        primeros.put("s", primS);

        Set<String> primSentenciaFact = new HashSet<String>();
        primSentenciaFact.add("else");
        primeros.put("sentenciaFact", primSentenciaFact);

        Set<String> primOpMul = new HashSet<String>();
        primOpMul.add("multiplicacion");
        primOpMul.add("division");
        primeros.put("opMul", primOpMul);

        Set<String> primOpUn = new HashSet<String>();
        primOpUn.add("suma");
        primOpUn.add("resta");
        primOpUn.add("not");
        primeros.put("opUn", primOpUn);

        Set<String> primOpAd = new HashSet<String>();
        primOpAd.add("suma");
        primOpAd.add("resta");
        primeros.put("opAd", primOpAd);

        Set<String> primOpComp = new HashSet<String>();
        primOpComp.add("menor");
        primOpComp.add("mayor");
        primOpComp.add("menorigual");
        primOpComp.add("mayorigual");
        primeros.put("opComp", primOpComp);

        Set<String> primOpIg = new HashSet<String>();
        primOpIg.add("igualIgual");
        primOpIg.add("notIgual");
        primeros.put("opIg", primOpIg);

        Set<String> primLiteral = new HashSet<String>();
        primLiteral.add("null");
        primLiteral.add("true");
        primLiteral.add("false");
        primLiteral.add("entero");
        primLiteral.add("caracter");
        primLiteral.add("tString");
        primeros.put("literal", primLiteral);

        Set<String> primEncadenado = new HashSet<String>();
        primEncadenado.add("punto");
        primEncadenado.add("corcheteAbre");
        primeros.put("encadenado", primEncadenado);

        Set<String> primEnca = new HashSet<String>();
        primEnca.addAll(primeros.get("encadenado"));
        primeros.put("enca", primEnca);

        Set<String> primExpresionParentizada = new HashSet<String>();
        primExpresionParentizada.add("parentesisAbre");
        primExpresionParentizada.addAll(primeros.get("enca"));
        primeros.put("expresionParentizada", primExpresionParentizada);

        Set<String> primLlamadaMetodoEstatico = new HashSet<String>();
        primLlamadaMetodoEstatico.add("idClase");
        primeros.put("llamadaMetodoEstatico", primLlamadaMetodoEstatico);

        Set<String> primLlamadaCtor = new HashSet<String>();
        primLlamadaCtor.add("new");
        primeros.put("llamadaCtor", primLlamadaCtor);

        Set<String> primPrimario = new HashSet<String>();
        primPrimario.addAll(primeros.get("expresionParentizada"));
        primPrimario.addAll(primeros.get("accesoThis"));
        primPrimario.addAll(primeros.get("llamadaMetodoEstatico"));
        primPrimario.addAll(primeros.get("llamadaCtor"));
        primPrimario.add("idMetVar");
        primeros.put("primario", primPrimario);

        Set<String> primOperando = new HashSet<String>();
        primOperando.addAll(primeros.get("literal"));
        primOperando.addAll(primeros.get("primario"));
        primeros.put("operando", primOperando);

        Set<String> primExpUn = new HashSet<String>();
        primExpUn.addAll(primeros.get("opUn"));
        primExpUn.addAll(primeros.get("operando"));
        primeros.put("expUn", primExpUn);

        Set<String> primRestoExpMul = new HashSet<String>();
        primRestoExpMul.addAll(primeros.get("opMul"));
        primeros.put("restoExpMul", primRestoExpMul);

        Set<String> primExpMul = new HashSet<String>();
        primExpMul.addAll(primeros.get("expUn"));
        primeros.put("expMul", primExpMul);

        Set<String> primRestoExpAd = new HashSet<String>();
        primRestoExpAd.addAll(primeros.get("opAd"));
        primeros.put("restoExpAd", primRestoExpAd);

        Set<String> primExpAd = new HashSet<String>();
        primExpAd.addAll(primeros.get("expMul"));
        primeros.put("expAd", primExpAd);

        Set<String> primCompFactorizada = new HashSet<String>();
        primCompFactorizada.addAll(primeros.get("opComp"));
        primeros.put("compFactorizada", primCompFactorizada);

        Set<String> primExpComp = new HashSet<String>();
        primExpComp.addAll(primeros.get("expAd"));
        primeros.put("expComp", primExpComp);

        Set<String> primExpIg = new HashSet<String>();
        primExpIg.addAll(primeros.get("expComp"));
        primeros.put("expIg", primExpIg);

        Set<String> primRestoExpIg = new HashSet<String>();
        primRestoExpIg.addAll(primeros.get("opIg"));
        primeros.put("restoExpIg", primRestoExpIg);

        Set<String> primExpAnd = new HashSet<String>();
        primExpAnd.addAll(primeros.get("expIg"));
        primeros.put("expAnd", primExpAnd);

        Set<String> primRestoExpAnd = new HashSet<String>();
        primRestoExpAnd.add("and");
        primeros.put("restoExpAnd", primRestoExpAnd);

        Set<String> primExpOr = new HashSet<String>();
        primExpOr.addAll(primeros.get("expAnd"));
        primeros.put("expOr", primExpOr);

        Set<String> primRestoExpOr = new HashSet<String>();
        primRestoExpOr.add("or");
        primeros.put("restoExpOr", primRestoExpOr);

        Set<String> primExpresion = new HashSet<String>();
        primExpresion.addAll(primeros.get("expOr"));
        primeros.put("expresion", primExpresion);

        Set<String> primExp = new HashSet<String>();
        primExp.addAll(primeros.get("expresion"));
        primeros.put("exp", primExp);

        Set<String> primArgsActuales = new HashSet<String>();
        primArgsActuales.add("parentesisAbre");
        primeros.put("argsActuales", primArgsActuales);

        Set<String> primPrimarioFactorizado = new HashSet<String>();
        primPrimarioFactorizado.addAll(primeros.get("argsActuales"));
        primPrimarioFactorizado.addAll(primeros.get("enca"));
        primeros.put("primarioFactorizado", primPrimarioFactorizado);

        Set<String> primLlamadaMetodo = new HashSet<String>();
        primLlamadaMetodo.add("idMetVar");
        primeros.put("llamadaMetodo", primLlamadaMetodo);

        Set<String> primLlamadaCtorFactorizado = new HashSet<String>();
        primLlamadaCtorFactorizado.add("idClase");
        primLlamadaCtorFactorizado.addAll(primeros.get("tipoPrimitivo"));
        primeros.put("llamadaCtorFactorizado", primLlamadaCtorFactorizado);

        Set<String> primListaExps = new HashSet<String>();
        primListaExps.addAll(primeros.get("expresion"));
        primeros.put("listaExps", primListaExps);

        Set<String> primLE = new HashSet<String>();
        primLE.addAll(primeros.get("listaExps"));
        primeros.put("le", primLE);

        Set<String> primListaExpsFact = new HashSet<String>();
        primListaExpsFact.add("coma");
        primeros.put("listaExpsFact", primListaExpsFact);

        Set<String> primEncadenadoFactorizado = new HashSet<String>();
        primEncadenadoFactorizado.addAll(primeros.get("argsActuales"));
        //primEncadenadoFactorizado.addAll(primeros.get("enca"));
        primeros.put("encadenadoFactorizado", primEncadenadoFactorizado);

        Set<String> primLlamadaMetodoEncadenado = new HashSet<String>();
        primLlamadaMetodoEncadenado.add("idMetVar");
        primeros.put("llamadaMetodoEncadenado", primLlamadaMetodoEncadenado);

        Set<String> primAccesoVarEnca = new HashSet<String>();
        primAccesoVarEnca.add("idMetVar");
        primeros.put("accesoVarEnca", primAccesoVarEnca);

        Set<String> primAccesoArregloEncadenado = new HashSet<String>();
        primAccesoArregloEncadenado.add("[");
        primeros.put("accesoArregloEncadenado", primAccesoArregloEncadenado);
        Set<String> primExpCompFactorizada = new HashSet<String>();
        primExpCompFactorizada.addAll(primeros.get("opComp"));
        primeros.put("expCompFactorizada", primExpCompFactorizada);

        //Agrego los Siguientes.
        Set<String> sigC = new HashSet<String>();
        sigC.add("EOF");
        siguientes.put("c", sigC);

        Set<String> sigH = new HashSet<String>();
        sigH.add("llaveAbre");
        siguientes.put("h", sigH);

        Set<String> sigMiembros = new HashSet<String>();
        sigMiembros.add("llaveCierra");
        siguientes.put("miembros", sigMiembros);

        Set<String> sigMiembro = new HashSet<String>();
        sigMiembro.addAll(primeros.get("miembro"));
        siguientes.put("miembro", sigMiembro);

        Set<String> sigArgsFormales = new HashSet<String>();
        sigArgsFormales.addAll(primeros.get("bloque"));
        siguientes.put("argsFormales", sigArgsFormales);

        Set<String> sigLAF = new HashSet<String>();
        sigLAF.add("parentesisCierra");
        siguientes.put("laf", sigLAF);

        Set<String> sigArgFormal = new HashSet<String>();
        sigArgFormal.addAll(primeros.get("lafs"));
        sigArgFormal.addAll(primeros.get("tipo"));
        siguientes.put("argFormal", sigArgFormal);

        Set<String> sigFormaMetodo = new HashSet<String>();
        sigFormaMetodo.add("void");
        sigFormaMetodo.addAll(primeros.get("tipo"));
        siguientes.put("formaMetodo", sigFormaMetodo);

        Set<String> sigVisibilidad = new HashSet<String>();
        sigVisibilidad.addAll(primeros.get("tipo"));
        siguientes.put("visibilidad", sigVisibilidad);

        Set<String> sigTipoMetodo = new HashSet<String>();
        sigTipoMetodo.add("idMetVar");
        siguientes.put("tipoMetodo", sigTipoMetodo);

        Set<String> sigTipo = new HashSet<String>();
        sigTipo.add("idMetVar");
        sigTipo.addAll(primeros.get("listaDecVars"));
        siguientes.put("tipoMetodo", sigTipoMetodo);

        Set<String> sigBool = new HashSet<String>();
        sigBool.addAll(primeros.get("tipo"));
        siguientes.put("bool", sigBool);

        Set<String> sigLDVFact = new HashSet<String>();
        sigLDVFact.add("puntoYComa");
        siguientes.put("ldvfact", sigLDVFact);

        Set<String> sigS = new HashSet<String>();
        sigS.add("llaveCierra");
        siguientes.put("s", sigS);

        Set<String> sigSentenciaFact = new HashSet<String>();
        sigSentenciaFact.addAll(siguientes.get("s"));
        siguientes.put("sentenciaFact", sigSentenciaFact);

        Set<String> sigExp = new HashSet<String>();
        sigExp.add("puntoYComa");
        siguientes.put("exp", sigExp);

        Set<String> sigRestoExpOr = new HashSet<String>();
        sigRestoExpOr.addAll(primeros.get("restoExpOr"));
        siguientes.put("restoExpOr", sigRestoExpOr);

        Set<String> sigLe = new HashSet<String>();
        sigLe.add("parentesisCierra");
        siguientes.put("le", sigLe);

        Set<String> sigListaExp = new HashSet<String>();
        sigListaExp.add("parentesisCierra");
        siguientes.put("listaExp", sigListaExp);

        Set<String> sigListaExpsFact = new HashSet<String>();
        sigListaExpsFact.addAll(siguientes.get("listaExp"));
        siguientes.put("listaExpsFact", sigListaExpsFact);

    }

    public static void match(String s) throws Exception {
        if (s.equals(t.getName())) {
            t = alex.getToken();
        } else {
            throw new Exception("Error sintactico: Se esperaba " + s + " pero se encontro un " + t.getName() + " en la linea " + t.getLineNumber());
        }
    }

    private static void inicial() throws Exception {
        clase();
        c();

    }

    private static void clase() throws Exception {
        match("class");
        Token aux = t;
        match("idClase");
        String h = h();
        Clase c;
        if (!ts.estaClase(aux.getLexema())) {
            c = new Clase(aux);
            ts.agregarClase(c);
            c.setHereda(h);
            Ctor contructor = crearConstructorPredefinido(aux);
            c.setConstructorPredefinido(true);
            c.setConstructor(contructor);
        } else {
            throw new Exception("Error Semantico: Se declaró la clase " + aux.getLexema() + " ya existente en linea: " + aux.getLineNumber());
        }

        match("llaveAbre");
        miembros();
        match("llaveCierra");
    }

    private static void c() throws Exception {
        if (primeros.get("inicial").contains(t.getName())) {
            inicial();
        } else if (!siguientes.get("c").contains(t.getName())) {
            throw new Exception("Error Sintactico: Se esperaba un EOF");
        }
    }

    private static String h() throws Exception {
        if (primeros.get("herencia").contains(t.getName())) {
            return herencia();
        } else if (!siguientes.get("h").contains(t.getName())) {
            throw new Exception("Error Sintactico: Se esperaba un {");
        }
        return "Object";
    }

    private static void miembros() throws Exception {
        if (primeros.get("miembros").contains(t.getName())) {
            miembro();
            miembros();
        } else if (!siguientes.get("miembros").contains(t.getName())) {
            throw new Exception("Error sintactico: Se esperaba } pero se encontro: " + t.getLexema() + " en la linea " + t.getLineNumber());

        }

    }

    private static String herencia() throws Exception {
        match("extends");
        String aux = t.getLexema();
        match("idClase");
        return aux;
    }

    private static void miembro() throws Exception {

        if (primeros.get("atributo").contains(t.getName())) {
            atributo();
        } else if (primeros.get("ctor").contains(t.getName())) {
            ctor();
        } else if (primeros.get("metodo").contains(t.getName())) {
            metodo();
        } else {
            throw new Exception("Error sintactico: Tipo de metodo invalido");
        }
    }

    private static void atributo() throws Exception {
        String visibilidad = visibilidad();
        Tipo tipo = tipo();
        ArrayList<Token> l = new ArrayList<Token>();
        l = listaDecVars(l);

        for (Token token : l) {
            ts.getClaseActual().agregarVariable(token, visibilidad, tipo);
        }
        match("puntoYComa");
    }

    private static void ctor() throws Exception {
        Token aux = t;
        match("idClase");
        if (!ts.getClaseActual().getNombre().equals(aux.getLexema())) {
            throw new Exception("Error Semantico: El nombre del constructor " + aux.getLexema() + " en " + aux.getLineNumber() + " no coicide con la clase");
        }
        if (ts.getClaseActual().getTieneConst()) {
            throw new Exception("Error Semantico: en la linea: " + aux.getLineNumber() + " La clase " + ts.getClaseActual().getNombre() + " ya tiene constructor");
        } else {
            //elimino el constructor predefinido y seteo el nuevo.
            ts.getClaseActual().setConstructor(null);
            ts.getClaseActual().setConstructorPredefinido(false);
            Ctor c = ts.getClaseActual().agregarConstructor(aux);
            ts.setUnidadActual(c);
        }
        argsFormales();
        Bloque b = bloque();
        Unidad uni = ts.getUnidadActual();
        uni.setCuerpo(b);

        //ts.getUnidadActual().setCuerpo(bloque());
    }

    private static void metodo() throws Exception {
        String fm = formaMetodo();
        TipoBase tipo = tipoMetodo();
        Token aux = t;
        match("idMetVar");
        Metodo m = new Metodo(aux, fm, tipo);
        ts.setUnidadActual(m);
        ts.getClaseActual().agregarMetodo(m);
        m.setDeclaradoEn(ts.getClaseActual());
        argsFormales();
        Bloque b = bloque();
        m.setCuerpo(b);
    }

    private static String visibilidad() throws Exception {

        if (t.getName().equals("public")) {
            match("public");
            return "public";
        } else if (t.getName().equals("private")) {
            match("private");
            return "private";
        } else {
            throw new Exception("Se esperaba public | private");
        }
    }

    private static void argsFormales() throws Exception {
        match("parentesisAbre");
        LAF();
        match("parentesisCierra");
    }

    private static Bloque bloque() throws Exception {
        match("llaveAbre");
        Bloque previo = ts.getBloqueActual();
        Bloque actual = new Bloque();
        ts.setBloqueActual(actual);
        actual.setPadre(previo);
        s();
        match("llaveCierra");
        ts.setBloqueActual(previo);
        return actual;
    }

    private static void LAF() throws Exception {
        if (primeros.get("listaArgsFormales").contains(t.getName())) {
            listaArgsFormales();
        } else if (!siguientes.get("laf").contains(t.getName())) {
            throw new Exception("Error sintactico: se esperaba lista de argumentos formales o: " + siguientes.get("laf").toString() + " en linea " + t.getLineNumber());
        }
    }

    private static void listaArgsFormales() throws Exception {
        argFormal();
        lafs();
    }

    private static void argFormal() throws Exception {
        Tipo tipo = tipo();
        Token aux = t;
        match("idMetVar");
        ts.getUnidadActual().agregarParam(aux, tipo);
    }

    private static void lafs() throws Exception {
        if (t.getName().equals("coma")) {
            match("coma");
            listaArgsFormales();
        }

    }

    private static Tipo tipo() throws Exception {
        Token aux = t;
        if (t.getName().equals("boolean")) {
            match("boolean");
            return bool(aux);
        } else if (t.getName().equals("char")) {
            match("char");
            return ch(aux);
        } else if (t.getName().equals("int")) {
            match("int");
            return i(aux);
        } else if (t.getName().equals("idClase")) {
            match("idClase");
            //  System.out.println("name "+ aux.getName()+ " Lexema: "+aux.getLexema());
            return new TipoClase(aux.getLineNumber(), aux.getColumNumber(), aux.getLexema());
        } else if (t.getName().equals("String")) {
            match("String");
            return new TipoString(aux.getLineNumber(), aux.getColumNumber());
        } else {
            throw new Exception("Error Sintactico: Tipo invalido");
        }
    }

    private static Tipo bool(Token aux) throws Exception {
        if (t.getName().equals("corcheteAbre")) {
            match("corcheteAbre");
            if (t.getName().equals("corcheteCierra")) {
                match("corcheteCierra");
                return new TipoArregloBool(aux);
            } else {
                throw new Exception("Error sintactico: Falta el corchete que cierra");
            }
        }
        return new Bool(aux.getLineNumber(), aux.getColumNumber());
    }

    private static Tipo ch(Token aux) throws Exception {
        if (t.getName().equals("corcheteAbre")) {
            match("corcheteAbre");
            if (t.getName().equals("corcheteCierra")) {
                match("corcheteCierra");
                return new TipoArregloChar(aux);
            } else {
                throw new Exception("Error sintactico: Falta el corchete que cierra");
            }
        }
        return new Char(aux.getLineNumber(), aux.getColumNumber());
    }

    private static Tipo i(Token aux) throws Exception {
        if (t.getName().equals("corcheteAbre")) {
            match("corcheteAbre");
            if (t.getName().equals("corcheteCierra")) {
                match("corcheteCierra");
                return new TipoArregloInt(aux);
            } else {
                throw new Exception("Error sintactico: Falta el corchete que cierra");
            }
        }
        return new Int(aux.getLineNumber(), aux.getColumNumber());
    }

    private static ArrayList<Token> listaDecVars(ArrayList<Token> l) throws Exception {
        l.add(t);
        match("idMetVar");
        return ldvFact(l);
    }

    private static ArrayList<Token> ldvFact(ArrayList<Token> l) throws Exception {
        if (t.getName().equals("coma")) {
            match("coma");
            return listaDecVars(l);
        } else if (!siguientes.get("ldvfact").contains("puntoYComa")) {
            throw new Exception("Error Sintactico: Se esperaba una coma");
        }
        return l;
    }

    private static void s() throws Exception {
        if (primeros.get("s").contains(t.getName())) {
            Sentencia sen = sentencia();
            ts.getBloqueActual().agregarSentencia(sen);
            s();
            // System.out.println(t.getName());
        } else if (!siguientes.get("s").contains(t.getName())) {
            throw new Exception("Error sintactico: Se esperaba una llave que cierre");
        }
    }

    private static Sentencia sentencia() throws Exception {
        String lexema = t.getName();
        if (lexema.equals("puntoYComa")) {
            match(lexema);
            return new SentenciaVacia();
        } else if (lexema.equals("if")) {
            Token aux = t;
            match(lexema);
            match("parentesisAbre");
            NodoExp exp = expresion();
            match("parentesisCierra");
            Sentencia sen = sentencia();
            return sentenciaFact(sen, aux, exp);
        } else if (lexema.equals("while")) {
            Token aux = t;
            match(lexema);
            match("parentesisAbre");
            NodoExp exp = expresion();
            match("parentesisCierra");
            Sentencia sen = sentencia();
            return new While(aux, sen, exp);
        } else if (lexema.equals("return")) {
            Token aux = t;
            match(lexema);
            NodoExp exp = exp();
            match("puntoYComa");
            return new Return(aux, exp);
            //TODO: LOGRO DEL RETURN  
        } else if (primeros.get("asignacion").contains(lexema)) {
            //procesandoAsignacion = true;
            Asignacion a = asignacion();
            match("puntoYComa");
            //procesandoAsignacion = false;
            return a;
        } else if (primeros.get("sentenciaLlamada").contains(lexema)) {
            Sentencia sen = sentenciaDeLlamada();
            match("puntoYComa");
            return sen;
        } else if (primeros.get("tipo").contains(lexema)) {
            Tipo tipo = tipo();
            ArrayList<Token> l = new ArrayList<Token>();
            l = listaDecVars(l);
            match("puntoYComa");
            //Agrego el tipo de las variables
            ArrayList<Variable> variables = new ArrayList<Variable>();

            for (Token tok : l) {
                Variable var = new VarLocal(tok, tipo);
                variables.add(var);
                ts.getBloqueActual().agregarVariable(var);
            }

            return new DecVars(variables, tipo);
        } else if (primeros.get("bloque").contains(lexema)) {
            return bloque();
        } else {
            throw new Exception("Error sintactico: Sentencia invalida en linea: " + t.getLineNumber() + " se encontro: " + t.getLexema());
        }
    }

    private static If sentenciaFact(Sentencia sen, Token aux, NodoExp exp) throws Exception {
        if (primeros.get("sentenciaFact").contains(t.getName())) {
            match("else");
            Sentencia sen2 = sentencia();
            return new Else(aux, exp, sen, sen2);

        }
        /*
        else if (!siguientes.get("sentenciaFact").contains(t.getName())) {
            throw new Exception("Error sintactico: Se esperaba 'else' o '}' pero se encontro: " + t.getLexema() + " en linea: " + t.getLineNumber());

        }
         */
        return new If(aux, exp, sen);
    }

    private static NodoExp exp() throws Exception {
        if (primeros.get("exp").contains(t.getName())) {
            return expresion();
        } else if (!siguientes.get("exp").contains(t.getName())) {
            throw new Exception("Error sintactico: Se esperaba un ;");
        }
        //Deberia ser inalcanzable ese caso
        return null;
    }

    private static NodoExp expresion() throws Exception {
        return expOr();
    }

    private static Asignacion asignacion() throws Exception {
        Token aux = t;
        // System.out.println("Asignacion: "+aux.getName()+" "+aux.getLexema());
        if (primeros.get("accesoVar").contains(t.getName())) {
            ts.setLadoIzquierdo(true);
            NodoAcceso acceso = accesoVar();
            aux = t;
            match("asignacion");
            ts.setLadoIzquierdo(false);
            NodoExp exp = expresion();
            return new Asignacion(aux, exp, acceso);
        }
        if (primeros.get("accesoThis").contains(t.getName())) {
            ts.setLadoIzquierdo(true);
            NodoAcceso acceso = accesoThis();
            aux = t;
            match("asignacion");
            ts.setLadoIzquierdo(false);
            NodoExp exp = expresion();
            return new Asignacion(t, exp, acceso);
        } else {
            //System.out.println("CREANDO EL NODO ASIGNACION ME ENCONTRE CON OTRA COSA");
        }
        return null;

    }

    private static Sentencia sentenciaDeLlamada() throws Exception {
        Token aux = t;
        match("parentesisAbre");
        NodoExp exp = primario();
        match("parentesisCierra");
        return new SentSimple(aux, exp);
    }

    private static NodoExp expOr() throws Exception {
        NodoExp e = expAnd();
        return restoExpOr(e);
    }

    private static NodoExp expAnd() throws Exception {
        NodoExp e = expIg();
        return restoExpAnd(e);

    }

    private static NodoExp restoExpOr(NodoExp e) throws Exception {
        if (primeros.get("restoExpOr").contains(t.getName())) {
            Token aux = t;
            match("or");
            NodoExp e2 = expAnd();
            NodoExp expBinaria = new NodoExpBinaria(aux, e, e2);
            return restoExpOr(expBinaria);
        } else {
            return e;
        }
    }

    private static NodoExp expIg() throws Exception {
        NodoExp e = expComp();
        return restoExpIg(e);
    }

    private static NodoExp restoExpAnd(NodoExp e) throws Exception {
        if (primeros.get("restoExpAnd").contains(t.getName())) {
            Token aux = t;
            match("and");
            NodoExp e2 = expIg();
            NodoExp expBinaria = new NodoExpBinaria(aux, e, e2);
            return restoExpAnd(expBinaria);
        } else {
            return e;
        }
    }

    private static NodoExp expComp() throws Exception {
        NodoExp e = expAd();
        return expCompFactorizada(e);
    }

    private static NodoExp restoExpIg(NodoExp e) throws Exception {
        if (primeros.get("restoExpIg").contains(t.getName())) {
            Token aux = t;
            opIg();
            NodoExp e2 = expComp();
            NodoExp expBinaria = new NodoExpBinaria(aux, e, e2);
            return restoExpIg(expBinaria);
        } else {
            return e;
        }
    }

    private static NodoExp expCompFactorizada(NodoExp e) throws Exception {
        if (primeros.get("expCompFactorizada").contains(t.getName())) {
            Token aux = t;
            opComp();
            NodoExp e2 = expAd();
            return new NodoExpBinaria(aux, e, e2);
        } else {
            return e;
        }
    }

    private static void opIg() throws Exception {
        if (t.getName().equals("igualIgual")) {
            match("igualIgual");
        } else if (t.getName().equals("notIgual")) {
            match("notIgual");
        } else {
            throw new Exception("Error sintactico:  Operador invalido en linea: " + t.getLineNumber() + " se encontro :" + t.getLexema());
        }
    }

    private static void opComp() throws Exception {
        if (t.getName().equals("menor")) {
            match("menor");
        } else if (t.getName().equals("mayor")) {
            match("mayor");
        } else if (t.getName().equals("menorigual")) {
            match("menorigual");
        } else if (t.getName().equals("mayorigual")) {
            match("mayorigual");
        } else {
            throw new Exception("Error sintactico: Operador invalido, se encontro " + t.getLexema() + " en linea: " + t.getLineNumber());
        }

    }

    private static NodoExp expAd() throws Exception {
        NodoExp e = expMul();
        return restoExpAd(e);
    }

    private static NodoExp expMul() throws Exception {
        NodoExp e = expUn();
        return restoExpMul(e);
    }

    private static NodoExp restoExpAd(NodoExp e) throws Exception {
        if (primeros.get("restoExpAd").contains(t.getName())) {
            Token aux = t;
            //System.out.println("SUMA: "+aux.getName()+" "+aux.getLexema());
            opAd();
            NodoExp e2 = expMul();
            NodoExp expBinaria = new NodoExpBinaria(aux, e, e2);
            return restoExpAd(expBinaria);
        } else {
            return e;
        }
    }

    private static NodoExp expUn() throws Exception {
        if (primeros.get("opUn").contains(t.getName())) {
            Token aux = t;
            opUn();
            NodoExp e = expUn();
            return new NodoExpUnaria(aux, e);
        } else if (primeros.get("operando").contains(t.getName())) {
            return operando();
        } else {
            throw new Exception("Error sintactico: Expresion invalida en linea: " + t.getLineNumber() + " se encontro: " + t.getLexema());
        }
    }

    private static NodoExp restoExpMul(NodoExp e) throws Exception {
        if (primeros.get("restoExpMul").contains(t.getName())) {
            Token aux = t;
            opMul();
            NodoExp e2 = expUn();
            NodoExp expBinaria = new NodoExpBinaria(aux, e, e2);
            return restoExpMul(expBinaria);
        } else {
            return e;
        }
    }

    private static void opAd() throws Exception {
        if (t.getName().equals("suma")) {
            match("suma");
        } else if (t.getName().equals("resta")) {
            match("resta");
        } else {
            throw new Exception("Error sintactico: Operador invalido en linea: " + t.getLineNumber() + " se encontro: " + t.getLexema());
        }

    }

    private static void opUn() throws Exception {
        if (t.getName().equals("suma")) {
            match("suma");
        } else if (t.getName().equals("resta")) {
            match("resta");
        } else if (t.getName().equals("not")) {
            match("not");
        } else {
            throw new Exception("Error sintactico: Tipo de metodo invalido en linea: " + t.getLineNumber() + " se encontro " + t.getLexema());
        }
    }

    private static NodoExp operando() throws Exception {
        if (primeros.get("literal").contains(t.getName())) {
            return literal();
        } else if (primeros.get("primario").contains(t.getName())) {
            return primario();
        } else if (t.getName().equals("this")) {
            Token aux = t;
            match("this");
            return new NodoThis(aux);

        } else {
            throw new Exception("Error sintactico: Operador invalido en linea: " + t.getLineNumber() + " se encontro: " + t.getLexema());
        }
    }

    private static void opMul() throws Exception {
        if (t.getName().equals("multiplicacion")) {
            match("multiplicacion");
        } else if (t.getName().equals("division")) {
            match("division");
        } else if (t.getName().equals("modulo")) {
            match("modulo");
        } else {
            throw new Exception("Error sintactico: Operador invalido en linea: " + t.getLineNumber() + " se encontro: " + t.getLexema());
        }
    }

    private static NodoExp literal() throws Exception {
        Token aux = t;
        if (t.getName().equals("null")) {
            match("null");
        } else if (t.getName().equals("true")) {
            match("true");
        } else if (t.getName().equals("false")) {
            match("false");
        } else if (t.getName().equals("entero")) {
            match("entero");
        } else if (t.getName().equals("caracter")) {
            match("caracter");
        } else if (t.getName().equals("tString")) {
            match("tString");
        } else {
            throw new Exception("Error sintactico: literal invalido: " + t.getLexema() + " en linea: " + t.getLineNumber());
        }
        return new NodoLiteral(aux);
    }

    private static NodoExp primario() throws Exception {
        if (primeros.get("expresionParentizada").contains(t.getName())) {
            return expresionParentizada();
        } else if (primeros.get("accesoThis").contains(t.getName())) {
            return accesoThis();
        } else if (primeros.get("llamadaMetodoEstatico").contains(t.getName())) {
            return llamadaMetodoEstatico();
        } else if (primeros.get("llamadaCtor").contains(t.getName())) {
            return llamadaCtor();
        } else if (t.getName().equals("idMetVar")) {
            Token aux = t;
            match("idMetVar");
            return primarioFactorizado(aux);
        } else {
            throw new Exception("Error Sintactico: Primario");
        }
    }

    private static NodoAcceso accesoVar() throws Exception {
        Token aux = t;
        match("idMetVar");
        NodoVar var = new NodoVar(aux);
        if (ts.isLadoIzquierdo()) {
            var.setLadoIzq(true);
        }
        Encadenado enca = enca();
        var.setEnca(enca);
        return var;
    }

    private static NodoAcceso accesoThis() throws Exception {
        Token aux = t;
        match("this");
        NodoThis nodoThis = new NodoThis(aux);
        if (ts.isLadoIzquierdo()) {
            nodoThis.setLadoIzq(true);
        }
        Encadenado enca = enca();
        if (enca != null) {
            nodoThis.setEnca(enca);
        }
        return nodoThis;
    }

    private static Encadenado enca() throws Exception {
        return encadenado();
    }

    private static Encadenado encadenado() throws Exception {

        if (t.getName().equals("punto")) {
            match("punto");
            Token id = t;
            match("idMetVar");
            return encadenadoFactorizado(id);
        } else if (t.getName().equals("corcheteAbre")) {
            Token id = t;
            match("corcheteAbre");
            NodoExp e = expresion();
            match("corcheteCierra");
            Encadenado enca = enca();
            return new ArregloEncadenado(id, enca, e);
        }
        return null;
    }

    private static Encadenado encadenadoFactorizado(Token id) throws Exception {
        if (primeros.get("encadenadoFactorizado").contains(t.getName())) {
            Argumentos a = argsActuales();
            Encadenado enca = enca();
            LlamadaEncadenada llEnca = new LlamadaEncadenada(id, a, enca);
            return llEnca;
        } else if (primeros.get("enca").contains(t.getName())) {
            Encadenado Enca = enca();
            IdEncadenado idEnca = new IdEncadenado(id, Enca);
            return idEnca;
        }
        return new IdEncadenado(id, null);
    }

    private static Argumentos argsActuales() throws Exception {
        match("parentesisAbre");
        Argumentos a = le();
        match("parentesisCierra");
        return a;
    }

    private static Argumentos le() throws Exception {
        if (primeros.get("listaExps").contains(t.getName())) {
            return listaExps();
        } else if (!siguientes.get("le").contains(t.getName())) {
            throw new Exception("Error sintactico: se esperaba una lista de expresiones o: " + siguientes.get("r10").toString() + " en linea: " + t.getLineNumber());
        }
        return new Argumentos(null, null);
    }

    private static Argumentos listaExps() throws Exception {
        NodoExp exp = expresion();
        return listaExpsFact(exp);

    }

    private static Argumentos listaExpsFact(NodoExp exp) throws Exception {
        if (t.getName().equals("coma")) {
            match("coma");
            Argumentos ar = listaExps();
            return new Argumentos(exp, ar);
        } else if (!siguientes.get("listaExpsFact").contains(t.getName())) {
            throw new Exception("Error sintactico: se esperaba una coma o: " + siguientes.get("r14").toString() + " en linea " + t.getLineNumber());
        }
        return new Argumentos(exp, null);
    }

    private static NodoExp expresionParentizada() throws Exception {
        match("parentesisAbre");
        Token aux = t;
        NodoExp exp = expresion();
        match("parentesisCierra");
        Encadenado enca = enca();
        return new NodoExpParentizada(aux, exp, enca);
    }

    private static NodoLlamadaConClase llamadaMetodoEstatico() throws Exception {
        Token idClase = t;
        match("idClase");
        match("punto");
        Token id = t;
        match("idMetVar");
        Argumentos a = llamadaMetodo();
        Encadenado enca = enca();
        NodoLlamadaConClase llamada = new NodoLlamadaConClase(idClase, id, a);
        if (enca != null) {
            llamada.setEnca(enca);
        }
        return llamada;
    }

    private static Argumentos llamadaMetodo() throws Exception {
        //match("idMetVar");
        return argsActuales();
        //Encadenado enca = enca();
    }

    private static TipoBase tipoMetodo() throws Exception {
        if (t.getName().equals("void")) {
            match("void");
            return new Void2();
        } else if (primeros.get("tipo").contains(t.getName())) {
            return tipo();
        } else {
            throw new Exception("Error sintactico: Tipo de metodo invalido en linea: " + t.getLineNumber() + " se encontro: " + t.getLexema());
        }

    }

    private static String formaMetodo() throws Exception {
        if (t.getName().equals("static")) {
            match("static");
            return "static";
        } else if (t.getName().equals("dynamic")) {
            match("dynamic");
            return "dynamic";
        } else {
            throw new Exception("Error sintactico: Se esperaba un static o dynamic " + t.getName());
        }

    }

    private static NodoPrimario llamadaCtor() throws Exception {
        match("new");
        return llamadaCtorFactorizado();
    }

    private static NodoPrimario primarioFactorizado(Token id) throws Exception {
        if (primeros.get("argsActuales").contains(t.getName())) {
            Argumentos args = argsActuales();
            Encadenado enca = enca();
            return new NodoLlamadaDirecta(id, args, enca);
        }
        Encadenado enca = enca();
        //System.out.println("Nodo Primario "+id.getLexema()+id.getName());
        return new NodoLlamadaVar(id, enca);
    }

    private static NodoPrimario llamadaCtorFactorizado() throws Exception {
        if (t.getName().equals("idClase")) {
            Token id = t;
            match("idClase");
            Argumentos a = argsActuales();
            Encadenado enca = enca();
            NodoCtorComun ctor = new NodoCtorComun(id, a);
            if (enca != null) {
                ctor.setEnca(enca);
            }
            return ctor;
        } else if (primeros.get("tipoPrimitivo").contains(t.getName())) {
            Token id = t;
            Tipo tp = tipoPrimitivo();
            match("corcheteAbre");
            NodoExp exp = expresion();
            match("corcheteCierra");
            Encadenado enca = enca();
            NodoCtorArreglo constructorArreglo = new NodoCtorArreglo(id, exp, tp.getNombre());
            constructorArreglo.setEnca(enca);

            return constructorArreglo;
        } else {
            throw new Exception("Error Sintactico: llamadaCtorFactorizado");
        }
    }

    private static Tipo tipoPrimitivo() throws Exception {
        Token aux = t;
        if (t.getName().equals("char")) {
            match("char");
            return new Char(aux.getColumNumber(), aux.getLineNumber());
        } else if (t.getName().equals("boolean")) {
            match("boolean");
            return new Bool(aux.getLineNumber(), aux.getColumNumber());
        } else if (t.getName().equals("String")) {
            match("String");
            return new Cadena(aux.getLineNumber(), aux.getColumNumber());
        } else if (t.getName().equals("int")) {
            match("int");
            return new Int(aux.getLineNumber(), aux.getColumNumber());
        } else {
            throw new Exception("Error Sintactico: Error de tipo");
        }
    }

    public static TablaSimbolos getTs() {
        return ts;
    }

    private static Ctor crearConstructorPredefinido(Token aux) {
        Ctor constructor = new Ctor(aux);
        constructor.setDeclaradoEn(getTs().getClase(aux.getLexema()));
        constructor.setPredefinido(true);
        constructor.setColumna(0);
        constructor.setLinea(0);
        return constructor;
    }

}
