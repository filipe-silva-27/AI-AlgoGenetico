import java.util.ArrayList;
import java.util.List;

public class Board implements Comparable<Board>{

    private List<Integer> board;
    public static final int SIZE = 8;
    private int fitness;

    public Board() {
        this.setBoard(new ArrayList<Integer>(SIZE));
        for (int i = 0; i < SIZE; i++) {
            this.getBoard().add( (int) (Math.random() * SIZE) );
        }
        this.calculateFitness();
    }

    public Board(List<Integer> firstGenes, List<Integer> secondGenes) {
        List<Integer> board = new ArrayList<>(SIZE);
        board.addAll(firstGenes);
        board.addAll(secondGenes);
        this.setBoard(board);
        this.calculateFitness();
    }

    public int calculateFitness(){

        //na coluna já sabemos que só existe 1 rainha. Mas para saber em linha, o board[] não pode ter repetidos. Ou seja para a 1a coluna vamos ver as outras todas se alguma tb tem uma rainha com o mesmo valor
        //assim: ver onde está a rainha da 1 coluna (cujo valor vai ser usado para ser pesquisado).
        //ir a 2a coluna (usar o j para não perder a 1a) e ver se o seu valor de board[] é igual ao valor de board[] com i.
        fitness = 0;
        for (int i = 0; i < SIZE-1; i++) {
            for (int j = i+1; j < SIZE; j++) {
                if (getBoard().get(i)!=getBoard().get(j) && Math.abs(getBoard().get(i)-getBoard().get(j))!=Math.abs(i-j)){
                    fitness++;
                }
            }
        }
        return fitness;
    }

    //Override toString() method
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (getBoard().get(i) == j) {
                    s += "Q ";
                } else {
                    s += ". ";
                }

            }
            s += "\n";
        }
        s += ("Fitness: " + this.getFitness() +"\n");
        return s;
    }


    @Override
    public int compareTo(final Board o) {
        return o.getFitness() - this.getFitness();
    }

    protected int getFitness() {
        return fitness;
    }

    public List<Integer> getBoard() {
        return board;
    }

    public void setBoard(List<Integer> board) {
        this.board = board;
    }
}

