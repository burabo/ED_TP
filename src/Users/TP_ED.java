package Users;

import java.io.FileNotFoundException;
import pt01.ex04.OrientedNetwork;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL Numero: Turma:LEI
 */
public class TP_ED {

	/**
	 * @param args the command line arguments
	 * @throws java.io.FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO code application logic here
		User[] user = JsonReader.UserReader("SocialGraph.json");
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
			for (int j = 0; j < user[i].getMnameencoes().length; j++) {
				newNetwork.addEdge(user[i].getMnameencoes()[j], user[i], 1);
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
	}

}
