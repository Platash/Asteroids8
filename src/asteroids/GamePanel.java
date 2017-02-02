
package asteroids;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.stream.Stream;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author AP
 */
public class GamePanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private JLabel text = new JLabel("FPS: 0");
    private JLabel hp = new JLabel (" HP: ");
    private double interpolation;
    protected int acceleration;
    private Graphics2D g2;
    private ObjectsController pool = new ObjectsController();
     //Class is a Singleton, created at the beginning.
    public final static GamePanel PANEL = new GamePanel();
    
    private GamePanel() {
        
        acceleration = 0;
        setFocusable(true);
        setLayout(null);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        hp.setForeground(Color.white);
        hp.setFont(hp.getFont().deriveFont(36f));
        add(text);
        add(hp);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("./src/graphics/cursor.png");
        Cursor cursor = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), "aimpoint");
        setCursor (cursor);
        
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {}

            @Override
            public void mousePressed(MouseEvent me) {
                if(me.getButton() == 3) {
                    acceleration = 6;
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if(me.getButton() == 1 && pool.ship.alive) {
                    pool.shoot(g2, me.getX(), me.getY());
                } else if (me.getButton() == 3) {
                    acceleration = 0;
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {}

            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {}

            @Override
            public void keyPressed(KeyEvent ke) {
                if(pool.getShip().getCollisions().isEmpty()) {
                    if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                        acceleration = 6;
                    }
                    switch(ke.getKeyChar()) {
                       case 'a' :  
                                   pool.getShip().setVelX(-Ship.MAX_VELOCITY);
                                   break;
                       case 'd' :  
                                   pool.getShip().setVelX(Ship.MAX_VELOCITY);
                                   break;
                       case 's' :  
                                   pool.getShip().setVelY(Ship.MAX_VELOCITY);
                                   break;
                       case 'w' : 
                                   pool.getShip().setVelY(-Ship.MAX_VELOCITY);
                                   break;
                   }
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                    if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        Asteroids.ASTEROIDS.paused = true;
                    } else if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                        acceleration = 0;
                    } else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                        Asteroids.ASTEROIDS.paused = false;
                    } else {
                        pool.getShip().setVelY(pool.getShip().getVelY()/2);
                        pool.getShip().setVelX(pool.getShip().getVelX()/2);
                    }
            }
        });
    }
    
    
    @Override
     public void paintComponent(Graphics g) {
        g2 = (Graphics2D)g;
        
        Stars.STARS.draw(g2, interpolation);
        Mist.MIST.draw(g2, interpolation);
        
        pool.getAsteroids().stream().forEach(asteroid -> asteroid.draw(g2, interpolation));
        pool.getExplosions().stream().forEach(explosion -> explosion.draw(g2, interpolation));
        pool.getRockets().stream().forEach(rocket -> rocket.draw(g2, interpolation));

        pool.getShip().draw(g2, interpolation);
        if(pool.gameOver) {
            int y = (Asteroids.ASTEROIDS.WIN_SIZE_X - asteroids.Graphics.GAME_OVER.getWidth()) / 2;
            g2.drawImage(asteroids.Graphics.GAME_OVER, y , 300, null);
        }
        Asteroids.ASTEROIDS.frameCount++;
        
     }
    
    /**
     * Shows current FPS
     */
    public void update() {
        pool.updateObjects();
        text.setText(String.valueOf("FPS: " + Asteroids.ASTEROIDS.fps));
        hp.setText("HP: " + String.valueOf(pool.getShip().getHp()));
    }

    
    public void setInterpolation(double interp) {
        interpolation = interp;
    }

}
