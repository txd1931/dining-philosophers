package dinner_table;

import java.util.Random;

public class Philosofers implements Runnable{
	
	private static int idCounter = 0;
	
	private int id;
	private final int neighborL;
	private final int neighborR;
	
	private int bites = 0;
	private int thinkTimes = 0;
	private int cycles = 0;

	private Random random = new Random();
	
	public Philosofers() {
		this.id = ++idCounter;
		if(this.id == 1) {
			this.neighborL = 5;
			this.neighborR = 2;
		} else {
		if(this.id == 5) {
			this.neighborL = 4;
			this.neighborR = 1;
		} else {
			this.neighborL = this.id-1;
			this.neighborR = this.id+1;
		}
		}
	}
	
	public void think() {
		int thinkIexist = random.nextInt((Main.limitTime*1000)*20/100+1);
		try {
			Thread.sleep(thinkIexist);
			System.out.println("Filosofo "+id+" filosofou");
			thinkTimes++;
		} catch (Exception e){
			System.out.println("\u001B[31m"+"Filosofo"+id+" nao conseguiu filosofar"+"\u001B[37m");
		}
	}
	// Um filósofo pode passar para o estado comendo apenas se nenhum de seus vizinhos estiver comendo.
	public void takeForks() {
		int leftFork = this.id-1;
		int rightFork;
		if(this.id == 5) {rightFork = 0;} else {rightFork = this.id;}
		Main.statePhilosophers[id-1] = Main.StatePhilosopher.HUNGRY;
		System.out.println("Filosofo: "+this.id+" sentiu fome");
		// Verifica se seus adjacentes estam tentando pegar seus garfos
		while(lookforForks(Main.forks[leftFork], Main.forks[rightFork]));
		// Verifica se seus adjacentes delcararam estar comendo
		while(lookAround(this.neighborL, this.neighborR));
	}
	
	public boolean lookforForks(int leftFork, int rightFork) {
		if(leftFork == 0 && rightFork == 0) {
			Main.forks[leftFork] = 1;
			Main.forks[rightFork] = 1;
			System.out.println("Filosofo "+this.id+" tentou pegar seus garfos.");
			return false;
		} else {
			try {
				Thread.sleep(random.nextInt(501));
			} catch (Exception e) {
				System.out.println("\u001B[31m"+"Filosofo "+this.id+" nao esperou pelos garfos, pois "+e+"\u001B[37m");
			}
			return true;
		}
	}
	
	public boolean lookAround(int left, int right) {
		if(Main.statePhilosophers[left] != Main.StatePhilosopher.EATING && Main.statePhilosophers[right] != Main.StatePhilosopher.EATING) {
			Main.statePhilosophers[this.id-1] = Main.StatePhilosopher.EATING;
			System.out.println("Filosofo "+this.id+" delcarou estar comendo.");
			return true;
		} else {
			try {
				Thread.sleep(random.nextInt(501));
			} catch (Exception e) {
				System.out.println("\u001B[31m"+"Filosofo "+this.id+" nao esperou pelos garfos, pois "+e+"\u001B[37m");
			}
			return true;
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
		int rightFork;
		if(this.id == 5) {
			rightFork = 0;
		} else {
			rightFork = this.id;
		}
		int leftFork = this.id-1;
		Main.forks[rightFork] = 0;
		Main.forks[leftFork] = 0;
		System.out.println("Filosofo "+this.id+" devolveu os garfos");
	}
	
	synchronized public void graph() {
		System.out.print("\u001B[42m");
		System.out.println("-------------------------------");
		System.out.println("Filosofo "+this.id+":");
		System.out.println("Comeu "+this.bites+" vezes");
		System.out.println("Finalisou seu pensamento "+this.thinkTimes+" vezes");
		System.out.println("Realizou o ciclo "+this.cycles+" vezes");
		System.out.println("-------------------------------");
		System.out.print("\u001B[37m");
	}
	
	@Override
	public void run() {
		while ((System.currentTimeMillis() - Main.timeAtCreation) < Main.limitTime*1000) {
			think();
			takeForks();
			eat();
			putForks();
			cycles++;
			//Main.donePhilosophers[id-1] = true;
			System.out.println(this.id+" finalizou um ciclo");
		}
		System.out.println("Saiu da mesa: "+this.id);
        graph();
	}
	
}
