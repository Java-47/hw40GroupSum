package telran.numbers.model;

import java.util.Arrays;

public class ThreadsGroupSum extends NumberSum {

	public ThreadsGroupSum(int[][] numbersGroup) {
		super(numbersGroup);
	}

	@Override
	public int computeSum() {
		Thread[] threads = new Thread[numbersGroup.length];

		Task[] tasks = new Task[numbersGroup.length];
		for (int i = 0; i < tasks.length; i++) {
			tasks[i] = new Task(numbersGroup[i]);
			threads[i] = new Thread(tasks[i]);
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
