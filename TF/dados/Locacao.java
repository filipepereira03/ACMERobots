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

	private ArrayList<Robo> roboLista;

	private int dias;

	public Locacao(int numero, Status situacao, Date dataInicio, int dataFim, Cliente cliente, ArrayList<Robo> roboLista, int dias) {
		this.numero = numero;
		this.situacao = Status.CADASTRADA;
		this.dataInicio = new Date();
		this.dataFim = dataFim;
		this.cliente = cliente;
		this.dias = dias;
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
		long diff = dataFim - (dataInicio.getTime() / (1000 * 60 * 60 * 24));
		int dias = (int) diff;
		return dias;
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
		return "Locação #: " + numero + " - " + situacao + " - " + "Cliente: " +cliente.getNome();
	}

	public Cliente getCliente() {
		return cliente;
	}
}
