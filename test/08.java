// Referencia a this

class Base {

	static  void main() {
		A a;
		a = new A();
		(System.printI(a.a));
	}

}

class A {

	public int a;

	A() {
		a = (this).m1();
	}

	dynamic int m1() {
		return 1;
	}


}