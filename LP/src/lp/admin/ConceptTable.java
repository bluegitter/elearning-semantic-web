/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lp.admin;

import java.util.HashSet;
import ontology.resources.ISCB_Resource;

/**
 *
 * @author William Ma <williamma.wm@gmail.com>
 */
public class ConceptTable extends TableTemplate {

    public ConceptTable() {
        super();
    }
    private HashSet<ISCB_Resource> resources;

    @Override
    protected void initData() {
        resources = new HashSet<ISCB_Resource>();
        setResourceModel();
        clearTableModel();
        for (Object d : data) {
            ISCB_Resource res = (ISCB_Resource) d;
            Object[] oa = {res.getRid(), res.getName(), res.getDifficulty()};
            tableModel.addRow(oa);
        }
    }
}
