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
	ArrayList<Goal> goals = new ArrayList<Goal>();

	public GUI() {
		mapImage = new ImageIcon(getClass().getClassLoader().getResource("map.png")).getImage();
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					//Clears the currently selected node
					mostRecentRect = null;
				}
				else if (e.getKeyCode() == KeyEvent.VK_E) {
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
						SaveFile saveDialog = new SaveFile(nodes, connections,goals);
						saveDialog.dispose();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_L) {
					if (ctrlClicked) {
						//Loads a file into the editor
						LoadFile loadDialog = new LoadFile();
						if (loadDialog.okClicked) {
							nodes = loadDialog.getNodes();
							connections = loadDialog.getConnections();
							goals = loadDialog.getGoals();
						}
						loadDialog.dispose();
						repaint();
					}
				}else if (e.getKeyCode() == KeyEvent.VK_G) {
					// Edits the current goals
					if (ctrlClicked) {
						EditGoals goalsDialog = new EditGoals(nodes,goals);
						goals = goalsDialog.getCurrentGoals();
						goalsDialog.dispose();
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_R){
					//Resets the map to original state
					if (ctrlClicked){
						nodes.clear();
						connections.clear();
						goals.clear();
						repaint();
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					//Necessary to keep track of whether control is pressed for ctrl+KEY hotkeys
					ctrlClicked = true;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
					//Necessary to keep track of whether control is pressed for ctrl+KEY hotkeys
					ctrlClicked = false;
				}
			}
		});

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean rectClicked = false;
				//Checks whether a node or connection has been clicked
				for (Rectangle rect : nodeClicks) {
					if (rect.contains(e.getPoint())) {
						rectClicked = true;

						if (e.getButton() == MouseEvent.BUTTON1) {
							if (mostRecentRect == null){
								//Sets the most recently clicked node to be the node that was clicked to allow creation of connections
								mostRecentRect = rect;
							}
							else if (rect!=mostRecentRect){
								//If the node clicked isn't equal to the node that was clicked most recently then the connection creation dialog is initiated
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
							//If the node is right clicked then a node editing dialog is initiated
							int currentIndex = nodeClicks.indexOf(rect);
							EditNode editDialog = new EditNode(nodes.get(currentIndex));
							if (editDialog.getStoredNode()!=null){
								nodes.set(currentIndex,editDialog.getStoredNode());
							}
							else{
								//Deletes the node
								//TODO: delete any goal associated with this node
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
					//If a node is not clicked then all connections are checked
					for (Rect rect : connectionClicks) {
						if (rect.contains(e.getPoint())) {
							rectClicked = true;
							if (e.getButton() == MouseEvent.BUTTON3) {
								//If a connection is right clicked then the editing dialog is initiated
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
					//If no elements are clicked and the user left clicks on the map then a node is created
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
		//Resets click detection rectangles
		nodeClicks.clear();
		connectionClicks.clear();

		//Draws the map onto the JFrame
		g.clearRect(0, 0, getWidth(), getHeight());
		int imageWidth = (int) ((float) mapImage.getWidth(this) * ((float) getHeight() / (float) mapImage.getHeight(this)));
		g.drawImage(mapImage, getWidth() - imageWidth, 0, imageWidth, getHeight(), this);

		//Generates the font for the connection distance text
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));

		//Measures the metrics of the text to allow the connection lengths to be positioned appropriately
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int fontHeight = metrics.getHeight();

		//Draws each node
		for (Node n : nodes) {
			//Changes colour of node based on whether it is a station or junction
			if (n instanceof Station){
				//Edit here to change colour of Stations
				g.setColor(Color.MAGENTA);
			} else{
				//Edit here to change colour of Junctions
				g.setColor(Color.GREEN);
			}
			g.fillOval((int) (n.getLocation().getX() * getWidth()) - 10, (int) (n.getLocation().getY() * getHeight()) - 10, 20, 20);

			//Generates the rectangle required for click detection
			Rectangle rect = new Rectangle((int) (n.getLocation().getX() * getWidth()) - 10, (int)(n.getLocation().getY() * getHeight()) - 10, 20, 20);
			nodeClicks.add(rect);
		}

		//This code draws each connection on the map
		for (int x=0; x<nodes.size();x++) {
			for (int y = x; y < nodes.size(); y++) {
				if (connections.get(x).get(y) != null) {
					int startx = (int) nodeClicks.get(x).getLocation().getX() + 10;
					int starty = (int) nodeClicks.get(x).getLocation().getY() + 10;
					int endx = (int) nodeClicks.get(y).getLocation().getX() + 10;
					int endy = (int) nodeClicks.get(y).getLocation().getY() + 10;
					int fontWidth =  metrics.stringWidth(String.valueOf(connections.get(x).get(y).getDistance()));

					//Draws the line for the connection
					g.setColor(Color.BLUE);
					g.drawLine(startx, starty, endx, endy);

					//Generates the rectangle required for click detection
					Rect rect = new Rect(((startx + endx) / 2),(((starty + endy) / 2)-fontHeight)+3,fontWidth,fontHeight,x,y);
					connectionClicks.add(rect);

					//Draws the text showing the length of the connection onto the middle of the line
					g.setColor(Color.BLACK);
					g.drawString(String.valueOf(connections.get(x).get(y).getDistance()), (startx + endx) / 2, (starty + endy) / 2);
				}
			}
		}
	}
	private void rebalanceIDs(){
		//This method is used to ensure there are no wasted ID values (i.e uses all of 0 to nodes.size())
		for (int i = 0;i<nodes.size();i++){
			nodes.get(i).setId(i);
		}
	}
}