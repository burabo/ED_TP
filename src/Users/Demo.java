package Users;

import java.io.FileNotFoundException;
import java.util.Arrays;
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
        //g1.djisktra();
        for(int i = 0; i < user.length;i++){
        System.out.println(Arrays.toString(user[i].getCP()).toString());    
        }
        user[0].getFA();
                //g1.EditEdge(0, 1, 2, 3,10); //ñ funciona
        //System.out.println(g1.toString());
        System.out.println(g1.GraphTable());
        System.out.println(g1.NetworkTable());
        System.out.println(g1.IsNetworkComplete());

        System.out.println("\nIterator BFS\n");
        Iterator bfs = g1.iteratorBFS(user[0]);
        while (bfs.hasNext()) {
            System.out.println(bfs.next());
        }

        System.out.println("\nIterator DFS\n");
        Iterator dfs = g1.iteratorDFS(user[0]);
        while (dfs.hasNext()) {
            System.out.println(dfs.next());
        }
        System.out.println("\nIs connected? " + g1.isConnected());

    }

}
