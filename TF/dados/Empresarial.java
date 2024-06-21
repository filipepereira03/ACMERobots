package dados;

public class Empresarial extends Cliente {

	private int ano;

	public Empresarial(int codigo, String nome, int ano) {
		super(codigo, nome);
		this.ano = ano;
	}

	@Override
	public double calculaDesconto(int quantidadeRobos) {
		if (quantidadeRobos >= 2 && quantidadeRobos <= 9) {
			return 0.03;
		} else if (quantidadeRobos >= 10) {
			return 0.07;
		}
		return 0.00;
	}

	public String toString() {
		return "Cliente empresarial " +super.toString();
	}
}

