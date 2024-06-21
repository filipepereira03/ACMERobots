package dados;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import java.util.List;

public class Locacao {

	private int numero;

	private Status situacao;

	private Date dataInicio;

	private int dataFim;

	private Cliente cliente;

	private ArrayList<Robo> robo;

	public Locacao(int numero, Status situacao, Date dataInicio, int dataFim, Cliente cliente, Collection<Robo> robo) {
		this.numero = numero;
		this.situacao = Status.CADASTRADA;
		this.dataInicio = new Date();
		this.dataFim = dataFim;
		this.cliente = cliente;
		this.robo = new ArrayList<>();
	}

	public double calculaValorFinal() {
		double valorLocacao = 0;
		for (Robo r : robo) {
			valorLocacao += r.calculaLocacao(calcularDias());
		}
		double desconto = cliente.calculaDesconto(robo.size());
		double valorFinal = valorLocacao - (valorLocacao * desconto);
		return Math.max(valorFinal, 0);
	}

	public int calcularDias() {
		long diff = dataFim - dataInicio.getTime();
		return (int) (diff / (1000 * 60 * 60 * 24));
	}


	public int getNumero() {
		return numero;
	}

    public List<Robo> getRobos() {
		return robo;
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
		return "Locação #: " + numero + " - " + situacao + " - " + "Cliente: " +cliente.getNome();
	}
}
