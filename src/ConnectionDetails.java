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

        public NodeDetailsPanel() {
            tbDistance = new JTextField (5);
            jcomp3 = new JLabel ("Distance:");
            btnOK = new JButton ("OK");
            btnCancel = new JButton ("Cancel");

            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Creates a connection based on the parameters specified by the user
                    if (Integer.parseInt(tbDistance.getText())>0) {
                        storedConnection = new Connection(Integer.parseInt(tbDistance.getText()));
                        NodeDetailsPanel.this.Close();
                    }else{

                    }
                }
            });

            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Close();
                }
            });
            setPreferredSize(new Dimension(257, 80));
            setLayout (null);
            add (tbDistance);
            add (jcomp3);
            add (btnOK);
            add (btnCancel);
            tbDistance.setBounds (115, 10, 100, 25);
            jcomp3.setBounds(10, 10, 100, 25);
            btnOK.setBounds (50, 50, 100, 25);
            btnCancel.setBounds (155, 50, 100, 25);
        }
    }
}



