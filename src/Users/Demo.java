package Users;

import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL LOPES DOS SANTOS Numero:8170170 Turma:LEI
 */
public class Demo {

    public static void main(String[] args) throws FileNotFoundException {

        User[] user = UsersManagement.UserReader("SocialGraph.json");
        UsersManagement g1 = new UsersManagement();
        g1.LoadVertex(user);
        g1.LoadEdges();

        System.out.println(g1.toString());
        System.out.println(g1.GraphTable());
        System.out.println(g1.NetworkTable());

        System.out.println("\nIterator DFS\n");
        Iterator a = g1.iteratorDFS(user[0]);
        while (a.hasNext()) {
            System.out.println(a.next());
        }
        System.out.println(g1.isConnected());

    }

}
