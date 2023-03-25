package telran.numbers.model;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorGroupSum extends NumberSum {

	public ExecutorGroupSum(int[][] numbersGroup) {
		super(numbersGroup);
	}

	@Override
	public int computeSum() {
		int poolSize = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
		
		Task[] tasks = new Task[numbersGroup.length];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = new Task(numbersGroup[i]);
			executorService.execute(tasks[i]);
		}

		executorService.shutdown();

		try {
			executorService.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Arrays.stream(tasks).mapToInt(Task::getResult).sum();
	}

	private class Task implements Runnable {
		private int[] row;
		private int result;

		public Task(int[] row) {
			this.row = row;
		}

		@Override
		public void run() {
			for (int i = 0; i < row.length; i++) {
				result += row[i];
			}
		}

		public int getResult() {
			return result;
		}
	}
}