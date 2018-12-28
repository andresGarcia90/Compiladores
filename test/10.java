// Construccion de un objeto con el constructor predefinido

class Base {

	static  void main() {
		A a;
		a = new A();
		a.a = 10;
		a.b = 100;
		(a.metodo());
	}

}

class A {

	public int a;
	public int b;

	dynamic void metodo() {
		(System.printIln(a));
		(System.printIln(b));
	}

}