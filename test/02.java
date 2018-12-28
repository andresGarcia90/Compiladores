// Se prueban todas operaciones con tipos Arreglos

class Uno {

	static void main() {
		int a;
		a = 3;
		(m1(a));

	}

	static void m1(int a){
		int [] arregloInt;
		char [] arregloChar;
		boolean [] arregloBool;

		arregloInt = new int[a];
		arregloChar = new char[a];
		arregloBool = new boolean[a];

		arregloInt[0] = 0;
		arregloInt[1] = 10;
		arregloInt[2] = 20;

		arregloChar[0] = 'a';
		arregloChar[1] = 'b';
		arregloChar[2] = 'c';


		arregloBool[0] = true;
		arregloBool[1] = false;
		arregloBool[2] = true;

		int i;
		i = 0;

		while(i < a){
			(System.printIln(arregloInt[i]));
			(System.printCln(arregloChar[i]));
			(System.printBln(arregloBool[i]));
			i = i+1;
		}
		
	}

}

