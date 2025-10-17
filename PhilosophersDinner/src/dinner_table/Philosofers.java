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
			System.out.println("\u001B[31m"+"Filosofo"+id+" nao conseguiu filosofar, ou o tempo acabou."+"\u001B[37m");
		}
	}
	
	public void takeForks() {
		System.out.println("Filosofo: "+this.id+" sentiu fome");
		// Tenta pegar os garfos
		lookforForks(this.id, this.leftFork, this.rightFork);
	}

	public void lookforForks(int id, int leftFork, int rightFork) {
		boolean takef = false;
		while(!takef) {
			//Decide de modo aleatório a ordem que pega os garfos
			int wichfork = random.nextInt(1, 3);
			
			if(wichfork == 2) {
				//Tenta pegar o garfo direito 
				takef = Main.tryAcquire(Main.forks[rightFork]);
				if(takef) {
					System.out.println("Filosofo "+this.id+" pegou o garfo direito");	
					//Tenta pegar o garfo esquerdo		
					takef = Main.tryAcquire(Main.forks[leftFork]);
					if(takef) {
						System.out.println("Filosofo "+this.id+" pegou o garfo esquerdo");
					} else {
						//Se não consegiu ele devolve o garfo que pegou
						Main.forks[this.rightFork].release();
					}
				}
			}
			if(wichfork == 1) {
				//Tenta pegar o garfo esquerdo 
				takef = Main.tryAcquire(Main.forks[leftFork]);
				if(takef) {
					System.out.println("Filosofo "+this.id+" pegou o garfo esquerdo");	
					//Tenta pegar o garfo direito	
					takef = Main.tryAcquire(Main.forks[rightFork]);
					if(takef) {
						System.out.println("Filosofo "+this.id+" pegou o garfo direito");
					} else {
						//Se não consegiu ele devolve o garfo que pegou
						Main.forks[this.leftFork].release();
					}
				}
			}
		}
	}
	
	public void eat() {
		try {
			Thread.sleep(random.nextInt((Main.limitTime*1000)*20/100+1));
			bites++;
			System.out.println("Filosofo "+this.id+" Comeu");
		} catch (Exception e) {
			System.out.println("\u001B[31m"+"Filosofo "+this.id+" nao conseguiu comer, ou o tempo acabou."+"\u001B[37m");
		}
	}

	public void putForks() {
		// Devolve os garfos
		int wichfork = random.nextInt(1, 3);
		if(wichfork == 2) {
			//Tenta pegar o garfo esquerdo
			Main.forks[this.rightFork].release();
			System.out.println("Filosofo "+this.id+" devolveu o garfo direito primeiro");
			//Tenta pegar o garfo direito		
			Main.forks[this.leftFork].release();
			System.out.println("Filosofo "+this.id+" devolveu o garfo esquerdo");
		} //else {
		if(wichfork == 1) {
			//Tenta pegar o garfo esquerdo
			Main.forks[this.leftFork].release();
			System.out.println("Filosofo "+this.id+" devolveu o garfo esquerdo primeiro");
			//Tenta pegar o garfo direito		
			Main.forks[this.rightFork].release();
			System.out.println("Filosofo "+this.id+" devolveu o garfo direito");
		}
	}
	
	@Override
	public void run() {
		while ((System.currentTimeMillis() - Main.timeAtCreation) < Main.limitTime*1000) {
			think();
			takeForks();
			eat();
			putForks();
		}
		// 'Assina' seu nome na lista
		Main.donePhilosophers[id-1] = true;
		// Espera todos 'assinarem a lista' 
		while(!Main.allLeave());
		// Mostra as estatísticas
		Main.statistics(this.id, this.bites, this.thinkTimes);
	}
	
}
