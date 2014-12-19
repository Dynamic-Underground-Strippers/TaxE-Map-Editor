import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
//TODO: Edit goals
public class GUI extends JFrame {
	Image mapImage;
	ArrayList<Node> nodes = new ArrayList<Node>();
	boolean ctrlClicked = false;
	ArrayList<Rectangle> nodeClicks = new ArrayList<Rectangle>();
	Rectangle mostRecentRect=null;
	ArrayList<Rect> connectionClicks = new ArrayList<Rect>();
	ArrayList<ArrayList<Connection>> connections = new ArrayList<ArrayList<Connection>>();
	public GUI() {
		mapImage = new ImageIcon(getClass().getClassLoader().getResource("map.png")).getImage();
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) mostRecentRect = null;
				else if (e.getKeyCode() == KeyEvent.VK_Z) {
					//Undo
					if (ctrlClicked) {
						nodes.remove(nodes.size() - 1);
						repaint();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_E) {
					//Edits all nodes
					if (ctrlClicked) {
						EditAllNodes editDialog = new EditAllNodes(nodes);
						if (editDialog.getNodeList().size() > 0) {
							nodes = editDialog.getNodeList();
							repaint();
						}
						editDialog.dispose();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					// Saves the current map
					if (ctrlClicked) {
						SaveFile saveDialog = new SaveFile(nodes, connections);
						saveDialog.dispose();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_L) {
					if (ctrlClicked) {
						//Loads a file into the editor
						LoadFile loadDialog = new LoadFile();
						if (loadDialog.okClicked) {
							nodes = loadDialog.getNodes();
							connections = loadDialog.getConnections();
						}
						loadDialog.dispose();
						repaint();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					ctrlClicked = true;
				} else if (e.getKeyCode() == KeyEvent.VK_R){
					//Resets the map to original state
					if (ctrlClicked){
						nodes.clear();
						connections.clear();
						repaint();
					}
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
							}
							else if (rect!=mostRecentRect){
								int mostRecentIndex = nodeClicks.indexOf(mostRecentRect);
								int currentIndex = nodeClicks.indexOf(rect);
								mostRecentRect = null;
								if (connections.get(mostRecentIndex).get(currentIndex)==null) {
									Connection tempConnection = new ConnectionDetails().getStoredConnection();
									if (tempConnection != null) {
										connections.get(mostRecentIndex).set(currentIndex, tempConnection);
										connections.get(currentIndex).set(mostRecentIndex, tempConnection);
										repaint();
									}
								}
							}
						}
						else if (e.getButton() == MouseEvent.BUTTON3) {
							int currentIndex = nodeClicks.indexOf(rect);
							EditNode editDialog = new EditNode(nodes.get(currentIndex));
							if (editDialog.getStoredNode()!=null){
								nodes.set(currentIndex,editDialog.getStoredNode());
							}
							else{
								//Deletes the node
								for (int i = 0;i<nodes.size();i++)
									connections.get(i).remove(currentIndex);
								nodes.remove(currentIndex);
								connections.remove(currentIndex);
								rebalanceIDs();
							}
							editDialog.dispose();
							repaint();
						}
					}
				}
				if (!rectClicked) {
					for (Rect rect : connectionClicks) {
						if (rect.contains(e.getPoint())) {
							rectClicked = true;
							if (e.getButton() == MouseEvent.BUTTON3) {
								Connection clickedConnection = connections.get(rect.getStartIndex()).get(rect.getEndIndex());
								EditConnection editConnection = new EditConnection(clickedConnection);
								connections.get(rect.getStartIndex()).set(rect.getEndIndex(), editConnection.getStoredConnection());
								connections.get(rect.getEndIndex()).set(rect.getStartIndex(), editConnection.getStoredConnection());
								editConnection.dispose();
								repaint();
							}
						}
					}
				}
				if ((!rectClicked) && (e.getButton() == MouseEvent.BUTTON1)) {
					Point p = new Point((float) e.getX() / (float) getWidth(), (float) e.getY() / (float) getHeight());
					NodeDetails nodeDialog = new NodeDetails(nodes.size(), p);
					if (nodeDialog.getStoredNode() != null) {
						nodes.add(nodeDialog.getStoredNode());
						ArrayList<Connection> tempList = new ArrayList<Connection>();
						for (int i = 0; i<nodes.size();i++){
							tempList.add(null);
						}
						connections.add(tempList);
						for (int i=0; i<nodes.size()-1;i++){
							connections.get(i).add(null);
						}
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
		connectionClicks.clear();
		g.clearRect(0, 0, getWidth(), getHeight());
		int imageWidth = (int) ((float) mapImage.getWidth(this) * ((float) getHeight() / (float) mapImage.getHeight(this)));
		g.drawImage(mapImage, getWidth() - imageWidth, 0, imageWidth, getHeight(), this);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int fontHeight = metrics.getHeight();

		for (Node n : nodes) {
			if (n instanceof Station){
				g.setColor(Color.MAGENTA);
			} else{
				g.setColor(Color.GREEN);
			}
			g.fillOval((int) (n.getLocation().getX() * getWidth()) - 10, (int) (n.getLocation().getY() * getHeight()) - 10, 20, 20);
			Rectangle rect = new Rectangle((int) (n.getLocation().getX() * getWidth()) - 10, (int)(n.getLocation().getY() * getHeight()) - 10, 20, 20);
			nodeClicks.add(rect);
		}

		for (int x=0; x<nodes.size();x++) {
			for (int y = x; y < nodes.size(); y++) {
				if (connections.get(x).get(y) != null) {
					int startx = (int) nodeClicks.get(x).getLocation().getX() + 10;
					int starty = (int) nodeClicks.get(x).getLocation().getY() + 10;
					int endx = (int) nodeClicks.get(y).getLocation().getX() + 10;
					int endy = (int) nodeClicks.get(y).getLocation().getY() + 10;
					int fontWidth =  metrics.stringWidth(String.valueOf(connections.get(x).get(y).getDistance()));
					g.setColor(Color.BLUE);
					g.drawLine(startx, starty, endx, endy);
					Rect rect = new Rect(((startx + endx) / 2),(((starty + endy) / 2)-fontHeight)+3,fontWidth,fontHeight,x,y);
					connectionClicks.add(rect);
					g.setColor(Color.BLACK);
					g.drawString(String.valueOf(connections.get(x).get(y).getDistance()), (startx + endx) / 2, (starty + endy) / 2);
				}
			}
		}
	}
	private void rebalanceIDs(){
		for (int i = 0;i<nodes.size();i++){
			nodes.get(i).setId(i);
		}
	}
}