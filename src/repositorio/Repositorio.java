package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.TreeMap;

import modelo.Convidado;
import modelo.Evento;
import modelo.Ingresso;
import modelo.Participante;
import regras_negocio.Fachada;

public class Repositorio {
	public TreeMap<Integer, Evento> eventos = new TreeMap<>();
	public TreeMap<String,Participante> participantes = new TreeMap<>();
	public TreeMap<String,Ingresso> ingressos = new TreeMap<>();
	public static int ultimoID;
	
	

public Repositorio() {
	carregarObjetos();
}

public void carregarObjetos()  	{
	
	
	
	// carregar para o repositorio os objetos salvos nos arquivos csv
	try {
		//caso os arquivos nao existam, serao criados vazios
		File f1 = new File( new File(".\\eventos.csv").getCanonicalPath() ) ; 
		File f2 = new File( new File(".\\participantes.csv").getCanonicalPath() ) ; 
		File f3 = new File( new File(".\\ingressos.csv").getCanonicalPath() ) ; 
		if (!f1.exists() || !f2.exists() || !f3.exists()) {
			//System.out.println("criando arquivo .csv vazio");
			FileWriter arquivo1 = new FileWriter(f1); arquivo1.close();
			FileWriter arquivo2 = new FileWriter(f2); arquivo2.close();
			FileWriter arquivo3 = new FileWriter(f3); arquivo3.close();
			return;
		}
	}
	catch(Exception ex)		{
		throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
	}

	String linha;	
	String[] partes;	
	Evento evento;
	Participante participante;

	try	{
		String data, descricao, id, capacidade, preco ;
		File f = new File( new File(".\\eventos.csv").getCanonicalPath() )  ;
		Scanner arquivo1 = new Scanner(f);	 //  pasta do projeto
		while(arquivo1.hasNextLine()) 	{
			linha = arquivo1.nextLine().trim();		
			partes = linha.split(";");	
			//System.out.println(Arrays.toString(partes));
			id = partes[0];
			data = partes[1];
			
			descricao = partes[2];
			capacidade = partes[3];
			preco = partes[4];
			
			int idEvento = Integer.parseInt(id);
			
			if (idEvento > ultimoID) {
	            ultimoID = idEvento;
	        }
			
			evento = new Evento(idEvento, data, descricao,
					Integer.parseInt(capacidade), Double.parseDouble(preco));
			this.eventos.put(idEvento,evento);
		} 
		arquivo1.close();
		Fachada.atualizarEventoId(ultimoID);
	}
	catch(Exception ex)		{
		throw new RuntimeException("leitura arquivo de eventos:"+ex.getMessage());
	}

	try	{
		String cpf, nascimento, empresa, listaId;
		File f = new File( new File(".\\participantes.csv").getCanonicalPath())  ;
		Scanner arquivo2 = new Scanner(f);	 //  pasta do projeto
		while(arquivo2.hasNextLine()) 	{
			linha = arquivo2.nextLine().trim();	
			partes = linha.split(";");
			//System.out.println(Arrays.toString(partes));
			cpf = partes[0];
			nascimento = partes[1];
			if(partes.length==2) {
				participante = new Participante(cpf,nascimento);
				this.participantes.put(cpf,participante); // alterado
			}
			else {
				empresa = partes[2];
				participante = new Convidado(cpf,nascimento,empresa);
				this.participantes.put(cpf,participante);
			}

		}
		arquivo2.close();
	}
	catch(Exception ex)		{
		throw new RuntimeException("leitura arquivo de participantes:"+ex.getMessage());
	}
	
	try	{
		String codigo, telefone,cpf;
		int id;
		Ingresso ingresso;
		Evento evento1;
		Participante participante1;
		File f = new File( new File(".\\ingressos.csv").getCanonicalPath())  ;
		Scanner arquivo3 = new Scanner(f);	 //  pasta do projeto
		while(arquivo3.hasNextLine()) 	{
			linha = arquivo3.nextLine().trim();	
			partes = linha.split(";");
			//System.out.println(Arrays.toString(partes));
			codigo = partes[0];		//contem id e cpf
			telefone = partes[1];
			id = Integer.parseInt(codigo.split("-")[0]);
			cpf = codigo.split("-")[1];
			evento1 = this.eventos.get(id);
			participante1 = this.participantes.get(cpf);
			
			ingresso = new Ingresso(codigo,telefone,evento1,participante1);
			evento1.addIngresso(ingresso);
			participante1.addIngresso(ingresso);
			this.ingressos.put(codigo,ingresso);
		}
		arquivo3.close();
	}
	catch(Exception ex)		{
		throw new RuntimeException("leitura arquivo de participantes:"+ex.getMessage());
	}
}

//--------------------------------------------------------------------
public void	salvarObjetos()  {
	//gravar nos arquivos csv os objetos que estão no repositório
	try	{
		File f = new File( new File(".\\eventos.csv").getCanonicalPath())  ;
		FileWriter arquivo1 = new FileWriter(f); 
		for(Evento e : this.eventos.values()) 	{
			arquivo1.write(e.getId()+";"+e.getData()+";"+e.getDescricao()+";"+e.getCapacidade()+";"+e.getPreco()+"\n");	
		} 
		arquivo1.close();
	}
	catch(Exception e){
		throw new RuntimeException("problema na criação do arquivo  eventos "+e.getMessage());
	}

	try	{
		File f = new File( new File(".\\participantes.csv").getCanonicalPath())  ;
		FileWriter arquivo2 = new FileWriter(f) ; 
		for(Participante p : this.participantes.values()) {
			if(p instanceof Convidado c )
				arquivo2.write(p.getCpf() +";" + p.getNascimento() +";" + c.getEmpresa()+"\n");	
			else
				arquivo2.write(p.getCpf() +";" + p.getNascimento() +"\n");	

		} 
		arquivo2.close();
	}
	catch (Exception e) {
		throw new RuntimeException("problema na criação do arquivo  participantes "+e.getMessage());
	}
	try	{
		File f = new File( new File(".\\ingressos.csv").getCanonicalPath())  ;
		FileWriter arquivo3 = new FileWriter(f) ; 
		for(Ingresso i : this.ingressos.values()) {
				arquivo3.write(i.getCodigo() +";" + i.getTelefone()+"\n");	

		} 
		arquivo3.close();
	}
	catch (Exception e) {
		throw new RuntimeException("problema na criação do arquivo  participantes "+e.getMessage());
	}

}

	
}

	
