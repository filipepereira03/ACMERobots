package dados;

import java.util.Collection;

public abstract class Robo {

	private int id;

	private String modelo;

	private double valorDiario;

	private Collection<Locacao> locacao;

	public abstract double calculaLocacao(int dias);

}
