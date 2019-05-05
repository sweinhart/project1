package com.revature;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.revature.service.AircraftDAO;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JToggleButton;

public class App extends javax.swing.JFrame {

	private JPanel contentPane;
	private static App frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AircraftDAO dao = AircraftDAO.getDAO();
					dao.setup();
					frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton databaseButton = new JButton("Edit Database");
		databaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
				DatabaseUI ui = new DatabaseUI();
				ui.setVisible(true);
			}
		});
		databaseButton.setBounds(144, 58, 156, 39);
		contentPane.add(databaseButton);
		
		final JToggleButton tglbtnSoap = new JToggleButton("Start SOAP SVC");
		tglbtnSoap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            if (tBtn.isSelected()) {
	               tglbtnSoap.setText("SOAP SVC Running");
	            } else {
	            	tglbtnSoap.setText("Start SOAP SVC");
	            }
			}
		});
		tglbtnSoap.setBounds(144, 108, 156, 39);
		contentPane.add(tglbtnSoap);
		
		final JToggleButton tglbtnRest = new JToggleButton("Start REST SVC");
		tglbtnRest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JToggleButton tBtn = (JToggleButton)e.getSource();
	            if (tBtn.isSelected()) {
	               tglbtnRest.setText("REST SVC Running");
	            } else {
	            	tglbtnRest.setText("Start REST SVC");
	            }
			}
		});
		tglbtnRest.setBounds(144, 158, 156, 39);
		contentPane.add(tglbtnRest);
	}
}
