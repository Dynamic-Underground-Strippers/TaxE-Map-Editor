import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditAllNodes extends JDialog{
    private ArrayList<Node> nodes;

    public EditAllNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        MyPanel contentPanel = new MyPanel(this.nodes);
        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setAutoscrolls(true);
        scroll.setViewportView(contentPanel);
        this.getContentPane().add (scroll);
       this.pack();
       this.setVisible(true);
        this.setPreferredSize(new Dimension(260, 400));
    }

    public class MyPanel extends JPanel {
        private JLabel lblNode;
        private JLabel lblName;
        private JLabel lblLocation;
        private JTextField tbName;
        private JTextField tbLocation;
        private JButton btnOK;
        private JButton btnCancel;
        private JComboBox cbType;
        private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        public MyPanel(ArrayList<Node> nodes) {

            setLayout (null);
            int starty = 10;
            setPreferredSize (new Dimension (250,50 + nodes.size()*95));

            for (Node n:nodes) {
                String[] cbTypeItems = {"Station", "Junction"};
                cbType = new JComboBox(cbTypeItems);
                if (n instanceof Station){
                    cbType.setSelectedItem("Station");
                } else{
                    cbType.setSelectedItem("Junction");
                }

                lblNode = new JLabel("Node " + String.valueOf(n.getId()) + " - Type: ");
                lblName = new JLabel("Name");
                lblLocation = new JLabel("Location");
                tbName = new JTextField(5);
                tbName.setText(n.getName());
                tbLocation = new JTextField(5);
                tbLocation.setText(String.valueOf((int)(n.getLocation().getX()* screenSize.getWidth())) + "," + String.valueOf((int) (n.getLocation().getY()*screenSize.getHeight())));
                cbType.setBounds(100,starty,100,25);
                lblNode.setBounds(10, starty, 100, 25);
                lblName.setBounds(25, starty + 30, 100, 25);
                lblLocation.setBounds(25, starty + 60, 100, 25);
                tbName.setBounds(100, starty + 30, 100, 25);
                tbLocation.setBounds (100, starty+60, 100, 25);
                starty+= 95;
                add (lblNode);
                add (cbType);
                add (lblName);
                add (tbName);
                add (lblLocation);
                add (tbLocation);

            }

            btnOK =  new JButton("OK");
            btnCancel = new JButton("Cancel");
            btnOK.setBounds(75,starty+10,75,25);
            btnCancel.setBounds(165,starty+10,75,25);

            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Component[] components = MyPanel.this.getComponents();
                    Component component;
                    String nameField="";
                    String locationField="";
                    boolean station = false;
                    ArrayList<Node> nodes = new ArrayList<Node>();
                    float[] locations = {0,0};
                    for (int i = 1; i < components.length; i++)
                    {
                        component = components[i];
                            if ((i %  6)==3){
                                nameField = ((JTextField)component).getText();
                            }

                            else if ((i % 6)==5){
                                locationField =((JTextField)component).getText();
                                int count = 0;
                                for (String locationStr: locationField.split(",", 0)){
                                    locations[count] = Float.parseFloat(locationStr);
                                    count++;
                                }

                            } else if (((i % 6)==1) && (component instanceof JComboBox)) {
                                if (((JComboBox) component).getSelectedItem().toString().equals("Station")){
                                    station = true;
                                } else{
                                    station = false;
                                }

                            } else if ((i % 6)==0){
                                if (station){
                                    Station tempNode = new Station(nodes.size(),nameField,new Point((float) (locations[0]/screenSize.getWidth()),(float) (locations[1]/screenSize.getHeight())));
                                    nodes.add(tempNode);
                                }else{
                                    Junction tempNode = new Junction(nodes.size(),nameField,new Point((float) (locations[0]/screenSize.getWidth()),(float) (locations[1]/screenSize.getHeight())));
                                    nodes.add(tempNode);
                                }
                            }
                    }

                    setNodeList(nodes);
                    Close();
                }
            });

            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setNodeList(new ArrayList<Node>());
                    Close();
                }
            });

            add(btnOK);
            add(btnCancel);
        }

        private void Close(){
            EditAllNodes.this.setVisible(false);
        }
    }



    private void setNodeList(ArrayList<Node> nodes){
        this.nodes = nodes;
    }

    public ArrayList<Node> getNodeList(){
        return nodes;
    }
}
