package interfaceGrafica;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;

import javax.swing.SwingConstants;


import javax.swing.JMenuBar;
import java.awt.Color;

public class TelaPrincipal {

	private JFrame frame;
	private JMenu mnEvento;
	private JMenu mnIngresso;
	private JMenu mnParticipante;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPrincipal.class.getResource("/outros/logo.png")));
		frame.setResizable(false);
		frame.setTitle("4ever");
		frame.setBounds(100, 100, 505, 347);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Font font = null;

		InputStream fontStream = TelaPrincipal.class.getResourceAsStream("/outros/YesevaOne-Regular.ttf");
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
			font = font.deriveFont(Font.PLAIN, 14);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JLabel lblNewLabel_4 = new JLabel("by José Guilherme & Nirielly Brito");
		lblNewLabel_4.setFont(new Font("Calibri", Font.BOLD, 10));
		lblNewLabel_4.setForeground(new Color(199, 119, 229));
		lblNewLabel_4.setBounds(0, 272, 164, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		String nomeUsuario = System.getProperty("user.name");
		
				
		lblNewLabel_1 = new JLabel(nomeUsuario+"!");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(245, 218, 223));
		lblNewLabel_1.setFont(font);
		lblNewLabel_1.setBounds(171, 248, 146, 26);
		frame.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_3 = new JLabel(nomeUsuario+"!");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(new Color(199, 119, 229));
		lblNewLabel_3.setFont(font);
		lblNewLabel_3.setBounds(171, 250, 146, 25);
		frame.getContentPane().add(lblNewLabel_3);
		
		lblNewLabel = new JLabel("Seja bem-vindo(a),");
		lblNewLabel.setFont(font);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(171, 22, 146, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Seja bem-vindo(a),");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(new Color(199, 119, 229));
		lblNewLabel_2.setFont(font);
		lblNewLabel_2.setBounds(171, 23, 146, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 489, 286);
		
		ImageIcon imagem = new ImageIcon(getClass().getResource("/outros/thumb.png"));
		imagem = new ImageIcon(imagem.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_DEFAULT));//
		label.setIcon(imagem);
		frame.getContentPane().add(label);
		
		
		
		
		
		
		
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBackground(new Color(199, 119, 229));
		frame.setJMenuBar(menuBar);
		mnEvento = new JMenu("Eventos");
		mnEvento.setForeground(Color.WHITE);
		mnEvento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaEventos telaEven = new TelaEventos();
			}
		});
		menuBar.add(mnEvento);

		mnIngresso = new JMenu("Ingressos");
		mnIngresso.setForeground(Color.WHITE);
		mnIngresso.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaIngressos telaIng = new TelaIngressos();
			}
		});
		menuBar.add(mnIngresso);
		
		mnParticipante = new JMenu("Participantes");
		mnParticipante.setForeground(Color.WHITE);
		mnParticipante.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TelaParticipantes telaPart = new TelaParticipantes();
				
			}
		});
		menuBar.add(mnParticipante);

		frame.setVisible(true);
		
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Obtém as dimensões da janela
		Dimension windowSize = frame.getSize();

		// Calcula a posição da janela no centro da tela
		int x = (screenSize.width - windowSize.width) / 2;
		int y = (screenSize.height - windowSize.height) / 2;
		frame.setLocation(x,y);
		
	}
}

