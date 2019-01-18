package Users;

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

}

class formacaoAcademica {

    public formacaoAcademica(int ano, String formacao) {
        this.ano = ano;
        this.formacao = formacao;
    }

    private int ano;
    private String formacao;
}

class cargosProfissionais {

    public cargosProfissionais(int ano, String cargo, String empresa) {
        this.ano = ano;
        this.cargo = cargo;
        this.empresa = empresa;
    }

    private int ano;
    private String cargo, empresa;
}
