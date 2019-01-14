/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_ed;

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
 *
 * @author David Alexandre
 */
public class JsonReader {
    
    public static User[] UserReader(String path) throws FileNotFoundException{
     JSONParser parser = new JSONParser();
     User[] toReturn = null;
     try{
         Object obj = parser.parse(new FileReader(path));
         JSONObject jsonObject = (JSONObject) obj;
         JSONArray users = (JSONArray) jsonObject.get("grafoSocial");
         toReturn = new User[users.size()];
         
         for (int i = 0; i<users.size();i++){
             User tempUser = new User();
             JSONObject person = (JSONObject) users.get(i);
             tempUser.setId(Integer.parseInt(person.get("id").toString()));
             tempUser.setNome(person.get("nome").toString());
             tempUser.setIdade(Integer.parseInt(person.get("idade").toString()));
             tempUser.setEmail(person.get("email").toString());
         }
     }  catch (ParseException | IOException ex) {
            Logger.getLogger(JsonReader.class.getName()).log(Level.SEVERE, null, ex);   
     
    }

    return toReturn;
} 
}
