package dados;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Robo implements Serializable {

    private int id;

    private String modelo;

    private double valorDiario;

    private ArrayList<Locacao> locacao;

    private boolean disponivel;

    public Robo(int id, String modelo, double valorDiario) {
        this.id = id;
        this.modelo = modelo;
        this.valorDiario = valorDiario;
        locacao = new ArrayList<>();
        this.disponivel = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getValorDiario() {
        return valorDiario;
    }

    public void setValorDiario(double valorDiario) {
        this.valorDiario = valorDiario;
    }

    public ArrayList<Locacao> getLocacao() {
        return locacao;
    }

    public void setLocacao(ArrayList<Locacao> locacao) {
        this.locacao = locacao;
    }

    public abstract double calculaLocacao(int dias);

    public boolean estaDisponivel() {
        return disponivel;
    }

    public void locar() {
        this.disponivel = false;
    }

    public void liberar() {
        this.disponivel = true;
    }

    public boolean estaLocado() {
        return !disponivel;
    }

    public String toString() {
        return "Robo # " + id + " - " + modelo;
    }
}
