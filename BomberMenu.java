import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BomberMenu extends JFrame{
	
	BorderLayout border = new BorderLayout();
	Container c = getContentPane();

	//base panels added to contentPane
	private JPanel northPanel = new JPanel();
	private JPanel eastPanel = new JPanel();
	private JPanel westPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel centerPanel = new JPanel();

	//needed labels
	JLabel titleCover = new JLabel(new ImageIcon(getClass().getResource("images/covers/titleCover.png")));
	final JLabel play = new JLabel(new ImageIcon(getClass().getResource("images/buttons/playbttn.png")));
	final JLabel exit = new JLabel(new ImageIcon(getClass().getResource("images/buttons/exitbttn.png")));
	final JLabel help = new JLabel(new ImageIcon(getClass().getResource("images/buttons/helpbttn.png")));
	
	static boolean playable = false; // to check if a 'game' panel is open
	static boolean howtoopen = false; // to check if a 'how to' panel is open


	public BomberMenu(){
		this.setTitle("BomberMan");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(border);

		c.setLayout(border);
		c.setPreferredSize(new Dimension(550,500));
		
		northPanel.setBackground(Color.BLACK);
		eastPanel.setBackground(Color.BLACK);
		westPanel.setBackground(Color.BLACK);
		southPanel.setBackground(Color.BLACK);
		centerPanel.setBackground(Color.BLACK);

		c.add(northPanel, BorderLayout.NORTH);
		c.add(eastPanel, BorderLayout.EAST);
		c.add(westPanel, BorderLayout.WEST);
		c.add(southPanel, BorderLayout.SOUTH);
		c.add(centerPanel, BorderLayout.CENTER);

		centerPanel.add(titleCover);

		southPanel.setLayout(new GridLayout(1,3,0,0));

		//adds buttons to southPanel
		southPanel.add(help);
		southPanel.add(play);
		southPanel.add(exit);

		Sound.menu.loop(); // loops menu bgm

		this.setResizable(false);
		this.setVisible(true);
		this.pack();

		play.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e){
				if(playable  == false){ // checks if there are no 'game' panels open
					Playable game = new Playable();
					playable=true; // sets to true so that player cannot open another 'game' panel

					Sound.menu.stop(); // stops menu bgm
					Sound.bell.play(); // plays bell sfx
				}
			}

			public void mouseEntered(MouseEvent e){ }

			public void mouseExited(MouseEvent e){ }

			public void mousePressed(MouseEvent e){ }

			public void mouseReleased(MouseEvent e){ }

		});

		exit.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e){ 
				System.exit(0);
			}

			public void mouseEntered(MouseEvent e){ }

			public void mouseExited(MouseEvent e){ }

			public void mousePressed(MouseEvent e){ }

			public void mouseReleased(MouseEvent e){ }

		});

		help.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e){
				if(howtoopen==false){ // checks if there are no 'how to' panels open
					HowToPanel howTo = new HowToPanel();
					howTo.setVisible(true); 
					howtoopen=true; // sets to true so that player cannot open another 'how to' panel
				}
				
			}

			public void mouseEntered(MouseEvent e){ }

			public void mouseExited(MouseEvent e){ }

			public void mousePressed(MouseEvent e){ }

			public void mouseReleased(MouseEvent e){ }

		});
	}

}