import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Finestra extends JFrame implements ActionListener{	
	private Container c;
	private JPanel p1;
    private JPanel p2;
    private JButton add, remove, add99, reset, submit;
    private JTextArea status;
    private int clicks, total, count99, partialCount;
    private JTextField name;
    private String namePlayer;
    
    
    public Finestra(){
    	//let there be light (me when opening a window)
    	super("Clicker game");
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	this.setSize(500,500);
    	c=this.getContentPane();
        p1= new JPanel();
        p2= new JPanel();
        
        //counters (+name)
        clicks = 0;
        total = 0;
        count99 = 0;
        partialCount = 0;
        namePlayer = "";
        
        //building and adding elements to the window
    	c.add(p1);
    	p1.setLayout(new GridLayout(2,1));
    	status = new JTextArea("Welcome to the clicker game!\n(don't get greedy)\n");
    	p1.add(status);
    	p1.add(p2);
    	
    	p2.setLayout(new GridLayout(3,2));
    	add = new JButton("+1");
    	remove = new JButton("-1");
    	add99 = new JButton("+99");
    	reset = new JButton("reset");
    	name = new JTextField("[Insert your name here]");
    	submit = new JButton("Submit name");
    	
    	p2.add(add);
    	p2.add(remove);
    	p2.add(add99);
    	p2.add(reset);
    	p2.add(name);
    	p2.add(submit);
    	
    	//adding Action Listeners (sometimes my genius in commenting is almost frightening)
    	add.addActionListener(this);
    	remove.addActionListener(this);
    	add99.addActionListener(this);
    	reset.addActionListener(this);
    	submit.addActionListener(this);
    	
    	this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
    	
    	if(e.getSource()==submit){
    		namePlayer = name.getText();
    		status.setText("Name submitted!");
    	}
    	
    	//checks if you have been not [very greedy] (pressing other buttons besides add99) for enough times
    	if(partialCount>29){
    		partialCount = 0;
    		count99 = 0;
    	}
    	
        if(e.getSource()==reset){
        	
        	status.setText("Your total was "+total+"\n");
        	
        	clicks = 0;
        	total = 0;
        	partialCount=0;
        	count99=0;
        	status.setText("Welcome to the clicker game!\n(don't get greedy)\n");
        }
        
        if(e.getSource() == add){
        	clicks++;
        	partialCount++;
        	total++;
        	status.setText("+1!\nNew total: "+total);
        }
        
        if(e.getSource() == remove){
        	
        	if(isLow()){
        		status.setText("The total is too low!\nAdd something to it\n");
        	}
        	else{
        		clicks++;
        		partialCount++;
        		total--;
        		status.setText("-1!\nNew total: "+total);
        	}
        }
        
        if(e.getSource() == add99){
        	clicks++;
        	count99++;
        	total+= 99;
        	status.setText("+99!!!\nNew total: "+total);
        }
        
        if(isGreedy()){
        	save(0, "Greedy!");
        	
        	
    		System.out.println("I told you not to be greedy!");
    		System.exit(0);
    	}
    	
    	//checks if you have won :)
    	if(isWinning()){
    		
    		saveScore();
    		
    		System.out.println("Congrats!\nYou have been greedy just the right amount to keep playing :)\nYour prize is winning the game (and losing The Game)\n\n\nThank you for playing my silly game :))");
    		System.exit(0);
    	}
    }
    
    private boolean isGreedy(){
    	return (count99 > 5 || total > 800);
    }
                                                                                    
    //can't be too generous
    private boolean isLow(){
    	return total<-99;
    }
    
    private boolean isWinning(){
    	return clicks>999;
    }
    
    private void saveScore(){
    	int score = total+partialCount-count99;
    	if(score>999){
    		save(score, "True win");
    	}
    	else{
    		save(score, "");
    	}
    }
    
    private void save(int score, String greedy){
    	String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
    	String stringOut = "";
    	
    	//adds current date time and the ending condition
    	try(FileReader reader = new FileReader("score_file.txt"); Scanner scan = new Scanner(reader)){
    		
    		while(scan.hasNextLine()){
    			stringOut+= scan.nextLine()+"\n";
    		}
    		
    		stringOut+=timeStamp+" "+namePlayer+" "+score+" "+greedy;
    		
    		PrintWriter out = new PrintWriter("score_file.txt");
    		out.println(stringOut);
    		out.close();
    		
    	}
    	catch(IOException e){
    		System.out.println("Something wrong with the IO happened. Whoopsie");
    		System.exit(1);
    	}
    }
}