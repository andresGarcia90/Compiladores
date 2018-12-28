class Base{
	static void main(){
		Dos dos;
		dos = new Dos(10,20,30);
		(dos.mostrarVariables());
	}
}

class Uno{
	public int x;
	public int y;

	Uno(int a, int b){
		x = a;
		y = b;
	}
}

class Dos extends Uno{
	private int z;

	Dos(int a, int b, int c){
		x = a;
		y = b;
		z = c;
	}

	dynamic void mostrarVariables(){
		(System.printIln(x));
		(System.printIln(y));
		(System.printIln(z));
	}
}