package dinner_table;

import java.util.Random;

public class Philosofers implements Runnable{
	private static byte idCounter = 0;
	private byte id;
	private int state = 0;
	private String name;
	private Random random = new Random();
	
	public Philosofers() {
		this.id = idCounter++;
	}
	
	public void think() {
		int thinkIexist = random.nextInt(5001);
		try {
			Thread.sleep(thinkIexist);
		} catch (Exception e){
			System.out.println("Filosofo"+id+" fumou quem deixou Jorge Bem");
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
		int leftFork = id-1;
		while(Main.forks[leftFork] == 1);
		System.out.println("Filosofo "+id+" roubou um garfo, esse safado");
		Main.forks[leftFork] = 1;
	}
	
	public void takeRightFork() {
		int rightFork = id++;
		while(Main.forks[rightFork] == 1);
		System.out.println("Filosofo "+id+" roubou OUTRO garfo, esse safado");
		Main.forks[rightFork] = 1;
	}
	
	public void eat() {
		try {
			Thread.sleep(random.nextInt(5001));
		} catch (Exception e) {
			System.out.println("Filosofo "+id+" foi jurado de morte em xique-xique por cumê");
		}
	}

	public void putForks() {
		int leftFork = id-1;
		int rightFork = id++;
		Main.forks[rightFork] = 0;
		Main.forks[leftFork] = 0;
	}
	
	
	@Override
	public void run() {
		while (true) {
			think( );
			takeLeftFork();
			takeRightFork();
			eat();
			putForks();
			}
	}
	
}
