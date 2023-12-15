package modelo;

import java.util.ArrayList;

public class Convidado extends Participante {
	
	private String empresa;

	public Convidado(String cpf, String nascimento, String empresa) {
		super(cpf, nascimento);
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "Convidado [cpf=" + super.getCpf() + ", nascimento=" + super.getNascimento() + ", empresa=" + empresa + "]";
	}

	public String getEmpresa() {
		return empresa;
	}
	
	
	
	
	

}
