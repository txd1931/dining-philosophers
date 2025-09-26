package application;
import java.util.Random;
public class Philosopher implements Runnable {
	enum State{
		INACTIVE,
		THINKING,
		EATING,
		GRABBING_FORKS,
	}
	private byte id = -1;
	private double speed = Program.simulationSpeed;
	private long cycles = 0;
	private String color = "\u001B[37m";
	private final String COLOR_RESET = "\u001B[0m";
	private State state = State.INACTIVE;
	private Random rand = new Random();

	public Philosopher(final byte id, final double speed) {
		println("Novo filosofo criado como thread " + id);
		this.id = id;
		this.speed = speed;
		this.color = "\u001B[" + ((id % 8) + 30) +"m";
	}

	@Override
	public void run() {
		println("Filosofo " + id + " esta ativo");
		while(true){
			state = State.THINKING;
			think();
			state = State.GRABBING_FORKS;
			getForks();
			state = State.EATING;
			eat();
			println("Filosofo " + id + " completou o ciclo " + cycles++);
		}
	}
	private void think() {
		int delay = rand.nextInt((int)(100 / speed));
		println("Filosofo " + id + " esta pensando por " + delay + " milesegundos");
		try {
			Thread.sleep(delay);
		} catch (Exception e){
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
		} catch (Exception e){
			println("Filosofo " + id + " foi interrompido enquanto comia");
		}
	}
	public void outputInformation() {
		println("------------------"
		+ "Filosofo: " + id + "\n"
		+ "Estado: " + switch(state) {
			case THINKING -> "pensando";
			case EATING -> "comendo";
			default -> "outro";
		} + "\n"
		+ "Ciclos completos: " + cycles + "\n" 
		+ "------------------\n");		
	}
	private void println(final String text){
		System.out.println(color + text + COLOR_RESET);
	}
}

