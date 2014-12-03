import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
public class EditConnection extends JDialog{
    private Connection storedConnection=null;
    public EditConnection (Connection connection){
        this.storedConnection = connection;
        this.setModal(true);
        this.getContentPane().add (new MyPanel(connection));
        this.pack();
        this.setVisible(true);
    }
    private void setStoredConnection(Connection connection){
        this.storedConnection = connection;
    }
    public Connection getStoredConnection(){ return this.storedConnection;}
    public class MyPanel extends JPanel {

        private JLabel jcomp1;
        private JTextField tbDistance;
        private JButton btnDelete;
        private JButton btnOK;
        private JButton btnCancel;

        private void Close(){
            EditConnection.this.setVisible(false);
        }

        public MyPanel(final Connection connection) {
            jcomp1 = new JLabel ("Distance:");
            tbDistance = new JTextField (5);
            btnDelete = new JButton ("Delete");
            btnOK = new JButton ("OK");
            btnCancel = new JButton ("Cancel");
            setPreferredSize (new Dimension (301, 121));
            setLayout(null);
            tbDistance.setText(String.valueOf(connection.getDistance()));

            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connection tempConnection = new Connection(Integer.valueOf(tbDistance.getText()));
                    setStoredConnection(tempConnection);
                    Close();
                }
            });

            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setStoredConnection(null);
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
            add (tbDistance);
            add (btnDelete);
            add (btnOK);
            add (btnCancel);

            jcomp1.setBounds (15, 10, 100, 25);
            tbDistance.setBounds(80, 10, 195, 25);
            btnDelete.setBounds (5, 45, 100, 25);
            btnOK.setBounds (100, 45, 100, 25);
            btnCancel.setBounds (195, 45, 100, 25);
        }
    }
}
