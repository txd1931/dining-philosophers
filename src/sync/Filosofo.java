package sync;

import java.util.Random;

public class Filosofo implements Runnable{
	
	int id;
	static Random rand = new Random();
	//static boolean executando; talvez use
	int lGarfo;
	int rGarfo;
	
	static int tempoPensando;
	static int tempoComendo;
	
	public Filosofo(int id) {
		this.id = id;
	}
		
	@Override
	public void run(){
		pensar();
		
			
		esqGarfo();
		dirGarfo();
		comer();
		largar();
	}
	
	public void pensar() {
		tempoPensando = rand.nextInt(10001);
		System.out.println("Filosofo" + id + "está pensando");
		
		try {
			Thread.sleep(tempoPensando);
		}catch(Exception exc){
			System.out.println("Thread" + id + "interrompida");
		}
	}
	
	public void esqGarfo() {
		lGarfo = this.id - 1;
		
		Jantar.garfos[lGarfo] = 0;
		
		System.out.println("Filosofo" + id + "pegou o garfo a sua esquerda");
	}
	
	public void dirGarfo() {
		if(this.id == 5) {
			rGarfo = 0;
		}else {
			rGarfo = this.id;
		}
		
		Jantar.garfos[rGarfo] = 0;
		
		System.out.println("Filosofo" + + id + "pegou o garfo a sua direita");
	}
	
	public void comer() {
		if(Jantar.garfos[lGarfo] == 0 && Jantar.garfos[rGarfo] == 0) {
			tempoComendo = rand.nextInt(10001);
			System.out.println("Filosofo"+ id + "começou a comer");
			
			try {
				Thread.sleep(tempoPensando);
			}catch(Exception exc){
				System.out.println("Thread" + id + "interrompida");
			}
		}
	}
	
	public void largar() {
		Jantar.garfos[lGarfo] = 1;
		Jantar.garfos[rGarfo] = 1;
		
		System.out.println("Filosofo"+ this.id + "largou os garfos");
	}
}
