package Retriever;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

//TODO integrate the VSM into the gui

public class Retriever extends JFrame  implements ActionListener  {
	
	static double totalFval = 0.0;
	static int retrieves = 0;
	double averageFM= 0.0;

    static String file = new String();
    File requirementsDoc, goldenDoc;
    Scanner fScan;

    Set<String> keyWords;
    VSM mathModule;

    List<String> goldenDocs = new LinkedList<String>();
    List<String> testDocs = new LinkedList<String>();
    
    String guiFormat = "%-75s%-20s%-20s%-20s \n";
    static String test = "%-75s%-20.3f%-20.3f%-20.3f \n";
    
    /*-- START OF GUI VARIABLES
     *All of the variables contained in the GUI Section is only used in the gui and not a part of the actual code 
     */
    final JFileChooser fc = new JFileChooser();
    Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    int scrW = screenSize.width;
    int scrH = screenSize.height;

    //GUI Widgets
    JPanel mDisplay, scrollText, button, infoPan, popPan;

    JDialog popUp;
    
    JButton retrieveB, viewGolden;
    JRadioButton analyze;
    JTextArea infoField, popup;
    JLabel pRes;
    JScrollPane infoScroll , popupScroll;
    JCheckBox codeCB, commentCB;


    //booleans for code and comment
    boolean code = true;
    boolean comment = true;
    boolean banalyze = false;

    //---END OF GUI VARS



    public Retriever(){
        //--START OF GUI INITIALIZER CODE
        setTitle("--Golden Retriever--");
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
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 12);
        infoField.setFont(font);
        infoField.setEditable(false);

        //String test = "%-100s %-20s %-20s %-20s \n";
        String toinsert = String.format(guiFormat,"File :", "Recall:" , "Precision:", "F-Measure:");
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


        pRes = new JLabel("Files Retrieved:  ?          Total Precision:  ?          Total Recall:  ?                Total F-Measure: ?  " + "");


        infoPan.add(pRes, BorderLayout.CENTER);



        mDisplay.add(infoPan);



        //button panel
        button = new JPanel(new FlowLayout());
        button.setPreferredSize(new Dimension(600,500));
        
        // button for retrieve
        retrieveB = new JButton("Run Retriever");
        retrieveB.setActionCommand("openDiag");
        retrieveB.setMnemonic(KeyEvent.VK_O);
        retrieveB.setPreferredSize(new Dimension(200,50));
        retrieveB.addActionListener(this);
        
        //button for Golden Compare Pop-Up
        viewGolden = new JButton("View Golden");
        viewGolden.setActionCommand("viewGold");
        viewGolden.setMnemonic(KeyEvent.VK_V);
        viewGolden.setPreferredSize(new Dimension(200,30));
        viewGolden.addActionListener(this);
        
        //popUP display
        
        popPan = new JPanel(new FlowLayout());
        popUp = new JDialog();
        popUp.setTitle("Golden Standard View");
        //popUp.setModal(true);
        popUp.setSize(new Dimension(700,600));
        
        popup = new JTextArea();
        popupScroll = new JScrollPane(popup);
        popupScroll.setPreferredSize(new Dimension(600,500));
        
        popPan.add(popupScroll);  
        
        popUp.add(popPan);
        
        
        
        //radio button for analysis
        analyze = new JRadioButton("with Analysis");
        analyze.setActionCommand("analyzeThis");
        analyze.setMnemonic(KeyEvent.VK_A);
        analyze.setToolTipText("Choose Golden Standard Comparison File");
        analyze.addActionListener(this);

        //  This is for later implementation to take into account code over comment importance
        //      //checkbox for code
        //      codeCB = new JCheckBox("Code");
        //      codeCB.setSelected(true);
        //      codeCB.setActionCommand("code");
        //      codeCB.addActionListener(this);
        //  
        //      //checkbox for comment
        //      commentCB = new JCheckBox("Comment");
        //      commentCB.setSelected(true);
        //      commentCB.setActionCommand("comment");
        //      commentCB.addActionListener(this);
        //      


        button.add(retrieveB);
        button.add(analyze);
        button.add(viewGolden);

        //      button.add(codeCB);
        //      button.add(commentCB);


        mDisplay.add(button);

        r.add(mDisplay);

    }
    
    
    public void actionPerformed(ActionEvent e){


        //TODO  set variables when the check boxes are clicked
        // open second dialogue box for golden on with analysis radio


        if("openDiag".equals(e.getActionCommand())){
        	retrieves++;
            int returnVal = fc.showOpenDialog(Retriever.this);

            if(returnVal == JFileChooser.APPROVE_OPTION){
                System.out.println("Choose Requirement Document.");
                
                requirementsDoc = fc.getSelectedFile();
                file = requirementsDoc.getAbsolutePath();
                
                System.out.println(requirementsDoc.getAbsolutePath());
                
                //showing how to do some stuff with the umlIndexer
                umlIndexer uml = new umlIndexer(file);
                HashMap<String, Integer> keywords = uml.getKeywordMap();
                keyWords = uml.getKeySet();
   
                
                
//                Iterator<String> it = keyWords.iterator();
//                String temp;
//                while (it.hasNext()) {
//                    temp = it.next();
//                    //
//                    System.out.println(temp + " " + keywords.get(temp));
//                }
                
                //
                mathModule = new VSM(keyWords);
                testDocs = mathModule.getDocsList();
            }
            
            f_Measure(testDocs);
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
                    buildGolden(goldenDoc);
                }
                banalyze = true;
            }
            else{
                banalyze = false;
            }
        }

        
        // Opens popUp for the visual golden comparison
        if("viewGold".equals(e.getActionCommand())){
        	popup.setText("");
            popup.append("Golden Files: \n\n");
            for(String s: goldenDocs){
                popup.append(s + "\n");
            }
            
            popUp.setVisible(true);
        }
        
        
        
        // Uncomment to use later in determining importance of  code vs comment     
        //      if("code".equals(e.getActionCommand())){
        //          if(code)
        //              code=false; 
        //          else
        //              code = true;
        //      }
        //      
        //      if("comment".equals(e.getActionCommand())){
        //          if(comment)
        //              comment=false;  
        //          else
        //              comment = true;
        //      }

    }
    
    
    
    //_____---END GUI CODE> BEGIN PROCEDURAL METHODS


    // this gets the goldenfile txt and stores them for future manipulation.
    public void buildGolden(File f){
        testDocs = new LinkedList<String>();
        goldenDocs = new LinkedList<String>();
        
        String filename;
        String blank = "";
        
        try {
            fScan = new Scanner(f);
        } catch (FileNotFoundException e) {}
        
        String line;
        int i = 0;
        while(fScan.hasNextLine()){
            i++;
            line = fScan.nextLine().trim();
            if(i%2==0){
                testDocs.add(Integer.toString(i) +":"+ line);//////////////////////////////////////////////////
            }
            goldenDocs.add(":"+ line);
            
        }

//      //used for testing purposes
//                      int size = goldenDocs.size();
//                      
//                      for (int i = 0; i < size; i++){
//                          filename = goldenDocs.get(i);
//                          infoField.append(String.format(guiFormat, filename, blank, blank, blank));
//                      }
    }
    
    
    
      // Get the running f_measure and total f_measure
        public void f_Measure(List<String> retrieved){
             infoField.setText("");
             String toinsert = String.format(guiFormat,"File :", "Recall:" , "Precision:", "F-Measure:");
             infoField.append(toinsert + "\n");
              
              
              String guiUpdate;
              
              double beta = 2;
              double betaSq = beta*beta;
              double betaPl = 1+betaSq;
              
              double gold_Recall = goldenDocs.size();
              double our_Recall = retrieved.size();
              
              //System.out.println(our_Recall);
              
              
              double goldIntersect = 0.0;
             
              double PR;
              
              double running_FMeasure = 0.000;
              double running_Precision= 0.000;
              double running_Recall = 0.000;
              
              for(String each:goldenDocs){
                  if(retrieved.contains(each)){
                      goldIntersect += 1.000;
                     
                  
                  //System.out.println(goldIntersect + ":" + each);
                  
                  
                  running_Precision = goldIntersect / our_Recall;
                  running_Recall = goldIntersect / gold_Recall;
                  

                  
                  PR = running_Precision * running_Recall;
                  
                  // (1+beta)^2 * (P*R) / (beta^2 * P) + R
                  running_FMeasure = (betaPl * PR)/(( running_Precision * betaSq) + running_Recall);
                  
                  
                  infoField.append(String.format(test, each , running_Recall, running_Precision, running_FMeasure));
                 
                 // System.out.println(String.format(test, each , running_Recall, running_Precision, running_FMeasure));
              }
            }
              totalFval += running_FMeasure;
              averageFM = totalFval / retrieves;
              System.out.println("Average:" + averageFM);
              
              String format = " Files Retrieved:  %.0f        Total Recall:  %.3f                Total Precision:  %.3f          Total F-Measure: %.3f";
              guiUpdate = String.format(format, our_Recall,running_Recall, running_Precision, running_FMeasure);
              pRes.setText(guiUpdate);
        }




 

    public static void main(String[] args) {
        
        // TODO   use keyword list from the umlIndexer call to pass to the database 
        //        Get the string list of the files from the database to start the f2 compare
        //        Determine whether or not to display analysis information
        //        F2 compare the document list and print out the necessary info to the GUI
        //        
        //        
        //String test = "%-40s %-2.3f";
        
        
        SwingUtilities.invokeLater(new Runnable() {
            
            
            
            public void run() {
                Retriever ex = new Retriever();
                ex.setVisible(true);
                

            }
        });
        
        
        

    } 

}