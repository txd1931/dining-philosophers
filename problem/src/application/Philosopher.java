import java.util.Random;
public class Philosopher implements Runnable {
	enum State{
		INACTIVE,
		THINKING,
		EATING,
		GRABBING_FORKS,
	}
	private byte id = -1;
	private long cycles = 0;
	private String color = "\u001B[37m";
	private State state = State.INACTIVE;
	private final long TIME_AT_CREATION = System.nanoTime();
	
	private static double speed = Program.simulationSpeed;
	private static final String COLOR_RESET = "\u001B[0m";
	private final static Random rand = new Random();
	private final static long TIME_LIMIT = Program.simulationTime;

	public Philosopher(final byte id, final double speed) {
		println("Novo filosofo criado como thread " + id);
		this.id = id;
		Philosopher.speed = speed;
		this.color = "\u001B[" + ((id % 8) + 30) +"m";
	}

	@Override
	public void run() {
		println("Filosofo " + id + " esta ativo");
		while(true){
			//println((TIME_LIMIT - (System.nanoTime() - TIME_AT_CREATION)) < 0/* - 1000000000l*/);
			state = State.THINKING;
			think();
			state = State.GRABBING_FORKS;
			getForks();
			state = State.EATING;
			eat();
			println("Filosofo " + id + " completou o ciclo " + cycles++);
			if ((TIME_LIMIT - (System.nanoTime() - TIME_AT_CREATION)) < 0 && TIME_LIMIT > 0 ){
				//Thread.currentThread().interrupt();
				println("Filosofo " + id + " esta finalizando.");

                
                 
                Program.donePhilosophers[id] = true;
                while (!Program.checkIfAllDone()){
                    
                }
        
                outputInformation();
				return;
			}
		}
	}
	private void think() {
		int delay = rand.nextInt((int)(100 / speed));
		println("Filosofo " + id + " esta pensando por " + delay + " milesegundos");
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e){
			println("Filosofo " + id + " foi interrompido enquanto pensava");
		}
	}
	synchronized private void getForks(){
		
	}
	private void eat() {
		int delay = rand.nextInt((int)(100 / speed));
		println("Filosofo " + id + " esta comendo por " + delay + " milesegundos");
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e){
			println("Filosofo " + id + " foi interrompido enquanto comia");
		}
	}
	synchronized public void outputInformation() {
		println("------------------\nFilosofo: " + id + "\n"
		+ "Estado: " + switch(state) {
			case THINKING -> "pensando";
			case EATING -> "comendo";
			default -> "outro";
		} + "\n"
		+ "Ciclos completos: " + cycles + "\n" 
		+ "------------------\n");		
	}
	private void println(final Object text){
		System.out.println(color + text + COLOR_RESET);
	}
}
