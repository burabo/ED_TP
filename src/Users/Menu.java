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

        User newUser;
        int option;
        String email;
        String options[] = {"Consultar informação relativa a um utilizador",
            "Editar ligações", "Editar visualizações", "É completo?", "É conexo?",
            "Descobrir o caminho mais curto entre 2 utilizadores",
            "Utilizadores alcançáveis a partir de um utilizador",
            "Utilizadores não alcançáveis a partir de um utilizador",
            "Verificar a partir de um dado utilizador qual a lista de utilizadoresque fazem parte dos contactos da lista que têm determinada skill e trabalham em determinada empresa",
            "Lista de utilizadores de uma empresa passada como parâmetro que estão relacionados com um utilizador também passado como parâmetro(Falta)",
            "Verificar que os utilizadores que ocupam um cargo numa empresa não estão relacionados com utilizadores de outras empresas(Falta)",
            "Lista de utilizadores que contém um determinado skill no seu perfil ordenado pelo menor custo de ligação(Falta)",
            "Matriz de Adjacência", "Matriz de Adjacência Network"};

        do {
            do {
                System.out.println("\n\n");
                for (int i = 0; i < options.length; i++) {
                    System.out.println((i + 1) + ". " + options[i]);
                }
                System.out.print("\nIntroduza a opção pretendida: ");
                option = scanner.nextInt();
            } while (option < 1 && option > 20);

            switch (option) {

                //Consultar informação relativa a um utilizador
                case 1:
                    g1.LoadProfile(user);
                    break;

                //Editar ligações
                case 2:
                    g1.editContacts(user);
                    break;
                //Editar visualizações

                case 3:
                    g1.editViews(user);
                    break;

                //É completo?
                case 4:
                    System.out.println("Is complete? " + g1.IsNetworkComplete());
                    break;

                //É conexo?
                case 5:
                    System.out.println("\nIs connected? " + g1.isConnected());
                    break;

                //Descobrir o caminho mais curto entre 2 utilizadores
                case 6:
                    System.out.println("\nCusto de cada caminho: \n");
                    Iterator sp = g1.iteratorShortestPath(user[11], user[1]);
                    while (sp.hasNext()) {
                        System.out.println(sp.next());
                    }
                    break;

                //Utilizadores alcançáveis a partir de um utilizador
                case 7:
                    newUser = g1.searchEmail(user);
                    System.out.println("\nIterator BFS\n");
                    Iterator bfs = g1.iteratorBFS(newUser);
                    while (bfs.hasNext()) {
                        System.out.println(bfs.next());
                    }
                    System.out.println("\nIterator DFS\n");
                    Iterator dfs = g1.iteratorDFS(newUser);
                    while (dfs.hasNext()) {
                        System.out.println(dfs.next());
                    }
                    break;

                //Utilizadores não alcançáveis a partir de um utilizador 
                case 8:
                    g1.notReachable(user);
                    break;

                //Verificar a partir de um dado utilizador qual a lista de utilizadores que fazem parte dos contactos
                //da lista que têm determinada skill e trabalham em determinada empresa
                case 9:
                    g1.findUsers(user);
                    break;

                //Apresentar uma lista de utilizadores de uma empresa passada...
                case 10:
                    g1.findUsersThatWorkedInCompany(user);
                    break;
                //Verificar que os utilizadores que ocupam um cargo numa empresa....
                case 11:
                    break;
                //Apresentar uma lista de utilizadores que contém um determinado skill...
                case 12:
                    break;
                //Matriz de Adjacência    
                case 13:
                    System.out.println(g1.GraphTable());
                    break;
                //Matriz de Adjacênica Network
                case 14:
                    System.out.println(g1.NetworkTable());
                    break;
            }

        } while (option != 0);
    }

}
