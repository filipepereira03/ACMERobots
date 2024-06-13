package dados;

import java.util.Collection;

public abstract class Cliente {

	private int codigo;

	private String nome;

	private Collection<Locacao> locacao;

	public abstract double calculaDesconto(int quantidadeRobos);

}
