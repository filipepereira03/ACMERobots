package dados;

import javax.swing.*;
import java.io.Serializable;
import java.util.*;

public class Locacao implements Serializable {

    private int numero;

    private Status situacao;

    private Date dataInicio;

    private int dataFim;

    private Cliente cliente;

    private ArrayList<Robo> roboLista;


    public Locacao(int numero, Status situacao, Date dataInicio, int dataFim, Cliente cliente, ArrayList<Robo> roboLista) {
        this.numero = numero;
        this.situacao = Status.CADASTRADA;
        this.dataInicio = new Date();
        this.dataFim = dataFim;
        this.cliente = cliente;
        this.roboLista = new ArrayList<>(roboLista);

    }

    public Locacao(int numero, Date dataInicio, int dataFim, Cliente cliente, ArrayList<Robo> roboLista) {
        this.numero = numero;
        this.situacao = Status.CADASTRADA;
        this.dataInicio = new Date();
        this.dataFim = dataFim;
        this.cliente = cliente;
        this.roboLista = new ArrayList<>(roboLista);
    }

    public double calculaValorFinal() {
        double valorLocacao = 0;
        for (Robo r : roboLista) {
            double valorRobo = r.calculaLocacao(calcularDias());
            valorLocacao += valorRobo;

        }
        double desconto = cliente.calculaDesconto(roboLista.size());
        double valorFinal = valorLocacao - (valorLocacao * desconto);
        return Math.max(valorFinal, 0);
    }

    public int calcularDias() {
        long millisDataInicio = dataInicio.getTime();
        long millisDataFim = (long) dataFim * 24 * 60 * 60 * 1000L;
        long diferencaMillis = millisDataFim - millisDataInicio;
        return (int) (diferencaMillis / (1000 * 60 * 60 * 24));
    }


    public int getNumero() {
        return numero;
    }

    public ArrayList<Robo> getRobos() {
        return roboLista;
    }

    public void setStatus(Status novaSituacao) {
        if (this.situacao == Status.FINALIZADA || this.situacao == Status.CANCELADA) {
            throw new IllegalStateException("Não é possível alterar a situação de uma locação finalizada ou cancelada.");
        }

        this.situacao = novaSituacao;
    }

    public Status getStatus() {
        return situacao;
    }

    public String toString() {
        return "Locação #: " + numero + " - " + situacao + " - " + "Cliente: " + cliente.getNome();
    }

    public Cliente getCliente() {
        return cliente;
    }

}

