package Users;

import pt01.ex04.Network;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL LOPES DOS SANTOS Numero:8170170 Turma:LEI
 */
public class LoadGraph extends Network {

    public void LoadVertex(User[] pessoas) {
        for (User pessoa : pessoas) { //adiciona os vértices
            this.addVertex(pessoa);
        }
    }

    public void LoadEdges() {
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
                                    this.addEdge(user1, user2);
                                    this.addEdge(user1, user2, ((double) 1 / (double) user1.getVisualizacoes())); 
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    
  
}
