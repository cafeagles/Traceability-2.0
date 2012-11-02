package Retriever;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BoxLayout;
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
	final JFileChooser fc = new JFileChooser();
	Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	int scrW = screenSize.width;
	int scrH = screenSize.height;

	File requirementsDoc, goldenDoc;

	//booleans for code and comment
	boolean code = true;
	boolean comment = true;
	boolean banalyze = false;

	public Retriever(){
		setTitle("Retriever");

		setSize(scrW/2, scrH/2);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		buildGUI(this);
	}


	public void buildGUI(Retriever r){


		JPanel mDisplay, scrollText, button, infoPan;

		JButton retrieveB;
		JRadioButton analyze;
		JTextArea infoField,r_Dat,p_Dat,f_Dat;
		JLabel pRes, rRes,fRes , pLab, rLab, fLab, fileLab;
		JScrollPane infoScroll,r_DatS,p_DatS,f_DatS;
		JCheckBox codeCB, commentCB;


		mDisplay = new JPanel(new GridLayout(2,2,10,10));

		// Text area for the Scroll Area

		infoField = new JTextArea();
		r_Dat = new JTextArea();
		p_Dat = new JTextArea();
		f_Dat = new JTextArea();


		//ScrollPane for text area
		scrollText = new JPanel(new GridLayout(2,1,10,-50));
		//scrollText.setPreferredSize(new Dimension(scrW/3,100));

		//    	 rLab = new JLabel("");
		//    	 pLab = new JLabel("");
		//    	 fLab = new JLabel("");
		fileLab = new JLabel("File:                                            |     Recall:     |         Precision:       |     F-Measure:");


		infoScroll = new JScrollPane(infoField);  
		//    	 r_DatS =  new JScrollPane(r_Dat);
		//    	 p_DatS =  new JScrollPane(p_Dat);
		//    	 f_DatS =  new JScrollPane(r_Dat);
		//    	 

		//infoScroll.setPreferredSize(new Dimension(scrW/3,scrH/3));

		scrollText.add(fileLab);
		//    	 scrollText.add(pLab);
		//    	 scrollText.add(rLab);
		//    	 scrollText.add(fLab);

		scrollText.add(infoScroll);
		//    	 scrollText.add(p_DatS);
		//    	 scrollText.add(r_DatS);
		//    	 scrollText.add(f_DatS);
		//    	 
		mDisplay.add(scrollText);


		// text area for f- data
		infoPan = new JPanel();
		infoPan.setLayout(new BorderLayout());


		JLabel blank = new JLabel(" ");

		pRes = new JLabel("Total Precision:  (%s) \n    Total Recall:   (%s)  \n    Total F-Measure:  (%s)");



		infoPan.add(pRes, BorderLayout.CENTER);
		//infoPan.add(rLab);
		//infoPan.add(fLab);



		mDisplay.add(infoPan);



		// button for retrieve
		button = new JPanel(new FlowLayout());
		button.setPreferredSize(new Dimension(scrW/3,500));
		retrieveB = new JButton("Run Retriever");
		retrieveB.setActionCommand("openDiag");
		retrieveB.setMnemonic(KeyEvent.VK_O);
		retrieveB.setPreferredSize(new Dimension(200,50));
		retrieveB.addActionListener(this);

		analyze = new JRadioButton("with Analysis");
		analyze.setActionCommand("analyzeThis");
		analyze.setMnemonic(KeyEvent.VK_A);
		analyze.addActionListener(this);

		codeCB = new JCheckBox("Code");
		codeCB.setSelected(true);
		codeCB.setActionCommand("code");
		codeCB.addActionListener(this);
		// codeCB.addActionListener(this);
		commentCB = new JCheckBox("Comment");
		commentCB.setSelected(true);
		commentCB.setActionCommand("comment");
		commentCB.addActionListener(this);
		// commentCB.addActionListener(this);


		button.add(retrieveB);
		button.add(analyze);
		button.add(codeCB);
		button.add(commentCB);


		mDisplay.add(button);

		r.add(mDisplay);

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
		
		if("code".equals(e.getActionCommand())){
			if(code)
				code=false;	
			else
				code = true;
		}
		
		if("comment".equals(e.getActionCommand())){
			if(comment)
				comment=false;	
			else
				comment = true;
		}

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
