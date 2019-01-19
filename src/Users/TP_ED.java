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
        Grafo g1 = new Grafo();
        g1.LoadVertex(user);
        g1.LoadEdges();

        System.out.println(g1.toString());


        /*
                OrientedNetwork newNetwork = new OrientedNetwork();

		for (User user1 : user) {
			newNetwork.addVertex(user1.getId());
		}

		for (int i = 0; i < user.length; i++) {
			for (int j = 0; j < user[i].getContactos().length; j++) {
				newNetwork.addEdge(user[i], user[i].getContactos()[j], 1);
			}
		}

		for (int i = 0; i < user.length; i++) {
			for (int j = 0; j < user[i].getMencoes().length; j++) {
				newNetwork.addEdge(user[i].getMencoes()[j], user[i], 1);
			}
		}

//		System.out.println(user[0].getMnameencoes()[0]);
//		System.out.println(user[0].getMnameencoes()[1]);
//		System.out.println(user[0].getContactos()[0]);
//		System.out.println(user[0].getContactos()[1]);

		try {
			newNetwork.toString();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Exception");
		}
         */
    }

}
