package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import bot.Bot;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.WindowAdapter;
import java.awt.Color;

public class AddBotGUI {

	private JFrame frame;
	private JTextField nameTextField;
	private JFrame parentFrame;
	private JTextField consumerKeyTextField;
	private JTextField consumerSecretTextField;
	private JTextField accessTokenTextField;
	private JTextField accessSecretTextField;
	private JLabel nameFieldWarningLabel;
	private JLabel consumerKeyWarningLabel;
	private JLabel consumerSecretWarningLabel;
	private JLabel accessTokenWarningLabel;
	private JLabel accessSecretWarningLabel;

	/**
	 * Create the application.
	 */

	public AddBotGUI(JFrame parentFrame) {
		this.parentFrame = parentFrame;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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

		nameTextField = new JTextField();
		nameTextField.setBounds(123, 15, 112, 20);
		frame.getContentPane().add(nameTextField);
		nameTextField.setColumns(10);

		JLabel label = new JLabel("Name:");
		label.setBounds(75, 18, 41, 14);
		frame.getContentPane().add(label);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean noFieldsEmpty = true;
				if(nameTextField.getText().compareTo("")==0){
					nameFieldWarningLabel.setVisible(true);
					noFieldsEmpty = false;
				} else {
					nameFieldWarningLabel.setVisible(false);
				}
				if(consumerKeyTextField.getText().compareTo("")==0){
					consumerKeyWarningLabel.setVisible(true);
					noFieldsEmpty = false;
				} else {
					consumerKeyWarningLabel.setVisible(false);
				}
				if(consumerSecretTextField.getText().compareTo("")==0){
					consumerSecretWarningLabel.setVisible(true);
					noFieldsEmpty = false;
				} else {
					consumerSecretWarningLabel.setVisible(false);
				}
				if(accessTokenTextField.getText().compareTo("")==0){
					accessTokenWarningLabel.setVisible(true);
					noFieldsEmpty = false;
				} else {
					accessTokenWarningLabel.setVisible(false);
				}
				if(accessSecretTextField.getText().compareTo("")==0){
					accessSecretWarningLabel.setVisible(true);
					noFieldsEmpty = false;
				} else {
					accessSecretWarningLabel.setVisible(false);
				}
				if(noFieldsEmpty){
					String name = nameTextField.getText();
					String consumerKey = consumerKeyTextField.getText();
					String consumerSecret = consumerSecretTextField.getText();
					String accessToken = accessTokenTextField.getText();
					String accessSecret = accessSecretTextField.getText();
					Bot bot = new Bot(name, consumerKey, consumerSecret, accessToken, accessSecret);
					try{
						FileOutputStream fileOut =
							new FileOutputStream("Bots/" + name +".Bot");
						ObjectOutputStream out = new ObjectOutputStream(fileOut);
						out.writeObject(bot);
						out.close();
						fileOut.close();
					}catch(IOException i){
						i.printStackTrace();
					}
					parentFrame.setEnabled(true);
					frame.dispose();
				}
			}
		});
		btnNewButton.setBounds(179, 168, 89, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parentFrame.setEnabled(true);
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(278, 168, 89, 23);
		frame.getContentPane().add(btnNewButton_1);

		JLabel consumeKeyLabel = new JLabel("Consumer Key:");
		consumeKeyLabel.setBounds(25, 63, 89, 14);
		frame.getContentPane().add(consumeKeyLabel);

		consumerKeyTextField = new JTextField();
		consumerKeyTextField.setText("");
		consumerKeyTextField.setBounds(123, 60, 112, 20);
		frame.getContentPane().add(consumerKeyTextField);
		consumerKeyTextField.setColumns(10);

		JLabel lblConsumerSecret = new JLabel("Consumer Secret:");
		lblConsumerSecret.setBounds(10, 113, 113, 14);
		frame.getContentPane().add(lblConsumerSecret);

		consumerSecretTextField = new JTextField();
		consumerSecretTextField.setBounds(123, 110, 112, 20);
		frame.getContentPane().add(consumerSecretTextField);
		consumerSecretTextField.setColumns(10);

		JLabel label_1 = new JLabel("Access Secret:");
		label_1.setBounds(306, 113, 95, 14);
		frame.getContentPane().add(label_1);

		JLabel lblAcessToken = new JLabel("Access Token:");
		lblAcessToken.setBounds(310, 63, 123, 14);
		frame.getContentPane().add(lblAcessToken);

		accessTokenTextField = new JTextField();
		accessTokenTextField.setBounds(411, 60, 112, 20);
		frame.getContentPane().add(accessTokenTextField);
		accessTokenTextField.setColumns(10);

		accessSecretTextField = new JTextField();
		accessSecretTextField.setBounds(411, 110, 112, 20);
		frame.getContentPane().add(accessSecretTextField);
		accessSecretTextField.setColumns(10);

		nameFieldWarningLabel = new JLabel("Name must not be blank");
		nameFieldWarningLabel.setVisible(false);
		nameFieldWarningLabel.setForeground(Color.RED);
		nameFieldWarningLabel.setBounds(75, 38, 193, 14);
		frame.getContentPane().add(nameFieldWarningLabel);

		consumerKeyWarningLabel = new JLabel("Consumer Key must not be blank");
		consumerKeyWarningLabel.setVisible(false);
		consumerKeyWarningLabel.setForeground(Color.RED);
		consumerKeyWarningLabel.setBounds(72, 88, 195, 14);
		frame.getContentPane().add(consumerKeyWarningLabel);

		consumerSecretWarningLabel = new JLabel("Consumer Secret must not be blank");
		consumerSecretWarningLabel.setVisible(false);
		consumerSecretWarningLabel.setForeground(Color.RED);
		consumerSecretWarningLabel.setBounds(72, 138, 211, 14);
		frame.getContentPane().add(consumerSecretWarningLabel);

		accessTokenWarningLabel = new JLabel("Access Token must not be blank");
		accessTokenWarningLabel.setVisible(false);
		accessTokenWarningLabel.setForeground(Color.RED);
		accessTokenWarningLabel.setBounds(330, 88, 195, 14);
		frame.getContentPane().add(accessTokenWarningLabel);

		accessSecretWarningLabel = new JLabel("Access Secret must not be blank");
		accessSecretWarningLabel.setVisible(false);
		accessSecretWarningLabel.setForeground(Color.RED);
		accessSecretWarningLabel.setBounds(328, 138, 195, 14);
		frame.getContentPane().add(accessSecretWarningLabel);
	}
}
