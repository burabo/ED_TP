package Users;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL Numero: Turma:LEI
 */

public class JsonReader {

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

                // LER INTEIROS E STRINGS
                tmpUser.setId(Integer.parseInt(person.get("id").toString()));
                tmpUser.setNome(person.get("nome").toString());
                tmpUser.setIdade(Integer.parseInt(person.get("idade").toString()));
                tmpUser.setEmail(person.get("email").toString());
                tmpUser.setVisualizacoes(Integer.parseInt(person.get("visualizacoes").toString()));

                // LER FORMACAO ACADEMICA
                JSONArray jsonFormacoes = (JSONArray) person.get("formacaoAcademica");
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

                // LER CARGOS PROFICIONAIS
                JSONArray jsonCargos = (JSONArray) person.get("cargosProfissionais");
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

                // LER SKILLS
                JSONArray jsonSkills = (JSONArray) person.get("skills");
                String[] tmpSkills = new String[jsonSkills.size()];
                for (j = 0; j < jsonSkills.size(); j++) {
                    tmpSkills[j] = jsonSkills.get(j).toString();
                }
                tmpUser.setSkills(tmpSkills);

                // LER CONTACTOS
                JSONArray jsonContacts = (JSONArray) person.get("contacts");
                int[] tmpContacts = new int[jsonContacts.size()];
                for (j = 0; j < jsonContacts.size(); j++) {
                    JSONObject tmpContact = (JSONObject) jsonContacts.get(j);
                    tmpContacts[j] = Integer.parseInt(tmpContact.get("userid").toString());
                }
                tmpUser.setContactos(tmpContacts);

                // LER MENCOES
                JSONArray jsonMencoes = (JSONArray) person.get("mencoes");
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
            Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return toReturn;
    }
}
