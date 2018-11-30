package AnalizadorLexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import Excepciones.*;

import Token.Token;

public class ALex {
    
    String filename;
    int estado;
    BufferedReader s;
    int numLinea;
    int numColumna;
    int ci;
    char c;
    Map<String, Token> palabrasClave;

    public ALex(String fn) {
        filename = fn;
        estado = 0;
        numLinea = 1;
        numColumna = 1;
        inicializarMapeo();

        try {
            s = new BufferedReader(new FileReader(filename));
            ci = s.read();
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    public void inicializarMapeo() {
        palabrasClave = new HashMap<String, Token>();
        palabrasClave.put("class", new Token("class", "class", 0, 0));
        palabrasClave.put("extends", new Token("extends", "extends", 0, 0));
        palabrasClave.put("static", new Token("static", "static", 0, 0));
        palabrasClave.put("dynamic", new Token("dynamic", "dynamic", 0, 0));
        palabrasClave.put("String", new Token("String", "String", 0, 0));
        palabrasClave.put("boolean", new Token("boolean", "boolean", 0, 0));
        palabrasClave.put("char", new Token("char", "char", 0, 0));
        palabrasClave.put("int", new Token("int", "int", 0, 0));
        palabrasClave.put("public", new Token("public", "public", 0, 0));
        palabrasClave.put("private", new Token("private", "private", 0, 0));
        palabrasClave.put("void", new Token("void", "void", 0, 0));
        palabrasClave.put("null", new Token("null", "null", 0, 0));
        palabrasClave.put("if", new Token("if", "if", 0, 0));
        palabrasClave.put("else", new Token("else", "else", 0, 0));
        palabrasClave.put("while", new Token("while", "while", 0, 0));
        palabrasClave.put("return", new Token("return", "return", 0, 0));
        palabrasClave.put("this", new Token("this", "this", 0, 0));
        palabrasClave.put("new", new Token("new", "new", 0, 0));
        palabrasClave.put("true", new Token("true", "true", 0, 0));
        palabrasClave.put("false", new Token("false", "false", 0, 0));

    }

    /*
	 * Metodo que se encarga de recorrer el archivo y devolver el siguiente token.
     */
    public Token getToken() throws Exception {
        int numColumnaAux = 1;
        String lexema = "";
        estado = 0;
        c = (char) ci;
        if (ci == -1) {
            return new Token("EOF", "Fin de archivo", numLinea, numColumna);
        }
        try {
            while (true) {
                c = (char) ci;
                while (c == ' ' || c == '\t' || c == '\r') {
                    ci = s.read();
                    c = (char) ci;
                    numColumna++;
                }
                switch (estado) {
                    case 0: {
                        numColumnaAux = numColumna;
                        if (c == '\n') {
                            ci = s.read();
                            numLinea++;
                            numColumna = 1;
                            continue;
                        } else if (ci == -1) {
                            return new Token("EOF", "Fin de archivo", numLinea, numColumnaAux);
                        } else if (Character.isUpperCase(c)) { //idClase
                            estado = 1;
                            break;
                        } else if (Character.isLowerCase(c)) { //idMetVar
                            estado = 2;
                            break;
                        } else if (Character.isDigit(c)) { //idEntero
                            estado = 3;
                            break;
                        } else if (c == '"') { //idString
                            estado = 4;
                            break;
                        } else if (c == '\'') { //Caracter
                            estado = 13;
                            break;
                        } else if (c == '%') {//modulo
                            ci = s.read();
                            numColumna++;
                           // System.out.println(numColumna);
                            return new Token("modulo", "%", numLinea, numColumnaAux);
                        } else if (c == ')') { //parentesisCierra
                            lexema += c;
                            ci = s.read();
                            numColumna++;
                            return new Token("parentesisCierra", lexema, numLinea, numColumnaAux);
                        } else if (c == '(') { //parentesisAbre
                            lexema += c;
                            ci = s.read();
                            numColumna++;

                            return new Token("parentesisAbre", lexema, numLinea, numColumnaAux);
                        } else if (c == '{') { //llaveAbre
                            lexema += c;
                            ci = s.read();
                            return new Token("llaveAbre", lexema, numLinea, numColumnaAux);
                        } else if (c == '}') { //llaveCierra
                            lexema += c;
                            ci = s.read();
                            numColumna++;
                            return new Token("llaveCierra", lexema, numLinea, numColumnaAux);
                        } else if (c == ';') { //puntoYcoma
                            lexema += c;
                            ci = s.read();
                            numColumna++;
                            return new Token("puntoYComa", lexema, numLinea, numColumnaAux);
                        } else if (c == '[') { //corcheteAbre
                            lexema += c;
                            ci = s.read();
                            numColumna++;
                            return new Token("corcheteAbre", lexema, numLinea, numColumnaAux);
                        } else if (c == ']') { //corcheteCierra
                            lexema += c;
                            ci = s.read();
                            numColumna++;
                            return new Token("corcheteCierra", lexema, numLinea, numColumnaAux);
                        } else if (c == ',') { //coma
                            lexema += c;
                            ci = s.read();
                            numColumna++;
                            return new Token("coma", lexema, numLinea, numColumnaAux);
                        } else if (c == '.') { //punto
                            lexema += c;
                            ci = s.read();
                            numColumna++;
                            return new Token("punto", lexema, numLinea, numColumnaAux);
                        } else if (c == '+') { //suma
                            lexema += c;
                            ci = s.read();
                            numColumna++;
                            return new Token("suma", lexema, numLinea, numColumnaAux);
                        } else if (c == '-') { //resta
                            lexema += c;
                            ci = s.read();
                            numColumna++;
                            return new Token("resta", lexema, numLinea, numColumnaAux);
                        } else if (c == '*') { //mult
                            lexema += c;
                            ci = s.read();
                            numColumna++;
                            return new Token("multiplicacion", lexema, numLinea, numColumnaAux);
                        } else if (c == '/') { //div|Comentario
                            estado = 5;
                            break;
                        } else if (c == '>') { //mayor
                            estado = 6;
                            break;
                        } else if (c == '<') { //menor
                            estado = 7;
                            break;
                        } else if (c == '!') { //not
                            estado = 8;
                            break;
                        } else if (c == '=') { //igual
                            estado = 9;
                            break;
                        } else if (c == '&') { //and
                            ci = s.read();
                            c = (char) ci;
                            numColumna++;

                            if (c == '&') {
                                ci = s.read();
                                numColumna++;
                                return new Token("and", "&&", numLinea, numColumnaAux);
                            } else {
                                throw new ExAndMalFormado("Error lexico: AND mal formado en linea " + numLinea + " Columna: " + numColumna);
                            }
                        } else if (c == '|') { //or
                            ci = s.read();
                            c = (char) ci;
                            numColumna++;

                            if (c == '|') {
                                ci = s.read();
                                numColumna++;

                                return new Token("or", "||", numLinea, numColumnaAux);
                            } else {
                                throw new ExOrMalFormado("Error lexico: OR mal formado en linea " + numLinea + " Columna: " + numColumna);
                            }
                        } else {
                            throw new ExCaracterInvalido("Error lexico: caracter invalido en linea " + numLinea + " Columna: " + numColumna);
                        }
                    }

                    case 1: {
                        numColumnaAux = numColumna;
                        lexema = "";
                        while (true) {
                            lexema = lexema + c;
                            ci = s.read();
                            c = (char) ci;
                            numColumna++;
                            if (!Character.isUpperCase(c) && !Character.isDigit(c) && !Character.isLowerCase(c) && c != '_') {
                                if (c != '.' && c != '=' && c != '{' && c != '(' && c != ')' && c != '{' && c != '}' && c != '[' && c != ']' && c != ' ' && c != ';' && c != ',' && c != '!' && c != '%' && c != ';' && c != '\t' && c != '+' && c != '-' && c != '/' && c != '*' && c != '<' && c != '>' && c != '&' && c != '|' &&  c != '"' && ci != 10 && ci != -1 && ci != 13 && c != '\'') {
                                    throw new ExIdMalFormado("Error lexico: identificador mal formado en linea: " + numLinea + " Columna: " + numColumna);
                                }
                                if (palabraClave(lexema)) {
                                    Token aux = palabrasClave.get(lexema);
                                    aux.setLineNumber(numLinea);
                                    aux.setColumNumber(numColumnaAux);
                                    return aux;
                                } else {
                                    return new Token("idClase", lexema, numLinea, numColumnaAux);
                                }
                            }
                        }
                    }

                    case 2: {
                        numColumnaAux = numColumna;
                        lexema = "";
                        while (true) {
                            lexema = lexema + c;
                            ci = s.read();
                            c = (char) ci;
                            numColumna++;

                            if (!Character.isUpperCase(c) && !Character.isDigit(c) && !Character.isLowerCase(c) && c != '_') {
                                if (c != '.' && c != '=' && c != '{' && c != '(' && c != ')' && c != '{' && c != '}' && c != '[' && c != ']' && c != ' ' && c != ';' && c != ',' && c != '!' && c != '%' && c != ';' && c != '\t' && c != '+' && c != '-' && c != '/' && c != '*' && c != '<' && c != '>' && c != '&' && c != '|' && c != '"' && ci != 10 && ci != 13 && ci != -1 && c != '\'') {
                                    throw new ExIdMalFormado("Error lexico: identificador mal formado en linea: " + numLinea + " Columna: " + numColumna);
                                }
                                if (palabraClave(lexema)) {
                                    Token aux = palabrasClave.get(lexema);
                                    aux.setLineNumber(numLinea);
                                    aux.setColumNumber(numColumnaAux);
                                    return aux;
                                } else {
                                    return new Token("idMetVar", lexema, numLinea, numColumnaAux);
                                }
                            }
                        }
                    }

                    case 3: {
                        numColumnaAux = numColumna;
                        lexema = "";
                        lexema = lexema + c;
                        while (true) {
                            ci = s.read();
                            c = (char) ci;
                            numColumna++;
                            if (!Character.isDigit(c)) {
                                if (Character.isAlphabetic(c)) {
                                    throw new ExNumeroMalFormado("Error lexico: Numero mal formado en linea " + numLinea + " Columna: " + numColumna);
                                } else {
                                    int i = Integer.parseInt(lexema);
                                    lexema = "" + i;
                                    return new Token("entero", lexema, numLinea, numColumnaAux);
                                }
                            }
                            lexema = lexema + c;
                        }

                    }

                    case 4: {
                        numColumnaAux = numColumna;
                        lexema = c + "";
                        while (true) {
                            ci = s.read();
                            c = (char) ci;
                            numColumna++;
                            lexema += c;
                            if (c == '"') {
                                ci = s.read();
                                c = (char) ci;
                                numColumna++;
                                return new Token("tString", lexema, numLinea, numColumnaAux);
                            } else {
                                if (ci == -1 || c == '\n') {
                                    throw new ExStringMalFormado("Error lexico: String mal formado en linea " + numLinea + " Columna: " + numColumna);
                                }
                            }
                        }
                    }

                    case 5: {
                        numColumnaAux = numColumna;
                        lexema = "";
                        ci = s.read();
                        c = (char) ci;

                        switch (c) {
                            case '*': {
                                estado = 10;
                                ci = s.read();
                                continue;
                            }
                            case '/': {
                                estado = 11;
                                continue;
                            }
                            default: {
                                return new Token("division", "/", numLinea, numColumnaAux);
                            }
                        }
                    }

                    case 10: {
                        numColumnaAux = numColumna;
                        while (c != '*') {
                            ci = s.read();
                            c = (char) ci;
                            numColumna++;
                            if (ci == -1) {
                                throw new ExComentarioMalFormado("Error lexico: Comentario mal formado en linea " + numLinea + " Columna: " + numColumnaAux);
                            }
                            if (c == '\n') {
                                numLinea++;
                            } else if (c == '*') {
                                while (c == '*') {
                                    ci = s.read();
                                    c = (char) ci;
                                    numColumna++;
                                    if (ci == -1) {
                                        throw new ExComentarioMalFormado("Error lexico: Comentario mal formado en linea " + numLinea + " Columna: " + numColumnaAux);
                                    }
                                }
                                if (c == '\n') {
                                    numLinea++;
                                } else if (c == '/') {
                                    estado = 0;
                                    lexema = "";
                                    ci = s.read();
                                    break;
                                }
                            }
                        }

                    }

                    case 11: {
                        numColumnaAux = numColumna;
                        while (c != '\n' && ci != -1) {
                            ci = s.read();
                            c = (char) ci;
                            numColumna++;
                        }
                        //numLinea++;
                        estado = 0;
                        lexema = "";
                        break;
                    }

                    case 6: {
                        numColumnaAux = numColumna;
                        lexema += c;
                        ci = s.read();
                        c = (char) ci;
                        numColumna++;
                        if (c == '=') {
                            lexema += c;
                            ci = s.read();
                            c = (char) ci;
                            numColumna++;
                            return new Token("mayorigual", lexema, numLinea, numColumnaAux);
                        } else {
                            return new Token("mayor", lexema, numLinea, numColumnaAux);
                        }

                    }

                    case 7: {
                        numColumnaAux = numColumna;
                        lexema += c;
                        ci = s.read();
                        c = (char) ci;

                        if (c == '=') {
                            lexema += c;
                            ci = s.read();
                            c = (char) ci;
                            return new Token("menorigual", lexema, numLinea, numColumnaAux);
                        } else {
                            return new Token("menor", lexema, numLinea, numColumnaAux);
                        }
                    }

                    case 8: {
                        numColumnaAux = numColumna;

                        lexema += c;
                        ci = s.read();
                        c = (char) ci;

                        if (c == '=') {
                            ci = s.read();
                            lexema += c;
                            c = (char) ci;
                            return new Token("notIgual", lexema, numLinea, numColumnaAux);
                        } else {
                            return new Token("not", lexema, numLinea, numColumnaAux);
                        }
                    }

                    case 9: {
                        numColumnaAux = numColumna;
                        lexema += c;
                        ci = s.read();
                        c = (char) ci;

                        if (c == '=') {
                            lexema += c;
                            ci = s.read();
                            c = (char) ci;
                            return new Token("igualIgual", lexema, numLinea, numColumnaAux);
                        } else {
                            return new Token("asignacion", lexema, numLinea, numColumnaAux);
                        }
                    }

                    case 13: {
                        numColumnaAux = numColumna;
                        ci = s.read();
                        c = (char) ci;
                        numColumna++;
                        if (c == '\'' || ci == 10 || c == ' ') {
                            throw new ExCaracterMalFormado("Error lexico: Caracter mal formado en linea " + numLinea + " Columna: " + numColumna);
                        } else if (c == '\\') {
                            ci = s.read();
                            c = (char) ci;
                            if (c == 't') {
                                ci = s.read();
                                c = (char) ci;
                                numColumna++;
                                if (c != '\'') {
                                    throw new ExCaracterMalFormado("Error lexico: Caracter mal formado en linea " + numLinea + " Columna: " + numColumna);
                                }

                                ci = s.read();
                                c = (char) ci;
                                numColumna++;
                                lexema = "\'\\t\'";
                            } else if (c == 'n') {
                                ci = s.read();
                                c = (char) ci;
                                numColumna++;
                                if (c != '\'') {
                                    throw new ExCaracterMalFormado("Error lexico: Caracter mal formado en linea " + numLinea + " Columna: " + numColumna);
                                }

                                ci = s.read();
                                c = (char) ci;
                                numColumna++;
                                lexema = "\'\\n\'";
                            } else {
                                //lexema = "\'" + "\\" + c + "\'";
                                lexema = "\'" + c + "\'";
                                ci = s.read();
                                c = (char) ci;
                                numColumna++;
                                if (c != '\'') {
                                    throw new ExCaracterMalFormado("Error lexico: Caracter mal formado en linea " + numLinea + " Columna: " + numColumna);
                                }

                                ci = s.read();
                                c = (char) ci;
                                numColumna++;
                            }
                        } else {
                            lexema = "\'" + c + "\'";
                            ci = s.read();
                            c = (char) ci;
                            numColumna++;
                            if (c != '\'') {
                                throw new ExCaracterMalFormado("Error lexico: Caracter mal formado en linea " + numLinea + " Columna: " + numColumna);
                            }

                            ci = s.read();
                            c = (char) ci;
                            numColumna++;
                        }

                        return new Token("caracter", lexema, numLinea, numColumnaAux);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Token("ERROR", "ERROR", numLinea, numColumna);
    }

    /*
	 * Unidad encargada de verificar si un determinado String es una palabra clave del lenguaje.
     */
    private boolean palabraClave(String lexema) {
        if (palabrasClave.get(lexema) != null) {
            return true;
        }
        return false;
    }
}
