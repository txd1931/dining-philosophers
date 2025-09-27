package sync;

public class Main {
	public static Thread[] filosofos = new Thread[5];
	
	public static void main(String[] args) {
		
		filosofos = criarThreads(filosofos);
		usarThreads(filosofos);
	}
	
	public static Thread[] criarThreads(Thread[] threads) {
		
		for(int i = 0; i < threads.length; ++i) {
			threads[i] = new Thread(new Filosofo(i + 1));
		}
		
		return threads;
	}
	
	public static void usarThreads(Thread[] threads) {
		for(int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
	}
}
