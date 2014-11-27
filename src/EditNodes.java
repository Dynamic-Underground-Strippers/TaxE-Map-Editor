import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class EditNodes {

    public EditNodes(){
        JFrame frame = new JFrame ("MyPanel");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new MyPanel());
        frame.pack();
        frame.setVisible (true);
    }

    public class MyPanel extends JPanel {
        private JLabel lblNode;
        private JLabel lblName;
        private JLabel lblLocation;
        private JTextField tbName;
        private JTextField tbLocation;

        public MyPanel() {
            //construct components
            lblNode = new JLabel ("Node");
            lblName = new JLabel ("Name");
            lblLocation = new JLabel ("Location");
            tbName = new JTextField (5);
            tbLocation = new JTextField (5);

            //adjust size and set layout
            setPreferredSize (new Dimension (217, 361));
            setLayout (null);

            //add components
            add (lblNode);
            add (lblName);
            add (lblLocation);
            add (tbName);
            add (tbLocation);

            //set component bounds (only needed by Absolute Positioning)
            lblNode.setBounds (10, 10, 100, 25);
            lblName.setBounds (25, 35, 100, 25);
            lblLocation.setBounds (25, 65, 100, 25);
            tbName.setBounds (100, 35, 100, 25);
            tbLocation.setBounds (100, 65, 100, 25);
        }
    }
}
