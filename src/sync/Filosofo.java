package sync;


import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Filosofo implements Runnable{
	
	public static long inicio = System.currentTimeMillis();
	public static long tempoLimite = 70000;
	
	public static int[] garfos = {1, 1, 1, 1, 1};
	
	private int id;
	public static AtomicBoolean executando = new AtomicBoolean(false);
	private int lGarfo;
	private int rGarfo;
	public static int garfosAtuais = 0;
	
	private int execucoes;
	
	private long tempoPensando;
	private long tempoComendo;
	
	public Filosofo(int id) {
		this.id = id;
	}
		
	@Override
	public void run(){
			while(System.currentTimeMillis() - inicio < tempoLimite) {
				do {
					pensar();
				}while(!(executando.compareAndSet(false, true)) || !(garfos[this.lGarfo] == 1) || !(garfos[this.rGarfo] == 1));
				
				try {
					esqGarfo();
					dirGarfo();
					comer();
					largar();
				}finally {
					executando.compareAndSet(true, false);
				}
			}
			
	}
	
	
	public void pensar() {
		this.tempoPensando = ThreadLocalRandom.current().nextInt(10001);
		
		this.lGarfo = this.id - 1;

		if(this.id == 5) {
			this.rGarfo = 0;
		}else {
			this.rGarfo = this.id;
		}
		
		try {
			Thread.sleep(this.tempoPensando);
			System.out.println("Filosofo " + id + " está pensando");
		}catch(Exception exc){
			System.out.println("Thread " + id + " interrompida");
			Thread.currentThread().interrupt();
		}
	}
	public void esqGarfo() {
		
		
		if(garfos[lGarfo] == 1) {
			garfos[lGarfo] = 0;
			System.out.println("Filosofo " + id + " conseguiu pegar o garfo a sua esquerda");
			garfosAtuais++;
		}else {
			System.out.println("Filosofo " + id + " não conseguiu pegar o garfo a sua esquerda");
		}
	}
	
	public void dirGarfo() {
		
		if(garfos[rGarfo] == 1) {
			garfos[rGarfo] = 0;
			System.out.println("Filosofo " + + id + " conseguiu pegar o garfo a sua direita");
			garfosAtuais++;
		}else {
			System.out.println("Filosofo " + + id + " não conseguiu pegar o garfo a sua direita");
		}
	}
	
	public void comer() {
		if(garfosAtuais == 2) {
			garfosAtuais = 0;
			executando.set(false);
			
			this.tempoComendo = ThreadLocalRandom.current().nextInt(10001);
			System.out.println("Filosofo "+ id + " começou a comer");
			
			try {
				Thread.sleep(this.tempoComendo);
			}catch(Exception exc){
				System.out.println("Thread " + id + " interrompida");
				Thread.currentThread().interrupt();
			}
		}else {
			System.out.println("Filosofo "+ id +" não conseguiu comer");
			garfosAtuais = 0;
		}
	}
	
	public void largar() {
		
		garfos[lGarfo] = 1;
		garfos[rGarfo] = 1;
		System.out.println("Filosofo "+ this.id + " largou os garfos");
		this.execucoes++;
		System.out.println("Filosofo "+ this.id + " comeu " + this.execucoes);
	}
}
