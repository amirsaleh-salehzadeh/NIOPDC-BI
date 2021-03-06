
package rex;

import javax.swing.*;
import rex.graphics.datasourcetree.elements.DataSourceTreeElement;
import rex.graphics.datasourcetree.elements.CatalogElement;
import rex.metadata.ServerMetadata;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;
import rex.xmla.XMLAObjectsFactory;
import rex.graphics.mdxeditor.RexWizard;
import rex.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */

/**
 * 
 * @author Prakash
 *
 */

public class TestRexWizard extends JFrame implements ActionListener,RexWizardListener{

	
	JButton bDS=new JButton("Get DataSources");
	JButton bCat=new JButton("Get Catalogs");
	JButton bCube=new JButton("Get Cube");
	JButton bRexWiz1=new JButton("Show Rex Wizard Dialog with information provided");
	JButton bRexWiz2=new JButton("Show Rex Wizard Dialog with out info");
	JTextField tf=new JTextField(20);
	JTextArea taProp=new JTextArea(20,20);
	JScrollPane sp=new JScrollPane(taProp);
	String x[]={"",""};
	JComboBox cmbDS=new JComboBox(x);
	JComboBox cmbCat=new JComboBox(x);
	JComboBox cmbCube=new JComboBox(x);
	RexWizard rexWizard;
	
	public TestRexWizard() {
		super("Mondrian Explorer");
		/**	
		 * Commented to avoid unused local variables.
		 * by Prakash. 08-05-2007.
		 */
			/**
			 * 	GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			 * 	Rectangle bounds = env.getMaximumWindowBounds();
			 */
		/*
		 * End of the modification.
		 */
		Container con=getContentPane();
		con.setLayout(new FlowLayout());		
		
		tf.setText("http://localhost:8080/mondrian/xmla");
		con.add(new JLabel("Add XMLA URL"));
		con.add(tf);		
		con.add(bDS);
		con.add(cmbDS);
		con.add(bCat);
		con.add(cmbCat);
		con.add(bCube);
		con.add(cmbCube);
		con.add(bRexWiz1);
		con.add(bRexWiz2);
				
		con.add(sp);
				
		bDS.addActionListener(this);
		bCat.addActionListener(this);
		bCube.addActionListener(this);
		bRexWiz1.addActionListener(this);
		bRexWiz2.addActionListener(this);
		
		setSize(600,600);
		setVisible(true);
	
	}
	public void getMdx(RexWizardEvent evt)
	{
		taProp.setText(evt.getQuery());
	}
	public void actionPerformed(ActionEvent evt)
	{
		String str[];
		String ur=((tf.getText()).trim());
		Object obj=evt.getSource();
		if(ur.equalsIgnoreCase("http://localhost:8080/mondrian/xmla"))
		{
			if(obj==bDS)
			{
			       ServerMetadata smd = new ServerMetadata(ur);
			       DataSourceTreeElement ds[] = smd.discoverDataSources();
			       cmbDS.removeAllItems();
			       for (int i = 0; ds != null && i < ds.length; i++) 
			       {
			       		cmbDS.addItem((ds[i].toString()).trim());
			       }
			       cmbDS.repaint();
			}
			if(obj==bCat)
			{
				ServerMetadata smd = new ServerMetadata(ur);
				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();
			    properties.setDataSourceInfo((String)cmbDS.getSelectedItem());
				CatalogElement ds[]=(CatalogElement[])smd.getCatalogList(restrictions,properties);
				cmbCat.removeAllItems();
				for (int i = 0; i < ds.length; i++) 
			    {					
			    	cmbCat.addItem((ds[i].toString()).trim());
			    }
				cmbCat.repaint();
			}
			if(obj==bCube)
			{
				ServerMetadata smd = new ServerMetadata(ur);
				XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
				XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();
			    properties.setDataSourceInfo((String)cmbDS.getSelectedItem());
			    properties.setCatalog((String)cmbCat.getSelectedItem());
				DataSourceTreeElement ds[]=smd.getCubeList(restrictions,properties);
				cmbCube.removeAllItems();
				for (int i = 0; i < ds.length; i++) 
			    {	System.out.println(ds[i].toString());				
			    	cmbCube.addItem((ds[i].toString()));
			    }				
			}
			if(obj==bRexWiz1)
			{
				if(tf.getText().trim().length()>0 && cmbDS.getItemCount()>0 && cmbCat.getItemCount()>0 && cmbCube.getItemCount()>0)
				{
					rexWizard=new RexWizard(tf.getText().trim(),
                                                (String)cmbDS.getSelectedItem(),
                                                (String)cmbCat.getSelectedItem(),
                                                (String)cmbCube.getSelectedItem(),
                                                "he he",
                                                Locale.getDefault());
					rexWizard.addRexWizardListener(this);
					rexWizard.showDialog();
				}
			}
			if(obj==bRexWiz2)
			{
				rexWizard=new RexWizard();
				rexWizard.addRexWizardListener(this);
				rexWizard.showDialog();
			}
		}	
		else
		{
			JOptionPane.showMessageDialog(null
                    , "Sorry, nothing there!\n" + ur
                    , "Add data source"
                    , JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void main(String[] args) {
		new TestRexWizard();
	}
}
