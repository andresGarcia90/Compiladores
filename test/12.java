// Construccion de un objeto con el constructor predefinido

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
		a = System.read();
		b = 100;
	}

	dynamic void metodo() {
		(System.printIln(a));
		(System.printIln(b));
	}

}