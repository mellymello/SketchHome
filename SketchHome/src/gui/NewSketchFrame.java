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

/**
 * Boite de dialogue utilisée pour la fonctionnalité "nouveau document" dans la barre de menu.
 * Propose à l'utilisateur de créer un nouveau plan "standard" ou "personnalisé".
 * Pour un plan standard, on dessine les murs d'une pièce rectangulaire aux dimensions fournies par l'utilisateur.
 *
 */
public class NewSketchFrame extends JFrame {

	private MainFrame sketchHomeFrame;
	
	//panel proposant les boutons OK et Cancel
	private JPanel buttonPanel;
	//panel proposant les options de création de plan
	private JPanel optionPanel;

	/*
	 * dimensions de la pièce rectangulaire du plan standard
	 */
	private JLabel widthLabel;
	private JLabel heightLabel;
	private JLabel errorLabel;

	private JTextField txtWidth;
	private JTextField txtHeight;

	/*
	 * choix du type de plan
	 */
	private JRadioButton standardRadioButton;
	private JRadioButton customRadioButton;
	
	private JButton btnCreateNewSketch;
	private JButton btnCancel;
	
	/**
	 * Constructeur de la fenêtre de dialogue.
	 * @param sketchHomeFrame : fenêtre de l'application qui génèrera le nouveau plan
	 */
	public NewSketchFrame(MainFrame sketchHomeFrame) {
		
		this.sketchHomeFrame = sketchHomeFrame;

		setTitle("Create new plan");
		setPreferredSize(new Dimension(275,200));
		setMinimumSize(new Dimension(275,200));
		setLayout(new BorderLayout());

		makeOptions();
		makeButtons();

		add(optionPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);
		pack();
	}

	/**
	 * Génération de l'interface pour le choix du type de plan à créer.
	 */
	private void makeOptions() {
		optionPanel = new JPanel();
		optionPanel.setLayout(new BorderLayout());
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(4, 2));

		standardRadioButton = new JRadioButton("Standard room");
		standardRadioButton.setActionCommand("Standard room");
		standardRadioButton.setSelected(true);
	    
	    infoPanel.add(standardRadioButton);
	    //JLabel de remplissage pour faire un retour à la ligne
	    infoPanel.add(new JLabel());
	   		
		widthLabel = new JLabel("Width",JLabel.CENTER);
		heightLabel = new JLabel("Height",JLabel.CENTER);
		errorLabel= new JLabel("",JLabel.CENTER);
		errorLabel.setForeground(Color.RED);

		txtWidth = new JTextField("500");
		txtHeight = new JTextField("500");

		infoPanel.add(widthLabel);
		infoPanel.add(txtWidth);

		infoPanel.add(heightLabel);
		infoPanel.add(txtHeight);
		
	    customRadioButton = new JRadioButton("Custom plan");
	    customRadioButton.setActionCommand("Custom plan");
	    
	    infoPanel.add(customRadioButton);
		
		//Grouper les radioButton permet de s'assurer qu'un seul soit sélectionné
	    ButtonGroup group = new ButtonGroup();
	    group.add(standardRadioButton);
	    group.add(customRadioButton);
		
		optionPanel.add(infoPanel,BorderLayout.CENTER);
		optionPanel.add(errorLabel,BorderLayout.SOUTH);
	}

	/**
	 * Génération de l'interface permettant de valider le choix effectué.
	 */
	private void makeButtons() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnCreateNewSketch = new JButton("OK");
		btnCancel = new JButton("Cancel");

		//bouton pour valider la création d'un nouveau plan
		btnCreateNewSketch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				//création plan "standard"
				if(standardRadioButton.isSelected()) {
					if (checkValues()) {
						errorLabel.setText("");
						sketchHomeFrame.resetDrawingBoard(Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtHeight.getText()));
					}
				//création plan "personnalisé"
				} else {
					sketchHomeFrame.resetDrawingBoard();
				}
				dispose();
			}
		});
		
		//bouton pour annuler la création d'un nouveau plan
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		buttonPanel.add(btnCreateNewSketch);
		buttonPanel.add(btnCancel);
	}

	/**
	 * Vérifie les valeurs entrées par l'utilisateur.
	 * @return true si les champs de largeur et longueur contiennent des entiers, false sinon
	 */
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
