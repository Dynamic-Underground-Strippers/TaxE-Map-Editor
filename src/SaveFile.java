import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveFile extends JDialog {
    public boolean okClicked = false;
    private ArrayList<Node> nodes;
    private ArrayList<ArrayList<Connection>> connections;
    public SaveFile(ArrayList<Node> nodes,ArrayList<ArrayList<Connection>> connections){
        this.nodes = nodes;
        this.connections=connections;
        this.setModal(true);
        this.getContentPane().add (new SavePanel());
        this.pack();
        this.setVisible (true);
    }

    public class SavePanel extends JPanel {
        private JTextField tbFileName;
        private JLabel jcomp2;
        private JButton btnBrowse;
        private JButton btnOK;
        private JButton btnCancel;
        public SavePanel() {
            tbFileName = new JTextField (5);
            jcomp2 = new JLabel ("Enter location of file you wish to save to:");
            btnBrowse = new JButton ("Browse...");
            btnOK = new JButton ("OK");
            btnCancel = new JButton ("Cancel");
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
                    fc.setSelectedFile(new File("nodes.json"));
                    int returnVal = fc.showSaveDialog(getParent());
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        tbFileName.setText(fc.getSelectedFile().getAbsolutePath());
                        if (!((tbFileName.getText().substring(tbFileName.getText().length()-5)).equals(".json"))){
                            tbFileName.setText(tbFileName.getText() + ".json");
                        }
                    }
                }
            });
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                            save(tbFileName.getText());
                            okClicked = true;
                            close();
                    } catch(Exception ex){
                        ex.printStackTrace();
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
            jcomp2.setBounds (25, 5, 240, 30);
            btnBrowse.setBounds (505, 35, 100, 25);
            btnOK.setBounds (415, 75, 100, 25);
            btnCancel.setBounds (525, 75, 100, 25);
        }
        private void close(){
            SaveFile.this.setVisible(false);
        }
    }
    private void save(String fileName) throws IOException {
        if (this.nodes.size()>0) {
            JSONObject mapJSON = new JSONObject();
            JSONArray connectionJSON = new JSONArray();
            JSONArray nodeListJSON = new JSONArray();

            for (Node n : this.nodes) {
                JSONObject node = new JSONObject();
                if (n instanceof Station){
                    node.put("type","Station");
                } else{
                    node.put("type","Junction");
                }

                node.put("name", n.getName());
                node.put("location", n.getLocation().toString());
                nodeListJSON.add(node);
            }

            for (ArrayList<Connection> innerArray:connections){
                JSONArray innerArrayJSON = new JSONArray();
                for (int i = 0;i<innerArray.size();i++){
                    if (innerArray.get(i)==null){
                        innerArrayJSON.add(null);
                    }else{
                        innerArrayJSON.add(new Integer(innerArray.get(i).getDistance()));
                    }

                }
                connectionJSON.add(innerArrayJSON);
            }

            mapJSON.put("nodes",nodeListJSON);
            mapJSON.put("connections",connectionJSON);

            FileWriter file = new FileWriter(fileName);
            try {
                file.write(mapJSON.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                file.flush();
                file.close();
            }
        }else{
            //Print message or return error if nothing to save
        }
    }
    public ArrayList<Node> getNodes(){
        return this.nodes;
    }

}
