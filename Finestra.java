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
    private JButton add, remove, add99, reset;
    private JTextArea status;
    private int clicks, total, count99, partialCount;
    
    
    public Finestra(){
    	//let there be light (me when opening a window)
    	super("Clicker game");    	
    	this.setSize(500,500);
    	c=this.getContentPane();
        p1= new JPanel();
        p2= new JPanel();
        
        //initializing counters
        clicks = 0;
        total = 0;
        count99 = 0;
        partialCount = 0;
        
        //building and adding elements to the window
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
    	
    	//adding Action Listeners (sometimes my genius in commenting is almost frightening)
    	add.addActionListener(this);
    	remove.addActionListener(this);
    	add99.addActionListener(this);
    	reset.addActionListener(this);
    	
    	this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
    	
    	//checks if you have been not [very greedy] (pressing other buttons besides add99) for enough times
    	if(partialCount>29){
    		partialCount = 0;
    		count99 = 0;
    	}
    	
    	//prints total and resets everything
        if(e.getSource()==reset){
        	
        	status.setText("Your total was "+total+"\n");
        	
        	clicks = 0;
        	total = 0;
        	partialCount=0;
        	count99=0;
        	status.setText("Welcome to the clicker game!\n(don't get greedy)\n");
        }
        
        //adds one to total and ++'s the needed counters
        if(e.getSource() == add){
        	clicks++;
        	partialCount++;
        	total++;
        	status.setText("+1!\nNew total: "+total);
        }
        
        //removes one from total and ++'s and --'s the needed counters
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
        
        //adds 99 to total and ++'s the needed counters
        if(e.getSource() == add99){
        	clicks++;
        	count99++;
        	total+= 99;
        	status.setText("+99!!!\nNew total: "+total);
        }
        
        //checks if you have been greedy
        if(isGreedy()){
    		saveGreedy();
        	
        	
    		System.out.println("I told you not to be greedy!");
    		System.exit(0);
    	}
    	
    	//checks if you have won :)
    	if(isWinning()){
    		
    		saveScore();
    		
    		System.out.println("Congrats!\nYou have been greedy just the right amount to keep playing :)\nYour prize is winning the game\n\n\nThank you for playing my silly game :))");
    		System.exit(0);
    	}
    }
    
    //returns true if has pressed add99 too many times or if the total is too high
    private boolean isGreedy(){
    	return (count99 > 5 || total > 800);
    }
                                                                                    
    //returns if the total is too low (can't be too generous)
    private boolean isLow(){
    	return total<-99;
    }
    
    //it's pretty self-explanatory I think
    private boolean isWinning(){
    	return clicks>999;
    }
    
    //saves the new score in the scores file (and when it was saved)
    private void saveScore(){
    	int score = total+partialCount-count99;
    	save(score, "");
    }
    
    //prints on the scores file that you have been greedy
    private void saveGreedy(){
    	save(0, "Greedy!");
    }
    
    private void save(int score, String greedy){
    	String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
    	String stringOut = "";
    	
    	//adds current date and the winning score to the score file
    	try(FileReader reader = new FileReader("score_file.txt"); Scanner scan = new Scanner(reader); PrintWriter out = new PrintWriter("score_file.txt")){
    		
    		while(scan.hasNextLine()){
    			stringOut+= scan.nextLine()+"\n";
    			System.out.println(stringOut+" <- test");
    		}
    		
    		stringOut+=timeStamp+" "+score+" "+greedy+"\n";
    		out.println(stringOut);
    		
    	}
    	catch(IOException e){
    		System.out.println("Something wrong with the IO happened. Whoopsie");
    		System.exit(1);
    	}
    }
}