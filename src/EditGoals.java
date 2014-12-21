import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;

public class EditGoals extends JDialog{
    public EditGoals(ArrayList<Node> nodes){
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.getContentPane().add (new MyPanel(nodes));
        this.pack();
    }

    public class MyPanel extends JPanel {
        private JLabel lblStart;
        private JComboBox cbStart;
        private JLabel lblEnd;
        private JComboBox cbEnd;
        private JLabel lblPoints;
        private JTextField tbPoints;
        private JButton btnDelete;

        public MyPanel(ArrayList<Node> nodes) {
            String[] nodeNames = new String[nodes.size()];
            for (int i = 0;i<nodes.size();i++){
               nodeNames[i] = nodes.get(i).getName();
            }
            lblStart = new JLabel ("Start:");
            cbStart = new JComboBox (nodeNames);
            lblEnd = new JLabel ("End:");
            cbEnd = new JComboBox (nodeNames);
            lblPoints = new JLabel ("Points:");
            tbPoints = new JTextField (5);
            btnDelete = new JButton ("Delete");

            setPreferredSize (new Dimension (531, 43));
            setLayout (null);

            //add components
            add (lblStart);
            add (cbStart);
            add (lblEnd);
            add (cbEnd);
            add (lblPoints);
            add (tbPoints);
            add (btnDelete);

            //set component bounds (only needed by Absolute Positioning)
            lblStart.setBounds (5, 5, 40, 25);
            cbStart.setBounds (40, 5, 100, 25);
            lblEnd.setBounds (150, 5, 30, 25);
            cbEnd.setBounds (180, 5, 100, 25);
            lblPoints.setBounds (290, 5, 45, 25);
            tbPoints.setBounds (335, 5, 100, 25);
            btnDelete.setBounds (445, 5, 75, 25);
        }




    }

}
