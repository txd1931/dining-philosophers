package dinner_table;

import java.util.Random;

public class Philosofers implements Runnable{
	private static byte idCounter = 0;
	private byte id;
	private int bites = 0;
	private int thinkTimes = 0;
	private int cycles = 0;

	private Random random = new Random();
	
	public Philosofers() {
		this.id = ++idCounter;
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
	/*
	0 1 2 3 4
	1 = 0/1
	2 = 1/2
	3 = 2/3
	4 = 3/4
	5 = 4/0 
	forks[i] == 0 (livre)
	forks[i] == 1 (robarão)
	*/
	public void takeLeftFork() {
		int leftFork = this.id-1;
		while(Main.forks[leftFork] == 1) {
			try {
				Thread.sleep(random.nextInt(501));
			} catch (Exception e) {
				System.out.println("\u001B[31m"+"Filosofo "+this.id+" não esperou para pegar o garfo: "+leftFork+"\u001B[37m");
			}
		};
		System.out.println("Filosofo "+this.id+" pegou o garfo esquerdo: "+leftFork);
		Main.forks[leftFork] = 1;
	}
	
	public void takeRightFork() {
		int rightFork;
		if(this.id == 5) {
			rightFork = 0;
		} else {
			rightFork = this.id;
		}
		while(Main.forks[rightFork] == 1) {
			try {
				Thread.sleep(random.nextInt(501));
			} catch (Exception e) {
				System.out.println("\u001B[31m"+"Filosofo "+this.id+" não esperou para pegar o garfo: "+rightFork+"\u001B[37m");
			}
		};
		System.out.println("Filosofo "+this.id+" pegou o garfo direito: "+rightFork);
		Main.forks[rightFork] = 1;
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
		System.out.print("\u001B[93m");
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
			takeLeftFork();
			takeRightFork();
			eat();
			putForks();
			cycles++;
			Main.donePhilosophers[id-1] = true;
			System.out.println(this.id+" finalizou um ciclo");
		}
		System.out.println("Saiu da mesa: "+this.id);
        graph();
	}
	
}
