// Copyright (c) 2005 by Leif Frenzel. All rights reserved.
// See http://leiffrenzel.de
package de.leiffrenzel.propertyref.core;

import org.eclipse.osgi.util.NLS;

/** <p>provides internationalized messages Strings from the coretexts 
  * resource bundle.</p>
  *
  * @author Leif Frenzel
  */
public class CoreTexts extends NLS {
  
  private static final String BUNDLE_NAME 
    = "de.leiffrenzel.propertyref.core.coretexts"; //$NON-NLS-1$
  
  static {
    NLS.initializeMessages( BUNDLE_NAME, CoreTexts.class );
  }

  // message fields
  public static String renamePropertyProcessor_name;
  public static String renamePropertyDelegate_noSourceFile;
  public static String renamePropertyDelegate_roFile;
  public static String renamePropertyDelegate_noPropertyKey;
  public static String renamePropertyDelegate_collectingChanges;
  public static String renamePropertyDelegate_checking;
  public static String renamePropertyDelegate_propNotFound;
}