package Users;

import java.util.Arrays;

/**
 * Nome: DAVID ALEXANDRE FREIRE DOS SANTOS Numero:8170138 Turma:LSIRC
 *
 * Nome: GABRIEL LOPES DOS SANTOS Numero:8170170 Turma:LEI
 */
public class User {

    private int id;
    private String nome, email;
    private int idade;
    private String[] skills;
    formacaoAcademica[] fa;
    cargosProfissionais[] cp;
    private int[] contactos;
    private int[] mencoes;
    private int visualizacoes;

    public int[] getContactos() {
        return contactos;
    }

    public void setContactos(int[] contactos) {
        this.contactos = contactos;
    }

    public int[] getMencoes() {
        return mencoes;
    }

    public void setMencoes(int[] mencoes) {
        this.mencoes = mencoes;
    }

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

    public formacaoAcademica[] getFA() {
        return fa;
    }

    public void setFA(formacaoAcademica[] fa) {
        this.fa = fa;
    }

    public cargosProfissionais[] getCP() {
        return cp;
    }

    public void setCP(cargosProfissionais[] cp) {
        this.cp = cp;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(int visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    @Override
    public String toString() {
        return "User: " + id
                + "\n\t|Nome: " + nome
                + "\n\t|Email: " + email
                + "\n\t|Idade: " + idade
                + "\n\t|Skills: " + Arrays.toString(skills)
                + "\n\t|Formação Académica: " + Arrays.toString(fa)
                + "\n\t|Cargos Profissionais: " + Arrays.toString(cp)
                + "\n\t|Contactos: " + Arrays.toString(contactos)
                + "\n\t|Menções: " + Arrays.toString(mencoes)
                + "\n\t|Visualizações: " + visualizacoes;
    }
}

class formacaoAcademica {

    private int ano;
    private String formacao;

    public formacaoAcademica(int ano, String formacao) {
        this.ano = ano;
        this.formacao = formacao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    @Override
    public String toString() {
        return "Ano: " + ano + ", Formação: " + formacao;
    }
}

class cargosProfissionais {

    private int ano;
    private String cargo, empresa;

    public cargosProfissionais(int ano, String cargo, String empresa) {
        this.ano = ano;
        this.cargo = cargo;
        this.empresa = empresa;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "Ano: " + ano + ", Cargo: " + cargo + ", Empresa: " + empresa;
    }
}
