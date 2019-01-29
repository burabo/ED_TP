package Users;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
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

    public void LoadProfile(User[] pessoas, String email) {
        boolean cont = false;
        for (User pessoa : pessoas) {
            if (pessoa.getEmail().equals(email)) {
                System.out.println("Id: " + pessoa.getId());
                System.out.println("Nome: " + pessoa.getNome());
                System.out.println("Idade: " + pessoa.getIdade());
                System.out.println("Email: " + pessoa.getEmail());
                System.out.println("Formação Académica: " + Arrays.toString(pessoa.getFA()));
                System.out.println("Cargos Profissionais: " + Arrays.toString(pessoa.getCP()));
                System.out.println("Skills: " + Arrays.toString(pessoa.getSkills()));
                System.out.println("Contactos: " + Arrays.toString(pessoa.getContactos()));
                System.out.println("Menções: " + Arrays.toString(pessoa.getMencoes()));
                System.out.println("Visualizações: " + pessoa.getVisualizacoes());
                cont = true;
            }
        }
        if (!cont) {
            System.err.println("Email inexistente");
        }
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

    public String NetworkTable() {
        BigDecimal bd;
        String toReturn = "\nMatriz de Adjacência Network:\n\n";
        toReturn += " \t";
        for (int x = 0; x < NetworkMatrix.length; x++) {
            toReturn += "  " + x + "   |  ";
        }
        toReturn += "\n\n";
        for (int i = 0; i < NetworkMatrix.length; i++) {

            toReturn += "" + i + "\t";
            for (int j = 0; j < NetworkMatrix.length; j++) {
                bd = new BigDecimal(NetworkMatrix[i][j]).setScale(3, RoundingMode.HALF_EVEN);
                toReturn += bd + " |  ";

            }
            toReturn += "\n";

        }

        return toReturn;
    }

    public String GraphTable() {

        String toReturn = "\nMatriz de Adjacência:\n\n";
        toReturn += " \t";
        for (int x = 0; x < this.numVertices; x++) {
            toReturn += x + " ";
        }
        toReturn += "\n\n";
        for (int i = 0; i < this.numVertices; i++) {

            toReturn += "" + i + "\t";
            for (int j = 0; j < this.numVertices; j++) {

                if (adjMatrix[i][j] == true) {
                    toReturn += "1 ";
                } else {
                    toReturn += "0 ";
                }
            }
            toReturn += "\n";

        }
        return toReturn;

    }

    public String IsNetworkComplete() {
        int count = 0;
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                if (adjMatrix[i][j] == true) {
                    count++;
                }
            }
        }
        if (count == (this.numVertices * this.numVertices)) {
            return "Matriz de Adjacência completa";
        } else {
            return "Matriz de Adjacência Incompleta";
        }

    }

//    public void EditEdge(Object vertex1, Object vertex2, Object vertex3, Object vertex4) {
//        this.removeEdge(vertex1, vertex2);
//        this.addEdge(vertex3, vertex4);
//    }
    public void EditEdge(Object vertex1, Object vertex2, Object vertex3, Object vertex4, double weight) {
        this.removeEdge(vertex1, vertex2);
        this.addEdge(vertex3, vertex4, weight);
    }

    public void editViews(User user) {
        Scanner scanner = new Scanner(System.in);
        int valor;
        System.out.println("Actual Visualizações: " + user.getVisualizacoes());
        System.out.println("Insira o novo valor do campo Visualizações: ");
        valor = scanner.nextInt();
        user.setVisualizacoes(valor);

    }

    public void findUsers(User[] user) {
        Scanner scanner = new Scanner(System.in);
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
//                                for (int l = 0; l < user[j].getCP().length; l++) {
//                                    if (user[j].getFA()[l].equals(empresa)) {
                                        System.out.println("Utilizador encontrado: " + user[j]);
//                                    }
//                                }
                            }

                        }

                    }
                }
            }

        }
    }
}