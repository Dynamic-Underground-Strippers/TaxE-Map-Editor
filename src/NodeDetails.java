import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class NodeDetails extends JDialog {
    private Node storedNode;

    NodeDetails(final int id, final Point location){
        this.storedNode = null;
        this.setAlwaysOnTop(true);
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().add (new NodeDetailsPanel(id,location));
        this.pack();
        this.setVisible(true);
    }

    public Node getStoredNode(){
        return storedNode;
    }

    private class NodeDetailsPanel extends JPanel {
        private JTextField  tbName;
        private JComboBox cbType;
        private JLabel jcomp3;
        private JLabel jcomp4;
        private JButton btnOK;
        private JButton btnCancel;

        public void Close(){
            NodeDetails.this.setVisible(false);
        }

        public void Dispose(){
            NodeDetails.this.dispose();
        }

        public NodeDetailsPanel(final int id, final Point location) {
            String[] cbTypeItems = {"Station", "Junction"};
            tbName = new JTextField (5);
            cbType = new JComboBox (cbTypeItems);
            jcomp3 = new JLabel ("Node Type:");
            jcomp4 = new JLabel ("Node Name:");
            btnOK = new JButton ("OK");
            btnCancel = new JButton ("Cancel");
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean valid = true;
                    if (tbName.getText().equals("")){
                        JOptionPane.showMessageDialog(NodeDetails.this,"Please enter a name for the node", "No Node Name Entered", JOptionPane.PLAIN_MESSAGE);
                        valid = false;
                    }
                    if (valid) {
                        if (cbType.getSelectedItem().toString() == "Station") {
                            storedNode = new Station(id, tbName.getText(), location);
                        } else {
                            storedNode = new Junction(id, tbName.getText(), location);
                        }
                        NodeDetailsPanel.this.Close();
                    }

                }
            });
            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NodeDetailsPanel.this.Dispose();
                }
            });
            setPreferredSize(new Dimension(257, 124));
            setLayout(null);
            add (tbName);
            add(cbType);
            add(jcomp3);
            add (jcomp4);
            add (btnOK);
            add (btnCancel);
            tbName.setBounds (115, 55, 100, 25);
            cbType.setBounds (115, 10, 100, 25);
            jcomp3.setBounds(10, 10, 100, 25);
            jcomp4.setBounds (10, 55, 100, 25);
            btnOK.setBounds (50, 90, 100, 25);
            btnCancel.setBounds (155, 90, 100, 25);
        }
    }
}



