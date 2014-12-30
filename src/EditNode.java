import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
public class EditNode extends JDialog{
    private Node storedNode=null;
    public EditNode(Node node){
        this.storedNode = node;
        this.setModal(true);
        this.getContentPane().add (new MyPanel(node));
        this.pack();
        this.setVisible(true);
    }
    private void setStoredNode(Node node){
        this.storedNode = node;
    }
    public Node getStoredNode(){ return this.storedNode;}
    public class MyPanel extends JPanel {

        private JLabel jcomp1;
        private JTextField tbName;
        private JLabel jcomp3;
        private JTextField tbLocation;
        private JButton btnDelete;
        private JButton btnOK;
        private JButton btnCancel;
        private JLabel jcomp4;
        private JComboBox cbType;
        private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        private void Close(){
            EditNode.this.setVisible(false);
        }
        public MyPanel(final Node node) {
            String[] cbTypeItems = {"Station", "Junction"};
            cbType = new JComboBox (cbTypeItems);
            jcomp4= new JLabel("Type:");
            jcomp1 = new JLabel ("Name:");
            tbName = new JTextField (5);
            jcomp3 = new JLabel ("Location:");
            tbLocation = new JTextField (5);
            btnDelete = new JButton ("Delete");
            btnOK = new JButton ("OK");
            btnCancel = new JButton ("Cancel");
            setPreferredSize (new Dimension (301, 160));
            setLayout(null);
            tbName.setText(node.getName());
            tbLocation.setText(String.valueOf((int) (node.getLocation().getX()*screenSize.getWidth())) + "," + String.valueOf((int)(node.getLocation().getY() * screenSize.getHeight())));
            cbType.setSelectedItem(node.getClass().getName().toString());
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean valid = true;
                    float[] locations = {0,0};
                    if (tbName.getText().equals("")){
                        JOptionPane.showMessageDialog(EditNode.this,"Please enter a name for the node", "No Node Name Entered", JOptionPane.PLAIN_MESSAGE);
                        valid = false;
                    }
                    try{
                        new Point(tbLocation.getText());
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(EditNode.this,"Invalid format for the location", "Invalid Location", JOptionPane.PLAIN_MESSAGE);
                        valid = false;
                    }
                    if (valid) {
                        int count = 0;
                        for (String locationStr: tbLocation.getText().split(",", 0)){
                            locations[count] = Float.parseFloat(locationStr);
                            count++;
                        }
                        if (cbType.getSelectedItem().toString() == "Station") {
                            Station tempNode = new Station(node.getId(), tbName.getText(), new Point((float) (locations[0]/screenSize.getWidth()),(float) (locations[1]/screenSize.getHeight())));
                            setStoredNode(tempNode);
                        } else {
                            Junction tempNode = new Junction(node.getId(), tbName.getText(), new Point((float) (locations[0]/screenSize.getWidth()),(float) (locations[1]/screenSize.getHeight())));
                            setStoredNode(tempNode);
                        }
                        Close();
                    }
                }
            });

            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setStoredNode(null);
                    Close();
                }
            });

            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Close();
                }
            });

            add(jcomp1);
            add(tbName);
            add(jcomp3);
            add(tbLocation);
            add (btnDelete);
            add (btnOK);
            add (btnCancel);
            add (cbType);
            add (jcomp4);
            cbType.setBounds(80, 10, 195, 25);
            jcomp4.setBounds(15, 10, 100, 25);
            jcomp1.setBounds (15, 45, 100, 25);
            tbName.setBounds (80, 45, 195, 25);
            jcomp3.setBounds (15, 80, 100, 25);
            tbLocation.setBounds (80,80, 195, 25);
            btnDelete.setBounds (5, 120, 100, 25);
            btnOK.setBounds (100, 120, 100, 25);
            btnCancel.setBounds (195, 120, 100, 25);
        }
    }
}
