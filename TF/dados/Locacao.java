package dados;

import java.util.Date;
import java.util.Collection;

public class Locacao {

	private int numero;

	private Status situacao;

	private Date dataInicio;

	private int dataFim;

	private Cliente cliente;

	private Collection<Robo> robo;

	public double calculaValorFinal() {
		return 0;
	}

}
