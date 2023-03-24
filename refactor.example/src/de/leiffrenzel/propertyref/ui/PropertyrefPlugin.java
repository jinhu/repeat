// Copyright (c) 2005 by Leif Frenzel. All rights reserved.
// See http://leiffrenzel.de
package de.leiffrenzel.propertyref.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;

/** <p>The main plugin class for the property refactoring plugin.</p>
  * 
  * @author The mighty PDE wizard 
  */
public class PropertyrefPlugin extends AbstractUIPlugin {
  
	//The shared instance.
	private static PropertyrefPlugin plugin;
	
  
	public PropertyrefPlugin() {
		super();
		plugin = this;
	}

	public static PropertyrefPlugin getDefault() {
		return plugin;
	}
  
  public static String getPluginId() {
    return getDefault().getBundle().getSymbolicName();
  }
}
