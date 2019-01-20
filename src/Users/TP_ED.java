package Users;

import java.io.FileNotFoundException;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL LOPES DOS SANTOS Numero:8170170 Turma:LEI
 */
public class TP_ED {

    public static void main(String[] args) throws FileNotFoundException {

        User[] user = JsonReader.UserReader("SocialGraph.json");
        LoadGraph g1 = new LoadGraph();
        g1.LoadVertex(user);
        g1.LoadEdges();

        System.out.println(g1.toString());
        System.out.println(g1.GraphTable());
        System.out.println(g1.NetworkTable());


       
    }

}
