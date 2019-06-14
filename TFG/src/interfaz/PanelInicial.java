package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controlador.Controlador;

@SuppressWarnings("serial")
public class PanelInicial extends JPanel implements ActionListener{

	private VentanaPrincipal window;
	private JButton requisitosButton;
	private JButton reiniciarButton;
	private JButton SprintButton;
	
	private JButton coeficientesButton;
	private JButton movimientosButton;
	private JButton consultaMovButton;
	private JButton consultaProyecButton;
	
	private Controlador controlador;
	
	public PanelInicial(VentanaPrincipal w) {
		
		this.window=w;
		controlador=Controlador.getUnicaInstancia();
		reiniciarButton = new JButton("Reiniciar Proyecto");
		reiniciarButton.setMargin(new Insets(2, 85, 2, 87));
		reiniciarButton.addActionListener(this);
		if(controlador.isProyectoIniciado()) {
			reiniciarButton.setEnabled(true);
		}
		else
			reiniciarButton.setEnabled(false);
		
		requisitosButton = new JButton("Mostrar requisitos");
		requisitosButton.setMargin(new Insets(2, 85, 2, 87));
		requisitosButton.addActionListener(this);
		
		SprintButton = new JButton("Mostrar Sprint");
		SprintButton.setMargin(new Insets(2, 85, 2, 110));
		SprintButton.addActionListener(this);
		
		coeficientesButton = new JButton("Mantenimiento de Coeficientes");
		coeficientesButton.setMargin(new Insets(2, 75, 2, 76));
		coeficientesButton.addActionListener(this);
		
		movimientosButton = new JButton("Mantenimiento de Movimientos");
		movimientosButton.setMargin(new Insets(2, 75, 2, 75));
		movimientosButton.addActionListener(this);
		
		consultaMovButton = new JButton("Consulta de Movimientos");
		consultaMovButton.setMargin(new Insets(2, 92, 2, 92));
		consultaMovButton.addActionListener(this);
		
		consultaProyecButton = new JButton("Consulta de Proyectos");
		consultaProyecButton.setMargin(new Insets(2, 100, 2, 99));
		consultaProyecButton.addActionListener(this);
		
		JPanel pNorte = new JPanel();
		JPanel pEste = new JPanel();
		JPanel pCentro = new JPanel();
		JPanel pSur = new JPanel();
		JPanel pCentral = new JPanel();
		
		JLabel rotuloSuperior = new JLabel("SCRUM+AUDIT", SwingConstants.CENTER);
		Font font = new Font("Calibri", Font.BOLD, 40);
		rotuloSuperior.setFont(font);
		Color c= new Color(98, 2, 174);
		rotuloSuperior.setForeground(c);
		pNorte.setAlignmentX(Component.CENTER_ALIGNMENT);
		pNorte.setMaximumSize(new Dimension(700, 100));
		pNorte.add(rotuloSuperior);
		pNorte.setBorder(BorderFactory.createLineBorder(Color.black));
		
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		pCentro.setAlignmentY(Component.CENTER_ALIGNMENT);
		pCentro.setAlignmentX(Component.CENTER_ALIGNMENT);
		pCentro.add(reiniciarButton); 
		pCentro.add(Box.createRigidArea(new Dimension(15, 15)));
		pCentro.add(requisitosButton);
		pCentro.add(Box.createRigidArea(new Dimension(15, 15)));
		pCentro.add(SprintButton); 
		pCentro.add(Box.createRigidArea(new Dimension(15, 15)));
		
		JPanel pCentralaux = new JPanel();
		pCentralaux.setLayout(new BoxLayout(pCentralaux, BoxLayout.X_AXIS));
		JLabel imagen=new JLabel();
		imagen.setMaximumSize(new Dimension(450, 450));
		String nombre="logo.png";
		String nombre_im=System.getProperty("user.dir")+"\\"+nombre;
		imagen.setIcon(new ImageIcon(nombre_im));
		pCentralaux.add(pCentro);
		pCentralaux.add(Box.createRigidArea(new Dimension(50, 50)));
		pCentralaux.add(imagen);
		
		pCentral.setLayout(new BoxLayout(pCentral, BoxLayout.Y_AXIS));
		pCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
		pCentral.setAlignmentY(Component.CENTER_ALIGNMENT);
		pCentral.add(Box.createRigidArea(new Dimension(50, 50)));
		pCentral.add(pCentralaux);
		
		setLayout(new BorderLayout(10, 10));
		add(pNorte, BorderLayout.NORTH);
		add(pCentral,BorderLayout.CENTER);
		
		pEste.add(Box.createRigidArea(new Dimension(100, 100)));
		add(pEste, BorderLayout.EAST);
		
		pSur.add(Box.createRigidArea(new Dimension(50, 50)));
		add(pSur, BorderLayout.SOUTH);
	}
	
	public void actualizarPanel() {
		if(controlador.isProyectoIniciado()) {
			reiniciarButton.setEnabled(true);
		}
		else
			reiniciarButton.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()== requisitosButton){
			window.setPanelMostrarRequisitos();
		}
		else if (e.getSource()== SprintButton){
			window.setPanelSprint();
		}
		else if (e.getSource()== reiniciarButton){
			int ax = JOptionPane.showConfirmDialog(null, "Esta operación reiniciará todos los datos de auditoría del proyecto, ¿Desea continuar?");
	        if(ax == JOptionPane.YES_OPTION)
	        {
	        	controlador.reiniciarProyecto();
				reiniciarButton.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Proyecto reiniciado.");
	        }
	        else if(ax == JOptionPane.NO_OPTION)
	            JOptionPane.showMessageDialog(null, "Operación cancelada.");
			
		}
	}
}
