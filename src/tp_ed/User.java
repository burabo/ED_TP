/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_ed;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author David Alexandre
 */
public class User {
    
    private int id;
    private String nome, email;
    private int idade;
    private String[] skills;
    formacaoAcademica[] fa;
    cargosProfissionais[] cp;
    mencoes[] mencoes;
    contacts[] contactos;
    private int visualizacoes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public formacaoAcademica[] getFa() {
        return fa;
    }

    public void setFa(formacaoAcademica[] fa) {
        this.fa = fa;
    }

    public cargosProfissionais[] getCp() {
        return cp;
    }

    public void setCp(cargosProfissionais[] cp) {
        this.cp = cp;
    }

    public mencoes[] getMencoes() {
        return mencoes;
    }

    public void setMencoes(mencoes[] mencoes) {
        this.mencoes = mencoes;
    }

    public contacts[] getContactos() {
        return contactos;
    }

    public void setContactos(contacts[] contactos) {
        this.contactos = contactos;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(int visualizacoes) {
        this.visualizacoes = visualizacoes;
    }
    
    
}

class formacaoAcademica {

    private int ano;
    private String formacao;
}

class cargosProfissionais {

    private int ano;
    private String cargo, empresa;
}

class mencoes {
    private int UserID;
}
class contacts {
    private int UserID;
}



