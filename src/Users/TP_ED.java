package Users;

import java.io.FileNotFoundException;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL LOPES DOS SANTOS Numero:8170170 Turma:LEI
 */
public class TP_ED {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        User[] user = JsonReader.UserReader("SocialGraph.json");
        Grafo g1 = new Grafo();
        for (int i = 0; i < user.length; i++) { //adiciona os vértices
            g1.addVertex(user[i]);
            if ((i + 1) < user.length) { //aqui ele liga cada vértice de valor i para i+1. É PRECISO PERCORRER AO CONTRARIO E AINDA ME FALTA PASSAR O PESO (VISUALIZAÇÕES) DÁ ME ERRO
                g1.addEdge(user[i], user[i+1]);
            } else {
                g1.addEdge(user[i], user[i + 1 - user.length]);
            }

        }

        g1.EdgeLoad(user);
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
