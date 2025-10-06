package sync;


import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Filosofo implements Runnable{
	
	public static long inicio = System.currentTimeMillis();
	public static long tempoLimite = 6001;
	private long inicioLocal;
	
	public static AtomicBoolean executando = new AtomicBoolean(false);
	
	public static int[] garfos = {-1, -1, -1, -1, -1};
	
	private int id;
	private int lGarfo;
	private int rGarfo;
	private int garfosAtuais = 0;
	
	private int execucoes;
	
	private long tempoExecutando;
	
	public Filosofo(int id) {
		this.id = id;
	}
		
	@Override
	public void run(){
			while(System.currentTimeMillis() - inicio < tempoLimite) {
				do {
					pensar();
					pegarGarfos();
				}while(!(this.garfosAtuais == 2));
				
				try {
					comer();
				}finally {
					largar();
					this.execucoes++;
					System.out.println("Filosofo "+ this.id + " comeu " + this.execucoes + " vez(es)");
				}
			}
			
	}
	
	
	public void pensar() {
		this.tempoExecutando = ThreadLocalRandom.current().nextInt(1001);
		
		largar();
		
		this.lGarfo = this.id - 1;

		if(this.id == 5) {
			this.rGarfo = 0;
		}else {
			this.rGarfo = this.id;
		}
		
		try {
			Thread.sleep(this.tempoExecutando);
			System.out.println("Filosofo " + this.id + " está pensando");
		}catch(Exception exc){
			System.out.println("Thread " + this.id + " interrompida");
			Thread.currentThread().interrupt();
		}
	}
	public void pegarGarfos() {
		this.tempoExecutando = ThreadLocalRandom.current().nextInt(1001);
		this.inicioLocal = System.currentTimeMillis();
		
		if(executando.compareAndSet(false, true)) {
			while(System.currentTimeMillis() - this.inicioLocal < this.tempoExecutando && this.garfosAtuais != 2 ) {
				
				if(garfos[lGarfo] == -1) {
					garfos[lGarfo] = this.id;
					System.out.println("Filosofo " + this.id + " conseguiu pegar o garfo a sua esquerda");
					this.garfosAtuais++;
					
				}		
				if(garfos[rGarfo] == -1) {
					garfos[rGarfo] = this.id;
					System.out.println("Filosofo " + this.id + " conseguiu pegar o garfo a sua direita");
					this.garfosAtuais++;
					
				}
			}
		}
	}
	
	public void comer() {
			executando.compareAndSet(true, false);
			
			this.tempoExecutando = ThreadLocalRandom.current().nextInt(1001);
			System.out.println("Filosofo "+ this.id + " começou a comer");
			
			try {
				Thread.sleep(this.tempoExecutando);
			}catch(Exception exc){
				System.out.println("Thread " + this.id + " interrompida");
				Thread.currentThread().interrupt();
			}
	}
	
	public void largar() {
		if(this.garfosAtuais == 2) {

			executando.compareAndSet(true, false);
			this.garfosAtuais = 0;
			garfos[lGarfo] = -1;
			garfos[rGarfo] = -1;
			System.out.println("Filosofo "+ this.id + " largou os garfos");
			
		}else if(garfos[lGarfo] == this.id) {
			
			executando.compareAndSet(true, false);
			this.garfosAtuais = 0;
			System.out.println("Filosofo "+ this.id + " não conseguiu comer");
			garfos[lGarfo] = -1;
			
		}else if(garfos[rGarfo] == this.id) {
			
			executando.compareAndSet(true, false);
			this.garfosAtuais = 0;
			garfos[rGarfo] = -1;
			System.out.println("Filosofo "+ this.id + " não conseguiu comer");
		}
	}
}
