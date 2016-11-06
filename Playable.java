import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Playable extends JPanel{
	
	private Map map;

	//initial base panels added in frame
	private JPanel northPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel westPanel = new JPanel();
	private JPanel eastPanel = new JPanel();
	private JPanel centerPanel = new JPanel();

	//secondary panels added in the CENTER of eastPanel and westPanel 
	private JPanel pnorthPanel = new JPanel(new BorderLayout());
	private JPanel mnorthPanel = new JPanel(new BorderLayout());

	//panels to hold life hearts. added in pnorthPanel and mnorthPanel (lifeGrids)
	static JPanel plifeGrid = new JPanel(new GridLayout(1,3));
	static JPanel mlifeGrid = new JPanel(new GridLayout(1,3));

	//bomb counters
	static int pacbomb=0;
	static int mayweatherbomb=0;

	//players
	private Pacquiao pacman;
	private Mayweather mayweather;
	
	private JLabel time;

	private boolean isRunning;
	private boolean gameRunning=true;
	private JFrame frame;
	private JFrame gameoverframe; // new frame for to show gameOver

	private JLabel pimage = new JLabel(new ImageIcon(getClass().getResource("images/labels/pimage.png"))); // westPanel side image
	private JLabel mimage = new JLabel(new ImageIcon(getClass().getResource("images/labels/mimage.png"))); // eastPanel side image
	private JLabel pname = new JLabel(new ImageIcon(getClass().getResource("images/labels/pname.png"))); // pacquiao name label
	private JLabel mname = new JLabel(new ImageIcon(getClass().getResource("images/labels/mname.png"))); // mayweather name label
	static JLabel[] pheart = new JLabel[3]; // JLabel array to hold 3 hearts
	static JLabel[] mheart = new JLabel[3]; // JLabel array to hold 3 hearts

	//bomb indexes
	static int i = 0;
	static int j = 0;
	static int k = 0;
	static int l = 0;

	Font font = new Font("Courier New", Font.BOLD, 28);
	public Playable(){
		//playable =true;
		frame = new JFrame("BomberMan");
		frame.setLayout(new BorderLayout());
		map = new Map();

		pacman = map.getPacman();
		mayweather = map.getMayweather();

		centerPanel.setBackground(Color.BLACK);
		northPanel.setBackground(Color.BLACK);
		northPanel.setSize(new Dimension(760, 60));
		this.isRunning = false;

		time= new JLabel("0:00");
		northPanel.add(time);
		startTime();
		time.setFont(font);
		time.setForeground(Color.WHITE);
		
		southPanel.setBackground(Color.BLACK);
		southPanel.setPreferredSize(new Dimension(0,0));
		westPanel.setBackground(Color.BLACK);
		eastPanel.setBackground(Color.BLACK);

		//adds base panels to frame
		frame.add(this);
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.add(westPanel, BorderLayout.WEST);
		frame.add(eastPanel, BorderLayout.EAST);

		//loop to initialize heart labels
		for(int i=0; i<3; i++){
			pheart[i] = new JLabel();
			pheart[i].setIcon(new ImageIcon(getClass().getResource("images/labels/heart.png")));

			mheart[i] = new JLabel();
			mheart[i].setIcon(new ImageIcon(getClass().getResource("images/labels/heart.png")));
		}

		//loop to add heart labels to lifeGrids
		for(int i=0;i<3;i++){
			plifeGrid.add(pheart[i]);
			mlifeGrid.add(mheart[i]);
		}

		plifeGrid.setBackground(Color.BLACK);
		pnorthPanel.add(plifeGrid, BorderLayout.LINE_START); //adds plifeGrid to the very beginning of the layout
		pnorthPanel.add(pimage, BorderLayout.SOUTH); //adds pacquiao side image
		pnorthPanel.setBackground(Color.BLACK); 

		mlifeGrid.setBackground(Color.BLACK);	
		mnorthPanel.add(mlifeGrid, BorderLayout.LINE_START); //adds mlifeGrid to the very beginning of the layout
		mnorthPanel.add(mimage, BorderLayout.SOUTH); //adds mayweather side image
		mnorthPanel.setBackground(Color.BLACK);

		westPanel.setLayout(new BorderLayout());
		westPanel.setSize(300,300);
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setSize(200,300);
		westPanel.add(pname, BorderLayout.NORTH); //adds pacquiao name label
		westPanel.add(pnorthPanel, BorderLayout.CENTER);
		eastPanel.add(mname, BorderLayout.NORTH); //adds mayweather name label
		eastPanel.add(mnorthPanel, BorderLayout.CENTER);

		centerPanel.setPreferredSize(new Dimension(440,440));
		centerPanel.add(map);
		Sound.battle1.loop(); // plays battle/gameplay song in loop

		frame.setSize(800,500);
		frame.setBackground(Color.BLACK);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);

		//keyListeners for each player
		frame.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e){
				int keyCode = e.getKeyCode();

				//will only move if pacman's background color is not Green and the next tile to move on is color Green
				//i & j conditions are for impassable bombs
				if(!map.p[pacman.getXPos()][pacman.getYPos()].getBackground().equals(Color.GREEN)){
					if(keyCode == KeyEvent.VK_W){ // UP movement of pacquiao [xPos-1][yPos]
						if(!(i==pacman.getXPos()-1 && j==pacman.getYPos()) && !(k==pacman.getXPos()-1 && l==pacman.getYPos()) && map.p[pacman.getXPos()-1][pacman.getYPos()].getBackground().equals(Color.GREEN)){
							map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.GREEN);
							map.remove(map.getPLabel());
							pacman.setXPos((pacman.getXPos()-1));
							pacman.setYPos(pacman.getYPos());
							if (Bomb.pacdead == false){ 
								map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.RED);
								map.p[pacman.getXPos()][pacman.getYPos()].add(map.getPLabel());
							}
							
						}
					}

					if(keyCode == KeyEvent.VK_A){ // LEFT movement of pacquiao [xPos][yPos-1]
						if(!(i==pacman.getXPos() && j==pacman.getYPos()-1) && !(k==pacman.getXPos() && l==pacman.getYPos()-1) && map.p[pacman.getXPos()][pacman.getYPos()-1].getBackground().equals(Color.GREEN)){
							map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.GREEN);
							map.remove(map.getPLabel());
							pacman.setXPos(pacman.getXPos());
							pacman.setYPos((pacman.getYPos()-1));
							if (Bomb.pacdead == false){		
								map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.RED);
								map.p[pacman.getXPos()][pacman.getYPos()].add(map.getPLabel());
							}
						}
					}

					if(keyCode == KeyEvent.VK_S){ // DOWN movement of pacquiao [xPos+1][yPos]
						if(!(i==pacman.getXPos()+1 && j==pacman.getYPos()) && !(k==pacman.getXPos()+1 && l==pacman.getYPos()) && map.p[pacman.getXPos()+1][pacman.getYPos()].getBackground().equals(Color.GREEN)){
							map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.GREEN);
							map.remove(map.getPLabel());
							pacman.setXPos((pacman.getXPos()+1));
							pacman.setYPos(pacman.getYPos());
							if (Bomb.pacdead == false){
								map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.RED);
								map.p[pacman.getXPos()][pacman.getYPos()].add(map.getPLabel());
							}
						}
					}

					if(keyCode == KeyEvent.VK_D){ // RIGHT movement of pacquiao [xPos][yPos+1]
						if(!(i==pacman.getXPos() && j==pacman.getYPos()+1) && !(k==pacman.getXPos() && l==pacman.getYPos()+1) && map.p[pacman.getXPos()][pacman.getYPos()+1].getBackground().equals(Color.GREEN)){
							map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.GREEN);
							map.remove(map.getPLabel());
							pacman.setXPos(pacman.getXPos());
							pacman.setYPos((pacman.getYPos()+1));
							if (Bomb.pacdead == false){			///// edited
								map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.RED);
								map.p[pacman.getXPos()][pacman.getYPos()].add(map.getPLabel());
							}
						}
				}

				if(keyCode == KeyEvent.VK_C){// BOMB DROP of pacquiao
					i = pacman.getXPos();	//gets the position of the bomb
					j = pacman.getYPos();
					if (pacbomb == 0){ //allows only one bomb drop per player
						pacbomb++;		
						new Bomb(pacman.getXPos(), pacman.getYPos(), map);	
					}
				}
			}
			//will only move if pacman's background color is not Green and the next tile to move on is color Green
				//i & j conditions are for impassable bombs
			if(!map.p[mayweather.getXPos()][mayweather.getYPos()].getBackground().equals(Color.GREEN)){
				if(keyCode == KeyEvent.VK_UP){ // UP movement of mayweather [xPos-1][yPos]
					if(!(i==mayweather.getXPos()-1 && j==mayweather.getYPos()) && !(k==mayweather.getXPos()-1 && l==mayweather.getYPos()) && map.p[mayweather.getXPos()-1][mayweather.getYPos()].getBackground().equals(Color.GREEN)){
						map.p[mayweather.getXPos()][mayweather.getYPos()].setBackground(Color.GREEN);
						map.remove(map.getMLabel());
						mayweather.setXPos((mayweather.getXPos()-1));
						mayweather.setYPos(mayweather.getYPos());
						if (Bomb.maywdead == false){
							map.p[mayweather.getXPos()][mayweather.getYPos()].setBackground(Color.BLUE);
							map.p[mayweather.getXPos()][mayweather.getYPos()].add(map.getMLabel());
						}
					}
				}

				if(keyCode == KeyEvent.VK_LEFT){ // LEFT movement of mayweather [xPos][yPos-1]
					if(!(i==mayweather.getXPos() && j==mayweather.getYPos()-1) && !(k==mayweather.getXPos() && l==mayweather.getYPos()-1) && map.p[mayweather.getXPos()][mayweather.getYPos()-1].getBackground().equals(Color.GREEN)){
						map.p[mayweather.getXPos()][mayweather.getYPos()].setBackground(Color.GREEN);
						map.remove(map.getMLabel());
						mayweather.setXPos(mayweather.getXPos());
						mayweather.setYPos((mayweather.getYPos()-1));
						if (Bomb.maywdead == false){
							map.p[mayweather.getXPos()][mayweather.getYPos()].setBackground(Color.BLUE);
							map.p[mayweather.getXPos()][mayweather.getYPos()].add(map.getMLabel());
						}					
					}
				}

				if(keyCode == KeyEvent.VK_DOWN){ // DOWN movement of mayweather [xPos+1][yPos]
					if(!(i==mayweather.getXPos()+1 && j==mayweather.getYPos()) && !(k==mayweather.getXPos()+1 && l==mayweather.getYPos()) && map.p[mayweather.getXPos()+1][mayweather.getYPos()].getBackground().equals(Color.GREEN)){
						map.p[mayweather.getXPos()][mayweather.getYPos()].setBackground(Color.GREEN);
						map.remove(map.getMLabel());
						mayweather.setXPos((mayweather.getXPos()+1));
						mayweather.setYPos(mayweather.getYPos());
						if (Bomb.maywdead == false){
							map.p[mayweather.getXPos()][mayweather.getYPos()].setBackground(Color.BLUE);
							map.p[mayweather.getXPos()][mayweather.getYPos()].add(map.getMLabel());
						}
					}
				}

				if(keyCode == KeyEvent.VK_RIGHT){ // RIGHT movement of mayweather [xPos][yPos+1]
					if(!(i==mayweather.getXPos() && j==mayweather.getYPos()+1) && !(k==mayweather.getXPos() && l==mayweather.getYPos()+1) && map.p[mayweather.getXPos()][mayweather.getYPos()+1].getBackground().equals(Color.GREEN)){
						map.p[mayweather.getXPos()][mayweather.getYPos()].setBackground(Color.GREEN);
						map.remove(map.getMLabel());
						mayweather.setXPos(mayweather.getXPos());
						mayweather.setYPos((mayweather.getYPos()+1));
						if (Bomb.maywdead == false){
							map.p[mayweather.getXPos()][mayweather.getYPos()].setBackground(Color.BLUE);
							map.p[mayweather.getXPos()][mayweather.getYPos()].add(map.getMLabel());
						}
					}
				}

				if(keyCode == KeyEvent.VK_M){// BOMB DROP of mayweather
					k = mayweather.getXPos(); //gets the position of the bomb
					l = mayweather.getYPos();
					if ( mayweatherbomb == 0){ //allows only one bomb drop per player
						mayweatherbomb++; 				
						new Bomb(mayweather.getXPos(), mayweather.getYPos(), map);
					}
				}	
			}
		}
			

			public void keyReleased(KeyEvent e){}

			public void keyTyped(KeyEvent e){}

		});
			frame.addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			        
			        int answer = JOptionPane.showConfirmDialog(frame, 
			            "Are you sure you want to quit this game?", "Quit?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE);
         			if (answer == JOptionPane.YES_OPTION){
         				gameRunning = false;
						BomberMenu.playable=false;
						frame.dispose();
						Sound.battle1.stop(); //stops gameplay song
						Sound.menu.loop(); //loops menu song again

						//removes all hearts from lifeGrid to prevent multiple hearts on the succeeding frames
						plifeGrid.removeAll(); 
						mlifeGrid.removeAll();
					}
				}
			});

		

	}

	public Boolean getIsRunning(){
		return this.isRunning;
	}

	public void setIsRunning(Boolean isRunning){
		this.isRunning = isRunning;
	}

	public void startTime(){
		time.setFocusable(false);


		new Thread(new Runnable(){
			public void run(){
				setIsRunning(true);
				for(int i = 180; i>=0; i--){
					if(	i%60 < 10){
						time.setText(Integer.toString(i/60)+":0"+Integer.toString(i%60));
					} else{
						time.setText(Integer.toString(i/60)+":"+Integer.toString(i%60));
					}
					
					try{
						Thread.sleep(1000);
					} catch(Exception e){}
					
					if(pacman.getHp()==0 || mayweather.getHp()==0){
						break;
					}
					if (gameRunning == false){
						break;
					}

				}
				if (gameRunning == !false){
					declareWinner(pacman, mayweather);
					frame.dispose();
					try{Thread.sleep(2000);} catch(Exception e){}
					BomberMenu.playable=false;

				}
			}
		
		}).start();

	}



	public void declareWinner(Pacquiao pacman, Mayweather mayweather){
	Sound.battle1.stop(); // stops gameplay song
	Sound.win.loop(); // loops victory song
	gameoverframe = new JFrame("Game Over");
	JLabel winner = new JLabel();
	gameoverframe.add(winner);
			
		if(pacman.getHp() <= mayweather.getHp()){ //displays frame that shows mayweather victory
			gameoverframe.setSize(447,328);
			winner.setIcon(new ImageIcon(getClass().getResource("images/covers/mayweatherwin.png")));
		}
		else{ //displays frame that shows pacquiao victory
			gameoverframe.setSize(460,276);
			winner.setIcon(new ImageIcon(getClass().getResource("images/covers/pacquiaowin.png")));
		}

		gameoverframe.setResizable(false);
		gameoverframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		gameoverframe.setVisible(true);

		gameoverframe.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosing(WindowEvent e) {
			        
			        int answer = JOptionPane.showConfirmDialog(frame, 
			            "Return to Main Menu?", "Really Closing?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE);
         			if (answer == JOptionPane.YES_OPTION){
         				gameoverframe.dispose();
						Sound.win.stop(); //stops victory song
						Sound.menu.loop(); //loops menu song again

						//removes all heart labels from lifeGrid to avoid multiple hearts on succeeding frames
						plifeGrid.removeAll();
						mlifeGrid.removeAll();
					}
				}
		});

	}

	
}