package dinner_table;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
	// Timers
	public static long timeAtCreation;
	public static int limitTime;
	
	// Os garfos como um vetor de semáforos
	public static Semaphore[] forks = new Semaphore[5];
	
	public static boolean[] donePhilosophers = new boolean[5];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		for (int i=0; i < forks.length; i++) {
            forks[i] = new Semaphore(1, true);
        }
		
		Thread philosopher1 = new Thread(new Philosofers());
		Thread philosopher2 = new Thread(new Philosofers());
		Thread philosopher3 = new Thread(new Philosofers());
		Thread philosopher4 = new Thread(new Philosofers());
		Thread philosopher5 = new Thread(new Philosofers());
		
		System.out.println("Digite o tempo que deseja que dure o jantar em segundos:");
		limitTime = sc.nextInt();
		sc.close();
		
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		timeAtCreation = System.currentTimeMillis();
		
		// Inicia o jantar
		philosopher1.start();
		philosopher2.start();
		philosopher3.start();
		philosopher4.start();
		philosopher5.start();
		
		// Inicia o contador
		counterSimu();
		
		// "Interrompe" as threads filosofos
		philosopher1.interrupt();
		philosopher2.interrupt();
		philosopher3.interrupt();
		philosopher4.interrupt();
		philosopher5.interrupt();
		System.out.println("\u001B[93m"+"Fechamos senhores"+"\u001B[37m");
		
	}
	// Contador de tempo para a simulação
	public static void counterSimu() {
		System.out.println("\u001B[93m"+"("+((System.currentTimeMillis() - Main.timeAtCreation)/1000)+") Segundos"+"\u001B[37m");
		while((System.currentTimeMillis() - Main.timeAtCreation) < Main.limitTime*1000) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println("QUEBROU O CONTADOR");
			}
			System.out.println("\u001B[93m"+"("+((System.currentTimeMillis() - Main.timeAtCreation)/1000)+") Segundos"+"\u001B[37m");
		}
	}

	public static void acquire(Semaphore semaforo) {
		try {
			semaforo.acquire();
		} catch (Exception e) {
			Thread.currentThread().interrupt();
			System.out.println("ESTA THREAD FOI INTERROMPIDA");
		}
	}
	
	public static boolean allLeave(){
		for (boolean done : donePhilosophers) {
			if (!done) {
				return false;
			}
		}
		return true;
	}
	
	synchronized public static void statistics(int id, int bites, int thinkTimes, int cycles) {
		System.out.print("\u001B[32m");
		System.out.println("-------------------------------");
		System.out.println("Filosofo "+id+":");
		System.out.println("Comeu "+bites+" vezes");
		System.out.println("Filosofou "+thinkTimes+" vezes");
		System.out.println("Realizou o ciclo "+cycles+" vezes");
		System.out.print("\u001B[37m");
	}
}
