/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2000-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.messaging.visualvm.ui;

import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServerConnection;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import com.sun.messaging.visualvm.chart.ChartPanel;
import com.sun.tools.visualvm.core.ui.components.DataViewComponent;

/**
 * This is the abstract superclass of all JPanels that show a table showing a list of resources, each with multiple attributes
 * 
 * Features of this class:
 * - The panel has a right-mouse menu which offers the following options:
 *      Configure Attributes Displayed"
 *      Auto reload
 */
@SuppressWarnings("serial")
public abstract class MQResourceList extends MQList {
	
    ConfigureDisplayListDialog cfd = null;
    private MQResourceListTableModel tableModel = null;
      
	public MQResourceList(DataViewComponent dvc) {
    	super(dvc);
        initTableModel();
        initComponents();
        
        // Additions to handle cell selection
             
        dataTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    dataTable.setCellSelectionEnabled(true);
        
        // configure row selection
        dataTable.setRowSelectionAllowed(true);

		// configure column selection
		dataTable.setColumnSelectionAllowed(true);

		dataTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {

				if (e.isPopupTrigger()) {
					if (dataTable.getSelectedRow()>=0 && dataTable.getSelectedColumn()>=0){
						// a cell is selected
						//TODO ideally we should merge this with the tablePopup menu
						cellPopup.show(e.getComponent(), e.getX(), e.getY());
					} else {
						// no cell selected: show the default menu
						tablePopup.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}
		});


        dataTable.setRowSorter(new TableRowSorter<MQResourceListTableModel>(tableModel));
        tableModel.addTableModelListener(this);
        setAutoReloadValue(tableModel.getInterval());
    }
   
    public abstract void initTableModel();

    public abstract String[] getCompleteAttrsList();

    public abstract String[] getinitialDisplayedAttrsList();
    


    
    /**
     * Return the name of the attribute that can used as the key 
     * @return
     */
    public abstract String getPrimaryAttribute();

    public abstract void handleItemQuery(Object obj);

    public MQResourceListTableModel getTableModel() {
        return (tableModel);
    }

    public void setTableModel(MQResourceListTableModel model) {
        tableModel = model;
    }
    
    public JTableHeader getTableHeader(){
    	
    	return new JTableHeader(dataTable.getColumnModel()) {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                String tooltipText="";
                if (index>=0){
                	int realIndex = 
                        columnModel.getColumn(index).getModelIndex();
                	tooltipText = getTooltipForColumn(realIndex);
                }
                return tooltipText;
            }
        };

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePopup = new javax.swing.JPopupMenu();
        confDataDisplayedItem = new javax.swing.JMenuItem();
        autoReloadStatus = new javax.swing.JCheckBoxMenuItem();
        cellPopup = new javax.swing.JPopupMenu();
        displayOnChart = new javax.swing.JMenuItem();
        totalJLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        totalValue = new javax.swing.JLabel();
        autoReloadLabel = new javax.swing.JLabel();
        autoReloadValue = new javax.swing.JLabel();

        confDataDisplayedItem.setText(org.openide.util.NbBundle.getMessage(MQResourceList.class, "MQResourceList.confDataDisplayedItem.text")); // NOI18N
        confDataDisplayedItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confDataDisplayedItemActionPerformed(evt);
            }
        });
        tablePopup.add(confDataDisplayedItem);

        autoReloadStatus.setSelected(true);
        autoReloadStatus.setText(org.openide.util.NbBundle.getMessage(MQResourceList.class, "MQResourceList.autoReloadStatus.text")); // NOI18N
        autoReloadStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                autoReloadStatusItemStateChanged(evt);
            }
        });
        tablePopup.add(autoReloadStatus);

        displayOnChart.setText(org.openide.util.NbBundle.getMessage(MQResourceList.class, "MQResourceList.displayOnChart.text")); // NOI18N
        displayOnChart.setName("displayOnChart"); // NOI18N
        displayOnChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayOnChartActionPerformed(evt);
            }
        });
        cellPopup.add(displayOnChart);

        setComponentPopupMenu(tablePopup);

        totalJLabel.setText(org.openide.util.NbBundle.getMessage(MQResourceList.class, "MQResourceList.totalJLabel.text")); // NOI18N

        jScrollPane1.setInheritsPopupMenu(true);
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPane1MousePressed(evt);
            }
        });

        dataTable.setModel(getTableModel());
        dataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        dataTable.setColumnSelectionAllowed(true);
        dataTable.setTableHeader(getTableHeader());
        jScrollPane1.setViewportView(dataTable);

        totalValue.setText(org.openide.util.NbBundle.getMessage(MQResourceList.class, "MQResourceList.totalValue.text")); // NOI18N

        autoReloadLabel.setText(org.openide.util.NbBundle.getMessage(MQResourceList.class, "MQResourceList.autoReloadLabel.text")); // NOI18N

        autoReloadValue.setText(org.openide.util.NbBundle.getMessage(MQResourceList.class, "MQResourceList.autoReloadValue.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalJLabel)
                .addGap(18, 18, 18)
                .addComponent(totalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autoReloadLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autoReloadValue, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalValue)
                    .addComponent(totalJLabel)
                    .addComponent(autoReloadLabel)
                    .addComponent(autoReloadValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void jScrollPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MousePressed
// TODO add your handling code here:
}//GEN-LAST:event_jScrollPane1MousePressed

private void confDataDisplayedItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confDataDisplayedItemActionPerformed
    if (cfd == null) {
        cfd = new ConfigureDisplayListDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
        cfd.setNotDisplayedList(getCompleteAttrsList());
        cfd.setAlwaysDisplay(getPrimaryAttribute());
        cfd.addMQUIEventListener(this);
        cfd.setLocationRelativeTo((Frame) SwingUtilities.getWindowAncestor(this));
    }
    
    // tell the ConfigureDisplayListDialog what attributes are currently being displayed
    // user may have manually reordered columns, so need to get column names from the JTable, not the TableModel
    //cfd.setDisplayedList(tableModel.getAttributes());
    int nCols = dataTable.getTableHeader().getColumnModel().getColumnCount();
    String[] currentlyDisplayedAttributes = new String[nCols];
    for (int i = 0; i < nCols; i++) {
		currentlyDisplayedAttributes[i]=dataTable.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString();
	}
    cfd.setDisplayedList(currentlyDisplayedAttributes);
    		
    cfd.setVisible(true);
}//GEN-LAST:event_confDataDisplayedItemActionPerformed

private void autoReloadStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_autoReloadStatusItemStateChanged
    int stateChange = evt.getStateChange();

    if (stateChange == ItemEvent.DESELECTED) {
        tableModel.stop();
        setAutoReloadValue(-1);
    } else if (stateChange == ItemEvent.SELECTED) {
        tableModel.start();
        setAutoReloadValue(tableModel.getInterval());
    }
}//GEN-LAST:event_autoReloadStatusItemStateChanged

private void displayOnChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayOnChartActionPerformed
	
		MQResourceListTableModel model = (MQResourceListTableModel) dataTable.getModel();
		
		// Work out the name of the attribute we're using to define each row (rowIndentifierAttribute) 
		String rowIndentifierAttribute = getPrimaryAttribute();
		
		// Handle each column separately
		for (int iCol = 0; iCol < dataTable.getSelectedColumns().length; iCol++) {
			int thisSelectedColumnInModel = dataTable.convertColumnIndexToModel(dataTable.getSelectedColumns()[iCol]);
			
			// Work out the name of the attribute being charted (chartedAttribute),
			String thisChartedAttribute = model.getColumnName(thisSelectedColumnInModel);
			
			String[] rowIdentifierValues = new String[dataTable.getSelectedRows().length];
			boolean invalidColumn=false;
			for (int j = 0; j < dataTable.getSelectedRows().length; j++) {

				int thisSelectedRowInModel = dataTable.convertRowIndexToModel(dataTable.getSelectedRows()[j]);
								
				// Work out the *value* of the attribute we're using to identify this row (rowIdentifierValue)
				// This is the value of the primary attribute for this row
				Object thisRowIdentifierValue=null;
				for (int k = 0; k < model.getAttributes().length; k++) {
					if (model.getAttributes()[k].equals(rowIndentifierAttribute)){
						thisRowIdentifierValue=model.getValueAt(thisSelectedRowInModel, k);
						break;
					}
				}
				
				if (!(thisRowIdentifierValue instanceof String)){
					JOptionPane.showMessageDialog(this, "Debug: rowIndentifierAttribute "+rowIndentifierAttribute+" is not a string: "+rowIndentifierAttribute);
					return;
				}
				rowIdentifierValues[j]=(String)thisRowIdentifierValue;
				
				// look at the value of the selected cell and make sure it's a number
				Object currentValue = model.getValueAt(dataTable.getSelectedRow(),thisSelectedColumnInModel);
				if (!(currentValue instanceof Number)){
					JOptionPane.showMessageDialog(this, "Cannot chart non-numeric attribute "+thisChartedAttribute+", ignoring it");
					invalidColumn=true;
                    break;
				}
			}
			
			if (!invalidColumn){
			
		        // Create the charting JPanel "
		        ChartPanel chartPanel = new ChartPanel(thisChartedAttribute, rowIndentifierAttribute , rowIdentifierValues);
		        
		        // add the charting JPanel to the data view component representing the master view  
		        // set the name of the charted attribute as the title
		        this.getDvc().addDetailsView(new DataViewComponent.DetailsView(thisChartedAttribute, null, 10, chartPanel, null), this.getCorner());    
		        
	        	// link the detail view with its charting JPanel
		        registerChart(chartPanel);			
			}
			
		}
	
}//GEN-LAST:event_displayOnChartActionPerformed


	public void setTotalValue(String s) {
        totalValue.setText(s);
    }
    
    public void setAutoReloadValue(int i){
        String val;
        
        if (i == -1)  {
            val = "off";
        } else  {
            val = Integer.toString(i) + " seconds";
        }
        
        autoReloadValue.setText(val);
    }
    
    public void setMBeanServerConnection(MBeanServerConnection mbsc) {
        if (mbsc != null)  {
            tableModel.setMBeanServerConnection(mbsc);
            tableModel.start();
        }
    }
    
    public MBeanServerConnection getMBeanServerConnection()  {
        if (tableModel != null) {
            return (tableModel.getMBeanServerConnection());
        }
        
        return (null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel autoReloadLabel;
    private javax.swing.JCheckBoxMenuItem autoReloadStatus;
    private javax.swing.JLabel autoReloadValue;
    private javax.swing.JPopupMenu cellPopup;
    private javax.swing.JMenuItem confDataDisplayedItem;
    private javax.swing.JTable dataTable;
    private javax.swing.JMenuItem displayOnChart;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu tablePopup;
    private javax.swing.JLabel totalJLabel;
    private javax.swing.JLabel totalValue;
    // End of variables declaration//GEN-END:variables

    public void mqUIEventDispatched(MQUIEvent e) {
        if (e.getType() == MQUIEvent.UPDATE_DATA_LIST)  {
            String newAttrList[] = e.getAttrList();
            tableModel.setAttributes(newAttrList);
            tableModel.fireTableStructureChanged();
            initColumnSizes(dataTable);
        } else if (e.getType() ==MQUIEvent.ITEM_SELECTED) {
            Object obj = e.getSelectedObject();
            handleItemQuery(obj);
        }
    }
    
    // we only adjust the column sizes after the table is first populated
    // (and after mqUIEventDispatched() is called, see above) to avoid annoying the user
    boolean doneOnce = false;
    
    public void tableChanged(TableModelEvent e) {
        int type = e.getType();
        if ((type == TableModelEvent.DELETE) || 
                (type == TableModelEvent.INSERT) || 
                (type == TableModelEvent.UPDATE))  {
            setTotalValue(Integer.toString(tableModel.getRowCount()));
        }

        if (!doneOnce){
        	initColumnSizes(dataTable);
        	doneOnce=true;
        }
        
    }
    
    abstract String getTooltipForColumn(int columnIndex);
    
    protected ChartUpdater createChartUpdater(ChartPanel chartPanel){
    	return new MQResourceListChartUpdater(getTableModel(), chartPanel, chartPanel.getChartedAttribute(), chartPanel.getRowIndentifierAttribute(),
			chartPanel.getRowIdentifierValues());
    }
    
	/**
	 * Each instance of this class contains the information needed to update a
	 * particular ChartView
	 * 
	 */
	private class MQResourceListChartUpdater implements ChartUpdater {
		MQResourceListTableModel resourceListTableModel;
		ChartPanel chartPanel;
		String chartedAttribute;
		String rowIndentifierAttribute;
		String[] rowIdentifierValues;

		/**
		 * Create a ChartUpdater which will handle updates to the chart
		 * chartPanel
		 * 
		 * The specified chart contains multiple lines. Each line corresponds to
		 * a row in this table, and shows the value of the attribute
		 * chartedAttribute in that row changes with time.
		 * 
		 * The rows for which data will be displayed are defined by the array
		 * rowIdentifierValues, which contains values of the attribute
		 * rowIndentifierAttribute.
		 * 
		 * For example, if the rows in table represent destinations, and we want
		 * to display the values of some attribute for destinations foo and bar,
		 * then rowIndentifierAttribute will be DestinationAttributes.NAME and
		 * rowIdentifierValues will be a new String[] { "foo", "bar"}
		 * 
		 * @param resourceListTableModel
		 * 
		 * @param chartPanel
		 * @param chartedAttribute
		 * @param rowIndentifierAttribute
		 * @param rowIdentifierValues
		 */
		MQResourceListChartUpdater(MQResourceListTableModel resourceListTableModel, ChartPanel chartPanel, String chartedAttribute,
				String rowIndentifierAttribute, String[] rowIdentifierValues) {
			this.resourceListTableModel = resourceListTableModel;
			this.chartPanel = chartPanel;
			this.chartedAttribute = chartedAttribute;
			this.rowIndentifierAttribute = rowIndentifierAttribute;
			this.rowIdentifierValues = rowIdentifierValues;
		}

		public void updateCharts() {

			// find out which column in the table contains the attribute we are
			// displaying
			int chartedAttributeIndex = -1;
			for (int i = 0; i < resourceListTableModel.getAttributes().length; i++) {
				String attributeName = resourceListTableModel.getAttributes()[i];
				if (attributeName.equalsIgnoreCase(chartedAttribute)) {
					chartedAttributeIndex = i;
					break;
				}
			}

			// find out which column in the table contains the
			// rowIndentifierAttribute
			// used to define which rows to use
			int rowIndentifierAttributeIndex = -1;
			for (int i = 0; i < resourceListTableModel.getAttributes().length; i++) {
				String attributeName = resourceListTableModel.getAttributes()[i];
				if (attributeName.equalsIgnoreCase(rowIndentifierAttribute)) {
					rowIndentifierAttributeIndex = i;
					break;
				}
			}
			
			// in what follows, if we can't find a row or value use 0 instead
			// since we can't allow the number of values to change dynamically

			long[] values = new long[rowIdentifierValues.length];

			// now obtain the required attribute value for each of the required rows
			for (int i = 0; i < rowIdentifierValues.length; i++) {

				String rowIdentifierValue = rowIdentifierValues[i];
				// which row in the resource list is this?
				int row = -1;
				if (rowIndentifierAttributeIndex != -1) {
					for (int rowIndex = 0; rowIndex < resourceListTableModel.getRowCount(); rowIndex++) {
						String identifierValue = (String) resourceListTableModel.getValueAt(rowIndex,
								rowIndentifierAttributeIndex);
						if (identifierValue != null && identifierValue.equals(rowIdentifierValue)) {
							row = rowIndex;
							break;
						}
					}
				}
				// now fetch the value for this row
				Object chartedAttributeValue = null;
				if (row != -1 && chartedAttributeIndex !=-1) {
					chartedAttributeValue = resourceListTableModel.getValueAt(row, chartedAttributeIndex);
				}

				// convert to a long
				long longValue;
				if (chartedAttributeValue == null) {
					longValue = 0L;
				} else if (chartedAttributeValue instanceof Long) {
					longValue = ((Long) chartedAttributeValue).longValue();
				} else if (chartedAttributeValue instanceof Integer) {
					longValue = ((Integer) chartedAttributeValue).longValue();
				} else {
					// TODO What if the attribute is not a long?
					throw new RuntimeException("Need to finish this but to support values of " + chartedAttribute + " of type "	+ chartedAttributeValue.getClass());
				}

				// save the value
				values[i] = longValue;

			}

			// finally update the chart
			long timestamp = System.currentTimeMillis();
			chartPanel.addValues(timestamp, values);
			chartPanel.updateDetails(values);
		}
	}
	

	

}
