import java.util.Scanner;
public class Program {
	public static int numPhilosophers = 5;
	private static int numForks = numPhilosophers;
	private static Thread[] philosophers;
	public static long simulationTime = 2000l * 1000000l;
	public static double simulationSpeed = 5;

	public static boolean[] donePhilosophers;

	public static void main(String[] args){
        System.out.println("Problema do Jantar dos Filósofos \n================================\n");
		problemSetup();
		initialization();
		//waitForSimulation();
	}
	
	private static void problemSetup() {
        
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Insira a quantidade de filosofos (2 - 16): ");
            numPhilosophers = numForks = sc.nextInt();
            
            System.out.print("\nInsira o tempo de simulacao em segundos (0 para indeterminado): ");
            simulationTime = 1000000000l * (long)sc.nextInt();
            
            System.out.print("\nInsira a velocidade de simulacao de 1% - 1000% (sem %): ");
            simulationSpeed = ((double)sc.nextInt()) / 100;
        }
	}
	
	private static void initialization() {
        philosophers = new Thread[numPhilosophers];
        donePhilosophers = new boolean[numPhilosophers];
        Thread newThread = null;  
		for(int i = 0; i < numPhilosophers; i++) {
            Philosopher newPhilosopher = new Philosopher((byte)i, simulationSpeed);
            newThread = new Thread(newPhilosopher);
            philosophers[i] = newThread;
		}
        for (int i = 0; i < numPhilosophers; i++) {
            System.out.println(i);
            philosophers[i].start();
        }

	}
	private static void waitForSimulation() {
		if (simulationTime > 0) {	
			try {
				Thread.sleep(simulationTime);
			} catch (InterruptedException e) {}
		}
	}
	public static boolean checkIfAllDone(){
		for (boolean done : donePhilosophers) {
			if (!done) {
				return false;
			}
		}
		return true;
	}
}
