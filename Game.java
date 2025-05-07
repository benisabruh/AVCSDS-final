import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;


public class Game extends JPanel implements KeyListener, ActionListener 
{
   BufferedImage playerImage;
   BufferedImage playerImageLeft;
   BufferedImage playerImageRight;
   BufferedImage playerImageBack;

   int playerX = 50;
   int playerY = 50;
   int playerSize = 60;
   int speed = 2;

   Rectangle wall = new Rectangle(200, 100, 300, 200);

   boolean up, down, left, right;

   javax.swing.Timer timer;

   public Game() 
   {
      try 
      {
         playerImage = ImageIO.read(new File("player.png"));
         playerImageLeft = ImageIO.read(new File("playerleft.png"));
         playerImageRight = ImageIO.read(new File("playerright.png"));
         playerImageBack = ImageIO.read(new File("playerback.png"));
      
      }
      catch (IOException e) {
         e.printStackTrace();
         System.exit(1);
      }
      JFrame frame = new JFrame("Detention Dungeon");
      frame.setSize(1000, 1000);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(this);
      frame.setVisible(true);
      frame.addKeyListener(this);
   
      timer = new javax.swing.Timer(16, this); // ~60 FPS
      timer.start();
   }

   
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
   
      // Clear screen
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, getWidth(), getHeight());
   
      // Draw wall
      g.setColor(Color.GRAY);
      g.fillRect(wall.x, wall.y, wall.width, wall.height);
   
      // Draw player
      if(left) 
      g.drawImage(playerImageLeft, playerX, playerY, playerSize, playerSize, null);
         
      if(right) 
      g.drawImage(playerImageRight, playerX, playerY, playerSize, playerSize, null);
   
      if(up) 
      g.drawImage(playerImageBack, playerX, playerY, playerSize, playerSize, null);
   
      if(down) 
      g.drawImage(playerImage, playerX, playerY, playerSize, playerSize, null);
      
      if(!up && !right && !left && !down) 
      g.drawImage(playerImage, playerX, playerY, playerSize, playerSize, null);
   
   }
   
   public void actionPerformed(ActionEvent e) {
      int nextX = playerX;
      int nextY = playerY;
   
      // Movement logic
      if (up) {
         nextY -= speed;
         
      }
      if (down) {
         nextY += speed;
      }
      if (left) {
         nextX -= speed;
      }
      if (right) {
         nextX += speed;
      }
   
      // Check collision with wall
      Rectangle nextPlayer = new Rectangle(nextX, nextY, playerSize, playerSize);
      if (!nextPlayer.intersects(wall)) {
         playerX = nextX;
         playerY = nextY;
      }
   
      repaint();
   }

   
   public void keyPressed(KeyEvent e) {
      int code = e.getKeyCode();
      if (code == KeyEvent.VK_W) up = true;
      if (code == KeyEvent.VK_S) down = true;
      if (code == KeyEvent.VK_A) left = true;
      if (code == KeyEvent.VK_D) right = true;
   }

   
   public void keyReleased(KeyEvent e) {
      int code = e.getKeyCode();
      if (code == KeyEvent.VK_W) up = false;
      if (code == KeyEvent.VK_S) down = false;
      if (code == KeyEvent.VK_A) left = false;
      if (code == KeyEvent.VK_D) right = false;
   }

   
   public void keyTyped(KeyEvent e) {}

   public static void main(String[] args) {
      new Game();
   }
}
