package Users;

import Exceptions.EmptyCollectionException;
import Exceptions.ListEmptyException;
import Exceptions.QueueEmptyException;
import Interfaces.NetworkADT;
import LinkedBinaryTree.ArrayUnorderedList;
import LinkedBinaryTree.Graph;
import LinkedBinaryTree.LinkedQueue;
import LinkedBinaryTree.LinkedStack;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL LOPES DOS SANTOS Numero:8170170 Turma:LEI
 */
public class UsersNetwork<T> implements NetworkADT<T> {

    protected final int DEFAULT_CAPACITY = 2;
    protected int count; //used in DFS
    protected int numVertices;   // number of vertices in the graph
    protected boolean[][] adjMatrix;   // adjacency matrix
    protected double[][] NetworkMatrix;   // Network matrix
    protected T[] vertices;   // values of vertices

    public UsersNetwork() {
        count = 0;
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.NetworkMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    @Override
    public void addEdge(Object vertex1, Object vertex2, double weight) { //grafo pesado / network
        addEdge(getIndex((T) vertex1), getIndex((T) vertex2), weight);
    }

    private void addEdge(int index1, int index2, double weight) { //grafo pesado / network
        if (indexIsValidNetwork(index1) && indexIsValidNetwork(index2)) {
            adjMatrix[index1][index2] = true;
            NetworkMatrix[index1][index2] = weight;

        }
    }

    @Override
    public void addEdge(Object vertex1, Object vertex2) { //grafo
        addEdge(getIndex((T) vertex1), getIndex((T) vertex2));
    }

    private void addEdge(int index1, int index2) { //grafo
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
        }
    }

    private int getIndex(T vertex1) {
        int i = 0;
        boolean hasFound = false;
        while (i < vertices.length && hasFound == false) {
            if (vertex1.equals(vertices[i])) {
                hasFound = true;
            } else {
                i++;
            }
        }
        return i;
    }

    private boolean indexIsValidNetwork(int index) {
        return (index < NetworkMatrix.length && index >= 0);
    }

    private boolean indexIsValid(int index) {
        return (index < adjMatrix.length && index >= 0);
    }

    @Override
    public double shortestPathWeight(Object vertex1, Object vertex2) { // Já feito noutro método
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    @Override
    public void removeVertex(T vertex) {
        int index = getIndex(vertex);
        vertices[index] = null;
    }

    /*
    private boolean indexIsValid(int index) {
        return (index < adjMatrix.length && index >= 0);
    }
     */
    @Override
    public void removeEdge(Object vertex1, Object vertex2) {
        removeEdge(getIndex((T) vertex1), getIndex((T) vertex2));
    }

    private void removeEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
            NetworkMatrix[index1][index2] = 0;
            NetworkMatrix[index2][index1] = 0;
        }
    }

    @Override
    public Iterator iteratorBFS(T startVertex) {
        try {
            return iteratorBFS(getIndex(startVertex));
        } catch (QueueEmptyException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Iterator iteratorBFS(int startIndex) throws QueueEmptyException {

        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(new Integer(startIndex));
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x.intValue()]);
            /**
             * Find all vertices adjacent to x that have not been visited and
             * queue them up
             */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalQueue.enqueue(new Integer(i));
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) {
        try {
            return iteratorDFS(getIndex(startVertex));
        } catch (EmptyCollectionException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Iterator iteratorDFS(int startIndex) throws EmptyCollectionException {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = new boolean[numVertices];
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
        traversalStack.push(new Integer(startIndex));
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;
        count++;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;
            /**
             * Find a vertex adjacent to x that has not been visited and push it
             * on the stack
             */
            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalStack.push(new Integer(i));
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    count++;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    @Override
    public Iterator iteratorShortestPath(Object startVertex, Object targetVertex) {
        try {
            return iteratorShortestPath(getIndex((T) startVertex), getIndex((T) targetVertex));
        } catch (ListEmptyException ex) {
            Logger.getLogger(UsersNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Iterator iteratorShortestPath(int startVertex, int targetVertex) throws ListEmptyException { //FALTA FAZER
        int i = 0, j = 0;
        //Guarda os vértices pertencentes ao menor caminho encontrado
        ArrayUnorderedList<Double> menorCaminho = new ArrayUnorderedList<>();
        //Guarda os vértices que ainda não foram visitados
        ArrayUnorderedList<T> naoVisitados = new ArrayUnorderedList<>();
        //Guarda as distâncias
        double distancia[] = new double[numVertices];
        // Variavel que recebe os vertices pertencentes ao menor caminho
        int verticeCaminho = startVertex;
        // Variavel que guarda o vertice que esta a ser visitado
        int actual;
        // Variavel que marca o vizinho do vertice atualmente visitado
        int vizinho;
        double menor = 9999;
        boolean found;

        //Iguala todos os elementos de distânica a 0
        for (i = 0; i < numVertices; i++) {
            distancia[i] = 0;
        }

        //Adiciona todos os elementos à lista dos não visitados
        for (i = 0; i < vertices.length; i++) {
            naoVisitados.addToRear(vertices[i]);
        }

        //naoVisitados.remove(vertices[startVertex]);
        //Enquanto todos os vértices não tiverem sido visitados...
        while (naoVisitados.contains(vertices[targetVertex])) {

            actual = verticeCaminho;

            for (i = 0; i < numVertices; i++) {
                if (NetworkMatrix[actual][i] != 0 && naoVisitados.contains(vertices[i])) {
                    vizinho = getIndex(vertices[i]);
                    if (NetworkMatrix[actual][i] < menor) {
                        menor = NetworkMatrix[actual][i];
                        verticeCaminho = vizinho;
                        System.out.println("Vértice actual:" + actual);

                    }
                    menorCaminho.addToRear(menor);
                    distancia[verticeCaminho] += menor;
                    menor = 9999;
                    naoVisitados.remove(vertices[verticeCaminho]);
                }
            }

        }
        System.out.println("Vértice actual:" + targetVertex);
        double num = 0;
        for (i = 0; i < numVertices; i++) {
            num += distancia[i];
        }
        System.out.println("Soma: " + num);
        return menorCaminho.iterator();
    }

    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }

    @Override
    public boolean isConnected() {
        
        int visited[] = new int[adjMatrix.length];

        for (int row = 0; row < adjMatrix.length; row++) {
            for (int col = 0; col < adjMatrix.length; col++) {

                if (adjMatrix[row][col] == true && visited[row] == 0) {
                    visited[row] = 1;
                }

            }
        }

        boolean connected = false;

        for (int vertex = 0; vertex < adjMatrix.length; vertex++) {
            if (visited[vertex] == 1) {
                connected = true;
            } else {
                connected = false;
                break;
            }
        }

        return connected;
    }

    @Override
    public int size() {
        return numVertices;
    }

    public boolean IsNetworkComplete() {
        int count = 0;
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                if (adjMatrix[i][j] == true) {
                    count++;
                }
            }
        }
        return count == (this.numVertices * this.numVertices);

    }

    protected void expandCapacity() {
        T[] newVertices = (T[]) (new Object[vertices.length + 1]);
        boolean[][] newAdjMatrix = new boolean[adjMatrix.length + 1][adjMatrix.length + 1];
        double[][] newNetworkMatrix = new double[NetworkMatrix.length + 1][NetworkMatrix.length + 1];

        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = vertices[i];

        }

        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                newAdjMatrix[i][j] = adjMatrix[i][j];
            }
        }

        for (int i = 0; i < NetworkMatrix.length; i++) {
            for (int j = 0; j < NetworkMatrix.length; j++) {
                newNetworkMatrix[i][j] = NetworkMatrix[i][j];
            }
        }

        vertices = newVertices;
        adjMatrix = newAdjMatrix;
        NetworkMatrix = newNetworkMatrix;
    }

    @Override
    public String toString() {

        System.out.println("\nVertices:\n");
        for (int i = 0; i < vertices.length; i++) {
            System.out.println("Position " + i + ": " + vertices[i]);
        }

        System.out.println("\nNetworkMatrix:\n");
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                System.out.println("Position " + i + j + ": " + NetworkMatrix[i][j]);
            }
        }

        return null;
    }

    public String NetworkTable() {
        BigDecimal bd;
        String toReturn = "\nMatriz de Adjacência Network:\n\n";
        toReturn += " \t";
        for (int x = 0; x < NetworkMatrix.length; x++) {
            toReturn += "  " + x + "   |  ";
        }
        toReturn += "\n\n";
        for (int i = 0; i < NetworkMatrix.length; i++) {

            toReturn += "" + i + "\t";
            for (int j = 0; j < NetworkMatrix.length; j++) {
                if (Double.isNaN(NetworkMatrix[i][j]) || Double.isInfinite(NetworkMatrix[i][j])) {
                    toReturn += NetworkMatrix[i][j] + " |  ";
                } else {
                    bd = new BigDecimal(NetworkMatrix[i][j]).setScale(3, RoundingMode.HALF_EVEN);
                    toReturn += bd + " |  ";
                }

            }
            toReturn += "\n";

        }

        return toReturn;
    }

    public String GraphTable() {

        String toReturn = "\nMatriz de Adjacência:\n\n";
        toReturn += " \t";
        for (int x = 0; x < this.numVertices; x++) {
            toReturn += x + " ";
        }
        toReturn += "\n\n";
        for (int i = 0; i < this.numVertices; i++) {

            toReturn += "" + i + "\t";
            for (int j = 0; j < this.numVertices; j++) {

                if (adjMatrix[i][j] == true) {
                    toReturn += "1 ";
                } else {
                    toReturn += "0 ";
                }
            }
            toReturn += "\n";

        }
        return toReturn;

    }

}
