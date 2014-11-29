package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import bot.Bot;
import action.Action;

public class ModifyBotGUI {

	private JFrame frame;
	private JTextField nameTextField;
	private JTable table;
	private JFrame parentFrame;
	private Bot bot;
	private DefaultTableModel actionTableModel;

	/**
	 * Create the application.
	 */
	public ModifyBotGUI(JFrame parentFrame, Bot bot) {
		this.parentFrame = parentFrame;
		this.bot = bot;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				parentFrame.setEnabled(true);
				frame.dispose();
			}
			public void windowActivated(WindowEvent e){
				populateActionTable();
			}
		});
		frame.setBounds(100, 100, 600, 300);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		nameTextField = new JTextField(bot.getName());
		nameTextField.setBounds(91, 32, 110, 20);
		frame.getContentPane().add(nameTextField);
		nameTextField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(20, 35, 46, 14);
		frame.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 100, 550, 100);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		actionTableModel = new DefaultTableModel(new Object[][] {
			},
			new String[] {
				"Action", "Condition Type", "Delay", "Keywords", "Tweet Text"
			}
		);
		table.setModel(actionTableModel);
		populateActionTable();
		scrollPane.setViewportView(table);

		JButton btnAddAction = new JButton("Add Action");
		btnAddAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					AddActionGUI addActionGUI = new AddActionGUI(frame, bot);
					frame.setEnabled(false);
				}
		});
		btnAddAction.setBounds(20, 63, 100, 23);
		frame.getContentPane().add(btnAddAction);

		JButton btnModify = new JButton("Modify");
		btnModify.setBounds(130, 63, 100, 23);
		frame.getContentPane().add(btnModify);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(240, 63, 100, 23);
		btnDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				bot.deleteAction(table.getSelectedRow());
				try{
					FileOutputStream fileOut =
						new FileOutputStream("Bots/" + bot.getName() +".Bot");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(bot);
					out.close();
					fileOut.close();
				}catch(IOException i){
					i.printStackTrace();
				}
				populateActionTable();
			}
		});
		frame.getContentPane().add(btnDelete);

		JButton btnDuplicate = new JButton("Duplicate");
		btnDuplicate.setBounds(350, 63, 100, 23);
		frame.getContentPane().add(btnDuplicate);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.setEnabled(true);
				frame.dispose();
			}
		});
		btnSave.setBounds(20, 227, 89, 23);
		frame.getContentPane().add(btnSave);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parentFrame.setEnabled(true);
				frame.dispose();
			}
		});
		btnCancel.setBounds(119, 227, 89, 23);
		frame.getContentPane().add(btnCancel);
	}
	
	public void populateActionTable(){
		for(int i = 0; i<actionTableModel.getRowCount(); i++){
			actionTableModel.removeRow(i);
		}
		ArrayList<Action> botActions = bot.getActions();
		for(int i = 0; i<botActions.size(); i++){
			String[] rowData = { botActions.get(i).getAction(), botActions.get(i).getConditionType(), String.valueOf(botActions.get(i).getOnTimerDelay()), botActions.get(i).getKeywords(), botActions.get(i).getTweetText() };
			actionTableModel.addRow(rowData);
		}
	}
}
