import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class LoadFile extends JDialog {
    public boolean okClicked = false;
    private ArrayList<Node> nodes;
    private ArrayList<ArrayList<Connection>> connections;
    public LoadFile(){
        this.setModal(true);
       this.getContentPane().add (new LoadPanel());
        this.pack();
        this.setVisible (true);
    }

    public class LoadPanel extends JPanel {
        private JTextField tbFileName;
        private JLabel jcomp2;
        private JButton btnBrowse;
        private JButton btnOK;
        private JButton btnCancel;
        public LoadPanel() {
            tbFileName = new JTextField (5);
            jcomp2     = new JLabel  ("Enter location of file you wish to load:");
            btnBrowse  = new JButton ("Browse...");
            btnOK      = new JButton ("OK");
            btnCancel  = new JButton ("Cancel");
            setPreferredSize (new Dimension (635, 110));
            setLayout (null);
            add (tbFileName);
            add (jcomp2);
            add (btnBrowse);
            add (btnOK);
            add (btnCancel);
            btnBrowse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final JFileChooser fc = new JFileChooser();
/*                    Path currentRelativePath = Paths.get("");
                    String s = currentRelativePath.toAbsolutePath().toString();
                    fc.setCurrentDirectory(new File(s));*/
                    int returnVal = fc.showOpenDialog(getParent());
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        tbFileName.setText(fc.getSelectedFile().getAbsolutePath());
                    }
                }
            });
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (new File(tbFileName.getText()).isFile() && (tbFileName.getText().substring(tbFileName.getText().length()-5)).equals(".json"))  {
                        load(tbFileName.getText());
                        okClicked = true;
                        close();
                    } else {
                        JOptionPane.showMessageDialog(LoadFile.this,"Please select a valid file to load", "Invalid File Selected", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   close();
                }
            });
            tbFileName.setBounds (25, 35, 470, 25);
            jcomp2.setBounds     (25, 5, 240, 30);
            btnBrowse.setBounds  (505, 35, 100, 25);
            btnOK.setBounds      (415, 75, 100, 25);
            btnCancel.setBounds  (525, 75, 100, 25);
        }
        private void close(){
            LoadFile.this.setVisible(false);
        }
    }
    private void load(String fileName){
        //TODO: Add loading of goals
        ArrayList<Node> loadedNodes = new ArrayList<Node>();
        ArrayList<ArrayList<Connection>> loadedConnections = new ArrayList<ArrayList<Connection>>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(
                    fileName));
            JSONObject mapList = (JSONObject) obj;
            JSONArray nodeList = (JSONArray) mapList.get("nodes");
            JSONArray connectionList = (JSONArray) mapList.get("connections");

            for (int i =0; i<nodeList.size();i++){
                JSONObject nodeJSON = (JSONObject) nodeList.get(i);
                if (nodeJSON.get("type").toString().equals("Station")){
                    Station node = new Station(i,nodeJSON.get("name").toString(),new Point(nodeJSON.get("location").toString()));
                    loadedNodes.add(node);
                } else{
                    Junction node = new Junction(i,nodeJSON.get("name").toString(),new Point(nodeJSON.get("location").toString()));
                    loadedNodes.add(node);
                }
            }
            for (int i =0; i<connectionList.size();i++){
                ArrayList<Connection> innerList = new ArrayList<Connection>();
                JSONArray innerArray = (JSONArray) connectionList.get(i);
                for (int x =0; x<innerArray.size();x++){
                    if (innerArray.get(x)!=null){
                        innerList.add(new Connection(Integer.valueOf(innerArray.get(x).toString())));
                    }else{
                        innerList.add(null);
                    }
                }
                loadedConnections.add(innerList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nodes = loadedNodes;
        this.connections = loadedConnections;
    }
    public ArrayList<Node> getNodes(){
        return this.nodes;
    }
    public ArrayList<ArrayList<Connection>> getConnections(){
        return this.connections;
    }
}
