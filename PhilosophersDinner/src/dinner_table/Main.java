package dinner_table;

import java.util.Scanner;

/* 
5 filósofos, 5 garfos, um filósofo precisa de dois garfos para comer
O filósofo alterna entre COMER e PENSAR. 
Quando um filósofo fica faminto, ele tenta pegar seus garfos um de cada vez, não importa a ordem
Se pegar, ele come por um tempo, então para e continua a pensar
*/
// você consegue escrever um programa para cada filósofo que faça o que deve fazer e jamais fique travado?
//output vezes que comeu, vezes que pensou
public class Main {
	
	public enum StatePhilosopher {
		THINKING,
		EATING,
		HUNGRY,
		LEAVING
	}
	
	// Timers
	public static long timeAtCreation;
	public static int limitTime;
	
	public static int[] forks = new int[5];
	public static StatePhilosopher[] statePhilosophers = new StatePhilosopher[5];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		Thread philosopher1 = new Thread(new Philosofers());
		Thread philosopher2 = new Thread(new Philosofers());
		Thread philosopher3 = new Thread(new Philosofers());
		Thread philosopher4 = new Thread(new Philosofers());
		Thread philosopher5 = new Thread(new Philosofers());
		
		System.out.println("Digite o tempo que deseja que dure o jantar em segundos:");
		limitTime = sc.nextInt();
		
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		
		// Inicia o jnatar
		philosopher1.start();
		philosopher2.start();
		philosopher3.start();
		philosopher4.start();
		philosopher5.start();
		
		// Inicia o contador
		timeAtCreation = System.currentTimeMillis();
		counterSimu();
		
		// "Interrompe" as threads filosofos
		philosopher1.interrupt();
		philosopher2.interrupt();
		philosopher3.interrupt();
		philosopher4.interrupt();
		philosopher5.interrupt();
		System.out.println("\u001B[93m"+"Fechamos senhores"+"\u001B[37m");
		
	}
	
	
	public static boolean checkTimeLimit() {
		if((System.currentTimeMillis() - Main.timeAtCreation) == Main.limitTime*1000) {
			return true;
		}
		return false;
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
	
	// Verificação das "assinaturas" dos filosofos
	 static boolean allLeave(){
		for (StatePhilosopher leave : statePhilosophers) {
			if (leave != StatePhilosopher.LEAVING) {
				return false;
			}
		}
		return true;
	}
	
}
