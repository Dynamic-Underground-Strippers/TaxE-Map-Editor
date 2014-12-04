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
            //construct preComponents
            String[] cbTypeItems = {"Station", "Junction"};

            //construct components
            tbName = new JTextField (5);
            cbType = new JComboBox (cbTypeItems);
            jcomp3 = new JLabel ("Node Type:");
            jcomp4 = new JLabel ("Node Name:");
            btnOK = new JButton ("OK");
            btnCancel = new JButton ("Cancel");
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    storedNode = new Station(id,tbName.getText(),location);
                    NodeDetailsPanel.this.Close();
                }
            });
            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NodeDetailsPanel.this.Dispose();
                }
            });
            //adjust size and set layout
            setPreferredSize(new Dimension(257, 124));
            setLayout (null);

            //add components
            add (tbName);
            add (cbType);
            add (jcomp3);
            add (jcomp4);
            add (btnOK);
            add (btnCancel);

            //set component bounds (only needed by Absolute Positioning)
            tbName.setBounds (115, 55, 100, 25);
            cbType.setBounds (115, 10, 100, 25);
            jcomp3.setBounds(10, 10, 100, 25);
            jcomp4.setBounds (10, 55, 100, 25);
            btnOK.setBounds (50, 90, 100, 25);
            btnCancel.setBounds (155, 90, 100, 25);
        }
    }
}



