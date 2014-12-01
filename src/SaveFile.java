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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SaveFile extends JDialog {
    public boolean okClicked = false;
    private ArrayList<Station> nodes;
    public SaveFile(ArrayList<Station> nodes){
        this.nodes = nodes;
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
                    Path currentRelativePath = Paths.get("");
                    String s = currentRelativePath.toAbsolutePath().toString();
                    fc.setCurrentDirectory(new File(s));
                    fc.setSelectedFile(new File("nodes.json"));
                    int returnVal = fc.showSaveDialog(getParent());
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        tbFileName.setText(fc.getSelectedFile().getAbsolutePath());
                    }
                }
            });
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (new File(tbFileName.getText()).isFile()) {
                            save(tbFileName.getText());
                            okClicked = true;
                            close();
                        } else {

                        }
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
            JSONArray nodeListJSON = new JSONArray();
            for (Station n : this.nodes) {
                JSONObject node = new JSONObject();
                node.put("name", n.getName());
                node.put("location", n.getLocation().toString());
                nodeListJSON.add(node);
            }
            FileWriter file = new FileWriter(fileName);
            try {
                file.write(nodeListJSON.toJSONString());
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
    public ArrayList<Station> getNodes(){
        return this.nodes;
    }

}
