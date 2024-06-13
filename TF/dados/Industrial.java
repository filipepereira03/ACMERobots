package dados;

public class Industrial extends Robo {

	private String setor;

	public Industrial(int id, String modelo, String setor) {
		super(id, modelo, 90);
		this.setor = setor;
    }

	@Override
	public double calculaLocacao(int dias) {
		return getValorDiario() * dias;
	}
}
