import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class EditNodes extends JDialog{

    public EditNodes(ArrayList<Station> nodes) {
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        MyPanel contentPanel = new MyPanel(nodes);
        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setAutoscrolls(true);
        scroll.setViewportView(contentPanel);
        this.getContentPane().add (scroll);
       this.pack();
       this.setVisible(true);



    }

    public class MyPanel extends JPanel {
        private JLabel lblNode;
        private JLabel lblName;
        private JLabel lblLocation;
        private JTextField tbName;
        private JTextField tbLocation;

        public MyPanel(ArrayList<Station> nodes) {
            //construct components
            setLayout (null);
            int starty = 10;
            setPreferredSize (new Dimension (250,20 + nodes.size()*80));
            for (Station n:nodes) {
                lblNode = new JLabel("Node " + String.valueOf(n.getId()));
                lblName = new JLabel("Name");
                lblLocation = new JLabel("Location");
                tbName = new JTextField(5);
                tbName.setText(n.getName());
                tbLocation = new JTextField(5);
                tbLocation.setText(String.valueOf(n.getLocation().getX()*1920));
                lblNode.setBounds(10, starty, 100, 25);
                lblName.setBounds (25, starty+25, 100, 25);
                lblLocation.setBounds (25, starty+55, 100, 25);
                tbName.setBounds (100, starty+25, 100, 25);
                tbLocation.setBounds (100, starty+55, 100, 25);
                starty+= 80;
                add (lblNode);
                add (lblName);
                add (lblLocation);
                add (tbName);
                add (tbLocation);
            }
            //adjust size and set layout


            //add components


            //set component bounds (only needed by Absolute Positioning)

        }
    }
}
