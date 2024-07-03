package dados;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Cliente implements Serializable {

    private int codigo;

    private String nome;

    private ArrayList<Locacao> locacao;

    public Cliente(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        locacao = new ArrayList<Locacao>();
    }

    public abstract double calculaDesconto(int quantidadeRobos);

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String toString() {
        return "Cliente # " + codigo + " - " + nome;
    }
}
