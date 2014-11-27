import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GUI extends JFrame {
	Image mapImage;
	String text = "";
	DecimalFormat df = new DecimalFormat("#.##");
	ArrayList<Point> points = new ArrayList<Point>();

	class Point {
		float x;
		float y;

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

	public GUI() {
		mapImage = new ImageIcon(getClass().getClassLoader().getResource("map.png")).getImage();
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
				if (e.getKeyCode() == KeyEvent.VK_Z) {
					points.remove(points.size() - 1);
					repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = new Point((float) e.getX() / (float) getWidth(), (float) e.getY() / (float) getHeight());
				points.add(p);
				text = df.format(p.x) + "%, " + df.format(p.y) + "%";
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		int imageWidth = (int) ((float) mapImage.getWidth(this) * ((float) getHeight() / (float) mapImage.getHeight(this)));
		g.drawImage(mapImage, getWidth() - imageWidth, 0, imageWidth, getHeight(), this);
		g.setColor(Color.BLACK);
		g.drawString(text, 50, 50);
		g.setColor(Color.MAGENTA);
		for (Point p : points) {
			g.fillOval((int) (p.x * getWidth()) - 10, (int) (p.y * getHeight()) - 10, 20, 20);
		}
	}
}