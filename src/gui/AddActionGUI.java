package gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


import action.Action;
import bot.Bot;

public class AddActionGUI {

	private JFrame frame;
	private JFrame parentFrame;
	private DefaultListModel keywordListModel;
	private JComboBox actionsBox;
	private JComboBox conditionsTypeBox;
	private JTextField delayTextField;
	private Bot bot;


	/**
	 * Create the application.
	 */
	public AddActionGUI(JFrame parentFrame, Bot bot) {
		this.parentFrame = parentFrame;
		this.bot = bot;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 550, 350);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				parentFrame.setEnabled(true);
				frame.dispose();
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.setEnabled(true);
				frame.dispose();
			}
		});
		btnCancel.setBounds(275, 275, 100, 23);
		frame.getContentPane().add(btnCancel);
		
		JButton addActionButton = new JButton("Add Action");
		addActionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addActionToBot();
			}
		});
		addActionButton.setBounds(150,275,100,23);
		frame.getContentPane().add(addActionButton);
		
		JLabel actionsLabel = new JLabel("Action: ");
		actionsLabel.setBounds(10,20,80,23);
		frame.getContentPane().add(actionsLabel);
		
		String[] possibleActions = { "Tweet", "Retweet", "Log", "Favorite" };
		actionsBox = new JComboBox(possibleActions);
		actionsBox.setBounds(100, 20, 100, 23);
		actionsBox.setSelectedIndex(0);
		frame.getContentPane().add(actionsBox);
		
		JLabel conditionTypeLabel = new JLabel("Condition Type: ");
		conditionTypeLabel.setBounds(10,55,80,23);
		frame.getContentPane().add(conditionTypeLabel);
		
		String[] possibleConditionTypes = { "On Keyword", "On Delay", "On Keyword and Delay" };
		conditionsTypeBox = new JComboBox(possibleConditionTypes);
		conditionsTypeBox.setBounds(100, 55, 100, 23);
		conditionsTypeBox.setSelectedIndex(0);
		frame.getContentPane().add(conditionsTypeBox);
		
		JLabel delayLabel = new JLabel ("Delay: ");
		delayLabel.setBounds(10,90,80,23);
		frame.getContentPane().add(delayLabel);
		
		delayTextField = new JTextField();
		delayTextField.setBounds(100,90,100,23);
		frame.getContentPane().add(delayTextField);
		
		JLabel keywordLabel = new JLabel("Keyword: ");
		keywordLabel.setBounds(10,125,80,23);
		frame.getContentPane().add(keywordLabel);
		
		final JTextField keywordTextField = new JTextField();
		keywordTextField.setBounds(100,125,100,23);
		frame.getContentPane().add(keywordTextField);
		
		JLabel keywordListLabel = new JLabel("Keywords");
		keywordListLabel.setBounds(405,15,100,23);
		frame.getContentPane().add(keywordListLabel);
		
		JScrollPane keywordListScrollPane = new JScrollPane();
		keywordListScrollPane.setBounds(350,40,175,175);
		frame.getContentPane().add(keywordListScrollPane);
		
		keywordListModel = new DefaultListModel();
		
		final JList keywordList = new JList();
		keywordList.setModel(keywordListModel);
		keywordListScrollPane.setViewportView(keywordList);
		keywordList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt){
				
			}
		});
		
		JButton addKeywordButton = new JButton ("Add Keyword");
		addKeywordButton.setBounds(215,125,125,23);
		addKeywordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addKeywordToList(keywordTextField.getText());
				keywordTextField.setText("");
			}
		});
		frame.getContentPane().add(addKeywordButton);
		
		JButton deleteKeywordButton = new JButton ("Delete Keyword");
		deleteKeywordButton.setBounds(215,155,125,23);
		deleteKeywordButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int index = keywordList.getSelectedIndex();
				deleteKeywordFromList(index);
			}
		});
		frame.getContentPane().add(deleteKeywordButton);
		
		JLabel tweetTextLabel = new JLabel("Tweet Text: ");
		tweetTextLabel.setBounds(10,200,125,23);
		frame.getContentPane().add(tweetTextLabel);
		
		
		JTextArea tweetTextArea = new JTextArea();
		tweetTextArea.setEditable(true);
		tweetTextArea.setLineWrap(true);
		
		JScrollPane tweetTextAreaScrollPane = new JScrollPane(tweetTextArea);
		tweetTextAreaScrollPane.setBounds(100, 200, 235, 60);
		frame.getContentPane().add(tweetTextAreaScrollPane);
		
	}
	
	public void addActionToBot(){
		//Create the action
		String[] keywords = new String[keywordListModel.getSize()];
		Action action;
		for(int i = 0; i<keywordListModel.getSize(); i++){
			keywords[i] = (String)keywordListModel.getElementAt(i);
		}
		if(!delayTextField.getText().equals("")){
			action = new Action((String)actionsBox.getSelectedItem(), (String)conditionsTypeBox.getSelectedItem(), Long.parseLong((String)delayTextField.getText()), "test Text", keywords);
		} else {
			action = new Action((String)actionsBox.getSelectedItem(), (String)conditionsTypeBox.getSelectedItem(), 0, "test Text", keywords);
		}
		bot.addAction(action);
		
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
		parentFrame.setEnabled(true);
		frame.dispose();
	}
	
	public void addKeywordToList(String keyword){
		keywordListModel.addElement(keyword);
	}
	
	public void deleteKeywordFromList(int index){
		keywordListModel.remove(index);
	}
}
