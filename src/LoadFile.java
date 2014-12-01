import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LoadFile extends JDialog {
    public boolean okClicked = false;
    private ArrayList<Station> nodes;
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
            jcomp2 = new JLabel ("Enter location of file you wish to load:");
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
                    Path currentRelativePath = Paths.get("");
                    String s = currentRelativePath.toAbsolutePath().toString();
                    fc.setCurrentDirectory(new File(s));
                    int returnVal = fc.showOpenDialog(getParent());
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        tbFileName.setText(fc.getSelectedFile().getAbsolutePath());
                    }
                }
            });
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (new File(tbFileName.getText()).isFile()) {
                        load(tbFileName.getText());
                        okClicked = true;
                        close();
                    } else {
                        // throw error or return message to user
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
            LoadFile.this.setVisible(false);
        }
    }
    private void load(String fileName){
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
        this.nodes = loadedNodes;
    }
    public ArrayList<Station> getNodes(){
        return this.nodes;
    }
}
