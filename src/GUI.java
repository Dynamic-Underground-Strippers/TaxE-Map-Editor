import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GUI extends JFrame {
	Image mapImage;
	ArrayList<Station> nodes = new ArrayList<Station>();
	boolean ctrlClicked = false;
	ArrayList<Rectangle> nodeClicks = new ArrayList<Rectangle>();
	Rectangle mostRecentRect=null;
	Connection[][] connections;
	ArrayList<Line> lines;
	boolean altClicked = false;
	public GUI() {
		lines = new ArrayList<Line>();
		mapImage = new ImageIcon(getClass().getClassLoader().getResource("map.png")).getImage();
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) mostRecentRect = null;
				else if (e.getKeyCode() == KeyEvent.VK_Z) {
					if (ctrlClicked) {
						nodes.remove(nodes.size() - 1);
						repaint();
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_E){
					if (ctrlClicked) {
						EditAllNodes editDialog = new EditAllNodes(nodes);
						if (editDialog.getNodeList().size() > 0) {
							nodes = editDialog.getNodeList();
							repaint();
						}
						editDialog.dispose();
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_S) {
					if (ctrlClicked){
						SaveFile saveDialog = new SaveFile(nodes);
						saveDialog.dispose();
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_L) {
					if (ctrlClicked){
						LoadFile loadDialog = new LoadFile();
						if (loadDialog.okClicked){
							nodes = loadDialog.getNodes();
						}
						loadDialog.dispose();
						repaint();
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					ctrlClicked = true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					ctrlClicked = false;
				}
			}
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean rectClicked = false;
				for (Rectangle rect : nodeClicks) {
					if (rect.contains(e.getPoint())) {
						rectClicked = true;
						if (e.getButton() == MouseEvent.BUTTON1) {
							if (mostRecentRect == null){
								mostRecentRect = rect;
							}else if (rect!=mostRecentRect){
								int mostRecentIndex = nodeClicks.indexOf(mostRecentRect);
								int currentIndex = nodeClicks.indexOf(rect);
								mostRecentRect = null;
								Connection tempConnection = new ConnectionDetails().getStoredConnection();
								if (tempConnection != null){
									lines.add(new Line(nodes.get(mostRecentIndex).getLocation(),nodes.get(currentIndex).getLocation(),tempConnection.getDistance()));
									repaint();
								}
							}
						} else if (e.getButton() == MouseEvent.BUTTON3) {
							int currentIndex = nodeClicks.indexOf(rect);
							EditNode editDialog = new EditNode(nodes.get(currentIndex));

						}
					}
				}
				if (!rectClicked) {
					Point p = new Point((float) e.getX() / (float) getWidth(), (float) e.getY() / (float) getHeight());
					NodeDetails nodeDialog = new NodeDetails(nodes.size(), p);
					if (nodeDialog.getStoredNode() != null) {
						nodes.add(nodeDialog.getStoredNode());
						repaint();
					}

				}
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
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		nodeClicks.clear();
		g.clearRect(0, 0, getWidth(), getHeight());
		int imageWidth = (int) ((float) mapImage.getWidth(this) * ((float) getHeight() / (float) mapImage.getHeight(this)));
		g.drawImage(mapImage, getWidth() - imageWidth, 0, imageWidth, getHeight(), this);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		g.setColor(Color.MAGENTA);
		for (Station n : nodes) {
			g.fillOval((int) (n.getLocation().getX() * getWidth()) - 10, (int) (n.getLocation().getY() * getHeight()) - 10, 20, 20);
			Rectangle rect = new Rectangle((int) (n.getLocation().getX() * getWidth()) - 10, (int)(n.getLocation().getY() * getHeight()) - 10, 20, 20 );
			nodeClicks.add(rect);
		}
		for (Line l: lines){
			g.drawLine((int) (l.getStart().getX() * getWidth()), (int) (l.getStart().getY() * getHeight()), (int) (l.getEnd().getX() * getWidth()), (int) (l.getEnd().getY() * getHeight()));
			g.drawString(String.valueOf(l.getDistance()),(int) (l.getMidPoint().getX()*getWidth()),(int) (l.getMidPoint().getY()* getHeight()));
		}
	}

	public void rebalanceIDs(){
		for (int i=0;i<nodes.size();i++){
			nodes.get(i).setId(i);
		}
	}
}