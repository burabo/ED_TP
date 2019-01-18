package Users;

import LinkedBinaryTree.Network;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL LOPES DOS SANTOS Numero:8170170 Turma:LEI
 */

public class Grafo extends Network {

    public void EdgeLoad(User[] pessoas){
        for(int i = 0; i < pessoas.length; i++){
            for(int j = 0; j < pessoas.length; j++){
                for(int k : pessoas[i].getContactos()){
                    if (k == pessoas[j].getId()) {
                        addEdge(pessoas[i], pessoas[j], (pessoas[i].getVisualizacoes()));
                    }
                }
            }
        }
        System.out.println(this.numVertices);
        
    }
    
    @Override
    public String toString() {
        
        String toReturn = "Matriz de Adjacência:\n" ;
        toReturn += " \t";
        for (int x = 0; x < this.numVertices; x++){
            toReturn += x + " ";
        }
        toReturn += "\n\n";
        for (int i = 0; i < this.numVertices; i++) {
            
            toReturn += "" + i + "\t";
            for (int j = 0; j < this.numVertices; j++) {
                if (adjMatrix[i][j] == true){
                    toReturn += "1 ";
                }else {
                    toReturn += "0 ";
                }
            }
            toReturn += "\n";
        }
        return toReturn;
    }
}
