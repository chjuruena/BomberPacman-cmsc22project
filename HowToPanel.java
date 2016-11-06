import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HowToPanel extends JPanel{

	private JPanel northPanel = new JPanel();
	private JPanel eastPanel = new JPanel();
	private JPanel westPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel centerPanel = new JPanel();

	private JLabel inst1 = new JLabel(new ImageIcon(getClass().getResource("images/covers/inst1.png")));
	private JLabel inst2 = new JLabel(new ImageIcon(getClass().getResource("images/covers/inst2.png")));
	private JLabel credit = new JLabel(new ImageIcon(getClass().getResource("images/covers/credit.png")));
	private JScrollPane scroll = new JScrollPane(centerPanel); //creates scrollPane container 

	public HowToPanel(){
		final JFrame frame = new JFrame();
		frame.setTitle("How To Play");

		final Container c = frame.getContentPane();
		c.setPreferredSize(new Dimension(555,500));
		c.setBackground(Color.BLACK);
		c.setLayout(new BorderLayout());

		northPanel.setBackground(Color.BLACK);
		northPanel.setPreferredSize(new Dimension(0,0));
		c.add(northPanel, BorderLayout.NORTH);

		eastPanel.setBackground(Color.BLACK);
		eastPanel.setPreferredSize(new Dimension(0,0));
		c.add(eastPanel, BorderLayout.EAST);

		westPanel.setBackground(Color.BLACK);
		westPanel.setPreferredSize(new Dimension(0,0));
		c.add(westPanel, BorderLayout.WEST);

		southPanel.setBackground(Color.BLACK);
		southPanel.setPreferredSize(new Dimension(0,0));
		c.add(southPanel, BorderLayout.SOUTH);

		centerPanel.setBackground(Color.BLACK);
		centerPanel.setPreferredSize(new Dimension(535,1950));
		centerPanel.setLayout(new GridLayout(3,1,0,0));
		centerPanel.add(inst1);
		centerPanel.add(inst2);
		centerPanel.add(credit);

		c.add(scroll);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int answer = JOptionPane.showConfirmDialog(frame, 
			        "Are you sure you want to close this window?", "Close?", 
			        JOptionPane.YES_NO_OPTION,
			        JOptionPane.QUESTION_MESSAGE);
         		if (answer == JOptionPane.YES_OPTION){
					BomberMenu.howtoopen=false; 
					frame.dispose();
				}
			}
		});

	}

}