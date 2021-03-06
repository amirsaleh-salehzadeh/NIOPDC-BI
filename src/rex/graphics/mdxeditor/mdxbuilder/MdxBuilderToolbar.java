package rex.graphics.mdxeditor.mdxbuilder;


import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import rex.graphics.mdxeditor.MdxEditor;
import rex.graphics.mdxeditor.jsp.ReadEnv;
import rex.utils.*;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;


/**
 * MdxEditor's toolbar component.
 * @author Igor Mekterovic
 * @version 0.3
 */
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */

public class MdxBuilderToolbar extends JPanel implements LanguageChangedListener{
   private CanSaveAndResumeState parent;
   private HandlesMdxEditorSettings settingsHandler;
   private JLabel   saveQuery
          , loadQuery
          , undoAction
          , redoAction
          , newQuery;
   private static int MAX_UNDO_OPERATIONS = 20;
   private Stack undoStack
               , redoStack;

//   private UndoableRedoable parent;


   private static ImageIcon undoEnabledIcon;
   private static ImageIcon undoDisabledIcon;
   private static ImageIcon redoEnabledIcon;
   private static ImageIcon redoDisabledIcon;
   static { 
      undoEnabledIcon = S.getAppIcon("undo.gif");
      undoDisabledIcon = S.getAppIcon("undo_disabled.gif");
      redoEnabledIcon = S.getAppIcon("redo.gif");
      redoDisabledIcon = S.getAppIcon("redo_disabled.gif"); 
   }

   JFileChooser chooser;
   /**
    * Creates all the buttons
    * @param _parent MdxEditor
    */
   public MdxBuilderToolbar(CanSaveAndResumeState _parent, HandlesMdxEditorSettings _settingsHandler) {

        
      parent = _parent;
      settingsHandler = _settingsHandler;

	  newQuery = new JLabel(S.getAppIcon("new_query.gif"));
      newQuery.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            MdxBuilderToolbar.this.newQuery();
         }
      });
       /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */
      /* implementing localization */
      newQuery.setToolTipText(I18n.getString("toolTip.newQuery"));
      /* end of modification for I18n */

      saveQuery = new JLabel(S.getAppIcon("save_query.gif"));
      saveQuery.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            MdxBuilderToolbar.this.saveQuery();
         }
      });
             /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */
      /* implementing localization */
      saveQuery.setToolTipText(I18n.getString("toolTip.saveQuery"));
     /* end of modification for I18n */

      loadQuery = new JLabel(S.getAppIcon("open_query.gif"));
      loadQuery.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            MdxBuilderToolbar.this.loadQuery();
         }
      });
             /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */
      /* implementing localization */
      loadQuery.setToolTipText(I18n.getString("toolTip.loadQuery"));
       /* end of modification for I18n */

      loadQuery.setOpaque(false);


      undoAction = new JLabel(undoDisabledIcon);
      undoAction.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            MdxBuilderToolbar.this.undo();
         }
      });
             /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */
      /* implementing localization */
        undoAction.setToolTipText(I18n.getString("toolTip.undoAction"));
      /* end of modification for I18n */

      undoAction.setOpaque(false);
//      undoAction.setEnabled(false);

      redoAction = new JLabel(redoDisabledIcon);
      redoAction.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            MdxBuilderToolbar.this.redo();
         }
      });
             /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */
      /* implementing localization */
        redoAction.setToolTipText(I18n.getString("toolTip.redoAction"));
      /* end of modification for I18n */

      redoAction.setOpaque(false);
//      redoAction.setEnabled(false);


//      Line 'em up boys...
      this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      this.add(Box.createRigidArea(new Dimension(4, 0)));
      this.add(newQuery);
      this.add(Box.createRigidArea(new Dimension(4, 0)));
      this.add(saveQuery);
      this.add(Box.createRigidArea(new Dimension(4, 0)));
      this.add(loadQuery);
      this.add(Box.createRigidArea(new Dimension(4, 0)));
      this.add(undoAction);
      this.add(Box.createRigidArea(new Dimension(4, 0)));
      this.add(redoAction);
      this.setBorder(AppColors.TOOLBAR_BORDER);
      this.setOpaque(false);


      undoStack = new Stack();
      redoStack = new Stack();
         /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */
      
      chooser = new JFileChooser();
      try {
          chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
          //jfc.setFileFilter(new javax.swing.filechooser.FileFilter() {
          chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
              public boolean accept(File pathname) {
                  return pathname.getName().toLowerCase().endsWith(".mdx")
                  || pathname.isDirectory();
              }
              public String getDescription() {
                  return "MDX (*.mdx)";
              }
              
          });
          chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
              public boolean accept(File pathname) {
                  return pathname.getName().toLowerCase().endsWith(".mbt")
                  || pathname.isDirectory();
              }
              public String getDescription() {
                  return "MBT files (*.mbt)";
              }

          });
          chooser.setCurrentDirectory(new File((ReadEnv.getEnvVars()).getProperty("USERPROFILE")));
          //jfc.setSelectedFile(new File(getSaveName()));
          String fileName=((MdxBuilderTree)parent).getFileName();
          if(fileName != null & fileName.trim().length()>0)
          {
              chooser.setSelectedFile(new File(fileName));
          }
          //chooser.setSelectedFile(new File(((MdxBuilderTree)parent).getFileName()));          
      } catch (Exception ex) {
          //ex.printStackTrace();//Commented by Prakash
      	JOptionPane.showMessageDialog(null,ex.getMessage());
      }
      
      /* implementing localization */
      I18n.addOnLanguageChangedListener(this);
      applyI18n();
      /* end of modification for I18n */

   }
   /**
    * Sets the undo button to specified state
    * @param enabled boolean
    */
   public void setUndoEnabled(boolean enabled){
      undoAction.setEnabled(enabled);
//      S.out("setting undo = " + enabled);
      if (enabled){
         undoAction.setIcon(undoEnabledIcon);
      }else{
         undoAction.setIcon(undoDisabledIcon);
      }
   }

   /**
    * Sets the redo button to specified state
    * @param enabled boolean
    */
   public void setRedoEnabled(boolean enabled){
      redoAction.setEnabled(enabled);
      if (enabled){
         redoAction.setIcon(redoEnabledIcon);
      }else{
         redoAction.setIcon(redoDisabledIcon);
      }
   }

   /**
    * Refreshes the display(state) of undo and redo buttons.
    */
   public void refreshUndoRedoState(){
      setUndoEnabled(canUndo());
      setRedoEnabled(canRedo());
   }

   /**
    * This method is called by the object that uses this toolbar before it performs any operation.
    * <br>This method then saves the state of that object and pushes it onto the stack.
    */
   public void actionToBePerformed(){
      if (undoStack.size() == MAX_UNDO_OPERATIONS)
         undoStack.removeElementAt(0);
      ByteArrayOutputStream memStream = new ByteArrayOutputStream();
      redoStack.removeAllElements();
      parent.saveState(memStream);
      undoStack.push(memStream);
      refreshUndoRedoState();
   }

   /**
    * Performs undo operation.
    */
   public void undo(){
      if (canUndo()){
//       First, save current state (in case of redo) and push it on the redo stack
         ByteArrayOutputStream currMemStream = new ByteArrayOutputStream();
         parent.saveState(currMemStream);
         redoStack.push(currMemStream);
//       Then, retrieve previous state from the undo stack and resume it
         ByteArrayOutputStream memStream = (ByteArrayOutputStream)undoStack.pop();
         parent.resumeState(new ByteArrayInputStream(memStream.toByteArray()));
         refreshUndoRedoState();
      }else{
//         S.out("alert: undo called when there is nothing to undo!");
      }
   }

   /**
    * Performs redo operation.
    */
   public void redo(){
      if (canRedo()){
//       First, save current state (in case of undo) and push it on the UNDO stack
         ByteArrayOutputStream currMemStream = new ByteArrayOutputStream();
         parent.saveState(currMemStream);
         undoStack.push(currMemStream);
//       Then, retrieve previous state from the REDO stack and resume it
         ByteArrayOutputStream memStream = (ByteArrayOutputStream)redoStack.pop();
//         undoStack.push(memStream);
         parent.resumeState(new ByteArrayInputStream(memStream.toByteArray()));
         refreshUndoRedoState();
      }else{
//         S.out("alert: redo called when there is nothing to redo!");
      }
   }

   /**
    * Returns true if undo operation can be performed, false otherwise.
    * @return boolean
    */
   public boolean canUndo(){
      return !undoStack.empty();
   }

   /**
    * Returns true if redo operation can be performed, false otherwise.
    * @return boolean
    */
   public boolean canRedo(){
      return !redoStack.empty();
   }

   /**
    * Clears a state of the parent object - for a new query to be created.
    */
   protected void newQuery(){
       ((MdxBuilderTree)parent).newQuery();  
   }

   /**
    * Loads a query from the file. (dialog box for choosing a file is presented to user)
    */
   protected void loadQuery(){
       ((MdxBuilderTree)parent).openQuery();
   }

   /**
    * Saves a query to the file.  (dialog box for choosing a file is presented to user)
    */
   protected void saveQuery(){
       ((MdxBuilderTree)parent).saveQuery();
   }

   /**
    * Overrides the <code>paintComponent(Graphics g)<code> to paint gradient blue background.
    * @param g Graphics
    */
   public void paintComponent(Graphics g) {
      S.paintBackground(g, this);
      super.paintComponent(g);
   }
   
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */
 /**
  * Helper method that is executed when the language is changed
  */
    public void languageChanged(LanguageChangedEvent evt) {
        this.applyI18n();
    }
     
 /**
  *  Helper method to implement locatization when language is changed
  */  
    
    public void applyI18n(){
   newQuery.setToolTipText(I18n.getString("toolTip.newQuery"));
   saveQuery.setToolTipText(I18n.getString("toolTip.saveQuery"));
   loadQuery.setToolTipText(I18n.getString("toolTip.loadQuery"));
   undoAction.setToolTipText(I18n.getString("toolTip.undoAction"));
   redoAction.setToolTipText(I18n.getString("toolTip.redoAction"));
    }
   
    /* end of modification for I18n */

   

}
