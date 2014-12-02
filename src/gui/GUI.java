package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Utilities;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.swing.JCheckBox;

import twitter.TwitterHelper;
import bot.Bot;
import action.ActionPerformer;

public class GUI {

	private JFrame frame;
	static JScrollPane botStatusScrollPane = new JScrollPane();
	private DefaultListModel botList;
	static int listIndex;
	static String listValue;
	JList<String> botListPanel;
	private static String statusText = "";
	final static JTextArea botStatusTextArea = new JTextArea();
	final static JCheckBox autoScrollToBottomCheckBox = new JCheckBox("Auto Scroll to Bottom");
	private ActionPerformer actionPerformer;

	/**
	 * Create the window.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(new Dimension(700, 700));
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		botList = new DefaultListModel();
		frame.addWindowListener(new WindowAdapter(){
			public void windowActivated(WindowEvent e){
				loadBotList(botList);
			}
		});
		actionPerformer = new ActionPerformer();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 684, 600);
		frame.getContentPane().add(tabbedPane);

		JPanel runBotsPanel = new JPanel();
		runBotsPanel.setBounds(0, 30, 680, 430);
		//frame.getContentPane().add(botsPanel);
		runBotsPanel.setLayout(null);

		
		autoScrollToBottomCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(autoScrollToBottomCheckBox.isSelected()){
            		botStatusScrollPane.getVerticalScrollBar().setValue(botStatusScrollPane.getVerticalScrollBar().getMaximum());
            	}
			}
		});
		autoScrollToBottomCheckBox.setBounds(150, 335, 145, 23);
		runBotsPanel.add(autoScrollToBottomCheckBox);

		JScrollPane botListScrollpPane = new JScrollPane();
		botListScrollpPane.setBounds(10, 25, 130, 170);
		runBotsPanel.add(botListScrollpPane);

		botListPanel = new JList<String>();
		botListScrollpPane.setViewportView(botListPanel);
		loadBotList(botList);
		botListPanel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt){
				listIndex = botListPanel.getSelectedIndex();
				listValue = botListPanel.getSelectedValue();
			}
		});
		botListPanel.setModel(botList);

		botStatusScrollPane.setBounds(150, 25, 515, 300);
		runBotsPanel.add(botStatusScrollPane);

		botStatusTextArea.setEditable(false);
		botStatusTextArea.setLineWrap(true);
		botStatusScrollPane.setViewportView(botStatusTextArea);

		tabbedPane.addTab("Run Bots", runBotsPanel);

		JLabel lblBots = new JLabel("Bots");
		lblBots.setBounds(60, 9, 46, 14);
		runBotsPanel.add(lblBots);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(385, 11, 46, 14);
		runBotsPanel.add(lblStatus);

		JButton btnNewButton = new JButton("Stop");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformer.stopBot();
			}
		});
		btnNewButton.setBounds(35, 239, 80, 23);
		runBotsPanel.add(btnNewButton);

		final JButton btnRunBot = new JButton("Run");
		btnRunBot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        try{
					Bot bot;
					FileInputStream fileIn =
						new FileInputStream("Bots/" + listValue);
					ObjectInputStream in = new ObjectInputStream(fileIn);
					bot = (Bot)in.readObject();
					in.close();
					fileIn.close();
					actionPerformer.runBot(bot);
				}catch(IOException i){
					i.printStackTrace();
				}catch(ClassNotFoundException i){
					i.printStackTrace();
				}
			}
		});
		btnRunBot.setBounds(35, 205, 80, 23);
		runBotsPanel.add(btnRunBot);

		JPanel editBotsPanel = new JPanel();
		tabbedPane.addTab("Edit Bots", null, editBotsPanel, null);
		editBotsPanel.setLayout(null);
		
		JLabel botsLabel = new JLabel("Bots");
		botsLabel.setBounds(60, 9, 46, 14);
		editBotsPanel.add(botsLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 25, 130, 170);
		editBotsPanel.add(scrollPane);

		final JList<String> list = new JList<String>();
		list.setModel(botList);
		scrollPane.setViewportView(list);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt){
				listIndex = list.getSelectedIndex();
				listValue = list.getSelectedValue();
			}
		});

		JButton btnEdit = new JButton("Modify");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Bot bot;
					FileInputStream fileIn =
						new FileInputStream("Bots/" + listValue);
					ObjectInputStream in = new ObjectInputStream(fileIn);
					bot = (Bot)in.readObject();
					in.close();
					fileIn.close();
					ModifyBotGUI modifyBotGui = new ModifyBotGUI(frame, bot);
					frame.setEnabled(false);
				}catch(IOException i){
					i.printStackTrace();
				}catch(ClassNotFoundException i){
					i.printStackTrace();
				}
			}
		});
		btnEdit.setBounds(150, 85, 89, 23);
		editBotsPanel.add(btnEdit);

		JButton btnAddNew = new JButton("Add New");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBotGUI addBotGui = new AddBotGUI(frame);
				frame.setEnabled(false);
			}
		});
		btnAddNew.setBounds(150, 25, 89, 23);
		editBotsPanel.add(btnAddNew);

		JButton btnDeleteBot = new JButton("Delete");
		btnDeleteBot.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				DeleteBotGUI deleteBotGUI = new DeleteBotGUI(frame);
				frame.setEnabled(false);
			}
		});
		btnDeleteBot.setBounds(150, 55, 89, 23);
		editBotsPanel.add(btnDeleteBot);
	}
	
	public void loadBotList(DefaultListModel botList){
		botList.removeAllElements();
		File folder;
		File[] listOfFiles;
		folder = new File(System.getProperty("user.dir") + "/Bots");
		listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				botList.addElement(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
	}
	
	public static void deleteBot(){
		File file = new File(System.getProperty("user.dir") + "/Bots/" + listValue);
		file.delete();
	}
	
	public static void updateStatusText(String textToAdd){
		statusText = statusText + textToAdd;
		botStatusTextArea.setText(statusText);
		if(botStatusTextArea.getLineCount()>150){
		    while(botStatusTextArea.getLineCount() > 70){
				int end;
				try {
					end = botStatusTextArea.getLineEndOffset(0);
					botStatusTextArea.replaceRange("", 0, end);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
		    }
			statusText = botStatusTextArea.getText();
		}
		if(autoScrollToBottomCheckBox.isSelected()){
		    botStatusScrollPane.getVerticalScrollBar().setValue(botStatusScrollPane.getVerticalScrollBar().getMaximum());
		}
	}
}
