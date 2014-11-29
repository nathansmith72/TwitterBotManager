/**
**
*/
package gui;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DeleteBotGUI{
	private JFrame frame;
	private JFrame parentFrame;

	public DeleteBotGUI(JFrame parentFrame){
		this.parentFrame = parentFrame;
		initialize();
	}
	
	public void initialize(){
		frame = new JFrame();
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				parentFrame.setEnabled(true);
				frame.dispose();
			}
		});
		frame.setBounds(100, 100, 550, 250);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		JLabel warningLabel = new JLabel("Are you sure you want to delete " + gui.GUI.listValue + "?", SwingConstants.CENTER);
		warningLabel.setBounds(0, 25, 550, 50);
		frame.getContentPane().add(warningLabel);
		
		JButton btnDeleteBot = new JButton("Delete");
		btnDeleteBot.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gui.GUI.deleteBot();
				parentFrame.setEnabled(true);
				frame.dispose();
			}
		});
		btnDeleteBot.setBounds(160, 140, 89, 23);
		frame.getContentPane().add(btnDeleteBot);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				parentFrame.setEnabled(true);
				frame.dispose();
			}
		});
		btnCancel.setBounds(310, 140, 89, 23);
		frame.getContentPane().add(btnCancel);
	}
}