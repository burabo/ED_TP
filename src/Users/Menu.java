package Users;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL LOPES DOS SANTOS Numero:8170170 Turma:LEI
 */
import Exceptions.ListEmptyException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Menu {

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, IOException, ListEmptyException {

        User[] user = UsersManagement.UserReader("SocialGraph.json");
        UsersManagement g1 = new UsersManagement();
        g1.LoadVertex(user);
        g1.LoadEdges();
        Menu menu = new Menu(user, g1);
    }

    public Menu(User[] user, UsersManagement g1) throws IOException, ListEmptyException {

        User newUser;
        int option;
        String options[] = {"Consultar informação relativa a um utilizador",
            "Editar menções",
            "Editar visualizações",
            "O grafo é completo?",
            "O grafo é conexo?",
            "Descobrir o caminho mais curto entre 2 utilizadores",
            "Utilizadores alcançáveis a partir de um utilizador",
            "Utilizadores não alcançáveis a partir de um utilizador",
            "Utilizadores que têm determinadas skills ou formação académica",
            "Apresentar a média de menções e visualizações dos utilizadores alcançáveis VS geral",
            "Apresentar utilizadores alcançáveis que possuem uma determinada formação académica",
            "Determinar utilizadores que ocuparam um determinado cargo (não relacionados)",
            "Apresentar lista de utilizadores que contém uma determinada formação ordenada pelo menor custo de ligação",
            "Apresentar matriz de adjacência",
            "Apresentar matriz de custo"};

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
                case 2: //tópico 2
                    g1.editMencoes(user);
                    g1.print(user);
                    break;

                //Editar visualizações
                case 3: //tópico 2
                    g1.editViews(user);
                    g1.print(user);
                    break;

                //É completo?
                case 4:
                    System.out.println("O grafo é completo? " + g1.IsNetworkComplete());
                    break;

                //É conexo?
                case 5: //VALIDAR SE ESTÁ CERTO OU NÃO COM GRAFO CONEXO!
                    System.out.println("É conexo? " + g1.isConnected());
                    break;

                //Descobrir o caminho mais curto entre 2 utilizadores
                case 6: //tópico 4
                    String user1,
                     user2;
                    int i,
                     a = 0,
                     b = 0;

                    int cont = 0;
                    System.out.println("\nCusto de cada caminho: \n");
                    System.out.print("Email do utilizador de partida: ");
                    user1 = scanner.next();
                    System.out.print("Email do utilizador de destino: ");
                    user2 = scanner.next();
                    for (i = 0; i < user.length; i++) {
                        if (user[i].getEmail().equals(user1)) {
                            a = i;
                            cont++;
                        }
                    } 

                        for (i = 0; i < user.length; i++) {
                            if (user[i].getEmail().equals(user2)) {
                                b = i;
                                cont++;
                            }

                        }

                        if (cont != 2) {
                            System.out.print("Email/s não existe/existem ");
                        } else {
                            Iterator sp = g1.iteratorShortestPath(user[a], user[b]);
                            while (sp.hasNext()) {
                                System.out.println(sp.next());
                            }
                        }
                        break;

                        //Utilizadores alcançáveis a partir de um utilizador
                    
            
            case 7: //tópico 5
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
                case 8: //tópico 5-1
                    g1.notReachable(user);
                    break;

                //Utilizadores que têm determinadas skills ou formação académica
                case 9: //tópico 6
                    g1.FindSkillsFormacao(user);
                    break;

                //Apresentar a média de menções e visualizações dos utilizadores alcançáveis VS geral
                case 10: //tópico 7
                    g1.AvgViewsMencoes(user);
                    break;

                //Apresentar utilizadores alcançáveis que possuem uma determinada formação académica    
                case 11: //tópico 8
                    g1.GetFormacoesAlcancaveis(user);
                    break;

                //Determinar utilizadores que ocuparam um determinado cargo (não relacionados)
                case 12: //topico 9
                    g1.GetEmpresasNaoRelacionadas(user);
                    break;

                //Apresentar lista de utilizadores que contém uma determinada formação ordenada pelo menor custo de ligação
                case 13: //tópico 10
                    String formacao;
                    newUser = g1.searchEmail(user);
                    System.out.print("Introduza a formação pretendida: ");
                    formacao = scanner.next();
                    g1.connectFormacao(newUser, formacao);
                    break;

                //Matriz de Adjacência    
                case 14:
                    System.out.println(g1.GraphTable());
                    break;

                //Matriz de custo
                case 15:
                    System.out.println(g1.NetworkTable());
                    break;
            }

        } while (option != 0);
        }

    }
