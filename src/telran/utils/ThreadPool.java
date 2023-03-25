package telran.utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPool implements Executor {
	private Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
	private Thread[] threads;
	private AtomicBoolean isRunning = new AtomicBoolean(true);

	public ThreadPool(int nThreads) {
		threads = new Thread[nThreads];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new TaskWorker());
			threads[i].start();
		}
	}

	public void shutDown() {
		isRunning.set(false);
	}

	public void joinToPool() throws InterruptedException {
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
	}

	@Override
	public void execute(Runnable command) {
		if (isRunning.get()) {
			workQueue.add(command);
		}

	}

	private class TaskWorker implements Runnable {

		@Override
		public void run() {
			while (!workQueue.isEmpty() || isRunning.get()) {
				Runnable task = workQueue.poll();
				if (task != null) {
					task.run();
				}
			}

		}

	}

}
