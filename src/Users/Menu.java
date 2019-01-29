/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

/**
 *
 * @author gls
 */
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Menu {

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {

        User[] user = UsersManagement.UserReader("SocialGraph.json");
        UsersManagement g1 = new UsersManagement();
        g1.LoadVertex(user);
        g1.LoadEdges();
        Menu menu = new Menu(user, g1);
    }

    public Menu(User[] user, UsersManagement g1) {
        int option;
        String email;
        String options[] = {"Consultar informação relativa a um utilizador",
            "Editar ligações", "Editar visualizações", "É completo?", "É conexo?",
            "Utilizadores alcançáveis a partir de um utilizador",
            "Matriz de Adjacência", "Matriz de Adjacência Network"};

        do {
            do {
                for (int i = 0; i < options.length; i++) {
                    System.out.println((i + 1) + ". " + options[i]);
                }
                System.out.println("Introduza a opção pretendida: ");
                option = scanner.nextInt();
            } while (option < 1 && option > 20);
            switch (option) {
                //Consultar informação relativa a um utilizador
                case 1:
                    System.out.println("Introduza o email do utilizador: ");
                    email = scanner.next();
                    g1.LoadProfile(user, email);
                    break;
                //Editar ligações
                case 2:
                    g1.EditEdge(user[0], user[1], user[2], user[3], 2);
                    System.out.println("Ligação editada");
                    break;
                //Editar visualizações
                case 3:
                    System.out.println("Introduza o email do utilizador: ");
                    email = scanner.next();
                    for (User user1 : user) {
                        if (user1.getEmail().equals(email)) {
                            g1.editViews(user1);
                        }
                    }
                    break;

                //É completo?
                case 4:
                    System.out.println("Is complete? " + g1.IsNetworkComplete());
                    break;

                //É conexo?
                case 5:
                    System.out.println("\nIs connected? " + g1.isConnected());
                    break;

                //Utilizadores alcançáveis a partir de um utilizador
                case 6:
                    System.out.println("Introduza o email do utilizador: ");
                    email = scanner.next();
                    for (User user1 : user) {
                        if (user1.getEmail().equals(email)) {
                            System.out.println("\nIterator BFS\n");
                            Iterator bfs = g1.iteratorBFS(user1);
                            while (bfs.hasNext()) {
                                System.out.println(bfs.next());
                            }
                            System.out.println("\nIterator DFS\n");
                            Iterator dfs = g1.iteratorDFS(user1);
                            while (dfs.hasNext()) {
                                System.out.println(dfs.next());
                            }
                        }
                    }
                    break;

                //Utilizadores não alcançáveis a partir de um utilizador 
                case 7:
                    break;
                //Verificar a partir de um dado utilizador qual a lista...
                case 8:
                    g1.findUsers(user);
                    break;
                //Apresentar uma lista de utilizadores de uma empresa passada...
                case 9:
                    break;
                //Verificar que os utilizadores que ocupam um cargo numa empresa....
                case 10:
                    break;
                //Apresentar uma lista de utilizadores que contém um determinado skill...
                case 11:
                    break;
                //Matriz de Adjacência    
                case 12:
                    System.out.println(g1.GraphTable());
                    break;
                //Matriz de Adjacênica Network
                case 13:
                    System.out.println(g1.NetworkTable());
                    break;
            }

        } while (option != 0);
    }

}
