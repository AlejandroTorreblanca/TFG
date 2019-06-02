package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import controlador.Controlador;

@SuppressWarnings("serial")
public class PanelMostrarRequisitos extends JPanel implements ActionListener, KeyListener {

	private VentanaPrincipal window;
	private Controlador controlador;
	private JTextField textoCD;
	private JTextField textoDescrip;
	private JTextField textoPrecio;
	private JTable tabla;
	private JScrollPane scrollPane;
	private JButton confirmarButton;
	private JButton cancelarButton;

	private void fixedSize(JComponent c, int x, int y) {
		c.setMinimumSize(new Dimension(x, y));
		c.setMaximumSize(new Dimension(x, y));
		c.setPreferredSize(new Dimension(x, y));
	}

	public PanelMostrarRequisitos(VentanaPrincipal w) {

		this.window = w;
		controlador = Controlador.getUnicaInstancia();

		cancelarButton = new JButton("Volver");
		cancelarButton.setMargin(new Insets(2, 28, 2, 28));
		cancelarButton.addActionListener(this);

		JPanel panelCentral = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		
		JTree arbol= new JTree(controlador.getRequisitos());
		arbol.setRootVisible(false);
		scrollPane= new JScrollPane(arbol);
		scrollPane.setPreferredSize(new Dimension(450, 400));
		panel1.add(Box.createRigidArea(new Dimension(50, 15)));
		panel1.add(scrollPane);

		panel2.add(Box.createRigidArea(new Dimension(50, 15)));
		panel2.add(cancelarButton);

		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panel2.setAlignmentX(LEFT_ALIGNMENT);
		
		
		panelCentral.add(Box.createRigidArea(new Dimension(20, 20)));
		panelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelCentral.add(panel1);
		panelCentral.add(panel2);
		
		 

		JPanel pCentral = new JPanel();
		JPanel pNorte = new JPanel();
		JPanel pEste = new JPanel();
		JPanel pSur = new JPanel();
		JPanel pOeste = new JPanel();

		pCentral.setLayout(new BoxLayout(pCentral, BoxLayout.Y_AXIS));
		pCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
		pCentral.add(panelCentral);

		JLabel rotuloSuperior = new JLabel("Requisitos del Proyecto", SwingConstants.CENTER);
		Font font = new Font("Calibri", Font.BOLD, 40);
		rotuloSuperior.setFont(font);
		pNorte.setAlignmentX(Component.CENTER_ALIGNMENT);
		pNorte.add(rotuloSuperior);
		pNorte.setBorder(BorderFactory.createLineBorder(Color.black));

		setLayout(new BorderLayout(10, 10));
		add(pNorte, BorderLayout.NORTH);

		JLabel imagen = new JLabel();
		imagen.setMaximumSize(new Dimension(80, 74));
		String nombre = "logo.png";
		String nombre_im = System.getProperty("user.dir") + "\\" + nombre;
		imagen.setIcon(new ImageIcon(nombre_im));
		pOeste.setLayout(new BoxLayout(pOeste, BoxLayout.Y_AXIS));
		pOeste.setAlignmentY(Component.LEFT_ALIGNMENT);
//		pOeste.add(imagen);
		pOeste.add(Box.createRigidArea(new Dimension(25, 200)));
		add(pOeste, BorderLayout.WEST);
		add(pCentral, BorderLayout.CENTER);

		pEste.add(Box.createRigidArea(new Dimension(100, 100)));
		add(pEste, BorderLayout.EAST);

		pSur.add(Box.createRigidArea(new Dimension(50, 50)));
		add(pSur, BorderLayout.SOUTH);

	}


	public boolean isNumeric(String cadena) {
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException excepcion) {
			return false;
		}
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirmarButton) {
		} else if (e.getSource() == cancelarButton) {
			window.setPanelInicial();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
}
