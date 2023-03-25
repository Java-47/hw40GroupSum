package telran.numbers.controller;

import java.util.Random;

import telran.numbers.model.ExecutorGroupSum;
import telran.numbers.model.NumberSum;
import telran.numbers.model.ParallelStreamGroupSum;
import telran.numbers.model.ThreadsGroupSum;
import telran.numbers.test.GroupSumPerfomanceTest;

public class GroupSumAppl {
	private static final int N_GROUPS = 10_000;
	private static final int NUMBERS_PER_GROUP = 10_000;
	private static int[][] arr = new int[N_GROUPS][NUMBERS_PER_GROUP];
	private static Random random = new Random();


	public static void main(String[] args) {
		
		fillArray();
		NumberSum executorsSum = new ExecutorGroupSum(arr);
		NumberSum threadSum = new ThreadsGroupSum(arr);
		NumberSum streamSum = new ParallelStreamGroupSum(arr);
		new GroupSumPerfomanceTest("ExecutorGroupSum", executorsSum).runTest();
		new GroupSumPerfomanceTest("ThreadsGroupSum", threadSum).runTest();
		new GroupSumPerfomanceTest("ParallelStreamGroupSum", streamSum).runTest();

	}

	private static void fillArray() {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = random.nextInt();
			}
		}
		
	}

}
