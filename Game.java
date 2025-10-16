////DETENTION DUNGEON BY BEN, MILTOS AND KADEN
/// 
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
//import org.w3c.dom.css.Rect;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Game extends JPanel implements KeyListener, ActionListener {
   BufferedImage playerImage;
   BufferedImage playerImageLeft;
   BufferedImage playerImageRight;
   BufferedImage playerImageBack;
   BufferedImage playerBackStep1;
   BufferedImage playerBackStep2;
   BufferedImage playerLeftStep;
   BufferedImage playerRightStep;
   BufferedImage playerStep1;
   BufferedImage playerStep2;
   BufferedImage classroomFrontWall;
   BufferedImage closedDoor;
   BufferedImage openDoor;
   BufferedImage hallwayHall;
   BufferedImage deskImage;
   BufferedImage classroomFloor;
   BufferedImage blankWall;
   BufferedImage cannonballFire;
   BufferedImage mrCannonBoss;
   BufferedImage tape;
   BufferedImage countertop;
   BufferedImage cookie;
   BufferedImage floortile;
   BufferedImage chef;
   BufferedImage finalBoss;


   int playerX = 470;
   int playerY = 550;
   int playerWidth = 46;
   int playerHeight = 58;
   int speed = 3;
   int animationFrame = 0;
   int animationSpeed = 6;
   int animationCounter = 0;
   int currentLevel = 1;
   int hallwayLevel = 1;
   int health = 3;

   int bulletY = 300;
   int bulletX = -100;
   boolean bulletActive = false;
   boolean warningActive = false;
   int warningTimer = 0;
   int bulletSpeed = 15;
   Random rand = new Random();
   int bossLife = 0;
   boolean historyWon = false;
   boolean walkedThroughDoor = false;
   boolean bossFighting = false;
   boolean chefWon = false;
   boolean bossFightLifeFinal = false;


   Rectangle wall = new Rectangle(150, 335, 275, 250);
   Rectangle wall2 = new Rectangle(550, 335, 275, 250);
   Rectangle frontwall = new Rectangle(100, 50, 800, 105);
   Rectangle deskBossFight = new Rectangle(50, 350, 850, 15);
   Rectangle floorLeft = new Rectangle(80, 135, 20, 600);
   Rectangle floorRight = new Rectangle(900, 135, 20, 600);
   Rectangle counterBossFight = new Rectangle(50, 350, 850, 15);

   ArrayList<BufferedImage> walkUp = new ArrayList<BufferedImage>();
   ArrayList<BufferedImage> walkDown = new ArrayList<BufferedImage>();
   ArrayList<BufferedImage> walkLeft = new ArrayList<BufferedImage>();
   ArrayList<BufferedImage> walkRight = new ArrayList<BufferedImage>();

   boolean up, down, left, right, space, nearbyDoor1, nearbyDoor2, nearbyDoor3;
   ArrayList<FallingCookie> fallingCookies = new ArrayList<>();
   int cookieSpawnTimer = 0;
   boolean cookieBossActive = false;
   int cookieCount = 0;

   private class FallingCookie {
      int x, y, targetY;
      boolean active = true;
   
      public FallingCookie(int x, int targetY) {
         this.x = x;
         this.y = 100; // start near the top
         this.targetY = targetY;
      }
   
      public void update() {
         if (y < targetY) y += 8;
         else active = false;
      }
   
      public Rectangle getRect() {
         return new Rectangle(x, y, 40, 40);
      }
   }

   javax.swing.Timer timer;

   private void loadLevel(int level) {
      if (level == 1) {
         playerX = 470;
         playerY = 550;
      } else if (level == 2) {
         if (walkedThroughDoor == false) {
            playerX = 335;
            playerY = 175;
         } else if (hallwayLevel == 1) {
            if (playerX > 500)
               playerX = 100;
            else
               playerX = 850;
         } else if (hallwayLevel == 2) {
            playerX = 100;
         } else if (hallwayLevel == 0) {
            playerX = 850;
         }
      } else if (level == 3) {
         bossFighting = true;
         playerX = 475;
         playerY = 600;
         bulletActive = false;
         warningActive = false;
      } else if (level == 4) {
         bossFighting = true;
         cookieBossActive = false;
         fallingCookies.clear();
         cookieCount = 0;
         playerX = 475;
         playerY = 600;
         bulletActive = false;
         warningActive = false;
      }
      else if (level ==5){
         bossLife=0;
         bossFighting = true;
         cookieBossActive = false;
         fallingCookies.clear();
         cookieCount = 0;
         playerX = 475;
         playerY = 600;
         bulletActive = false;
         warningActive = false;
      }
   }

   public Game() {
      try {
         playerImage = ImageIO.read(new File("player.png"));
         playerImageLeft = ImageIO.read(new File("playerleft.png"));
         playerImageRight = ImageIO.read(new File("playerright.png"));
         playerImageBack = ImageIO.read(new File("playerback.png"));
         playerBackStep1 = ImageIO.read(new File("playerbackstep.png"));
         playerBackStep2 = ImageIO.read(new File("playerbackstep2.png"));
         playerLeftStep = ImageIO.read(new File("playerleftstep.png"));
         playerRightStep = ImageIO.read(new File("playerrightstep.png"));
         playerStep1 = ImageIO.read(new File("playerstep.png"));
         playerStep2 = ImageIO.read(new File("playerstep2.png"));
      
         walkDown.add(playerImage);
         walkDown.add(playerStep1);
         walkDown.add(playerImage);
         walkDown.add(playerStep2);
         walkUp.add(playerImageBack);
         walkUp.add(playerBackStep1);
         walkUp.add(playerImageBack);
         walkUp.add(playerBackStep2);
         walkLeft.add(playerImageLeft);
         walkLeft.add(playerLeftStep);
         walkLeft.add(playerImageLeft);
         walkLeft.add(playerLeftStep);
         walkRight.add(playerImageRight);
         walkRight.add(playerRightStep);
         walkRight.add(playerImageRight);
         walkRight.add(playerRightStep);
      
         deskImage = ImageIO.read(new File("desks.png"));
         classroomFloor = ImageIO.read(new File("classroomfloor.png"));
         classroomFrontWall = ImageIO.read(new File("classroomfrontwall.png"));
         closedDoor = ImageIO.read(new File("door.png"));
         openDoor = ImageIO.read(new File("opendoor.png"));
         hallwayHall = ImageIO.read(new File("hallwaywall.png"));
         blankWall = ImageIO.read(new File("blankwall.png"));
         cannonballFire = ImageIO.read(new File("cannonballfire.png"));
         mrCannonBoss = ImageIO.read(new File("mrcannonboss.png"));
         tape = ImageIO.read(new File("tape.png"));
         countertop = ImageIO.read(new File("countertop.png"));
         cookie = ImageIO.read(new File("cookie.png"));
         floortile = ImageIO.read(new File("floortile.png"));
         chef = ImageIO.read(new File("chef.png"));
         finalBoss = ImageIO.read(new File("finalboss.png"));
      
      } catch (IOException e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(null, "fail");
         System.exit(1);
      }
   
      JFrame frame = new JFrame("Detention Dungeon");
      frame.setSize(1024, 1024);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(this);
      frame.setVisible(true);
      frame.addKeyListener(this);
   
      timer = new javax.swing.Timer(16, this); // ~60 FPS
      timer.start();
   }

   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
   
      if (currentLevel ==6) {
         g.setColor(Color.BLACK);
         g.fillRect(0, 0, getWidth(), getHeight());
         g.setColor(Color.GREEN);
         g.setFont(new Font("Monospaced", Font.BOLD, 50));
         g.drawString("YOU WIN!", 370, 400);
         g.setFont(new Font("Monospaced", Font.PLAIN, 30));
         g.drawString("Congratulations! Press ESC to exit.", 220, 480);
         return;
      }
      // Check for game over
      if (health <= 0) {
         g.setColor(Color.BLACK);
         g.fillRect(0, 0, getWidth(), getHeight());
         g.setColor(Color.RED);
         g.setFont(new Font("Arial", Font.BOLD, 50));
         g.drawString("Game Over!", 350, 400);
         g.setFont(new Font("Arial", Font.PLAIN, 30));
         g.drawString("Press ESC to exit", 400, 500);
         return;
      }
   
    
      // Clear screen
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(Color.WHITE);
      g.drawString(("Health: " + health), 75, 25);
      g.setColor(Color.BLACK);
   
      if (currentLevel == 1) {
         g.drawImage(classroomFloor, 100, 135, 800, 600, null);
       
         g.drawImage(classroomFrontWall, 100, 50, 795, 150, null);
         g.setColor(Color.WHITE);
         g.drawString("Welcome to Detention...", 380, 90);
         g.drawString("Use W-A-S-D to move and SPACE to enter doors.", 310, 120);
      
         g.drawImage(closedDoor, 750, 100, 60, 100, null);
         if (nearbyDoor1)
            g.drawImage(openDoor, 750, 97, 85, 106, null);
      
         for (int i = 335; i <= 585; i += 80) {
            g.drawImage(deskImage, 150, i, 128, 60, null);
            g.drawImage(deskImage, 300, i, 128, 60, null);
            g.drawImage(deskImage, 550, i, 128, 60, null);
            g.drawImage(deskImage, 700, i, 128, 60, null);
         }
      }
   
      // Level 2: Hallway
      if (currentLevel == 2) {
         g.drawImage(classroomFloor, 100, 135, 800, 600, null);
         g.drawImage(hallwayHall, 102, 50, 795, 160, null);
      
         if (hallwayLevel == 1) {
            g.drawImage(openDoor, 330, 99, 85, 106, null);
            g.drawString("<-- CAFETERIA | MAIN ENTRANCE -->", 400, 70);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString("DETENTION", 332, 95);
            g.drawString("HISTORY", 646, 95);
         
            g.drawImage(closedDoor, 640, 101, 60, 100, null);
            if (nearbyDoor2 || historyWon)
               g.drawImage(openDoor, 640, 98, 85, 106, null);
         }
      
         if (hallwayLevel == 0) {
            g.drawString("HISTORY -->", 450, 70);
            g.drawImage(closedDoor, 640, 101, 60, 100, null);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString("CAFETERIA", 332, 95);
            g.drawString("THEATRE", 646, 95);
            g.drawImage(tape, 622, 110, 85, 80, null);
            g.drawImage(closedDoor, 330, 101, 60, 100, null);
            if (nearbyDoor3 || chefWon)
               g.drawImage(openDoor, 330, 98, 85, 106, null);
         }
      
         if (hallwayLevel == 2) {
            g.drawString("<-- HISTORY", 450, 70);
            g.drawImage(closedDoor, 640, 101, 60, 100, null);
            g.drawImage(closedDoor, 330, 101, 60, 100, null);
            g.drawImage(tape, 312, 110, 85, 80, null);
            if (nearbyDoor2)
            {
               g.drawImage(openDoor, 640, 98, 85, 106, null);
            }
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString("PHYSICS", 332, 95);
            g.drawString("MAIN ENTRANCE", 635, 95);
            if(!historyWon && !chefWon)
            {  
               for(int i =220; i <= 650; i += 50)
               {
                  g.drawImage(tape, 450, i, 150,75,null);
               } 
            }
         
         }
      }
   
      /// Level 3: History Boss
      if (currentLevel == 3) {
         g.drawImage(classroomFloor, 100, 135, 800, 600, null);
         g.drawImage(blankWall, 102, 50, 795, 150, null);
         g.drawImage(closedDoor, 725, 100, 60, 100, null);
         g.drawString("H I S T O R Y    C L A S S", 688, 85);
         g.drawString("Aviod cannoballs to defeat Mr. Cannon", 110,100);
         if (!bossFighting) {
            g.drawImage(openDoor, 725, 97, 85, 106, null);
         }
         if (bossFighting) {
            g.drawImage(mrCannonBoss, 325, 70, 325, 300, null);
            for (int i = 140; i <= 800; i += 150) {
               g.drawImage(deskImage, i, 350, 128, 60, null);
            }
         }
         if (bossLife < 20) {
            if (warningActive) {
               Graphics2D g2d = (Graphics2D) g.create();
               g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
               g2d.setColor(Color.RED);
               g2d.fillRect(100, bulletY - 20, 800, 40);
               g2d.dispose();
            }
            if (bulletActive) {
               Graphics2D g2d = (Graphics2D) g.create();
               g2d.rotate(Math.toRadians(180), bulletX + 20, bulletY + 20);
               g2d.drawImage(cannonballFire, bulletX, bulletY, 300, 50, null);
               g2d.dispose();
            }
         } else {
            bossFighting = false;
            historyWon = true;
         }
      }
   
      // Level 4: Cafeteria
      if (currentLevel == 4) {
         g.drawImage(classroomFloor, 100, 135, 800, 600, null);
         g.drawImage(blankWall, 102, 50, 795, 150, null);
         g.drawImage(closedDoor, 725, 100, 60, 100, null);
         g.drawString("      C A F E T E R I A ", 688, 85);
         g.drawString("Aviod the falling cookies to defeat the Chef", 110,100);

         if (bossFighting) {
            g.drawImage(deskImage, 150, 350, 128, 60, null);
            g.drawImage(deskImage, 730, 350, 128, 60, null);
            g.drawImage(chef, 370, 80, 260, 240, null);
            g.drawImage(countertop, 305, 305, 400, 100, null);
            g.drawImage(cookie, 420, 295, 32, 32, null);
         }
         if (!bossFighting) {
            g.drawImage(openDoor, 725, 97, 85, 106, null);
         }
         if (bossFighting && cookieBossActive) {
            for (FallingCookie fc : fallingCookies) {
               g.setColor(new Color(0, 0, 0, 60));
               g.fillOval(fc.x - 5, fc.targetY + 30, 60, 35);
               g.drawImage(cookie, fc.x, fc.y, 50, 50, null);
            }
         }            
         else {chefWon = true;}
      
      }
      ///// Level 5: Final Boss
      if (currentLevel == 5) 
      {
         g.drawImage(classroomFloor, 100, 135, 800, 600, null);
         g.drawImage(blankWall, 102, 50, 795, 150, null);
         g.drawImage(closedDoor, 725, 100, 60, 100, null);
         g.drawImage(closedDoor, 720-47, 100, 60, 100, null);
         g.drawImage(finalBoss, 350, 40,270,250, null);
         g.drawString("        E X I T ", 688, 85);
      
      
         if (warningActive) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
            g2d.setColor(Color.RED);
            g2d.fillRect(100, bulletY - 20, 800, 40);
            g2d.dispose();
         }
         if (bulletActive) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Monospaced", Font.BOLD, 80));
            g.drawString("0", bulletX, bulletY + 40);
         }
         for (FallingCookie fc : fallingCookies) {
            g.setColor(new Color(0, 0, 0, 60));
            g.fillOval(fc.x - 5, fc.targetY + 30, 60, 35);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Monospaced", Font.BOLD, 80));
            g.drawString("1", fc.x, fc.y + 40);
         }
      }
   
      // Draw player
      if (left && !right)
         g.drawImage(walkLeft.get(animationFrame), playerX, playerY, playerWidth, playerHeight, null);
      else if (right && !left)
         g.drawImage(walkRight.get(animationFrame), playerX, playerY, playerWidth, playerHeight, null);
      else if (up)
         g.drawImage(walkUp.get(animationFrame), playerX, playerY, playerWidth, playerHeight, null);
      else if (down)
         g.drawImage(walkDown.get(animationFrame), playerX, playerY, playerWidth, playerHeight, null);
      else
         g.drawImage(playerImage, playerX, playerY, playerWidth, playerHeight, null);
   
     
   }

   public void actionPerformed(ActionEvent e) {
      // Check for game over
      if (health <= 0) {
         timer.stop();
         repaint();
         return;
      }
   
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
   
      // Check collision
      Rectangle floorhitbox = new Rectangle(100, 155, 800, 550);
      Rectangle doorHitBox1 = new Rectangle(725, 100, 100, 100);
      Rectangle doorHitBox2 = new Rectangle(615, 100, 100, 100);
      Rectangle cafedoorhitbox = new Rectangle(330 - 20, 101, 100, 100);
      Rectangle tapeHitbox = new Rectangle(490, 220, 150, 600);
      Rectangle playerHitbox = new Rectangle(nextX, nextY, playerWidth, playerHeight);
   
      if (currentLevel == 1 && !playerHitbox.intersects(wall) && !playerHitbox.intersects(wall2)
             && !playerHitbox.intersects(frontwall) && floorhitbox.contains(playerHitbox)) {
         playerX = nextX;
         playerY = nextY;
      }
   
      if (currentLevel == 2 && floorhitbox.contains(playerHitbox) && !(hallwayLevel==2 && (!historyWon && !chefWon) && playerHitbox.intersects(tapeHitbox))) 
      {
         playerX = nextX;
         playerY = nextY;
      }
   
   
      if (currentLevel == 3 && floorhitbox.contains(playerHitbox)
             && !(bossFighting && playerHitbox.intersects(deskBossFight))) {
         playerX = nextX;
         playerY = nextY;
      }
   
      if (currentLevel == 4 && floorhitbox.contains(playerHitbox)
             && !(bossFighting && playerHitbox.intersects(counterBossFight))) {
         playerX = nextX;
         playerY = nextY;
      }
      if (currentLevel == 5 && floorhitbox.contains(playerHitbox)) 
      {
         playerX = nextX;
         playerY = nextY;
      }
   
      // Hallway navigation
      if (currentLevel == 2 && (hallwayLevel == 1 || hallwayLevel == 2) && playerHitbox.intersects(floorLeft)) {
         hallwayLevel--;
         walkedThroughDoor = true;
         loadLevel(currentLevel);
      }
   
      if (currentLevel == 2 && (hallwayLevel == 1 || hallwayLevel == 0) && playerHitbox.intersects(floorRight)) {
         hallwayLevel++;
         walkedThroughDoor = true;
         loadLevel(currentLevel);
      }
   
   
      if (currentLevel == 2 && hallwayLevel == 0 && playerHitbox.intersects(cafedoorhitbox)) {
         nearbyDoor3 = true;
      } else {
         nearbyDoor3 = false;
      }
   
      // Check if player is near the door
      if (playerHitbox.intersects(doorHitBox1)) {
         nearbyDoor1 = true;
      } else {
         nearbyDoor1 = false;
      }
   
      if (playerHitbox.intersects(doorHitBox2)) {
         nearbyDoor2 = true;
      } else {
         nearbyDoor2 = false;
      }
   
      if (space && nearbyDoor1) {
         currentLevel = 2;
         loadLevel(currentLevel);
      
      } else if (space && nearbyDoor2) {
         if (hallwayLevel == 1)
            currentLevel = 3;
         if (hallwayLevel == 2)
            currentLevel = 5;
         loadLevel(currentLevel);
      
      } else if (space && nearbyDoor3) {
         currentLevel = 4;
         loadLevel(currentLevel);
      }
   
      // Level 3: History Boss
      if (currentLevel == 3) {
         if (!bulletActive && !warningActive) {
            bulletY = 430 + rand.nextInt(250);
            warningActive = true;
            warningTimer = 120;
         }
         if (warningActive) {
            warningTimer -= 5;
            if (warningTimer <= 0) {
               warningActive = false;
               bulletActive = true;
               bulletX = 150;
               bossLife++;
            }
         }
         if (bossFighting) {
            if (bulletActive) {
               bulletX += bulletSpeed;
               Rectangle bulletRect = new Rectangle(bulletX, bulletY - 15, 40, 30);
               Rectangle playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);
               if (bulletRect.intersects(playerRect)) {
                  health--;
                  bulletActive = false;
               }
               if (bulletX > 900) {
                  bulletActive = false;
               }
            }
         }
      }
   
      // Level 4: Cafeteria Boss
      if (currentLevel == 4 && bossFighting) {
         cookieBossActive = true;
         cookieSpawnTimer--;
         if (cookieSpawnTimer <= 0 && cookieCount < 100) {
            int x = 200 + rand.nextInt(600);
            int targetY = 450 + rand.nextInt(200);
            fallingCookies.add(new FallingCookie(x, targetY));
            cookieSpawnTimer = 8 + rand.nextInt(20);
            cookieCount++;
         }
      
         for (int i = 0; i < fallingCookies.size(); i++) {
            fallingCookies.get(i).update();
         }
         for (int i = fallingCookies.size() - 1; i >= 0; i--) {
            if (!fallingCookies.get(i).active) {
               fallingCookies.remove(i);
            }
         }
      
         Rectangle playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);
         for (FallingCookie fc : fallingCookies) {
            if (fc.active && playerRect.intersects(fc.getRect())) {
               health--;
               fc.active = false;
            }
         }
      
         if (cookieCount >= 100 && fallingCookies.isEmpty()) {
            bossFighting = false;
            cookieBossActive = false;
            cookieCount = 0;
         }
      }
   
   // Level 5: final boss
      if (currentLevel == 5 && bossFighting) {
         if(!bossFightLifeFinal)
         {
            if (!bulletActive && !warningActive) {
               bulletY = 200 + rand.nextInt(450);
               warningActive = true;
               warningTimer = 90;
            }
            if (warningActive) {
               warningTimer -= 5;
               if (warningTimer <= 0) {
                  warningActive = false;
                  bulletActive = true;
                  bulletX = 100;
                  bossLife++;
               
               }
            }
            if (bulletActive) {
               bulletX += bulletSpeed;
               Rectangle bulletRect = new Rectangle(bulletX, bulletY - 15, 40, 30);
               Rectangle playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);
               if (bulletRect.intersects(playerRect)) {
                  health--;
                  bulletActive = false;
               }
               if (bulletX > 900) {
                  bulletActive = false;
                  bulletSpeed++;
               }
            }
         }
         cookieBossActive = true;
         cookieSpawnTimer--;
         if (cookieSpawnTimer <= 0 && cookieCount < 100) {
            int x = 150 + rand.nextInt(650);
            int targetY = 400 + rand.nextInt(200);
            fallingCookies.add(new FallingCookie(x, targetY));
            cookieSpawnTimer = 5 + rand.nextInt(20);
            cookieCount++;
         }
         for (int i = 0; i < fallingCookies.size(); i++) {
            fallingCookies.get(i).update();
         }
         for (int i = fallingCookies.size() - 1; i >= 0; i--) {
            if (!fallingCookies.get(i).active) {
               fallingCookies.remove(i);
            }
         }
         Rectangle playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);
         for (FallingCookie fc : fallingCookies) {
            if (fc.active && playerRect.intersects(fc.getRect())) {
               health--;
               fc.active = false;
            }
         }
         if(cookieCount >= 100)
         {fallingCookies.clear();
            currentLevel=6;
            timer.stop(); 
            repaint();
            return;}
      
         if (cookieCount >= 120) {
            System.out.println("win");
            bossFightLifeFinal = true;
            bossFighting = false;
            cookieBossActive = false;
            fallingCookies.clear();
            currentLevel=6;
            timer.stop(); 
            repaint();
            return;
         }
      }
      // Player animation
      if (up || down || left || right) {
         animationCounter++;
         if (animationCounter >= animationSpeed) {
            animationFrame = (animationFrame + 1) % walkUp.size();
            animationCounter = 0;
         }
      } else {
         animationFrame = 0;
         animationCounter = 0;
      }
   
      repaint();
   }

   public void keyPressed(KeyEvent e) {
      int code = e.getKeyCode();
      if (code == KeyEvent.VK_W) up = true;
      if (code == KeyEvent.VK_S) down = true;
      if (code == KeyEvent.VK_A) left = true;
      if (code == KeyEvent.VK_D) right = true;
      if (code == KeyEvent.VK_SHIFT) speed = 5;
      if (code == KeyEvent.VK_SPACE) space = true;
      if (code == KeyEvent.VK_ESCAPE) System.exit(0);
      // Debug keys
      if (code == KeyEvent.VK_Q) {
         currentLevel++;
         loadLevel(currentLevel);
      }
      if (code == KeyEvent.VK_L) {
         bossLife += 20;
      }
      if (code == KeyEvent.VK_P) {historyWon = true; chefWon = true;}
      if(code == KeyEvent.VK_H) {health++;}
   
       
   }

   public void keyReleased(KeyEvent e) {
      int code = e.getKeyCode();
      if (code == KeyEvent.VK_W) up = false;
      if (code == KeyEvent.VK_S) down = false;
      if (code == KeyEvent.VK_A) left = false;
      if (code == KeyEvent.VK_D) right = false;
      if (code == KeyEvent.VK_SHIFT) speed = 3;
      if (code == KeyEvent.VK_SPACE) space = false;
   }

   public void keyTyped(KeyEvent e) {}

   public static void main(String[] args) {
      new Game();
   }
}