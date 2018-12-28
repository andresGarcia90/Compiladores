// Llamada a un metodo sin retorno

class Base {

	static  void main() {
		A a;
		a = new A();
		(a.metodo());
	}

}

class A {

	public int a;
	public int b;

	A(){
		a = 10;
		b = 100;
	}

	dynamic void metodo() {
		(System.printIln(a));
		(System.printIln(b));
	}

}