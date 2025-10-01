package dinner_table;

import java.util.Random;

public class Philosofers implements Runnable{
	// Contador para um 'auto increment' nos ids
	private static int idCounter = 0;
	
	private int id;
	final int leftFork;
	final int rightFork;
	
	// Contadores para as estatísticas
	private int bites = 0;
	private int thinkTimes = 0;
	private int cycles = 0;

	private Random random = new Random();
	
	public Philosofers() {
		this.id = ++idCounter;
		this.leftFork = this.id-1;
		if(this.id == 5) {this.rightFork = 0;} else {this.rightFork = this.id;}
	}
	
	public void think() {
		int thinkIexist = random.nextInt((Main.limitTime*1000)*20/100+1);
		try {
			System.out.println("Filosofo "+id+" comecou a filosofar");
			Thread.sleep(thinkIexist);
			System.out.println("Filosofo "+id+" terminou de filosofar");
			thinkTimes++;
		} catch (Exception e){
			System.out.println("\u001B[31m"+"Filosofo"+id+" nao conseguiu filosofar"+"\u001B[37m");
		}
	}
	
	public void takeForks() {
		System.out.println("Filosofo: "+this.id+" sentiu fome");
		// Tenta pegar os garfos
		lookforForks(this.id, this.leftFork, this.rightFork);
		System.out.println("Filosofo: "+this.id+" pegou os garfos de indices "+this.leftFork+" e "+this.rightFork);
	}

	public void lookforForks(int id, int leftFork, int rightFork) {
		if(this.id == 5) {
			//Tenta pegar o garfo esquerdo
			Main.acquire(Main.forks[rightFork]);
			//Tenta pegar o garfo direito		
			Main.acquire(Main.forks[leftFork]);
		} else {
			//Tenta pegar o garfo esquerdo
			Main.acquire(Main.forks[leftFork]);
			//Tenta pegar o garfo direito		
			Main.acquire(Main.forks[rightFork]);
		}
	}
	
	public void eat() {
		try {
			Thread.sleep(random.nextInt((Main.limitTime*1000)*20/100+1));
			bites++;
			System.out.println("Filosofo "+this.id+" Comeu");
		} catch (Exception e) {
			System.out.println("\u001B[31m"+"Filosofo "+this.id+" nao conseguiu comer"+"\u001B[37m");
		}
	}

	public void putForks() {
		// Verifica se seus adjacentes delcararam estar comendo
		Main.forks[this.leftFork].release();
		Main.forks[this.rightFork].release();
		System.out.println("Filosofo "+this.id+" devolveu os garfos");
	}
	
	@Override
	public void run() {
		while ((System.currentTimeMillis() - Main.timeAtCreation) < Main.limitTime*1000) {
			think();
			takeForks();
			eat();
			putForks();
			cycles++;
			System.out.println(this.id+" finalizou um ciclo");
		}
		Main.donePhilosophers[id-1] = true;
		while(!Main.allLeave());
		Main.statistics(this.id, this.bites, this.thinkTimes, this.cycles);
	}
	
}
