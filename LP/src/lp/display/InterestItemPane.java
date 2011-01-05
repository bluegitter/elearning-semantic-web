/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.display;

/**
 *
 * @author william
 */
public class InterestItemPane extends LPItemPane implements java.awt.event.MouseListener {

    public static void main(String[] args) {
        jena.impl.ELearnerModelImpl emi = new jena.impl.ELearnerModelImpl(new java.io.File("test\\owl\\conceptsAndresource_RDF-XML.owl"));
        ontology.people.ELearner el = emi.getELearner("el001");
        java.util.ArrayList<ontology.EInterest> interests = emi.getEInterests(el);
        System.out.println(interests);
        javax.swing.JFrame frame = new javax.swing.JFrame();
        // LPListPane lppane = new LPListPane(interests);
        InterestItemPane p = new InterestItemPane(interests.get(0));
        frame.add(p);
        frame.pack();
        frame.setVisible(true);
    }

    public InterestItemPane() {
        super();
    }

    public InterestItemPane(ontology.EInterest interest) {
        this.interest = interest;
        jLabel1.setIcon(null);
        addMouseListener(this);
        initComponents();

    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(LPItemPane.class);
        jLabel1.setIcon(resourceMap.getIcon("jLabel1.icon")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        icon = resourceMap.getIcon("jLabel1.icon");

        jLabel2.setBackground(resourceMap.getColor("jLabel2.background")); // NOI18N
        jLabel2.setIcon(resourceMap.getIcon("jLabel2.icon")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jLabel2.setText(interest.getEConcept().getName());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE).addContainerGap()));
    }
    private ontology.EInterest interest;

    public ontology.EInterest getInterest() {
        return interest;
    }

    public void setInterest(ontology.EInterest interest) {
        this.interest = interest;
    }
}
