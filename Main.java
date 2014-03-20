import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	public static final int N = 8;
	public static final int POPULATION_SIZE = 50;
	public static int numberOfFitness = 0; 
	public static Boolean foundSolution = false;
	public static char [][] board = new char[N][N];
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				board[i][j] = ' ';
			}
		}
		int[][] population = new int[POPULATION_SIZE][];
		int[] fitnesses = new int[POPULATION_SIZE];
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < N; i++){
			list.add(i);
		}
		//create Members of Population
		for(int i = 0; i < POPULATION_SIZE; i++){
			java.util.Collections.shuffle(list);
			int[] member = new int[N];
			for(int j = 0; j < N; j++){
				member[j] = list.get(j);
			}
			population[i] = member;
		}
		//natural selection
		while(numberOfFitness < 100000000 && !foundSolution){
			for(int i = 0; i < POPULATION_SIZE; i++){
				fitnesses[i] = fitness(population[i]);
			}
			if(foundSolution){
				System.out.println("Found Solution.");
				for(int i = 0; i < POPULATION_SIZE; i++){
					if(fitnesses[i] == 0){

						for(int j = 0; j < N; j++){
							board[j][population[i][j]] = 'Q';
							System.out.print(population[i][j]+ " ");
						}
						break;

					}
				}
				String[] row = new String[2*N + 1];
				String border = "";
				for(int i = 0; i < N; i++){
					border += "+---";
				}
				border += "+";
				for(int i = 0; i < 2*N +1; i+=2){
					row[i] = border;
				}
				row[2*N] = border;
				System.out.println();
				for(int k = 0; k < N; k++){
					String spaces = "| ";
					for(int j = 0; j < N; j++){
						spaces += board[k][j];
						spaces += " | ";
					}
					
					row[2*k +1 ] = spaces;

				}
				for(int i = 0; i < 2*N +1; i++){
					System.out.println(row[i]);
				}
				System.exit(0);
			}
			Random rand = new Random();
			int[] randomArray = new int[5];
			for(int i = 0; i < 5; i++){
				randomArray[i] = rand.nextInt(POPULATION_SIZE);
				Boolean duplicate = true;
				while(duplicate){
					Boolean breaker = true;
					for(int j = i; j>0 && breaker; j--){
						if(randomArray[i] == randomArray[j-1]){
							randomArray[i] = rand.nextInt(POPULATION_SIZE);
							duplicate = true;
							breaker = false;
						}
						else{
							duplicate = false;
						}
					}
					if(i == 0){
						duplicate = false;
					}
				}
			}
			for(int i = 0; i < 2; i++){
				for(int j = i; j<5; j++){
					if(fitnesses[randomArray[i]] > fitnesses[randomArray[j]]){
						int temp = randomArray[i];
						randomArray[i] = randomArray[j];
						randomArray[j] = temp;
					}
				}
			}
			
			int[] child = crossover(population[randomArray[0]], population[randomArray[1]]);
			int weakestMember = 0;
			for(int i = 0; i < POPULATION_SIZE; i++){
				if(fitnesses[i] < fitnesses[weakestMember]){
					weakestMember = i;
				}
			}
			population[weakestMember] = child;
			
			for(int i = 0; i < POPULATION_SIZE; i++){
				population[i] = mutate(population[i]);
			}
			
		}System.out.println("None Found");

	}
	static int[] crossover(int[] parent1, int[] parent2){
		int[] child = new int[N];
		Random rand = new Random();
		int crossoverPoint = rand.nextInt(N);
		for(int i = 0; i < crossoverPoint; i++){
			child[i] = parent1[i];
		}
		int temp[] = new int [N - crossoverPoint+1];
		int index = 0;
		Boolean duplicate = false;
		for(int i = crossoverPoint; i < N; i++){
			for(int j = 0; j < crossoverPoint; j++){
				if(child[j] == parent2[i]){
					duplicate = true;
				}
			}
			if(!duplicate){
				temp[index] = parent2[i];
				index++;
			}duplicate = false;
		}
		for(int i = 0; i < crossoverPoint; i++){
			for(int j = 0; j < crossoverPoint; j++){
				if(child[j] == parent2[i]){
					duplicate = true;
				}
			}
			if(!duplicate){
				temp[index] = parent2[i];
				index++;
			}duplicate = false;
		}
		index = 0;
		for(int i = crossoverPoint; i < N; i++){
			child[i] = temp[index];
			index++;
		}
		return child;
	}
	static int[] mutate(int[] parent){
		Random rand = new Random();
		int a = rand.nextInt(5);
		if(a != 4){
			int point1 = rand.nextInt(N);
			int point2 = rand.nextInt(N);
			int temp = parent[point1];
			parent[point1] = parent[point2];
			parent[point2] = temp;
		}
		return parent;
	}
	static int fitness(int[] parent){
		int f = 0;
		for(int i = 0; i < parent.length; i++){
			for(int j = (parent.length -1); j > i; j--){
				int x = Math.abs(j -i);
				int y = Math.abs(parent[j] - parent[i]);
				if(x == y) f++;
			}
		}
		numberOfFitness++;
		if(f == 0){
			foundSolution = true;
		}
		return f;
	}
}

