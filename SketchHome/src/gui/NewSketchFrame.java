package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import drawableObjects.DrawingBoardContent;

public class NewSketchFrame extends JFrame {

	private MainFrame sketchHomeFrame;
	
	private JPanel buttonPanel;
	private JPanel optionPanel;

	private JLabel widthLabel;
	private JLabel heightLabel;
	private JLabel errorLabel;

	private JTextField txtWidth;
	private JTextField txtHeight;

	private JRadioButton standardRadioButton;
	private JRadioButton customRadioButton;
	
	private JButton btnCreateNewSketch;
	private JButton btnCancel;
	
	public NewSketchFrame(MainFrame sketchHomeFrame) {
		
		this.sketchHomeFrame = sketchHomeFrame;

		setTitle("Create new plan");
		setPreferredSize(new Dimension(275,200));
		setMinimumSize(new Dimension(275,200));
		setLayout(new BorderLayout());

		makeOption();
		makeButtons();

		add(optionPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);
		pack();
	}

	private void makeOption() {
		optionPanel = new JPanel();
		optionPanel.setLayout(new BorderLayout());
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(4, 2));

		standardRadioButton = new JRadioButton("Standard room");
		standardRadioButton.setActionCommand("Standard room");
		standardRadioButton.setSelected(true);
	    
	    infoPanel.add(standardRadioButton);
	    infoPanel.add(new JLabel());
	   		
		widthLabel = new JLabel("Width",JLabel.CENTER);
		heightLabel = new JLabel("Height",JLabel.CENTER);
		errorLabel= new JLabel("",JLabel.CENTER);
		errorLabel.setForeground(Color.RED);

		txtWidth = new JTextField("200");
		txtHeight = new JTextField("200");

		infoPanel.add(widthLabel);
		infoPanel.add(txtWidth);

		infoPanel.add(heightLabel);
		infoPanel.add(txtHeight);
		
	    customRadioButton = new JRadioButton("Custom plan");
	    customRadioButton.setActionCommand("Custom plan");
	    
	    infoPanel.add(customRadioButton);
		
		 //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(standardRadioButton);
	    group.add(customRadioButton);
		
		optionPanel.add(infoPanel,BorderLayout.CENTER);
		optionPanel.add(errorLabel,BorderLayout.SOUTH);
	}

	private void makeButtons() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnCreateNewSketch = new JButton("OK");
		btnCancel = new JButton("Cancel");

		btnCreateNewSketch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(standardRadioButton.isSelected()) {
					if (checkValues()) {
						errorLabel.setText("");
						sketchHomeFrame.resetDrawingBoard(Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtHeight.getText()));
					}
				} else {
					sketchHomeFrame.resetDrawingBoard();
				}
				dispose();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		buttonPanel.add(btnCreateNewSketch);
		buttonPanel.add(btnCancel);
	}

	private boolean checkValues(){
		boolean isOk=true;
		if(txtWidth.getText().isEmpty()){
			isOk=false;
			errorLabel.setText("give a width");
		}
		else if(txtHeight.getText().isEmpty()){
			isOk=false;
			errorLabel.setText("give an height");
		}
		else{
			try{
				Integer.parseInt(txtWidth.getText());
				Integer.parseInt(txtHeight.getText());
			}catch(NumberFormatException nfe){
				isOk= false;
				errorLabel.setText("Dimension must be integer");
			}
		}
			
			
		return isOk;
	}
}
