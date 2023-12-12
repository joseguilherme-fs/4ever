package modelo;

public class Ingresso {
	private String codigo;
	private String telefone;
	private Evento evento;
	private Participante participante; 
	
	public Ingresso(int id, String cpf, String telefone) {
		this.codigo = id+"-"+cpf;
		this.telefone = telefone;

	}

	
	public Ingresso(String codigo, String telefone, Evento evento, Participante participante) {
		
		this.codigo = codigo;
		this.telefone = telefone;
		this.evento = evento;
		this.participante = participante;
	}



	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public String getCodigo() {
		return codigo;
	}

	public Participante getParticipante() {
		return participante;
	}
	
	
	
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public String getTelefone() {
		return telefone;
	}

	public double calcularPreco() {
		 Integer idade = participante.calcularIdade();
	     double desconto = 0;
	     double preco = this.evento.getPreco();
	     
	     if (idade < 18) {
	    	 desconto += 0.1;
	    
	     } else if (idade >= 60) {
	    	 desconto += 0.2;
	        }
	     
	     if (participante instanceof Convidado) {

	    	 desconto += 0.5;
	     }
	   
	     
	     double descontoTotal = preco * desconto;

	     return preco - descontoTotal;
 	
		
	}

	@Override
	public String toString() {
		return "Ingresso [codigo=" + codigo + ", telefone=" + telefone + "]";
	}
	
	
	
	
}


