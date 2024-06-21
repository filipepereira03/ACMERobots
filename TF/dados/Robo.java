package dados;

import java.util.ArrayList;

public abstract class Robo {

	private int id;

	private String modelo;

	private double valorDiario;

	private ArrayList<Locacao> locacao;

	public Robo(int id, String modelo, double valorDiario) {
		this.id = id;
		this.modelo = modelo;
		this.valorDiario = valorDiario;
		locacao = new ArrayList<Locacao>();
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

	public String toString() {
		return "Id=" + id;
	}
}
