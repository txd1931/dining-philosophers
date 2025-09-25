package dinner_table;

/* 
5 filósofos, 5 garfos, um filósofo precisa de dois garfos para comer
O filósofo alterna entre COMER e PENSAR. 
Quando um filósofo fica faminto, ele tenta pegar seus garfos um de cada vez, não importa a ordem
Se pegar, ele come por um tempo, então para e continua a pensar
*/
public class Main {
	public static int[] forks = new int[5];
	
	public static void main(String[] args) {
		Thread philosopher1 = new Thread(new Philosofers());
		Thread philosopher2 = new Thread(new Philosofers());
		Thread philosopher3 = new Thread(new Philosofers());
		Thread philosopher4 = new Thread(new Philosofers());
		Thread philosopher5 = new Thread(new Philosofers());
		
		philosopher1.start();
		philosopher2.start();
		philosopher3.start();
		philosopher4.start();
		philosopher5.start();
	}
	
}
