import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
public class EditNode extends JDialog{
    private Node storedNode=null;
    public EditNode(Station node){
        this.storedNode = node;
        this.setModal(true);
        this.getContentPane().add (new MyPanel(node));
        this.pack();
        this.setVisible(true);
    }
    private void setStoredNode(Station node){
        this.storedNode = node;
    }
    public class MyPanel extends JPanel {

        private JLabel jcomp1;
        private JTextField tbName;
        private JLabel jcomp3;
        private JTextField tbLocation;
        private JButton btnDelete;
        private JButton btnOK;
        private JButton btnCancel;
        private void Close(){
            EditNode.this.setVisible(false);
        }
        public MyPanel(final Node node) {
            jcomp1 = new JLabel ("Name:");
            tbName = new JTextField (5);
            jcomp3 = new JLabel ("Location:");
            tbLocation = new JTextField (5);
            btnDelete = new JButton ("Delete");
            btnOK = new JButton ("OK");
            btnCancel = new JButton ("Cancel");
            setPreferredSize (new Dimension (301, 121));
            setLayout(null);
            tbName.setText(node.getName());
            tbLocation.setText(node.getLocation().toString());
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Station tempNode = new Station(node.getId(),tbName.getText(),new Point(tbLocation.getText()));
                    setStoredNode(tempNode);
                    Close();
                }
            });
            add(jcomp1);
            add (tbName);
            add (jcomp3);
            add (tbLocation);
            add (btnDelete);
            add (btnOK);
            add (btnCancel);
            jcomp1.setBounds (15, 10, 100, 25);
            tbName.setBounds (80, 10, 195, 25);
            jcomp3.setBounds (15, 45, 100, 25);
            tbLocation.setBounds (80, 45, 195, 25);
            btnDelete.setBounds (5, 85, 100, 25);
            btnOK.setBounds (100, 85, 100, 25);
            btnCancel.setBounds (195, 85, 100, 25);
        }
    }
}
