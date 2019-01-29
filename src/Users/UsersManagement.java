package Users;

import LinkedBinaryTree.ArrayOrderedList;
import LinkedBinaryTree.ArrayUnorderedList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL LOPES DOS SANTOS Numero:8170170 Turma:LEI
 */
public class UsersManagement<T> extends UsersNetwork<T> {

    Scanner scanner = new Scanner(System.in);
    User user;

    public static User[] UserReader(String name) throws FileNotFoundException {
        JSONParser parser = new JSONParser();
        User[] toReturn = null;

        try {
            Object obj = parser.parse(new FileReader(name));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray users = (JSONArray) jsonObject.get("grafoSocial");
            toReturn = new User[users.size()];

            for (int i = 0; i < users.size(); i++) {
                int j;
                User tmpUser = new User();
                JSONObject person = (JSONObject) users.get(i);

                tmpUser.setId(Integer.parseInt(person.get("id").toString()));
                tmpUser.setNome(person.get("nome").toString());
                tmpUser.setIdade(Integer.parseInt(person.get("idade").toString()));
                tmpUser.setEmail(person.get("email").toString());
                tmpUser.setVisualizacoes(Integer.parseInt(person.get("visualizacoes").toString()));

                JSONArray jsonFormacoes = (JSONArray) person.get("formacaoAcademica"); //Formação Académica
                formacaoAcademica[] tmpFormacoes = new formacaoAcademica[jsonFormacoes.size()];
                for (j = 0; j < jsonFormacoes.size(); j++) {
                    JSONObject tmpFormacao = (JSONObject) jsonFormacoes.get(j);
                    formacaoAcademica fa = new formacaoAcademica(
                            Integer.parseInt(tmpFormacao.get("ano").toString()),
                            tmpFormacao.get("formacao").toString()
                    );
                    tmpFormacoes[j] = fa;
                }
                tmpUser.setFA(tmpFormacoes);

                JSONArray jsonCargos = (JSONArray) person.get("cargosProfissionais"); //Cargos Profissionais
                cargosProfissionais[] tmpCargos = new cargosProfissionais[jsonCargos.size()];
                for (j = 0; j < jsonCargos.size(); j++) {
                    JSONObject tmpCargo = (JSONObject) jsonCargos.get(j);
                    cargosProfissionais cp = new cargosProfissionais(
                            Integer.parseInt(tmpCargo.get("ano").toString()),
                            tmpCargo.get("cargo").toString(),
                            tmpCargo.get("empresa").toString()
                    );
                    tmpCargos[j] = cp;
                }
                tmpUser.setCP(tmpCargos);

                JSONArray jsonSkills = (JSONArray) person.get("skills"); //skills
                String[] tmpSkills = new String[jsonSkills.size()];
                for (j = 0; j < jsonSkills.size(); j++) {
                    tmpSkills[j] = jsonSkills.get(j).toString();
                }
                tmpUser.setSkills(tmpSkills);

                JSONArray jsonContacts = (JSONArray) person.get("contacts"); //contactos
                int[] tmpContacts = new int[jsonContacts.size()];
                for (j = 0; j < jsonContacts.size(); j++) {
                    JSONObject tmpContact = (JSONObject) jsonContacts.get(j);
                    tmpContacts[j] = Integer.parseInt(tmpContact.get("userid").toString());
                }
                tmpUser.setContactos(tmpContacts);

                JSONArray jsonMencoes = (JSONArray) person.get("mencoes"); //menções
                int[] tmpMencoes = new int[jsonMencoes.size()];
                for (j = 0; j < jsonMencoes.size(); j++) {
                    JSONObject tmpMencao = (JSONObject) jsonMencoes.get(j);
                    tmpMencoes[j] = Integer.parseInt(tmpMencao.get("userid").toString());
                }
                tmpUser.setMencoes(tmpMencoes);

                toReturn[i] = tmpUser;

            }
        } catch (IOException | ParseException ex) {
            System.out.println("Erro na leitura dos dados do ficheiro JSON");
            Logger.getLogger(UsersManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

        return toReturn;
    }

    public void LoadVertex(User[] pessoas) {
        for (User pessoa : pessoas) { //adiciona os vértices
            this.addVertex((T) pessoa);
        }
    }

    public void LoadEdges() {
        Object[] vetor_vertices = this.vertices;
        for (int c = 0; c < vetor_vertices.length; c++) {
            User user1 = (User) vetor_vertices[c];
            for (int v = 0; v < vetor_vertices.length; v++) {
                User user2 = (User) vetor_vertices[v];
                if (c < v) {
                    if (user1 != null) {
                        for (int k : user1.getContactos()) {
                            if (user2 != null) {
                                if (k == user2.getId()) {
                                    this.addEdge(user1, user2, ((double) 1 / (double) user1.getVisualizacoes()));
                                    this.addEdge(user2, user1, ((double) 1 / (double) user2.getVisualizacoes()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void LoadProfile(User[] users) {
        user = searchEmail(users);
        System.out.println("Id: " + user.getId());
        System.out.println("Nome: " + user.getNome());
        System.out.println("Idade: " + user.getIdade());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Formação Académica: " + Arrays.toString(user.getFA()));
        System.out.println("Cargos Profissionais: " + Arrays.toString(user.getCP()));
        System.out.println("Skills: " + Arrays.toString(user.getSkills()));
        System.out.println("Contactos: " + Arrays.toString(user.getContactos()));
        System.out.println("Menções: " + Arrays.toString(user.getMencoes()));
        System.out.println("Visualizações: " + user.getVisualizacoes());
    }

    public void editContacts(User[] users) {
        user = searchEmail(users);
        int valor;
        int[] novosContactos;
        System.out.println("Actual Contactos: " + Arrays.toString(user.getContactos()));
        System.out.println("Insira o número de contactos que deseja inserir: ");
        valor = scanner.nextInt();
        novosContactos = new int[valor];
        for (int i = 0; i < valor; i++) {
            System.out.println("Insira o contacto no campo " + i);
            novosContactos[i] = scanner.nextInt();
        }
        user.setContactos(novosContactos);
    }

    public void editViews(User[] users) {
        user = searchEmail(users);
        int valor;
        System.out.println("Actual Visualizações: " + user.getVisualizacoes());
        System.out.println("Insira o novo valor do campo Visualizações: ");
        valor = scanner.nextInt();
        user.setVisualizacoes(valor);

    }

    public void notReachable(User[] users) {
        String email;
        System.out.println("Introduza o email do utilizador: ");
        email = scanner.next();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getEmail().equals(email)) {
                for (int j = 0; j < adjMatrix.length; j++) {
                    if (adjMatrix[i][j] == false) {
                        System.out.println(vertices[j]);
                    }
                }

            }
        }
    }

    public void findUsers(User[] user) {
        String skill, empresa, email;
        System.out.println("Introduza o email do utilizador: ");
        email = scanner.next();
        for (int i = 0; i < user.length; i++) {
            if (user[i].getEmail().equals(email)) {
                System.out.println("Introduza a skill pretendida");
                skill = scanner.next();
                System.out.println("Introduza a empresa pretendida");
                empresa = scanner.next();
                for (int j = 0; j < adjMatrix.length; j++) {
                    if (adjMatrix[i][j] == true) {
                        for (int k = 0; k < user[j].getSkills().length; k++) {
                            if (user[j].getSkills()[k].equals(skill)) {
                                for (int l = 0; l < user[j].getCP().length; l++) {
                                    if (user[j].cp[l].getEmpresa().equals(empresa)) {
                                        System.out.println("Utilizador encontrado: " + user[j]);
                                    }
                                }
                            }

                        }

                    }
                }
            }

        }
    }

    public String findUsersThatWorkedInCompany(User[] users, User user, String empresa) {
        int i, j;

        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        for (i = 0; i < numVertices; i++) {
            for (j = 0; j < users[i].getCP().length; j++) {
                if (users[i].cp[j].getEmpresa().equals(empresa)) {
                    for (j = 0; j < users[i].getContactos().length; j++) {
                        if (users[i].getContactos()[j] == user.getId()) {
                            resultList.addToRear(vertices[i]);
                        }
                    }
                }
            }
        }
        return resultList.toString();
    }

    public void doWorkersConnect(User[] user, String empresa, String empresa2) {
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                for (int k = 0; k < user[i].cp.length; k++) {
                    for (int l = 0; l < user[j].cp.length; l++) {
                        if (user[i].cp[k].getEmpresa().equals(empresa) && user[j].cp[l].getEmpresa().equals(empresa2)
                                || user[j].cp[l].getEmpresa().equals(empresa) && user[i].cp[k].getEmpresa().equals(empresa2)) {
                            if (adjMatrix[i][j]) {
                                System.out.println("Utilizadores " + user[i].getEmail() + " e " + user[j].getEmail() + " relacionam-se");
                            } else {
                                System.out.println("Utilizadores " + user[i].getEmail() + " e " + user[j].getEmail() + " não se relacionam");
                            }
                        }
                    }
                }
            }
        }
    }

    public void connectSkill(User[] users, String skill) {
//
//        ArrayOrderedList<T> menorCaminho = new ArrayOrderedList<>();
//
//        for (int i = 0; i < numVertices; i++) {
//            for (int j = 0; j < users[i].getSkills().length; j++) {
//                if (users[i].getSkills()[j].equals(skill)) {
//                    menorCaminho.add(vertices[i]);
//                }
//            }
//        }
//            System.out.println(menorCaminho.toString());
    }

    public User searchEmail(User[] users) {
        String email;
        System.out.println("Introduza o email do utilizador: ");
        email = scanner.next();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getEmail().equals(email)) {
                return users[i];
            }
        }
        return null;
    }
}
