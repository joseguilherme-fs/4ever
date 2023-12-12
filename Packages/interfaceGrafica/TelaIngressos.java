package interfaceGrafica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
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
import modelo.Evento;
import regras_negocio.Fachada;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class TelaIngressos {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JButton btn_Criar;
	private JTextField txtID;
	private JTextField txtCPF;
	private JTextField txtTelefone;
	private JLabel lblDescrio;

	
	public TelaIngressos() {
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
		frame.setTitle("Ingressos");
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
		label.setBounds(479, 329, 311, 14);
		frame.getContentPane().add(label);

		
		
		txtID = new JTextField();
		txtID.setBounds(154, 248, 126, 20);
		frame.getContentPane().add(txtID);
		txtID.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ID do Evento");
		lblNewLabel.setBounds(154, 229, 126, 14);
		frame.getContentPane().add(lblNewLabel);
		
		txtCPF = new JTextField();
		txtCPF.setColumns(10);
		txtCPF.setBounds(154, 297, 126, 20);
		frame.getContentPane().add(txtCPF);
		
		JLabel lblCapacidade = new JLabel("CPF do Participante");
		lblCapacidade.setBounds(154, 279, 126, 14);
		frame.getContentPane().add(lblCapacidade);
		
		txtTelefone = new JTextField();
		txtTelefone.setColumns(10);
		txtTelefone.setBounds(337, 276, 126, 20);
		frame.getContentPane().add(txtTelefone);
		
		lblDescrio = new JLabel("Telefone do participante");
		lblDescrio.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDescrio.setBounds(337, 258, 125, 14);
		frame.getContentPane().add(lblDescrio);
		
		JButton btn_Apagar = new JButton("Apagar ingresso");
		btn_Apagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						String codigo = (String) table.getValueAt( table.getSelectedRow(), 0);
						Fachada.apagarIngresso(codigo);
						label.setText("Removeu o ingresso de código " + codigo);
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
		btn_Apagar.setBounds(509, 295, 160, 23);
		frame.getContentPane().add(btn_Apagar);

		btn_Criar = new JButton("Criar ingresso");
		btn_Criar.setToolTipText("");
		btn_Criar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtID.getText();
				String telefone = txtTelefone.getText();
				String cpf = txtCPF.getText();
	
				try {
					if (id.isEmpty() || telefone.isEmpty() || cpf.isEmpty()) {
						throw new Exception("Campo(s) vazio(s)");
					}
					if (cpf.length() != 11) {
						throw new Exception("O CPF deve ter 11 dígitos");
					}
					
					if (telefone.length() != 11) {
						throw new Exception("O telefone deve ter 11 dígitos");
					}
					
					Fachada.criarIngresso(Integer.parseInt(id),cpf,telefone);
					label.setText("Ingresso criado com sucesso");
					txtID.setText("");
					txtTelefone.setText("");
					txtCPF.setText("");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		btn_Criar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_Criar.setBounds(509, 246, 160, 23);
		frame.getContentPane().add(btn_Criar);

	}
	

	public void listagem() {
		try{
			List<Ingresso> lista = Fachada.listarIngressos();

			//objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//criar as colunas (0,1,2) da tabela
			model.addColumn("Código");
			model.addColumn("Telefone");
			model.addColumn("Data do evento");
			model.addColumn("Preço do evento");
			model.addColumn("Preço do Ingresso");
			model.addColumn("Idade do participante");
			
			//criar as linhas da tabela
			for(Ingresso i : lista) {
				 model.addRow(new Object[]{
						 i.getCodigo(),
			             i.getTelefone(),
			             i.getEvento().getData(),
			             i.getEvento().getPreco(),
			             i.calcularPreco(),
			             i.getParticipante().calcularIdade()
			            });

			}
			table.setModel(model);


			//redimensionar a coluna 
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