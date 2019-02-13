package Users;

import LinkedBinaryTree.ArrayUnorderedList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                        for (int k : user1.getMencoes()) {
                            if (user2 != null) {
                                if (k == user2.getId()) {
                                    this.addEdge(user1, user2, ((double) 1 / (double) user1.getMencoes().length));
                                    this.addEdge(user2, user1, ((double) 1 / (double) user2.getMencoes().length));
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

    public void editMencoes(User[] users) throws IOException {
        user = searchEmail(users);
        if (user == null) {
            System.out.println("O email não existe.");
        } else {
            int valor;
            int[] novasMencoes;
            System.out.println("Actuais Menções: " + Arrays.toString(user.getMencoes()));
            System.out.println("Insira o número de menções que deseja inserir: ");
            valor = scanner.nextInt();
            novasMencoes = new int[valor];
            for (int i = 0; i < valor; i++) {
                System.out.println("Insira a menção no campo " + i);
                novasMencoes[i] = scanner.nextInt();
            }
            user.setMencoes(novasMencoes);
            JSONObject obj = new JSONObject();
            JSONArray array_obj = new JSONArray();
            for (int i = 0; i < user.getMencoes().length; i++) {
                obj.put("Menção nº " + i + ": ", user.getMencoes()[i]);

            }
            array_obj.add(obj);
            Files.write(Paths.get("mencoes_alteradas.txt"), array_obj.toJSONString().getBytes());
        }
    }

    public void editViews(User[] users) throws IOException {
        user = searchEmail(users);
        if (user == null) {
            System.out.println("O email não existe.");
        } else {
            int valor;
            System.out.println("Actual Visualizações: " + user.getVisualizacoes());
            System.out.println("Insira o novo valor do campo Visualizações: ");
            valor = scanner.nextInt();
            user.setVisualizacoes(valor);
            /*JSONArray array_obj = new JSONArray();
            for (User user1 : users) {
                array_obj.add(user.getVisualizacoes());
            }
            Files.write(Paths.get("visualizacoes_alteradas.txt"), array_obj.toJSONString().getBytes());*/

        }

    }

    public void print(User[] users) throws IOException {
        JSONObject obj = new JSONObject();
        JSONObject obj_geral = new JSONObject();
        JSONObject obj_fa = new JSONObject();
        JSONArray array_obj = new JSONArray();

        for (int i = 0; i < users.length; i++) {
            obj.put("id: ", users[i].getId());
            obj.put("nome: ", users[i].getNome());
            obj.put("idade: ", users[i].getIdade());
            obj.put("email: ", users[i].getEmail());
            System.out.println(users[i].getFA().length);
            for (int x = 0; x < users[i].getFA().length; x++) {
                //array_obj_1.add("ano: " + users[i].fa[x].getAno() + ", formacao: "+ users[i].fa[x].getFormacao() );

                obj_fa.put("ano: ", users[i].fa[x].getAno());
                obj_fa.put("formacao : ", users[i].fa[x].getFormacao());
            }

            /*  for(int x  = 0; x < users[i].getCP().length; x++){
               obj.put("ano : ", users[i].cp[i].getAno()); 
               obj.put("cargo : ", users[i].cp[i].getCargo()); 
               obj.put("empresa : ", users[i].cp[i].getEmpresa());
            }
            for(int x  = 0; x < users[i].getSkills().length; x++){
               obj.put("skills : ", users[i].getSkills()[x]); 
            }
            for(int x  = 0; x < users[i].getContactos().length; x++){
               obj.put("userid : ", users[i].getContactos()[x]); 
            }
            for(int x  = 0; x < users[i].getMencoes().length; x++){
               obj.put("userid : ", users[i].getMencoes()[x]); 
            }
             */
            obj.put("visualizacoes: ", users[i].getVisualizacoes());
            obj_geral.put("grafoSocial", obj);
            obj_geral.put("formacaoAcademica ", obj_fa);
            array_obj.add(obj_geral);

        }

        Files.write(Paths.get("teste.txt"), array_obj.toJSONString().getBytes());
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
        String formacoes, email;
        String[] skill = new String[5];
        Scanner kb = new Scanner(System.in);
        Scanner kb_skill = new Scanner(System.in);
        int count = 0, escolha = 0;
        System.out.println("Introduza o email do utilizador: ");
        email = scanner.next();
        for (int i = 0; i < user.length; i++) {
            if (user[i].getEmail().equals(email)) {
                count++;
            }
        }
        if (count == 0) {
            System.out.println("O email não existe.");
        }

        for (int i = 0; i < user.length; i++) {
            if (user[i].getEmail().equals(email)) {
                do {
                    System.out.print("Pretende inserir skills(1) ou formações académicas(2)? ");
                    escolha = kb.nextInt();
                } while (!(escolha == 1 || escolha == 2));
                if (escolha == 1) {
                    int num_skills = 0;
                    do {
                        System.out.print("Quantas skills pretende procurar? ");
                        num_skills = kb_skill.nextInt();
                    } while (num_skills <= 0);
                    for (int m = 0; m < num_skills; m++) {
                        System.out.print("Introduza a skill pretendida: ");
                        skill[m] = scanner.next();
                    }
                    for (int j = 0; j < adjMatrix.length; j++) {
                        if (adjMatrix[i][j] == true) {
                            for (int k = 0; k < user[j].getSkills().length; k++) {
                                for (int n = 0; n < num_skills; n++) {
                                    if (user[j].getSkills()[k].equals(skill[n])) {
                                        System.out.println("Utilizador encontrado! " + user[j]);
                                    }
                                }

                            }
                        }
                    }
                } else if (escolha == 2) {
                    System.out.print("Introduza a formação académica pretendida: ");
                    formacoes = scanner.next();
                    for (int j = 0; j < adjMatrix.length; j++) {
                        if (adjMatrix[i][j] == true) {
                            for (int l = 0; l < user[j].getFA().length; l++) {
                                if (user[j].fa[l].getFormacao().equals(formacoes)) {
                                    System.out.println("Utilizador encontrado! " + user[j]);
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
            } else {
                return null;
            }
        }
        return null;
    }
}
