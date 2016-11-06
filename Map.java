import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.awt.event.*;
import java.applet.Applet;

public class Map extends JPanel{

	private JLabel[][] walls = new JLabel[17][17]; //2D array of walls
	private JLabel[][] brick = new JLabel[17][17]; //2D array of bricks
	public static boolean bombOff = false;	

	//needed labels
	JLabel pacLabel = new JLabel(new ImageIcon(getClass().getResource("images/players/pacman.png")));
	JLabel mayLabel = new JLabel(new ImageIcon(getClass().getResource("images/players/mayweather.png")));

	JPanel [][] p = new JPanel[17][17];
	private char[][] mapArray = new char[17][17];

	private Pacquiao pacman;
	private Mayweather mayweather;
	
	Random r = new Random();

	String path;

	public Map(){
		path = "images/maps/map" + Integer.toString(r.nextInt(5)+1) + ".txt"; // randomizes map filename to be read	
		setLayout(new GridLayout(17, 17));
		setPreferredSize(new Dimension(440,440));
		setBackground(Color.BLACK);

		pacman = new Pacquiao();
		mayweather = new Mayweather();

		readMap();
		
	}

	public void readMap(){ //reads map
		
		for(int i=0; i<17; i+=1){
			for(int j=0; j<17; j+=1){
				p[i][j] = new JPanel(); 
				p[i][j].setBackground(Color.GREEN);	
				p[i][j].setLayout(new BorderLayout());
				this.add(p[i][j]);	
				
			}
		}
	
		try{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = null;
			
			int i = 0;
			while((line = reader.readLine()) != null && i<17){
				for(int j = 0; j < 17; j++){
					mapArray[i][j] = line.charAt(j);
					if(mapArray[i][j]=='B'){ // if reader finds 'B' it is set as a solid/unbreakable block
						walls[i][j] = new JLabel();
                		walls[i][j].setIcon(new ImageIcon("images/blocks/solidbricksmall.png"));
                		p[i][j].add(walls[i][j]);
                		p[i][j].setBackground(Color.GRAY);

					} else if(mapArray[i][j]=='E'){ //if reader finds 'E' it is set as a breakable block
						brick[i][j] = new JLabel();
                		brick[i][j].setIcon(new ImageIcon("images/blocks/bricksmall.png"));
                		p[i][j].add(brick[i][j]);
                		p[i][j].setBackground(Color.LIGHT_GRAY);
					}
				}
				i++;
			}
			positionPacman();
			positionMayweather();

		} catch(Exception e){}
	}	
	
	public void positionPacman(){ // sets 'pacquiao' bomber at a random position on the map
		do{
			pacman.setXPos(r.nextInt(17));
			pacman.setYPos(r.nextInt(17));
		}while(mapArray[pacman.getXPos()][pacman.getYPos()]=='B' || mapArray[pacman.getXPos()][pacman.getYPos()]=='E' || (mayweather.getXPos()==pacman.getXPos() && mayweather.getYPos()==pacman.getYPos()));
		
		p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.RED);
		p[pacman.getXPos()][pacman.getYPos()].add(pacLabel);
		pacLabel.setName("pacLabel");
		Bomb.pacdead = false;
	}

	public void positionMayweather(){ // sets 'mayweather' bomber at a random position on the map
		do{
			mayweather.setXPos(r.nextInt(17));
			mayweather.setYPos(r.nextInt(17));

		}while(mapArray[mayweather.getXPos()][mayweather.getYPos()]=='B' || mapArray[mayweather.getXPos()][mayweather.getYPos()]=='E' || (mayweather.getXPos()==pacman.getXPos() && mayweather.getYPos()==pacman.getYPos()));
		
		p[mayweather.getXPos()][mayweather.getYPos()].setBackground(Color.BLUE);
		p[mayweather.getXPos()][mayweather.getYPos()].add(mayLabel);
		mayLabel.setName("mayLabel");
		Bomb.maywdead = false;
	}

	//getters
	public JLabel getPLabel(){
		return this.pacLabel;
	}

	public JLabel getMLabel(){
		return this.mayLabel;
	}

	public Pacquiao getPacman(){
		return this.pacman;
	}

	public Mayweather getMayweather(){
		return this.mayweather;
	}

	public JLabel getWall(int x, int y){
		return this.brick[x][y];
	}

}
