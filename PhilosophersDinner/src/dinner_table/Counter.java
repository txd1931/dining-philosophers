package dinner_table;

public class Counter implements Runnable{
	@Override
	public void run() {
		System.out.println(Main.counter);
		while(Main.counter < 10) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println("fudeo o contador");
			}
			Main.counter++;
			System.out.println(Main.counter);
		}
		System.out.println("Fechamos senhores");
	}
}
