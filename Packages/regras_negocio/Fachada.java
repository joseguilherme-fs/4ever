package regras_negocio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import modelo.Convidado;
import modelo.Evento;
import modelo.Ingresso;
import modelo.Participante;
import repositorio.Repositorio;

public class Fachada {
	private static Repositorio repositorio = new Repositorio();
	private static int eventoId;// ID sequencial para eventos
	
	
	 public static void criarEvento(String data, String descricao, int capacidade, double preco) {
	        if (capacidade < 2 || preco < 0 || data.isEmpty() || descricao.isEmpty()) {
	            throw new IllegalArgumentException("Dados do evento inválidos.");
	        }
	        Evento novoEvento = new Evento(++eventoId, data, descricao, capacidade, preco);
	        repositorio.eventos.put(eventoId,novoEvento);
	        repositorio.salvarObjetos();
	    }
	 
	 public static void criarParticipante(String cpf, String nascimento) throws Exception {
		 	repositorio.participantes.get(cpf);
		 	if (cpf.isEmpty() || nascimento.isEmpty()) {
	            throw new IllegalArgumentException("Dados do participante inválidos.");
	        }
		 	else if (repositorio.participantes.get(cpf) != null){
			 	throw new Exception("Esse participante já existe.");
		 	}
		 	
	        Participante novoParticipante = new Participante(cpf, nascimento);
	        repositorio.participantes.put(cpf,novoParticipante);
	        repositorio.salvarObjetos();
	 }
	 
	 public static void criarConvidado(String cpf, String nascimento, String empresa) {
	        if (cpf.isEmpty() || nascimento.isEmpty() || empresa.isEmpty()) {
	            throw new IllegalArgumentException("Dados do convidado inválidos");
	        }
	        Convidado novoConvidado = new Convidado(cpf, nascimento, empresa);
	        repositorio.participantes.put(cpf,novoConvidado);
	 }
	 
	 public static void criarIngresso(int idEvento, String cpf, String telefone) throws Exception {
	        Evento evento = repositorio.eventos.get(idEvento);
	        Participante participante = repositorio.participantes.get(cpf);
	        Ingresso testeIngresso = repositorio.ingressos.get(idEvento+"-"+cpf);
	        
	        if (cpf.isEmpty() || telefone.isEmpty()) {
	        	throw new IllegalArgumentException("Dados do evento inválidos.");
	        }
	        else if (testeIngresso != null) {
	        	throw new Exception("Esse ingresso já foi criado.");
	        }
	        else if (evento == null) {
	        	throw new Exception("Esse evento não existe.");
	        }
	        else if (participante == null) {
	        	throw new Exception("Esse participante não existe.");
	        }
	        else if (evento.lotado()) {
	        	throw new Exception("Esse evento está lotado.");
	        }
	        	Ingresso novoIngresso = new Ingresso(idEvento,cpf,telefone);
		        String codigo = idEvento+"-"+cpf;
		        repositorio.ingressos.put(codigo, novoIngresso);
		        
		        Ingresso ingressoAdd = repositorio.ingressos.get(codigo);
		        participante.addIngresso(ingressoAdd);
		        evento.addIngresso(ingressoAdd);
		        
		        ingressoAdd.setParticipante(participante);
		        ingressoAdd.setEvento(evento);
		        repositorio.salvarObjetos();
	        
	        
	        
	        
	 }
	 
	 public static void apagarEvento(int id) throws Exception {
		 	Evento evento = repositorio.eventos.get(id);
	        if (evento == null || !evento.getIngressos().isEmpty()) {
	            throw new Exception("Evento não encontrado ou possui ingressos vendidos");
	        }
	        
	        repositorio.eventos.remove(id);     
	        repositorio.salvarObjetos();
	        
	    }
	 
	 public static void apagarParticipante(String cpf) throws Exception {
		 	Participante participante = repositorio.participantes.get(cpf);
	        if (participante == null) {
	            throw new Exception("Participante não existe.");
	        }
	        
	        ArrayList<Ingresso> ingressos = participante.getIngressos();
	        
	        if (!ingressos.isEmpty()) {
				
		        if (verificarIngressoValido(participante)) {
		        	throw new Exception("Este participante ainda tem ingressos válidos");	
				}
		        else {
			        apagarTodosIngressos(cpf);
			        repositorio.participantes.remove(cpf);
		        } 
	        }
	        
	        repositorio.salvarObjetos();
	        
	    }
	 
	 public static void apagarIngresso(String codigo) throws Exception {
		 	Ingresso ingresso = repositorio.ingressos.get(codigo);
		 	if (ingresso == null) {
		 		throw new Exception("Ingresso não encontrado.");
		 	}
		 	repositorio.ingressos.remove(codigo);
		 	
		 	Integer id = Integer.parseInt(codigo.split("-")[0]);
			String cpf = codigo.split("-")[1];
			
			repositorio.participantes.get(cpf).getIngressos().remove(ingresso);
			repositorio.eventos.get(id).getIngressos().remove(ingresso);
			
		 	repositorio.salvarObjetos();
		 	
	 }
	 
	 
	 
	 public static ArrayList<Evento> listarEventos() {
			 ArrayList<Evento> todosEventos = new ArrayList<>(repositorio.eventos.values());
		 	 return todosEventos;
	 }
	
	 
	 public static ArrayList<Participante> listarParticipantes() {
		 ArrayList<Participante> todosParticipantes = new ArrayList<>(repositorio.participantes.values());
	 	 return todosParticipantes;
	 }
	 
	 public static ArrayList<Ingresso> listarIngressos() {
		 ArrayList<Ingresso> todosIngressos = new ArrayList<>(repositorio.ingressos.values());
	 	 return todosIngressos;
	 }
	 
	 public static ArrayList<Ingresso> listarIngressosEvento(int id) {
		 Evento evento = repositorio.eventos.get(id);
		 return evento.getIngressos();
	 }
	 
	 public static ArrayList<Ingresso> listarIngressosPart(String cpf) {
		 Participante participante = repositorio.participantes.get(cpf);
		 return participante.getIngressos();
	 }
	 
	 
	 // Métodos utilitários:
	 
	 public static boolean verificarIngressoValido(Participante participante) {
		 ArrayList<Ingresso> ingressos = participante.getIngressos();
		 Ingresso ultimoIngresso = ingressos.get(ingressos.size() - 1);
		 Evento evento = ultimoIngresso.getEvento();
		 String dataEvento = evento.getData();
		 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	     LocalDate dataEventoFormatada = LocalDate.parse(dataEvento, formatter);
	     LocalDate dataAgora = LocalDate.now();
	     
	     boolean ingressoValido = !dataEventoFormatada.isBefore(dataAgora); 
	     // Se a data do evento for anterior a data atual, .isBefore retornará true, porém será negado, 
	     // o que significa que o ingresso não é válido.
	     
	     return ingressoValido;
		 
		 
	 }
	 
	 public static void apagarTodosIngressos (String cpf) {
		 ArrayList<Ingresso> ingressosRemover = new ArrayList<>();

		 for (Ingresso i : repositorio.ingressos.values()) {
			 if (i.getParticipante().getCpf().equals(cpf)) {
		            ingressosRemover.add(i); 
		        }
		 }
		 // Remove os ingressos do repositório:
		 for (Ingresso ingresso : ingressosRemover) {	 
			 repositorio.ingressos.remove(ingresso.getCodigo());
			 ingresso.setParticipante(null);
		 }
	 }

	 public static void atualizarEventoId(int novoId) {
	        eventoId = novoId;
	    }
	 
	 


	 
	 
}
