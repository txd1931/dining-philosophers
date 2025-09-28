package sync;


import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Filosofo implements Runnable{
	
	public static int[] garfos = {1, 1, 1, 1, 1};
	
	int id;
	public static AtomicBoolean executando = new AtomicBoolean(false);
	int lGarfo;
	int rGarfo;
	static int garfosAtuais = 0;
	
	static int tempoPensando;
	static int tempoComendo;
	
	public Filosofo(int id) {
		this.id = id;
	}
		
	@Override
	public void run(){
		do {
			pensar();
		}while(!(executando.compareAndSet(false, true)));
		
		try {
			esqGarfo();
			dirGarfo();
			comer();
			largar();
		}finally {
			executando.set(false);
		}
	}
	
	public void pensar() {
		tempoPensando = ThreadLocalRandom.current().nextInt(10001);
		
		try {
			Thread.sleep(tempoPensando);
			System.out.println("Filosofo " + id + " está pensando");
		}catch(Exception exc){
			System.out.println("Thread " + id + " interrompida");
			Thread.currentThread().interrupt();
		}
	}
	public void esqGarfo() {
		
		lGarfo = this.id - 1;
		
		
		if(garfos[lGarfo] == 1) {
			garfos[lGarfo] = 0;
			System.out.println("Filosofo " + id + " pegou o garfo a sua esquerda");
			garfosAtuais++;
		}
	}
	
	public void dirGarfo() {
		if(this.id == 5) {
			rGarfo = 0;
		}else {
			rGarfo = this.id;
		}
		
		if(garfos[rGarfo] == 1) {
			garfos[rGarfo] = 0;
			System.out.println("Filosofo " + + id + " pegou o garfo a sua direita");
			garfosAtuais++;
		}
	}
	
	public void comer() {
		if(garfosAtuais == 2) {
			tempoComendo = ThreadLocalRandom.current().nextInt(10001);
			System.out.println("Filosofo "+ id + " começou a comer");
			
			try {
				Thread.sleep(tempoPensando);
			}catch(Exception exc){
				System.out.println("Thread " + id + " interrompida");
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void largar() {
		
		garfos[lGarfo] = 1;
		garfos[rGarfo] = 1;
		garfosAtuais = 0;
		System.out.println("Filosofo "+ this.id + " largou os garfos");
	}
}
