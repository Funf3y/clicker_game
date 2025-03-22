/*- Too greedy (count <800)
- Save system (sortarlo per maggiori click)
- limite ai click negativi*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Finestra extends JFrame implements ActionListener{	
	Container c;
	JPanel p1;
    JPanel p2;
    JButton add, remove, add99, reset;
    JTextArea status;
    private int clicks, total;
    
    
    public Finestra(){
    	super("Clicker game");    	
    	this.setSize(500,500);
    	c=this.getContentPane();
        p1= new JPanel();
        p2= new JPanel();
        
        clicks = 0;
        total = 0;
        
    	c.add(p1);
    	p1.setLayout(new GridLayout(2,1));
    	status = new JTextArea("Welcome to the clicker game!\n(don't get greedy)\n");
    	p1.add(status);
    	p1.add(p2);
    	
    	p2.setLayout(new GridLayout(2,2));
    	add = new JButton("+1");
    	remove = new JButton("-1");
    	add99 = new JButton("+99");
    	reset = new JButton("reset");
    	
    	p2.add(add);
    	p2.add(remove);
    	p2.add(add99);
    	p2.add(reset);
    	
    	add.addActionListener(this);
    	remove.addActionListener(this);
    	add99.addActionListener(this);
    	reset.addActionListener(this);
    	
    	this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
    	
    	if(isGreedy()){
    		//here code to save totals
    		System.out.println("I told you not to be greedy!");
    		System.exit(0);
    	}
    	
        if(e.getSource()==reset){
        	
        	status.setText("Your total was "+total+"\n");
        	
        	clicks = 0;
        	total = 0;
        	status.setText("Welcome to the clicker game!\n(don't get greedy)\n");
        }
        
        if(e.getSource() == add){
        	clicks++;
        	total++;
        	status.setText("+1!\nNew total: "+total);
        }
        
        if(e.getSource() == remove){
        	
        	if(isLow()){
        		status.setText("The total is too low!\nAdd something to it\n");
        	}
        	else{
        		clicks++;
        		total--;
        		status.setText("-1!\nNew total: "+total);
        	}
        }
        
        if(e.getSource() == add99){
        	clicks++;
        	total+= 99;
        	status.setText("+99!!!\nNew total: "+total);
        }
        
        
    }
    
    public boolean isGreedy(){
    	if(total > 799){
    		return true;
    	}
    	return false;
    }
    
    public boolean isLow(){
    	if(total<-99){
    		return true;
    	}
    	return false;
    }
}