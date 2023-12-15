package modelo;

import java.util.ArrayList;

public class Evento {
	
	private int id;
	private String data;
	private String descricao;	
	private int capacidade;
	private double preco;
	private boolean lotado = false; 
	private ArrayList<Ingresso> ingressos = new ArrayList<>();
	
	public Evento(int ID,String data, String descricao, int capacidade, double preco) {
		this.id = ID;
		this.data = data;
		this.descricao = descricao;
		this.capacidade = capacidade;
		this.preco = preco;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean lotado() {
		if (this.ingressos.size() == this.capacidade) {
			this.lotado = true;
		}
		return this.lotado;
	}


	public ArrayList<Ingresso> getIngressos() {
		return ingressos;
	}

	public void addIngresso(Ingresso ingresso) {
		this.ingressos.add(ingresso);
	}

	public String getData() {
		return data;
	}
	
	
	
	public String getDescricao() {
		return descricao;
	}


	public int getCapacidade() {
		return capacidade;
	}


	public double getPreco() {
		return preco;
	}


	public int quantidadeIngressos() {
		return this.ingressos.size();
	}
	
	
	public double totalArrecadado() {
		double total = 0;
		for (Ingresso i : this.ingressos) {
			
			total += i.calcularPreco();
			
		}
		return total;
	}


	@Override
	public String toString() {
		return "Evento [id=" + id + ", data=" + data + ", descricao=" + descricao + ", capacidade=" + capacidade
				+ ", preco=" + preco + "]";
	}
	
	
	
	

}
