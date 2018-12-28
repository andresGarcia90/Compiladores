// Llamada a un metodos estaticos

class Base {

	static  void main() {
		(A.m1());
		(A.m2(2));
	}

}

class A {

	static  void m1() {
		(System.printI(1));
	}

	static  void m2(int num) {
		(System.printI(num));
	}

	

}