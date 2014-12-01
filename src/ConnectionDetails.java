import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class ConnectionDetails extends JDialog {
    private Connection storedConnection;
    ConnectionDetails(){
        this.storedConnection = null;
        this.setAlwaysOnTop(true);
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().add (new NodeDetailsPanel());
        this.pack();
        this.setVisible(true);
        this.setTitle("New Connection");
    }

    public Connection getStoredConnection(){
        return storedConnection;
    }

    private class NodeDetailsPanel extends JPanel {
        private JTextField  tbDistance;
        private JLabel jcomp3;
        private JButton btnOK;
        private JButton btnCancel;

        public void Close(){
            ConnectionDetails.this.setVisible(false);
        }
        public void Dispose(){
            ConnectionDetails.this.dispose();
        }
        public NodeDetailsPanel() {
            tbDistance = new JTextField (5);
            jcomp3 = new JLabel ("Distance:");
            btnOK = new JButton ("OK");
            btnCancel = new JButton ("Cancel");
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    storedConnection = new Connection(Integer.parseInt(tbDistance.getText()));
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
            setPreferredSize(new Dimension(257, 80));
            setLayout (null);

            //add components
            add (tbDistance);
            add (jcomp3);
            add (btnOK);
            add (btnCancel);

            //set component bounds (only needed by Absolute Positioning)
            tbDistance.setBounds (115, 10, 100, 25);
            jcomp3.setBounds(10, 10, 100, 25);
            btnOK.setBounds (50, 50, 100, 25);
            btnCancel.setBounds (155, 50, 100, 25);
        }
    }
}



