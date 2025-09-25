package application;

import java.util.Random;

public class Philosopher implements Runnable {
	enum State{
		thinking,
		eating,
		other,
	}
	private Program program = null;
	private byte id;
	private double speed = 100;
	private State state = State.other;
	private long cycles = 0;
	private Random rand = new Random();


	public Philosopher(final byte id, final double speed) {
		System.out.println("Novo filosofo criado como thread " + id);
		this.id = id;
		this.speed = speed;
		this.program = program;
	}
	
	
	@Override
	public void run() {
		System.out.println("Filosofo " + id + " esta ativo");
        
        while(true){
			think();
            getForks();
			eat();
			
			System.out.println("filosofo " + id + " completou o ciclo " + cycles++);
		}
	}
	
	private void think() {
		int delay = rand.nextInt((int)(100 * speed));
		System.out.println("Filosofo " + id + " esta pensando por " + delay + " milesegundos");
		try {
			Thread.sleep(delay);
		} catch (Exception e){
			System.out.println("Filosofo " + id + " foi interrompido enquanto pensava");
		}
	}
	
    private void getForks(){

    }

	private void eat() {
		int delay = rand.nextInt((int)(100 * speed));
		System.out.println("Filosofo " + id + " esta comendo por " + delay + " milesegundos");
		try {
			Thread.sleep(delay);
		} catch (Exception e){
			System.out.println("Filosofo " + id + " foi interrompido enquanto comia");
		}
	}
	
	public void outputInformation() {

		System.out.println("------------------");
		System.out.println("Filosofo: " + id);
		System.out.println("Estado: "  +
			switch(state) {
				case thinking -> "pensando";
				case eating -> "comendo";
				case other -> "outro";
			});
		System.out.println("Ciclos completos: " + cycles);
		System.out.println("------------------");		
	}
}

