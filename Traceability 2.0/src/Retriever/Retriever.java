package Retriever;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Retriever extends JFrame  implements ActionListener  {


	static String file = new String();
	File requirementsDoc, goldenDoc;
	Scanner fScan;


	/*-- START OF GUI VARIABLES
	 *All of the variables contained in the GUI Section is only used in the gui and not a part of the actual code 
	 */
	final JFileChooser fc = new JFileChooser();
	Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	int scrW = screenSize.width;
	int scrH = screenSize.height;

	//GUI Widgets
	JPanel mDisplay, scrollText, button, infoPan;

	JButton retrieveB;
	JRadioButton analyze;
	JTextArea infoField;
	JLabel pRes;
	JScrollPane infoScroll;
	JCheckBox codeCB, commentCB;


	//booleans for code and comment
	boolean code = true;
	boolean comment = true;
	boolean banalyze = false;

	//---END OF GUI VARS



	public Retriever(){
		//--START OF GUI INITIALIZER CODE
		setTitle("Retriever");
		setSize(scrW, scrH/2);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		buildGUI(this);
		//---END OF GUI INITIALIZER CODE



	}


	public void buildGUI(Retriever r){



		mDisplay = new JPanel(new GridLayout(2,2,10,10));

		// Text area for the Scroll Area

		infoField = new JTextArea();
		infoField.setEditable(false);

		String test = "%-200s %-20s %-20s %-20s \n";
		String toinsert = String.format(test,"File :", "Recall:" , "Precision:", "F-Measure:");
		infoField.append(toinsert + "\n");


		//ScrollPane for text area
		scrollText = new JPanel(new GridLayout(1,1));

		// this makes the infoField scrollable
		infoScroll = new JScrollPane(infoField);  


		scrollText.add(infoScroll);
		mDisplay.add(scrollText);


		// text area for f- data
		infoPan = new JPanel();
		infoPan.setLayout(new BorderLayout());


		pRes = new JLabel("Total Precision:  ?          Total Recall:  ?                Total F-Measure: ?  " +
				"");


		infoPan.add(pRes, BorderLayout.CENTER);



		mDisplay.add(infoPan);



		// button for retrieve
		button = new JPanel(new FlowLayout());
		button.setPreferredSize(new Dimension(600,500));
		retrieveB = new JButton("Run Retriever");
		retrieveB.setActionCommand("openDiag");
		retrieveB.setMnemonic(KeyEvent.VK_O);
		retrieveB.setPreferredSize(new Dimension(200,50));
		retrieveB.addActionListener(this);

		//radio button for analysis
		analyze = new JRadioButton("with Analysis");
		analyze.setActionCommand("analyzeThis");
		analyze.setMnemonic(KeyEvent.VK_A);
		analyze.setToolTipText("Choose Golden Standard Comparison File");
		analyze.addActionListener(this);

		//  This is for later implementation to take into account code over comment importance
		//		//checkbox for code
		//		codeCB = new JCheckBox("Code");
		//		codeCB.setSelected(true);
		//		codeCB.setActionCommand("code");
		//		codeCB.addActionListener(this);
		//	
		//		//checkbox for comment
		//		commentCB = new JCheckBox("Comment");
		//		commentCB.setSelected(true);
		//		commentCB.setActionCommand("comment");
		//		commentCB.addActionListener(this);
		//		


		button.add(retrieveB);
		button.add(analyze);

		//		button.add(codeCB);
		//		button.add(commentCB);


		mDisplay.add(button);

		r.add(mDisplay);

	}


	public void buildGolden(File f){
		fScan = new Scanner(inF);
		
	}


	public void actionPerformed(ActionEvent e){


		//TODO  set variables when the check boxes are clicked
		// open second dialogue box for golden on with analysis radio


		if("openDiag".equals(e.getActionCommand())){
			int returnVal = fc.showOpenDialog(Retriever.this);

			if(returnVal == JFileChooser.APPROVE_OPTION){
				System.out.println("Choose Requirement Document.");
				requirementsDoc = fc.getSelectedFile();
				file = requirementsDoc.getAbsolutePath();
				System.out.println(requirementsDoc.getAbsolutePath());
				umlIndexer uml = new umlIndexer(file);
				HashMap<String, Integer> keywords = uml.getKeywordMap();
				Set<String> keyset = uml.getKeySet();
				Iterator<String> it = keyset.iterator();
				String temp;
				while (it.hasNext()) {
					temp = it.next();
					System.out.println(temp + " " + keywords.get(temp));
				}
			}
		}


		// Opens the dialouge box for the golden text file if it is chosen
		if("analyzeThis".equals(e.getActionCommand())){

			// checks to see if the radio button is selected
			if(!banalyze){
				System.out.println("Choose Golden Standard File.");
				int returnVal = fc.showOpenDialog(Retriever.this);

				if(returnVal == JFileChooser.APPROVE_OPTION){
					goldenDoc = fc.getSelectedFile();
					System.out.println(goldenDoc.getAbsolutePath());
				}
				banalyze = true;
			}
			else{
				banalyze = false;
			}
		}

		// Uncomment to use later in determining importance of  code vs comment		
		//		if("code".equals(e.getActionCommand())){
		//			if(code)
		//				code=false;	
		//			else
		//				code = true;
		//		}
		//		
		//		if("comment".equals(e.getActionCommand())){
		//			if(comment)
		//				comment=false;	
		//			else
		//				comment = true;
		//		}

	}



	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Retriever ex = new Retriever();
				ex.setVisible(true);

			}
		});

	} 

}
