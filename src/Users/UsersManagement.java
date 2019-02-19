package Users;

import Exceptions.ListEmptyException;
import LinkedBinaryTree.ArrayOrderedList;
import com.google.gson.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
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
        }

    }

    public void print(User[] users) throws IOException {

        JSONArray array_obj = new JSONArray();

        System.out.println(users.length);
        for (int i = 0; i < users.length; i++) {
            JSONArray fa = new JSONArray();
            JSONArray cp = new JSONArray();
            JSONArray skills = new JSONArray();
            JSONArray contactos = new JSONArray();
            JSONArray mencoes = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("id", users[i].getId());
            obj.put("nome", users[i].getNome());
            obj.put("idade", users[i].getIdade());
            obj.put("email", users[i].getEmail());
            for (int x = 0; x < users[i].getFA().length; x++) {
                JSONObject obj_fa = new JSONObject();
                obj_fa.put("ano", users[i].fa[x].getAno());
                obj_fa.put("formacao", users[i].fa[x].getFormacao());
                fa.add(obj_fa);
            }
            obj.put("formacaoAcademica", fa);

            for (int x = 0; x < users[i].getCP().length; x++) {
                JSONObject obj_cp = new JSONObject();
                obj_cp.put("ano", users[i].cp[x].getAno());
                obj_cp.put("cargo", users[i].cp[x].getCargo());
                obj_cp.put("empresa", users[i].cp[x].getEmpresa());
                cp.add(obj_cp);
            }
            obj.put("cargosProfissionais", cp);

            for (int x = 0; x < users[i].getSkills().length; x++) {
                skills.add(users[i].getSkills()[x]);
            }
            obj.put("skills", skills);

            for (int x = 0; x < users[i].getContactos().length; x++) {
                JSONObject obj_contactos = new JSONObject();
                obj_contactos.put("userid", users[i].getContactos()[x]);
                contactos.add(obj_contactos);
            }
            obj.put("contacts", contactos);

            for (int x = 0; x < users[i].getMencoes().length; x++) {
                JSONObject obj_mencoes = new JSONObject();
                obj_mencoes.put("userid", users[i].getMencoes()[x]);
                mencoes.add(obj_mencoes);
            }
            obj.put("mencoes", mencoes);
            obj.put("visualizacoes", users[i].getVisualizacoes());
            array_obj.add(obj);
        }
        JSONObject final_obj = new JSONObject();
        final_obj.put("grafoSocial", array_obj);

        try (FileWriter file = new FileWriter("SocialGraph.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(final_obj.toJSONString());
            String prettyJsonString = gson.toJson(je);
            file.write(prettyJsonString);
            file.flush();
            System.out.println("Ficheiro escrito e atualizado com sucesso.");

        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void AvgViewsMencoes(User[] users) {
        int cont = 0, c = 0;
        String email;
        int media_views = 0, media_mencoes = 0;
        int media_views_geral = 0, media_mencoes_geral = 0;
        System.out.println("Introduza o email do utilizador: ");
        email = scanner.next();
        for (User user1 : users) {
            if (user1.getEmail().equals(email)) {
                cont++;
            }
        }
        if (cont == 0) {
            System.out.println("O email não existe.");
        } else {
            for (int i = 0; i < users.length; i++) {
                if (users[i].getEmail().equals(email)) {
                    for (int j = 0; j < adjMatrix.length; j++) {
                        if (adjMatrix[i][j] == true) {
                            media_views += users[j].getVisualizacoes();
                            media_mencoes += users[j].getMencoes().length;
                            c++;
                            //System.out.println(vertices[j]);
                        }
                    }

                }
                media_views_geral += users[i].getVisualizacoes();
                media_mencoes_geral += users[i].getMencoes().length;

            }
            System.out.println("Nº de utilizadores alcançáveis: " + c);
            System.out.println("Visualizações (utilizadores alcançáveis vs geral): " + media_views + " vs " + media_views_geral);
            System.out.println("Menções (utilizadores alcançáveis vs geral): " + media_mencoes + " vs " + media_mencoes_geral);
            System.out.println("Média Visualizações (utilizadores alcançáveis vs geral): " + media_views / c + " vs " + media_views_geral / users.length);
            System.out.println("Média Menções (utilizadores alcançáveis vs geral): " + media_mencoes / c + " vs " + media_mencoes_geral / users.length);
        }

    }

    public void FindSkillsFormacao(User[] user) {
        String formacoes, email;
        String[] skill = new String[5];
        Scanner kb = new Scanner(System.in);
        Scanner kb_skill = new Scanner(System.in);
        int cont = 0, escolha = 0;
        System.out.println("Introduza o email do utilizador: ");
        email = scanner.next();
        for (User user1 : user) {
            if (user1.getEmail().equals(email)) {
                cont++;
            }
        }
        if (cont == 0) {
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

    public void GetFormacoesAlcancaveis(User[] users) {
        String formacao, email;
        int cont = 0;
        System.out.println("Introduza o email do utilizador: ");
        email = scanner.next();
        for (int x = 0; x < users.length; x++) {
            if (users[x].getEmail().equals(email)) {
                cont++;
            }
        }
        if (cont == 0) {
            System.out.println("O email não existe.");
        } else {
            System.out.println("Introduza a formação do utilizador: ");
            formacao = scanner.next();

            for (int i = 0; i < users.length; i++) {
                if (users[i].getEmail().equals(email)) {
                    for (int j = 0; j < adjMatrix.length; j++) {
                        if (adjMatrix[i][j] == true) {
                            for (int k = 0; k < users[j].getFA().length; k++) {
                                if (users[j].fa[k].getFormacao().equals(formacao)) {
                                    System.out.println("Utilizador encontrado! " + users[j]);
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void GetEmpresasNaoRelacionadas(User[] users) {
        String empresaA, empresaB, cargo;
        System.out.println("Introduza o nome da empresa A: ");
        empresaA = scanner.next();
        System.out.println("Introduza o nome da empresa B: ");
        empresaB = scanner.next();
        System.out.println("Introduza o cargo que pretende: ");
        cargo = scanner.next();

        System.out.println("Utilizadores não relacionados: ");

        for (int i = 0; i < users.length; i++) {
            for (int k = 0; k < users[i].getCP().length; k++) {
                if (users[i].cp[k].getEmpresa().equals(empresaA) && users[i].cp[k].getCargo().equals(cargo)) {
                    for (int j = 0; j < users.length; j++) {
                        if (adjMatrix[i][j] == false) {
                            for (int l = 0; l < users[j].getCP().length; l++) {
                                if (users[j].cp[l].getEmpresa().equals(empresaB) && users[j].cp[l].getCargo().equals(cargo)) {
                                    System.out.println(users[i]);
                                    System.out.println(users[j]);
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    public ArrayOrderedList<User> connectFormacao(T id, String formacao) throws ListEmptyException {

        Iterator it = this.iteratorDFS(id);
        LinkedList<User> tmp = new LinkedList<>();
        ArrayOrderedList<User> toReturn = new ArrayOrderedList<>();
        while (it.hasNext()) {
            tmp.add((User) it.next());
        }
        for (int i = 0; i < tmp.size(); i++) {
            for (int j = 0; j < tmp.get(i).getFA().length; j++) {
                if (tmp.get(i).fa[j].getFormacao().equals(formacao)) {
                    toReturn.add(tmp.get(i));

                }
            }
        }
        System.out.println(toReturn.toString());
        return toReturn;
    }

    public User searchEmail(User[] users) {
        String email;
        int count = 0;
        System.out.println("Introduza o email do utilizador: ");
        email = scanner.next();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getEmail().equals(email)) {
                count++;
                return users[i];
            }
        }
        if (count == 0) {
            System.out.println("O email não existe.");
        }
        return null;
    }
}
