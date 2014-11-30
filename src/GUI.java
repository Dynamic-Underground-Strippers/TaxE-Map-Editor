import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;

public class GUI extends JFrame {
	Image mapImage;
	String text = "";
	DecimalFormat df = new DecimalFormat("#.##");
	ArrayList<Station> nodes = new ArrayList<Station>();

	public GUI() {
		this.nodes = loadNodeList("nodes.json");
		mapImage = new ImageIcon(getClass().getClassLoader().getResource("map.png")).getImage();
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
				if (e.getKeyCode() == KeyEvent.VK_Z) {
					nodes.remove(nodes.size() - 1);
					repaint();
				}
				if (e.getKeyCode() == KeyEvent.VK_E){
					EditNodes editDialog = new EditNodes(nodes);
					if (editDialog.getNodeList().size()>0){
						nodes = editDialog.getNodeList();
						repaint();
					}
					editDialog.dispose();

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
				text = df.format(p.getX()) + "%, " + df.format(p.getY()) + "%";
				NodeDetails nodeDialog = new NodeDetails(nodes.size(),p);
				if (nodeDialog.getStoredNode() != null){
					nodes.add(nodeDialog.getStoredNode());
					repaint();
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
		g.clearRect(0, 0, getWidth(), getHeight());
		int imageWidth = (int) ((float) mapImage.getWidth(this) * ((float) getHeight() / (float) mapImage.getHeight(this)));
		g.drawImage(mapImage, getWidth() - imageWidth, 0, imageWidth, getHeight(), this);
		g.setColor(Color.BLACK);
		g.drawString(text, 50, 50);
		g.setColor(Color.MAGENTA);
		for (Station n : nodes) {
			g.fillOval((int) (n.getLocation().getX() * getWidth()) - 10, (int) (n.getLocation().getY() * getHeight()) - 10, 20, 20);
		}
	}
	public void rebalanceIDs(){
		for (int i=0;i<nodes.size();i++){
			nodes.get(i).setId(i);
		}
	}
	private ArrayList<Station> loadNodeList(String fileName){
		ArrayList<Station> loadedNodes = new ArrayList<Station>();
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(
					fileName));
			JSONArray nodeList = (JSONArray) obj;

			for (int i =0; i<nodeList.size();i++){
				JSONObject nodeJSON = (JSONObject) nodeList.get(i);
				Station node = new Station(i,nodeJSON.get("name").toString(),new Point(nodeJSON.get("location").toString()));
				loadedNodes.add(node);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return loadedNodes;
	}
}