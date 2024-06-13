package dados;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;

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
		return 0;
	}

}