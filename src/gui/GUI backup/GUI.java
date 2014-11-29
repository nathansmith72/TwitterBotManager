package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Utilities;

import java.awt.Dimension;

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

public class GUI {

	private JFrame frame;
	private JScrollPane botStatusScrollPane;

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

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 684, 600);
		frame.getContentPane().add(tabbedPane);

		JPanel runBotsPanel = new JPanel();
		runBotsPanel.setBounds(0, 30, 680, 430);
		//frame.getContentPane().add(botsPanel);
		runBotsPanel.setLayout(null);

		final JCheckBox autoScrollToBottomCheckBox = new JCheckBox("Auto Scroll to Bottom");
		autoScrollToBottomCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(autoScrollToBottomCheckBox.isSelected()){
            		botStatusScrollPane.getVerticalScrollBar().setValue(botStatusScrollPane.getVerticalScrollBar().getMaximum());
            	}
			}
		});
		autoScrollToBottomCheckBox.setBounds(120, 335, 145, 23);
		runBotsPanel.add(autoScrollToBottomCheckBox);

		JScrollPane botListScrollpPane = new JScrollPane();
		botListScrollpPane.setBounds(10, 25, 100, 170);
		runBotsPanel.add(botListScrollpPane);

		JList<String> botList = new JList<String>();
		botListScrollpPane.setViewportView(botList);
		botList.setModel(new AbstractListModel<String>() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"StealthMountain", "SeinfeldQuotes"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});

		botStatusScrollPane = new JScrollPane();
		botStatusScrollPane.setBounds(120, 25, 545, 300);
		runBotsPanel.add(botStatusScrollPane);

		final JTextArea botStatusTextArea = new JTextArea();
		botStatusTextArea.setEditable(false);
		botStatusTextArea.setLineWrap(true);
		botStatusScrollPane.setViewportView(botStatusTextArea);

		tabbedPane.addTab("Run Bots", runBotsPanel);

		JLabel lblBots = new JLabel("Bots");
		lblBots.setBounds(45, 11, 46, 14);
		runBotsPanel.add(lblBots);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(300, 11, 46, 14);
		runBotsPanel.add(lblStatus);

		JButton btnNewButton = new JButton("Stop");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TwitterHelper.stopFilterStream();
				botStatusTextArea.setText(botStatusTextArea.getText() + "Stopping Bot" + "\n\n");
			}
		});
		btnNewButton.setBounds(20, 239, 80, 23);
		runBotsPanel.add(btnNewButton);

		final JButton btnRunBot = new JButton("Run");
		btnRunBot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        StatusListener listener = new StatusListener(){
		            public void onStatus(Status status) {
		            	botStatusTextArea.setText(botStatusTextArea.getText() + status.getUser().getName() + " : " + status.getText() + "\n\n");
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
		            	}
		            	if(autoScrollToBottomCheckBox.isSelected()){
		            		botStatusScrollPane.getVerticalScrollBar().setValue(botStatusScrollPane.getVerticalScrollBar().getMaximum());
		            	}
		            }
		            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
		            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
		            public void onException(Exception ex) {
		                ex.printStackTrace();
		            }
					@Override
					public void onScrubGeo(long arg0, long arg1) {
						// TODO Auto-generated method stub

					}
					@Override
					public void onStallWarning(StallWarning arg0) {
						// TODO Auto-generated method stub

					}
		        };
		        String[] keywords = {"Science"};
		        TwitterHelper.startFilterStream(keywords, listener);
		        botStatusTextArea.setText(botStatusTextArea.getText() + "Running bot" +"\n\n");
			}
		});
		btnRunBot.setBounds(20, 205, 80, 23);
		runBotsPanel.add(btnRunBot);

		JPanel editBotsPanel = new JPanel();
		tabbedPane.addTab("Edit Bots", null, editBotsPanel, null);
		editBotsPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 100, 170);
		editBotsPanel.add(scrollPane);

		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"SilentMountain", "SeinfeldQuotes"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);

		JButton btnEdit = new JButton("Modify");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ModifyBotGUI modifyBotGui = new ModifyBotGUI(frame);
				frame.setEnabled(false);
			}
		});
		btnEdit.setBounds(120, 79, 89, 23);
		editBotsPanel.add(btnEdit);

		JButton btnAddNew = new JButton("Add New");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBotGUI addBotGui = new AddBotGUI(frame);
				frame.setEnabled(false);
			}
		});
		btnAddNew.setBounds(120, 11, 89, 23);
		editBotsPanel.add(btnAddNew);

		JButton btnDeleteBot = new JButton("Delete");
		btnDeleteBot.setBounds(120, 45, 89, 23);
		editBotsPanel.add(btnDeleteBot);
	}
}
