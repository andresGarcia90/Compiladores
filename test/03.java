class Base{
	static void main(){
		Dos dos;
		dos = new Dos();
		(System.printIln(dos.mostrarX()));
		(System.printIln(dos.mostrarY()));
		(System.printIln(dos.sumarxy()));
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

class Dos{
	public Uno uno;
	Dos(){
		uno = new Uno(1,2);
	}

	dynamic int mostrarX(){
		return uno.x;
	}

	dynamic int mostrarY(){
		return uno.y;
	}
	
	dynamic int sumarxy(){
		return uno.x+uno.y;
	}
}