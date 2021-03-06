package rex.utils;

import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class UIPropertyManager {
   public static final String METAL_LF    = "javax.swing.plaf.metal.MetalLookAndFeel";
   public static final String MOTIF_LF    = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
   public static final String WINDOWS_LF  = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

   private static String currentUI;

   private static Border lineBasic;
   private static Border lineFull;
   private static Border loweredBasic;
   private static Border raisedBasic;
   private static Border raisedFull;
   private static Border etchedBasic;
   private static Border etchedFull;
   private static Border emptyWrapperWin;
   private static Border emptyWrapperMetal;
   private static Border emptyBasic;
   private static Border emptyWin;
   private static Border emptyMetal;
   private static Border formBorder;
   private static Border formActiveBorder;

   private static Insets menuInsets;

   private static Color backgroundColor;
   private static Color inactiveColor;
   private static Color activeNotnullColor;
   private static Color activeColor;
   private static Color activeBorderColor;
   private static Color labelColor;

   static {
      currentUI = "";

      lineBasic    = BorderFactory.createLineBorder(Color.black);
      loweredBasic = new SoftBevelBorder(BevelBorder.LOWERED);
      raisedBasic  = new SoftBevelBorder(BevelBorder.RAISED);
      etchedBasic  = BorderFactory.createEtchedBorder();
      emptyBasic   = BorderFactory.createEmptyBorder(1, 6, 1, 6);

      emptyWrapperWin   = BorderFactory.createEmptyBorder(3 //insets.top
                                                        , 3 //insets.left
                                                        , 3 //insets.bottom
                                                        , 3); //insets.right);
      emptyWrapperMetal = BorderFactory.createEmptyBorder(2 //insets.top
                                                        , 2 //insets.left
                                                        , 2 //insets.bottom
                                                        , 2); //insets.right);

      lineFull   = BorderFactory.createCompoundBorder(lineBasic, emptyBasic);
      raisedFull = BorderFactory.createCompoundBorder(raisedBasic, emptyBasic);
      etchedFull = BorderFactory.createCompoundBorder(etchedBasic, emptyBasic);
      emptyWin   = BorderFactory.createCompoundBorder(emptyWrapperWin, emptyBasic);
      emptyMetal = BorderFactory.createCompoundBorder(emptyWrapperMetal, emptyBasic);

//      formBorder = BorderFactory.createCompoundBorder(new MatteBorder(3, 3, 3, 3, Color.gray)
//                                                    , raisedBasic);
//      formActiveBorder = BorderFactory.createCompoundBorder(new MatteBorder(3, 3, 3, 3, Color.yellow)
//                                                    , raisedBasic);

      backgroundColor = Color.lightGray;
      inactiveColor = new Color(225, 225, 225);
      activeNotnullColor = new Color(255, 255, 232);
      activeColor = Color.white;
      activeBorderColor = new Color(252, 237, 66);
      labelColor = Color.blue.darker().darker();

//      backgroundColor = new Color(215, 215, 215);
//      inactiveColor = new Color(235, 235, 235);
//      activeColor = Color.white;
//      labelColor = Color.blue.darker().darker();
   }

   public static boolean isCurrentUI(String ui) {
      return currentUI.equals(ui);
   }

   public static void setCurrentUI(String ui) {
      currentUI = ui;
      if (ui.equals(METAL_LF)) {
         setMetalProperties();
      }
      else if (ui.equals(MOTIF_LF)) {
         // ovih nema zasad
         //setMotifProperties();
         setMetalProperties();
      }
      else if (ui.equals(WINDOWS_LF)) {
         setWindowsProperties();
      }
   }
   public static void setSystemUI() {
      String ui = UIManager.getSystemLookAndFeelClassName();
      if (ui.equals(METAL_LF)) {
         setMetalProperties();
      }
      else if (ui.equals(MOTIF_LF)) {
         // ovih nema zasad
         //setMotifProperties();
         setMetalProperties();
      }
      else if (ui.equals(WINDOWS_LF)) {
         setWindowsProperties();
      }
   }

   public static void setMetalProperties() {
      S.out("Setting metal UI properties...");
      // ovo redefinira gumbe na svim JOptionPane derivacijama
//      UIManager.put("OptionPane.yesButtonText", "Da");
//      UIManager.put("OptionPane.noButtonText", "Ne");
//      UIManager.put("OptionPane.okButtonText", "Prihvati");
//      UIManager.put("OptionPane.cancelButtonText", "Odbaci");
//
//      // privremeno dok ne proradi toolbar
//      UIManager.put("Button.border", etchedFull);
//
//      Font bold = UIManager.getFont("Label.font");
//      Border formBorder = BorderFactory.createCompoundBorder(
//                            new MatteBorder(3, 3, 3, 3
//                              , UIManager.getColor("TextField.selectionBackground"))
//                          , etchedBasic);
//      Border formBorder = new MatteBorder(3, 3, 3, 3
//                            , UIManager.getColor("TextField.selectionBackground"));
//
//
//      Color c = UIManager.getColor("Form.activeBorderColor.predefined");
//      if (c == null) {
//         //c = UIManager.getColor("Panel.foreground");
//         c = activeBorderColor;
//      }
//      Border formActiveBorder = BorderFactory.createCompoundBorder(
//                            new MatteBorder(3, 3, 3, 3
//                              , c)
//                          , etchedBasic);
//
//      Border focusCellBorder = BorderFactory.createCompoundBorder(
//                           new LineBorder(c)
//                         , BorderFactory.createEmptyBorder(0, 6, 0, 6));
//      Border nonfocusCellBorder = BorderFactory.createEmptyBorder(1, 7, 1, 7);
//      UIManager.put("Table.focusCellHighlightBorder", focusCellBorder);
//      UIManager.put("Table.nonfocusCellBorder", nonfocusCellBorder);
//
//
//      Border formActiveBorder = new MatteBorder(3, 3, 3, 3
//                              , UIManager.getColor("Panel.foreground"));
//
//      // Form
//      UIManager.put("Form.border", formBorder);
//      UIManager.put("Form.activeBorder", formActiveBorder);
//
//      // FormField
//      UIManager.put("FormField.activeBackground", activeColor);
//      c = UIManager.getColor("FormField.activeNotnullBackground.predefined");
//      if (c != null) {
//         UIManager.put("FormField.activeNotnullBackground", c);
//      }
//      else {
//         UIManager.put("FormField.activeNotnullBackground", activeNotnullColor);
//      }
//      c = UIManager.getColor("FormField.inactiveBackground.predefined");
//      if (c != null) {
//         UIManager.put("FormField.inactiveBackground", c);
//      }
//      else {
//         UIManager.put("FormField.inactiveBackground", inactiveColor);
//      }
//
//      Font plain = UIManager.getFont("TextField.font");
//      UIManager.put("FormField.font", plain);
//      font = font.deriveFont(Font.BOLD);  // neka postane BOLD
//      UIManager.put("FormField.labelFont", bold);
//      UIManager.put("FormField.border", UIManager.getBorder("TextField.border"));
//
//      c = UIManager.getColor("FormField.labelForeground.predefined");
//      if (c != null) {
//         UIManager.put("FormField.labelForeground", c);
//         // ovo postavlja boju naziva kolona u gridu na boju labela na free formi
//         // prvo nek se svi sloze
//         //UIManager.put("TableHeader.foreground", c);
//      }
//      else {
//         UIManager.put("FormField.labelForeground", Color.black);
//      }
//
//      UIManager.put("FormField.labelForeground", UIManager.getColor("Label.foreground"));
//      UIManager.put("FormField.labelForeground", Color.black);
//
//
//      UIManager.put("FormField.blinkBackground", UIManager.getColor("TextField.selectionBackground"));
//      UIManager.put("FormField.blinkBackground", Color.red);
//
//      // ImageFormField
//      UIManager.put("ImageFormField.selectionBackground", UIManager.get("TextField.selectionBackground"));
//
//      // StatusLabel
//      UIManager.put("StatusLabel.foreground", UIManager.get("Label.foreground"));
//      UIManager.put("StatusLabel.background", UIManager.get("Panel.background"));
//      UIManager.put("StatusLabel.border", UIManager.get("TextField.border"));
//
//      UIManager.put("StatusLabel.foreground", UIManager.get("Label.foreground"));
//      UIManager.put("StatusLabel.background", UIManager.get("Panel.background"));
//      UIManager.put("StatusLabel.border", loweredBasic);
//      UIManager.put("State.inactiveBackground", UIManager.get("Panel.background"));
//      c = UIManager.getColor("Form.activeBorderColor.predefined");
//      if (c == null) {
//         c =  activeBorderColor;
//      }
//      UIManager.put("State.activeBackground", c);
//
//
//      // MessageField
//      UIManager.put("MessageField.normalForeground", UIManager.get("Panel.foreground"));
//      UIManager.put("MessageField.normalBackground", UIManager.get("Panel.background"));
//      UIManager.put("MessageField.alertForeground", Color.yellow);
//      UIManager.put("MessageField.alertBackground", Color.red.darker());
//      UIManager.put("MessageField.warningForeground", Color.blue);
//      UIManager.put("MessageField.warningBackground", Color.orange);
//      UIManager.put("MessageField.approvalForeground", Color.white);
//      UIManager.put("MessageField.approvalBackground", Color.green.darker());
//
//      // TetxField i PasswordField na LoginDialogu
//      Font fo1 = ((Font) UIManager.get("Label.font")).deriveFont((float) 16);
//      Font fo2 = ((Font) UIManager.get("Label.font")).deriveFont((float) 16);
//      UIManager.put("LoginLabel.font", bold);
//      UIManager.put("LoginTextField.font", UIManager.getFont("TextField.font"));
//      UIManager.put("LoginPasswordField.font", UIManager.getFont("TextField.font"));
//      UIManager.put("LoginTextField.background", UIManager.getColor("TextField.background"));
//      UIManager.put("LoginTextField.foreground", UIManager.getColor("TextField.foreground"));
//      UIManager.put("LoginPasswordField.background", UIManager.getColor("TextField.background"));
//      UIManager.put("LoginPasswordField.foreground", UIManager.getColor("TextField.foreground"));
//
//      UIManager.put("FormField.border", lineBasic);

   }

//------------------------------------------------------------
  // ovo ne bi trebalo koristiti
   public static void setWindowsProperties() {
      S.out("Setting windows UI properties...");
      UIManager.put("Label.font", (new java.awt.Font("Dialog", 0, 12))); // this works
      UIManager.put("Label.border", BorderFactory.createLineBorder(Color.BLACK)); // this doesn't ?


      UIManager.put("Table.font", (new java.awt.Font("Dialog", 0, 10))); // this works
//      UIManager.put("Panel.background", backgroundColor);
//      UIManager.put("Button.background", backgroundColor);
//      UIManager.put("CheckBox.background", backgroundColor);
//      UIManager.put("Desktop.background", activeColor);
//      UIManager.put("Desktop.background", Color.gray);
//
//      // TextField uvijek ima pozadinu kao i panel na kojem se nalazi
//      UIManager.put("TextField.background", UIManager.get("Panel.background"));
//
//      // TextArea uvijek ima pozadinu kao i panel na kojem se nalazi
//      UIManager.put("TextArea.background", UIManager.get("Panel.background"));
//
//      // JOptionPane uvijek ima pozadinu kao i panel
//      UIManager.put("OptionPane.background", UIManager.get("Panel.background"));
//      UIManager.put("DialogPane.background", UIManager.get("Panel.background"));
//      UIManager.put("TabbedPane.background", UIManager.get("Panel.background"));
//      UIManager.put("RadioButton.background", UIManager.get("Panel.background"));
//
//      // Table uvijek ima pozadinu kao i panel na kojem se nalazi
//      //UIManager.put("Table.background", UIManager.get("Panel.background"));
//      UIManager.put("Table.background", inactiveColor);
//      UIManager.put("Table.activeBackground", activeColor);
//
//      UIManager.put("TableHeader.background", backgroundColor);
//      UIManager.put("TableHeader.foreground", labelColor);
//
//      // Forem
//      UIManager.put("Form.border", formBorder);
//      UIManager.put("Form.activeBorder", formActiveBorder);
//
//      // FormField
//      UIManager.put("FormField.activeBackground", activeColor);
//      UIManager.put("FormField.inactiveBackground", inactiveColor);
//
//      Font font = UIManager.getFont("TextField.font");
//      UIManager.put("FormField.font", font);
//      font = font.deriveFont(Font.BOLD);  // neka postane BOLD
//      UIManager.put("FormField.labelFont", font);
//
//      // ImageFormField
//      UIManager.put("ImageFormField.selectionBackground", UIManager.get("TextField.selectionBackground"));
//
//      // StatusLabel
//      UIManager.put("StatusLabel.foreground", UIManager.get("Panel.foreground"));
//      UIManager.put("StatusLabel.background", UIManager.get("Panel.background"));
//      UIManager.put("StatusLabel.border", UIManager.get("TextField.border"));
//
//
//      // MessageField
//      UIManager.put("MessageField.normalForeground", UIManager.get("Panel.foreground"));
//      UIManager.put("MessageField.normalBackground", UIManager.get("Panel.background"));
//      UIManager.put("MessageField.alertForeground", Color.yellow);
//      UIManager.put("MessageField.alertBackground", Color.red.darker());
//      UIManager.put("MessageField.warningForeground", Color.blue);
//      UIManager.put("MessageField.warningBackground", Color.yellow);
//      UIManager.put("MessageField.approvalForeground", Color.white);
//      UIManager.put("MessageField.approvalBackground", Color.green.darker());
//
//      // TetxField i PasswordField na LoginDialogu
//      Font fo1 = ((Font) UIManager.get("Label.font")).deriveFont((float) 16);
//      Font fo2 = ((Font) UIManager.get("Label.font")).deriveFont((float) 16);
//      UIManager.put("LoginLabel.font", fo1);
//      UIManager.put("LoginTextField.font", fo2);
//      UIManager.put("LoginPasswordField.font", fo2);
//      UIManager.put("LoginTextField.background", activeColor);
//      UIManager.put("LoginTextField.foreground", Color.red);
//      UIManager.put("LoginPasswordField.background", activeColor);
//      UIManager.put("LoginPasswordField.foreground", Color.blue);
//
//      UIManager.put("FormField.border", loweredBasic);
//      UIManager.put("FormField.labelForeground", labelColor);
//
//         UIManager.put("Menu.font", font);
//         UIManager.put("MenuItem.font", font);
//         UIManager.put("CheckBoxMenuItem.font", font);
//         UIManager.put("RadioButtonMenuItem.font", ((Font) UIManager.get("Button.font")).deriveFont(Font.BOLD));

//      UIManager.put("ScrollBar.track", inactiveColor);
//         //UIManager.put("ScrollBar.border", Basic);

   }


   public static void setFixedProperties() {

      System.setProperty("StyleReport.locale.properties", "/StyleReportMappings");

      UIManager.put("FileChooser.acceptAllFileFilterText", "Sve datoteke");
      UIManager.put("FileChooser.saveButtonToolTipText", "Spremi odabranu datoteku");
      UIManager.put("FileChooser.openButtonToolTipText", "Otvori odabranu datoteku");
      UIManager.put("FileChooser.cancelButtonToolTipText", "Odustani od rada s izbornikom datoteka");
      UIManager.put("FileChooser.updateButtonToolTipText", "A�uriraj listu kazala");
      UIManager.put("FileChooser.helpButtonToolTipText", "Pomo�");

      UIManager.put("FileChooser.newFolderErrorText", "Pogre�ka pri kreiranju novog kazala");
      // UIManager.put("FileChooser.newFolderErrorSeparator", "");

      UIManager.put("FileChooser.fileDescriptionText", "Generi�ka datoteka");
      UIManager.put("FileChooser.directoryDescriptionText", "Kazalo");

      UIManager.put("FileChooser.saveButtonText", "Spremi");
      UIManager.put("FileChooser.openButtonText", "Otvori");
      UIManager.put("FileChooser.cancelButtonText", "Odustani");
      UIManager.put("FileChooser.updateButtonText", "Promijeni");
      UIManager.put("FileChooser.helpButtonText", "Pomo�");
      //UIManager.put("FileChooser.lookInLabelMnemonic");
      UIManager.put("FileChooser.lookInLabelText", "Pogledaj u:");
      //UIManager.put("FileChooser.fileNameLabelMnemonic");
      UIManager.put("FileChooser.fileNameLabelText", "Ime datoteke:");
      //UIManager.put("FileChooser.filesOfTypeLabelMnemonic");
      UIManager.put("FileChooser.filesOfTypeLabelText", "Datoteke vrste:");
      UIManager.put("FileChooser.upFolderToolTipText", "Nadre�eno kazalo");
      UIManager.put("FileChooser.homeFolderToolTipText", "Osnovno kazalo korisnika");
      UIManager.put("FileChooser.newFolderToolTipText", "Kreiraj novo kazalo");
      UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
      UIManager.put("FileChooser.detailsViewButtonToolTipText", "Lista s detaljima");


      // pohrvacenje tekstova u JColorChooseru
      UIManager.put("ColorChooser.okText", "Prihvati");
      UIManager.put("ColorChooser.cancelText", "Odbaci");
      UIManager.put("ColorChooser.resetText", "Obnovi");

      UIManager.put("ColorChooser.previewText", "Rezultat");

      UIManager.put("ColorChooser.swatchesRecentText", "Do sada");
      UIManager.put("ColorChooser.sampleText", "Uzorak teksta");
      // i ovi se tekstovi mogu postaviti, ali ih ili ne znam prevesti
      // ili ih nema smisla prevoditi??
      // UIManager.put("ColorChooser.swatchesNameText", "");

      // UIManager.put("ColorChooser.rgbNameText", "");
      // UIManager.put("ColorChooser.rgbRedText", "");
      // UIManager.put("ColorChooser.rgbGreenText", "");
      // UIManager.put("ColorChooser.rgbBlueText", "");

      // UIManager.put("ColorChooser.hsbNameText", "");
      // UIManager.put("ColorChooser.hsbRedText", "");
      // UIManager.put("ColorChooser.hsbGreenText", "");
      // UIManager.put("ColorChooser.hsbBlueText", "");
      // UIManager.put("ColorChooser.hsbHueText", "");
      // UIManager.put("ColorChooser.hsbSaturationText", "");
      // UIManager.put("ColorChooser.hsbBrightnessText", "");


   }

}