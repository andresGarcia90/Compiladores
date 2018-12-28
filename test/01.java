// Todas las operaciones para tipos primitivos
class Uno {

	static void main() {
		int a,b;
		a = 10;
		b = 20;
		(m1(a,b));

	}

	static void m1(int a, int b){
		int a1, b1, i;
		a1 = a;
		b1 = b;

		(System.printI(a1));
		(System.println());

		(System.printI(b1));
		(System.println());


		(System.printI(-a1));
		(System.println());


		(System.printI(-b1));
		(System.println());


		i = a1 + b1;
		(System.printI(i));
		(System.println());


		i = a1 - b1;
		(System.printI(i));
		(System.println());


		i = a1 * b1;
		(System.printI(i));
		(System.println());


		i = a1 / b1;
		(System.printI(i));
		(System.println());

		(System.printB(a1>b1));
		(System.println());

		(System.printB(a1<b1));
		(System.println());

		String texto;
		texto = "Esto es un texto";
		(System.printS(texto));
		(System.println());

		
		char c;
		c='c';

		(System.printC(c));
		(System.println());

		boolean t,f,prueba;
		t = true;
		f = false;

		(System.printB(t && t));
		(System.println());


		(System.printB(t || t));
		(System.println());

		(System.printB(f && f));
		(System.println());


		(System.printB(f || f));
		(System.println());

		(System.printB(t && f));
		(System.println());


		(System.printB(t || f));
		(System.println());



		(System.printB(!t));
		(System.println());

		(System.printB(!f));
		(System.println());

	}

}

