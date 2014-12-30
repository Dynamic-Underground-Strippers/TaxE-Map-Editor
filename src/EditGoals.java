import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;

public class EditGoals extends JDialog{
    private ArrayList<Goal> currentGoals;
    public EditGoals(ArrayList<Node> nodes, ArrayList<Goal> goals){
        setCurrentGoals(goals);
        this.setModal(true);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        MyPanel contentPanel = new MyPanel(nodes,goals);
        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setAutoscrolls(true);
        scroll.setViewportView(contentPanel);
        this.getContentPane().add (scroll);
        this.pack();
        this.setVisible(true);
        setPreferredSize(new Dimension(450,200));
    }

    private void setCurrentGoals(ArrayList<Goal> goals){
        this.currentGoals = goals;
    }

    public ArrayList<Goal> getCurrentGoals(){
        return this.currentGoals;
    }

    public class MyPanel extends JPanel {
        int starty = 10;
        private JButton btnAdd;
        private JButton btnOK;
        private JButton btnCancel;
        private ArrayList<Goal> tempGoals;
        private ArrayList<Node> tempNodes;
        public MyPanel(ArrayList<Node> nodes, ArrayList<Goal> goals) {
            this.tempGoals= new ArrayList<Goal>();
            //Unable to set tempGoals to equal goals because it uses it as a reference variable for whatever reason. Both ArrayLists are at the same memory address,
            //therefore a copy is created by adding all of the contents of the original arraylist to the temporary copy.
            for (Goal goal: goals){
                tempGoals.add(goal);
            }
            this.tempNodes = nodes;
            setLayout(null);
            ArrayList<String> initialNodeNames = new ArrayList<String>();
            for (int i = 0;i<nodes.size();i++){
                if (nodes.get(i) instanceof Station) {
                    initialNodeNames.add(nodes.get(i).getName());
                }
            }
            final ArrayList<String> nodeNames = initialNodeNames;
            for (Goal goal: tempGoals){
                addRow(goal,nodeNames,starty);
                starty += 30;
            }
            btnAdd = new JButton("Add");
            btnAdd.setBounds(445,starty,75,25);
            btnAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    saveGoals();
                    tempGoals.add(null);
                    redraw(tempNodes,tempGoals);

                }
            });
            btnOK = new JButton("OK");
            btnCancel = new JButton("Cancel");
            btnOK.setBounds(350,starty+30,75,25);
            btnCancel.setBounds(445,starty+30,75,25);
            add(btnAdd);
            add(btnOK);
            add(btnCancel);
            setPreferredSize(new Dimension(540,starty+60));
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    saveGoals();
                    boolean valid = true;
                    for (Goal goal:tempGoals){
                        if (goal.getStart()==goal.getEnd()){
                            valid = false;
                        } else if (goal.getPoints()==0){
                            valid = false;
                        }
                    }
                    if (valid){
                        setCurrentGoals(tempGoals);
                        close();
                    } else{
                        JOptionPane.showMessageDialog(EditGoals.this,"Please ensure that all goals have a points value of >0 and that no goal has the same start and end point", "Invalid Goals Detected", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   close();
                }
            });
        }
        private void close(){
            EditGoals.this.setVisible(false);
        }
        private void addRow(Goal goal,final ArrayList<String> nodeNames, final int starty){
            JLabel lblStart = new JLabel ("Start:");
            JComboBox cbStart = new JComboBox (nodeNames.toArray());
            JLabel lblEnd = new JLabel ("End:");
            JComboBox cbEnd = new JComboBox (nodeNames.toArray());
            JLabel lblPoints = new JLabel ("Points:");
            JTextField tbPoints = new JTextField (5);
            JButton btnDelete = new JButton ("Delete");
            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteRow((int) Math.floor(((JComponent)e.getSource()).getY()/30));
                }
            });
            if (goal != null){
                cbStart.setSelectedItem(goal.getStart().getName());
                cbEnd.setSelectedItem(goal.getEnd().getName());
                tbPoints.setText(String.valueOf(goal.getPoints()));
            }
            //add components
            add(lblStart);
            add (cbStart);
            add (lblEnd);
            add (cbEnd);
            add (lblPoints);
            add (tbPoints);
            add (btnDelete);
            //set component bounds (only needed by Absolute Positioning)
            lblStart.setBounds (5, starty, 40, 25);
            cbStart.setBounds (40, starty, 100, 25);
            lblEnd.setBounds (150, starty, 30, 25);
            cbEnd.setBounds (180, starty, 100, 25);
            lblPoints.setBounds (290, starty, 45, 25);
            tbPoints.setBounds (335, starty, 100, 25);
            btnDelete.setBounds (445, starty, 75, 25);
        }

        private void deleteRow(int rowIndex){
            saveGoals();
           tempGoals.remove(rowIndex);
            redraw(tempNodes,tempGoals);
        }

        private void saveGoals(){
            tempGoals.clear();
            String startField = "";
            String endField = "";
            int pointField = 0;
            for (int i=1;i<(this.getComponentCount()-2);i++){
                if (i%7==1){
                    startField = ((JComboBox) this.getComponent(i)).getSelectedItem().toString();
                } else if(i%7==3){
                    endField = ((JComboBox) this.getComponent(i)).getSelectedItem().toString();
                } else if(i%7==5){
                    try {
                        pointField = Integer.valueOf(((JTextField) this.getComponent(i)).getText());
                    } catch(Exception e){
                        pointField = 0;
                    }
                } else if (i%7==0){
                    Node startNode=null;
                    Node endNode=null;
                    for (Node node:tempNodes){
                        if (node.getName() == startField){
                            startNode = node;
                        }
                        if (node.getName() == endField){
                            endNode = node;
                        }
                    }
                    Goal tempGoal = new Goal(pointField,startNode,endNode);
                    tempGoals.add(tempGoal);
                }

            }
        }
    }
    private void redraw(ArrayList<Node> nodes,ArrayList<Goal> goals){
        this.getContentPane().removeAll();
        MyPanel contentPanel = new MyPanel(nodes,goals);
        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setAutoscrolls(true);
        scroll.setViewportView(contentPanel);
        this.getContentPane().add (scroll);
        this.pack();
    }
}
