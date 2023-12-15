package modelo;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Participante {
	private String cpf;
	private String nascimento;
	private ArrayList<Ingresso> ingressos = new ArrayList<>();
	
	public Participante(String cpf, String nascimento) {
		super();
		this.cpf = cpf;
		this.nascimento = nascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNascimento() {
		return nascimento;
	}

	public ArrayList<Ingresso> getIngressos() {
		return ingressos;
	}

	public void addIngresso(Ingresso ingresso) {
		this.ingressos.add(ingresso);
	}
	
	public int calcularIdade() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataNascimento = LocalDate.parse(this.getNascimento(),formatter);
	    LocalDate dataAtual = LocalDate.now();
	    int idade = Period.between(dataNascimento, dataAtual).getYears();
	    return idade;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Participante [cpf=" + cpf + ", nascimento=" + nascimento + "]";
	}
	
	
	
	
	
	
	

}
