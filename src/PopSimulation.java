import java.sql.SQLOutput;
import java.util.*;

public class PopSimulation {

    private final int NR_INDIVIDUALS = 100;
    private final int NR_GENERATIONS = 1000;
    private final double FIT_PRCTG = 0.2; //percentagem que queremos de individuos fit
    private final int NR_INDIV_FIT = (int) (NR_INDIVIDUALS*FIT_PRCTG); //serve de auxilio
    private final double MUTATION_PROB = 0.05;
    private List<Board> population;


    private int gens=0; //current nr of generations

    public void startSim() {
        createPopulation();
        population = selectFit();
        while (gens < NR_GENERATIONS)
            createNewGen();
        System.out.println("Final Population:\n" + this);
    }

    //create a population of individual  methods
    private void createPopulation() {
        population = new LinkedList<Board>();
        for (int i = 0; i < NR_INDIVIDUALS-1; i++) {
            population.add(new Board());
        }
        System.out.println("Original Population:\n" + this);
    }
    private Integer averageFitness() {
        int sum = 0;
        for (Board board: population) {
            sum += board.getFitness();
        }
        return sum/population.size();
    }

    public List<Board> selectFit(){
        Collections.sort(population);
        return population.subList(0, NR_INDIV_FIT); //vai buscar os FIT_PRCTG individuos mais fit
    }

    public void createNewGen(){
        crossPop();
        for (Board board: population) {
            if (Math.random()<MUTATION_PROB)
                mutateBoard(board);
        }
        //uncomment para debug
        //System.out.println("\n\n----------POPULATION NR " + gens + "--------------\n" + this);
    }

    public void crossPop(){

        /*
        adiciona a populacao até que esta chegue a NR_INDIVIDUOS
         */
        while (population.size() < NR_INDIVIDUALS) {
            //pick 2 boards (parents) from fit population list
            Board firstRand = population.get((int) (Math.random() * NR_INDIV_FIT));
            Board secondRand = population.get((int) (Math.random() * NR_INDIV_FIT));

            //make sure they are different
            while (firstRand.equals(secondRand)) {
                secondRand = population.get((int) (Math.random() * NR_INDIV_FIT));
            }

            //select random gene (between 0 and board size)
            int random = (int) (Math.random() * Board.SIZE);

            //get first part of genome from one parent and the second part of genome from second parent
            List<Integer> firstGenes = firstRand.getBoard().subList(0, random);
            List<Integer> secondGenes = secondRand.getBoard().subList(random, Board.SIZE);

            //create new board with those genes
            Board filho= new Board(firstGenes, secondGenes);                        //just for debug
            population.add(filho);

            //uncomment fot debug
            /*System.out.println("\n\n\n " + filho + "\n\n\n");
            System.out.println("novo board: " + firstRand + " Mixed with " + secondRand + "has index: " + population.indexOf(filho) + "\n\n\n");
            */

        }
        this.gens++;
    }


    public void mutateBoard(Board board){
        //select random gene (between 0 and board size)
        int random = (int) (Math.random() * Board.SIZE);
        //replace it with a random number between 0 e board size tb (mas é outro int)
        board.getBoard().set(random, (int) (Math.random() * Board.SIZE));

        //uncomment for debug
        //System.out.println(population.indexOf(board) + " mutated");
    }


    @Override
    public String toString() {
        String s = "";
        for (Board b : population) {
            s += b.toString();
            s+="\n";
        }
        s+= "\n Average Fitness: " + averageFitness() + "\n\n END POPULATION \n\n";
        return s;
    }
}