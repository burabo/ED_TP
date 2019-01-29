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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import static java.lang.System.in;
import java.util.Iterator;
import java.util.Scanner;

public class Interface {

    Scanner scanner = new Scanner(System.in);
    Scanner antiCrash = new Scanner(System.in);
    JButton button;
    JComboBox combo;

    public static void main(String[] args) throws FileNotFoundException {

        User[] user = UsersManagement.UserReader("SocialGraph.json");
        UsersManagement g1 = new UsersManagement();
        g1.LoadVertex(user);
        g1.LoadEdges();
        Interface in = new Interface(user, g1);
    }

    public Interface(User[] user, UsersManagement g1) {
        String options[] = {"Seleccione a opção pretendida", "Consultar informação relativa a um utilizador",
            "Editar ligações", "Editar visualizações", "É completo?", "É conexo?", "toString", "Matriz de Adjacência", "Matriz de Adjacência Network",
            "iterator BFS", "iterator DFS", "Iterator Shortest Path Weight", "Is Connected?",
            "Is Network Complete?"};
        JFrame frame = new JFrame("Menu");
        JPanel panel = new JPanel();
        combo = new JComboBox(options);
        button = new JButton("OK");
        combo.setBackground(Color.white);
        combo.setForeground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 150);
        frame.setVisible(true);
        panel.add(combo);
        panel.add(button);
        frame.add(panel);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = (String) combo.getSelectedItem();
                switch (str) {
                    case "Consultar informação relativa a um utilizador":
                        System.out.println("Introduza o email do utilizador: ");
                        antiCrash.close();
                        String email = scanner.nextLine();
                        g1.LoadProfile(user, email);
                        break;

                    case "Editar ligações":
                        g1.EditEdge(user[0], user[1], user[2], user[3], 2);
                        System.out.println("Ligação editada");
                        break;

                    case "Editar visualizações":
                        System.out.println("Introduza o email do utilizador: ");
//                        antiCrash.close();
//                        scanner.next();
                        g1.editViews(user[0]);
                        break;

                    case "É completo?":
                        System.out.println("Is complete? " + g1.IsNetworkComplete());
                        break;

                    case "É conexo?":
                        System.out.println("\nIs connected? " + g1.isConnected());
                        break;

                    case "Matriz de Adjacência":
                        System.out.println(g1.GraphTable());
                        break;
                    case "Matriz de Adjacência Network":
                        System.out.println(g1.NetworkTable());
                        break;
                    case "toString":
                        System.out.println(g1.toString());
                        break;

                    case "iterator BFS":
                        System.out.println("\nIterator BFS\n");
                        Iterator bfs = g1.iteratorBFS(user[0]);
                        while (bfs.hasNext()) {
                            System.out.println(bfs.next());
                        }
                        break;

                    case "iterator DFS":
                        System.out.println("\nIterator DFS\n");
                        Iterator dfs = g1.iteratorDFS(user[0]);
                        while (dfs.hasNext()) {
                            System.out.println(dfs.next());
                        }
                        break;

                    case "Iterator Shortest Path Weight":
                        Iterator sp = g1.iteratorShortestPath(user[0], user[4]);
                        while (sp.hasNext()) {
                            System.out.println(sp.next());
                        }
                        break;

                }

            }
        });

    }
}
