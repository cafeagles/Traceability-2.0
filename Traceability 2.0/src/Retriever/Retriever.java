package Retriever;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Retriever extends JFrame  implements ActionListener  {

    JButton b1;
    final JFileChooser fc = new JFileChooser();
    
        
    public Retriever(){
       b1 = new JButton("Open");
       
       b1.setActionCommand("openDiag");
       b1.addActionListener(this);
       add(b1);
       setTitle("Simple example");
       setSize(300, 200);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Retriever ex = new Retriever();
                ex.setVisible(true);
            }
        });
    }
    
    public void actionPerformed(ActionEvent e){
        if("openDiag".equals(e.getActionCommand())){
            int returnVal = fc.showOpenDialog(Retriever.this);
            
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File f = fc.getSelectedFile();
                System.out.println(f.getAbsolutePath());
            }
        }
            
    }
    
    
    
}
