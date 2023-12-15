package interfaceGrafica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Convidado;
import modelo.Participante;
import modelo.Ingresso;
import regras_negocio.Fachada;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class TelaParticipantes {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JButton btn_Criar;
	private JTextField txtCPF;
	private JTextField txtData;
	private JLabel lblPreo;
	private JTextField txtEmpresa;


	public TelaParticipantes() {
		initialize();
		listagem();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setResizable(false);
		frame.setModal(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});
		frame.setTitle("Participantes");
		frame.setBounds(100, 100, 816, 391);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 11, 758, 207);
		frame.getContentPane().add(scrollPane);

		table = new JTable(){
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.RED);
		label.setBounds(215, 329, 575, 14);
		frame.getContentPane().add(label);

		
		
		txtCPF = new JTextField();
		txtCPF.setBounds(21, 247, 126, 20);
		frame.getContentPane().add(txtCPF);
		txtCPF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("CPF");
		lblNewLabel.setBounds(21, 233, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		txtData = new JTextField();
		txtData.setColumns(10);
		txtData.setBounds(21, 296, 126, 20);
		frame.getContentPane().add(txtData);
		
		JLabel lblCapacidade = new JLabel("Data de Nascimento");
		lblCapacidade.setBounds(21, 278, 126, 14);
		frame.getContentPane().add(lblCapacidade);
		
		lblPreo = new JLabel("Empresa (para Convidados)");
		lblPreo.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPreo.setBounds(189, 260, 140, 14);
		frame.getContentPane().add(lblPreo);
		
		txtEmpresa = new JTextField();
		txtEmpresa.setColumns(10);
		txtEmpresa.setBounds(188, 275, 126, 20);
		frame.getContentPane().add(txtEmpresa);
		
		JButton btn_Listar = new JButton("Listar ingressos");
		btn_Listar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						String cpf = (String) table.getValueAt( table.getSelectedRow(), 0);
						
						
						List<Ingresso> lista = Fachada.listarIngressosPart(cpf);
						
						DefaultTableModel model = new DefaultTableModel();
				        model.addColumn("Código");
				        model.addColumn("Telefone");
				        
				        for (Ingresso i : lista) {
				        	model.addRow(new Object[]{i.getCodigo(), i.getTelefone()});
				        }
				        
				        JTable table = new JTable(model) {
				        	public boolean isCellEditable(int rowIndex, int vColIndex) {
								return false;
							}
				        };
				        
				        JScrollPane scrollPane = new JScrollPane(table);
						
				        JOptionPane.showMessageDialog(null, scrollPane, "Ingressos do CPF " + cpf, JOptionPane.PLAIN_MESSAGE);
						
					}
					else
						label.setText("Selecione uma Linha");
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		btn_Listar.setToolTipText("");
		btn_Listar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_Listar.setBounds(619, 259, 160, 23);
		frame.getContentPane().add(btn_Listar);
		
		JButton btn_Apagar = new JButton("Apagar participante");
		btn_Apagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						String cpf = (String) table.getValueAt( table.getSelectedRow(), 0);
						Fachada.apagarParticipante(cpf);
						label.setText("Removeu o participante do CPF " + cpf);
						listagem();
					}
					else
						label.setText("Selecione uma Linha");
				}
				catch(Exception erro) {
					label.setText(erro.getMessage());
				}
				
			}
		});
		

		btn_Apagar.setToolTipText("");
		btn_Apagar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_Apagar.setBounds(619, 295, 160, 23);
		frame.getContentPane().add(btn_Apagar);

		btn_Criar = new JButton("Criar participante");
		btn_Criar.setToolTipText("");
		btn_Criar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cpf = txtCPF.getText();
				String data = txtData.getText();
				String empresa = txtEmpresa.getText();
	
				try {
					if (cpf.isEmpty() || data.isEmpty()) {
						throw new Exception("Campo(s) vazio(s)");
					}
					if (cpf.length() != 11) {
						throw new Exception("O CPF deve ter 11 dígitos");
					}
					
					if (!validarData(data)) {
						throw new Exception("A data deve estar no formato correto (dd/mm/aaaa)");
					}
					
					if (empresa.isEmpty()) {
						Fachada.criarParticipante(cpf,data);
					}
					else {
						Fachada.criarConvidado(cpf,data,empresa);
					}
					
					label.setText("Participante criado com sucesso");
					txtCPF.setText("");
					txtData.setText("");
					txtEmpresa.setText("");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btn_Criar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_Criar.setBounds(366, 273, 160, 23);
		frame.getContentPane().add(btn_Criar);

	}
	
	public static boolean validarData(String data) {
        // Expressão regular para o formato dd/mm/aaaa
        String padrao = "\\d{2}/\\d{2}/\\d{4}";

        // Compila o padrão de expressão regular
        Pattern pattern = Pattern.compile(padrao);

        // Cria um matcher com a string fornecida
        Matcher matcher = pattern.matcher(data);

        // Verifica se a string corresponde ao padrão
        return matcher.matches();
    }
	
	public static boolean verificarConversaoDouble(String valor) {
        try {
            // Tenta converter a string para double
            double numero = Double.parseDouble(valor);
            // Se a conversão for bem-sucedida, retorna true
            return true;
        } catch (NumberFormatException e) {
            // Se ocorrer uma exceção, a string não pode ser convertida para double
            return false;
        }
    }

	public void listagem() {
		try{
			List<Participante> lista = Fachada.listarParticipantes();

			//objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//criar as colunas (0,1,2) da tabela
			model.addColumn("CPF");
			model.addColumn("Data de Nasc.");
			model.addColumn("Idade");
			model.addColumn("Empresa");
			
			//criar as linhas da tabela
			for(Participante p : lista) {
				String empresa;
				if (p instanceof Convidado) {
					empresa = ((Convidado) p).getEmpresa();
				} else {
					empresa = "";
				}
				 model.addRow(new Object[]{
						 p.getCpf(),
			             p.getNascimento(),
			             p.calcularIdade(),
			             empresa
			            });

			}
			table.setModel(model);


			//redimensionar a coluna 2
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //habilita
			table.getColumnModel().getColumn(0).setMinWidth(50);	
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Obtém as dimensões da janela
		Dimension windowSize = frame.getSize();

		// Calcula a posição da janela no centro da tela
		int x = (screenSize.width - windowSize.width) / 2;
		int y = (screenSize.height - windowSize.height) / 2;
		frame.setLocation(x,y);
	}
	
	
}