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
import modelo.Ingresso;
import modelo.Evento;
import regras_negocio.Fachada;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class TelaEventos {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JButton btn_Criar;
	private JTextField txtData;
	private JTextField txtCapacidade;
	private JLabel lblPreo;
	private JTextField txtPreco;
	private JTextField txtDescricao;
	private JLabel lblDescrio;


	public TelaEventos() {
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
		frame.setTitle("Eventos");
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

		
		
		txtData = new JTextField();
		txtData.setBounds(21, 247, 126, 20);
		frame.getContentPane().add(txtData);
		txtData.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Data ");
		lblNewLabel.setBounds(21, 233, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		txtCapacidade = new JTextField();
		txtCapacidade.setColumns(10);
		txtCapacidade.setBounds(21, 296, 126, 20);
		frame.getContentPane().add(txtCapacidade);
		
		JLabel lblCapacidade = new JLabel("Capacidade");
		lblCapacidade.setBounds(21, 278, 126, 14);
		frame.getContentPane().add(lblCapacidade);
		
		lblPreo = new JLabel("Preço");
		lblPreo.setBounds(196, 233, 46, 14);
		frame.getContentPane().add(lblPreo);
		
		txtPreco = new JTextField();
		txtPreco.setColumns(10);
		txtPreco.setBounds(196, 247, 126, 20);
		frame.getContentPane().add(txtPreco);
		
		txtDescricao = new JTextField();
		txtDescricao.setColumns(10);
		txtDescricao.setBounds(196, 296, 126, 20);
		frame.getContentPane().add(txtDescricao);
		
		lblDescrio = new JLabel("Descrição");
		lblDescrio.setBounds(196, 278, 66, 14);
		frame.getContentPane().add(lblDescrio);
		
		JButton btn_Listar = new JButton("Listar ingressos");
		btn_Listar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						Integer id = (Integer) table.getValueAt( table.getSelectedRow(), 0);
						
						List<Ingresso> lista = Fachada.listarIngressosEvento(id);
						
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
						
				        JOptionPane.showMessageDialog(null, scrollPane, "Ingressos do Evento", JOptionPane.PLAIN_MESSAGE);
						
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
		
		JButton btn_Apagar = new JButton("Apagar evento");
		btn_Apagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0){
						Integer id = (Integer) table.getValueAt( table.getSelectedRow(), 0);
						Fachada.apagarEvento(id);
						label.setText("Removeu o evento de ID " + id);
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

		btn_Criar = new JButton("Criar evento");
		btn_Criar.setToolTipText("");
		btn_Criar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = txtData.getText();
				String descricao = txtDescricao.getText();
				String preco = txtPreco.getText();
				String capacidade = txtCapacidade.getText();
	
				try {
					if (!validarData(data)) {
						throw new Exception("A data deve estar no formato correto (dd/mm/aaaa)");
					}
					
					Fachada.criarEvento(data,descricao,Integer.parseInt(capacidade),Double.parseDouble(preco));
					label.setText("Evento criado com sucesso");
					txtData.setText("");
					txtDescricao.setText("");
					txtPreco.setText("");
					txtCapacidade.setText("");
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
			List<Evento> lista = Fachada.listarEventos();

			//objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//criar as colunas (0,1,2) da tabela
			model.addColumn("ID");
			model.addColumn("Data");
			model.addColumn("Descrição");
			model.addColumn("Capacidade");
			model.addColumn("Preço");
			model.addColumn("Qntd. de Ingresso");
			model.addColumn("Total arrecadado");
			model.addColumn("Lotado");
			
			//criar as linhas da tabela
			for(Evento e : lista) {
				String statusLotado = e.lotado() ? "Sim" : "Não";
				 model.addRow(new Object[]{
						 e.getId(),
			             e.getData(),
			             e.getDescricao(),
			             e.getCapacidade(),
			             e.getPreco(),
			             e.quantidadeIngressos(),
			             e.totalArrecadado(),
			             statusLotado
			            });

			}
			table.setModel(model);


			//redimensionar a coluna 2
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 		//desabilita
			table.getColumnModel().getColumn(0).setMaxWidth(50);	
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //habilita
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