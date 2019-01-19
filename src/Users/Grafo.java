package Users;

import LinkedBinaryTree.Network;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL LOPES DOS SANTOS Numero:8170170 Turma:LEI
 */
public class Grafo extends Network {

    public void LoadVertex(User[] pessoas) {
        for (int i = 0; i < pessoas.length; i++) { //adiciona os vértices
            this.addVertex(pessoas[i]);
        }
    }
    //!!!!!!!!!!!!!!!!!!!!!!
//fui fazendo alterações na classe Network em todos os métodos relacionados com o addEdge para funcionar, olha e vê se falta alguma coisa pq continua sem dar.
    public void LoadEdges() {
        this.NetworkMatrix = new double[15][15];
        System.out.println(this.numVertices);
        Object[] vetor_vertices = this.vertices;
        for (int c = 0; c < vetor_vertices.length; c++) {
            User user1 = (User) vetor_vertices[c];
            for (int v = 0; v < vetor_vertices.length; v++) {
                User user2 = (User) vetor_vertices[v];
                if (c < v) {
                    if (user1 != null) {
                        for (int k : user1.getContactos()) {
                            if (user2 != null) {
                                if (k == user2.getId()) {
                                    //this.addEdge(user1, user2); //se fizer este ele cria a matriz de adjacencia direito, no de baixo não cria, tenta dar fix
                                    this.addEdge(user1, user2, ((double) 1 / (double) user1.getVisualizacoes())); //quero passar o peso de cada aresta
                                    this.addEdge(user2, user1, ((double) 1 / (double) user2.getVisualizacoes()));
                                    System.out.println(this.adjMatrix[c][v]);
                                    System.out.println(this.NetworkMatrix[c][v]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {

        String toReturn = "Matriz de Adjacência:\n";
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
