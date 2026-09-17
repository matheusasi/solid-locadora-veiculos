package br.com.locadora.dominio;

public class Cliente {

    private final String nome;
    private final String cpf;
    private final String email;
    private final String telefone;
    private final boolean fidelidade;

    public Cliente(String nome, String cpf, String email, String telefone, boolean fidelidade) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos");
        }
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.fidelidade = fidelidade;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean isFidelidade() {
        return fidelidade;
    }
}
