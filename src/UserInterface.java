

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

public class UserInterface extends JFrame implements ActionListener{
	private JTextField peText;
	private JTextField enviarText;
	JTextArea respuestaText;
	private JButton peBtn;
	private JButton btnYes;
	private JButton btnClavePE, enviarBtn;

	public UserInterface() {
		setResizable(false);
		getContentPane().setLayout(null);
		this.setSize(400, 350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton servidorBtn = new JButton("-");
		servidorBtn.setBounds(321, 68, 63, 23);
		getContentPane().add(servidorBtn);
		servidorBtn.addActionListener(this);
		
		peBtn = new JButton("-");
		peBtn.setBounds(5, 34, 146, 23);
		getContentPane().add(peBtn);
		peBtn.addActionListener(this);
		
		peText = new JTextField();
		peText.setBounds(161, 37, 223, 20);
		peText.setToolTipText("Ingrese IP de PE");
		getContentPane().add(peText);
		peText.setColumns(10);
		
		enviarText = new JTextField();
		enviarText.setBounds(5, 68, 306, 23);
		enviarText.setToolTipText("ingrese comando");
		getContentPane().add(enviarText);
		enviarText.setColumns(10);
		
		enviarBtn = new JButton("Ver interfaz");
		enviarBtn.setBounds(5, 5, 146, 23);
		getContentPane().add(enviarBtn);
		enviarBtn.addActionListener(this);
		
		respuestaText = new JTextArea();
		respuestaText.setBounds(5, 110, 379, 200);
		getContentPane().add(respuestaText);
		
		btnYes = new JButton("Puerto PE");
		btnYes.setBounds(161, 5, 101, 23);
		getContentPane().add(btnYes);
		btnYes.addActionListener(this);
		
		btnClavePE = new JButton("Protocolo BGP");
		btnClavePE.setBounds(269, 5, 115, 23);
		getContentPane().add(btnClavePE);
		btnClavePE.addActionListener(this);
		this.setVisible(true);
	}

	public void setRespuesta(String s) {
		System.out.println("done");
		respuestaText.setText((String) s);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnYes) {
			SshPortForwarding.verPuerto();
		}
		
		if(e.getSource()==btnClavePE) {SshPortForwarding.verBGP();}
		
		if(e.getSource()==enviarBtn) {SshPortForwarding.verInterfaz();}
	}
}
