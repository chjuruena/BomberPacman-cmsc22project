import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.*;
import javax.swing.*;
import java.io.*;

//based from Sir Mark Alfonso's Demonstration
@SuppressWarnings("serial")
public class BombAnimation extends JPanel {
   static final int CANVAS_WIDTH = 20;
   static final int CANVAS_HEIGHT = 20;
   
   //image filenames
   private String[] imgFire = {
         "images/fire/C1.png", "images/fire/C2.png", "images/fire/C3.png", "images/fire/C4.png",
          "images/fire/C5.png", "images/fire/C6.png", "images/fire/C7.png" , "images/fire/C8.png"};
   private String[] bombFilenames = {"images/bombs/1.png", "images/bombs/2.png"};
   private String[] tipUp = {"images/fire/tipUp(1).png", "images/fire/tipUp(2).png", "images/fire/tipUp(3).png"
            , "images/fire/tipUp(4).png", "images/fire/tipUp(5).png", "images/fire/tipUp(6).png", 
            "images/fire/tipUp(7).png", "images/fire/tipUp(8).png" };
   private String[] tipDown = {"images/fire/tipDown(1).png", "images/fire/tipDown(2).png", "images/fire/tipDown(3).png"
            , "images/fire/tipDown(4).png", "images/fire/tipDown(5).png", "images/fire/tipDown(6).png", 
            "images/fire/tipDown(7).png", "images/fire/tipDown(8).png" };
   private String[] tipLeft = {"images/fire/tipLeft(1).png", "images/fire/tipLeft(2).png", "images/fire/tipLeft(3).png"
            , "images/fire/tipLeft(4).png", "images/fire/tipLeft(5).png", "images/fire/tipLeft(6).png", 
            "images/fire/tipLeft(7).png", "images/fire/tipLeft(8).png" };
   private String[] tipRight = {"images/fire/tipRight(1).png", "images/fire/tipRight(2).png", "images/fire/tipRight(3).png"
            , "images/fire/tipRight(4).png", "images/fire/tipRight(5).png", "images/fire/tipRight(6).png", 
            "images/fire/tipRight(7).png", "images/fire/tipRight(8).png" };
  private String[] vert = {"images/fire/vert(1).png", "images/fire/vert(2).png", "images/fire/vert(3).png"
            , "images/fire/vert(4).png", "images/fire/vert(5).png", "images/fire/vert(6).png", 
            "images/fire/vert(7).png", "images/fire/vert(8).png" };
  private String[] hori = {"images/fire/hori(1).png", "images/fire/hori(2).png", "images/fire/hori(3).png"
            , "images/fire/hori(4).png", "images/fire/hori(5).png", "images/fire/hori(6).png", 
            "images/fire/hori(7).png", "images/fire/hori(8).png" };
   
   private Image[] imgFrames;    // array of Images to be animated
   private Image[] bombFrames;    // array of Images to be animated
   private int currentFrame = 0; // current frame number
   private int frameRate = 5;    // frame rate in frames per second
   private int imgWidth, imgHeight;    // width and height of the image
   private double x = 0, y = 0; // (x,y) of the center of image
   private double speed = 8;           // displacement in pixels per move
   private double direction = 0;       // in degrees
   private double rotationSpeed = 1;   // in degrees per move
 
   // Used to carry out the affine transform on images
   private AffineTransform transform = new AffineTransform();
 
   /** Constructor to set up the GUI components */
   public BombAnimation(int bombFireSet) {
      setVisible(true);
      setBackground(Color.GREEN);
      // Setup animation for each condition
      switch(bombFireSet){
         case 1: loadImages(bombFilenames); // bomb
               break;
         case 2: loadImages(imgFire); // bomb explodes
               break;         
         case 3: loadImages(tipUp); // upward explosion
               break;
         case 4: loadImages(tipDown); // downward explosion
               break;
         case 5: loadImages(tipLeft); // left explosion
               break;
         case 6: loadImages(tipRight); // right explosion
               break;
         case 7: loadImages(vert); // vertical explosion
               break;
         case 8: loadImages(hori); // horizontal explosion
               break;

      }
   
      Thread animationThread = new Thread () {
         @Override
         public void run() {
            while (true) {
               update();   // update the position and image
               repaint();  // Refresh the display
               try {
                  Thread.sleep(1000 / frameRate); // delay and yield to other threads
               } catch (InterruptedException ex) { }
            }
         }
      };
      animationThread.start();  // start the thread to run animation
 
      // Setup GUI
      setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
   }
 
   /** Helper method to load all image frames, with the same height and width */
   private void loadImages(String[] imgFileNames) {

      int numFrames = imgFileNames.length;
      imgFrames = new Image[numFrames];  // allocate the array
      URL imgUrl = null;
      for (int i = 0; i < numFrames; ++i) {
         imgUrl = getClass().getClassLoader().getResource(imgFileNames[i]);
         if (imgUrl == null) {
            System.err.println("Couldn't find file: " + imgFileNames[i]);
         } else {
            try {
               imgFrames[i] = ImageIO.read(imgUrl);  // load image via URL
            } catch (IOException ex) {
               ex.printStackTrace();
            }
         }
      }
      imgWidth = imgFrames[0].getWidth(null);
      imgHeight = imgFrames[0].getHeight(null);
   }
 
   /** Update the position based on speed and direction of the sprite */
   public void update() {      
      ++currentFrame;    // display next frame
      if (currentFrame >= imgFrames.length) {
         currentFrame = 0;
      }
   }
 
   /** Custom painting codes on this JPanel */
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);  // paint background
      Graphics2D g2d = (Graphics2D) g;
 
      transform.setToIdentity();
      transform.translate(x , y);
      g2d.drawImage(imgFrames[currentFrame], transform, null);
   }
   
}
