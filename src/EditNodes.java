import java.awt.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditNodes extends JDialog{
    private ArrayList<Station> nodes = new ArrayList<Station>();
    public EditNodes(ArrayList<Station> nodes) {
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
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
        private JButton btnOK;
        private JButton btnCancel;

        public MyPanel(ArrayList<Station> nodes) {

            setLayout (null);
            int starty = 10;
            setPreferredSize (new Dimension (250,50 + nodes.size()*80));

            for (Station n:nodes) {
                lblNode = new JLabel("Node " + String.valueOf(n.getId()));
                lblName = new JLabel("Name");
                lblLocation = new JLabel("Location");
                tbName = new JTextField(5);
                tbName.setText(n.getName());
                tbLocation = new JTextField(5);
                tbLocation.setText(String.valueOf(n.getLocation().getX()*1920) + "," + String.valueOf(n.getLocation().getY()*1080));
                lblNode.setBounds(10, starty, 100, 25);
                lblName.setBounds (25, starty+25, 100, 25);
                lblLocation.setBounds (25, starty+55, 100, 25);
                tbName.setBounds (100, starty+25, 100, 25);
                tbLocation.setBounds (100, starty+55, 100, 25);
                starty+= 80;
                add (lblNode);
                add (lblName);
                add (lblLocation);
                //First tb at location 3
                add (tbName);
                add (tbLocation);
            }

            btnOK =  new JButton("OK");
            btnCancel = new JButton("Cancel");
            btnOK.setBounds(75,starty+10,75,25);
            btnCancel.setBounds(165,starty+10,75,25);

            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int nodeIndex=0;
                    Component[] components = MyPanel.this.getComponents();
                    Component component = null;
                    String nameField="";
                    String locationField="";
                    ArrayList<Station> nodes = new ArrayList<Station>();
                    float[] locations = {0,0};
                    for (int i = 3; i < components.length; i++)
                    {
                        component = components[i];
                            if ((i %  5)==3){
                                nameField = ((JTextField)component).getText();
                            }

                            else if ((i % 5)==4){
                                locationField =((JTextField)component).getText();
                                int count = 0;
                                for (String locationStr: locationField.split(",", 0)){
                                   locations[count] = Float.parseFloat(locationStr);
                                    count++;
                                }

                            }

                            else if ((i % 5)==0){
                                Station tempStation = new Station(nodeIndex,nameField,new Point(locations[0]/1920,locations[1]/1080));
                                nodeIndex+=1;
                                nodes.add(tempStation);
                            }
                    }

                    setNodeList(nodes);
                    Close();
                }
            });

            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Dispose();
                }
            });

            add(btnOK);
            add(btnCancel);
        }

        private void Dispose(){
            EditNodes.this.dispose();
        }

        private void Close(){
            EditNodes.this.setVisible(false);
        }
    }

    private void setNodeList(ArrayList<Station> nodes){
        this.nodes = nodes;
    }

    public ArrayList<Station> getNodeList(){
        return nodes;
    }
}
