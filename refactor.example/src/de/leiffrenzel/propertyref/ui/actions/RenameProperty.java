// Copyright (c) 2005 by Leif Frenzel. All rights reserved.
// See http://leiffrenzel.de
package de.leiffrenzel.propertyref.ui.actions;

import org.eclipse.core.resources.*;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import de.leiffrenzel.propertyref.core.*;
import de.leiffrenzel.propertyref.ui.UITexts;
import de.leiffrenzel.propertyref.ui.wizards.RenamePropertyWizard;

/** <p>action that is triggered from the editor context menu.</p>
  * 
  * <p>This action is declared in the <code>plugin.xml</code>.</p>
  * 
  * @author Leif Frenzel
  */
public class RenameProperty implements IEditorActionDelegate {

  private static final String EXT_PROPERTIES = "properties"; //$NON-NLS-1$
  
  private ISelection selection;
  private IEditorPart targetEditor;
  private boolean onPropertiesFile;

  private RenamePropertyInfo info = new RenamePropertyInfo();
  
  
  // interface methods of IEditorActionDelegate
  /////////////////////////////////////////////

  public void setActiveEditor( final IAction action, 
                               final IEditorPart targetEditor ) {
    this.targetEditor = targetEditor;
    onPropertiesFile = false;
    IFile file = getFile();
    if(    file != null 
        && file.getFileExtension().equals( EXT_PROPERTIES ) ) {
      onPropertiesFile = true;
    }                           
  }

  public void run( final IAction action ) {
    if( !onPropertiesFile ) {
      refuse();
    } else {
      if( selection != null && selection instanceof ITextSelection ) {
        applySelection( ( ITextSelection )selection );
        if( saveAll() ) {
          openWizard();
        }
      }
    }
  }

  public void selectionChanged( final IAction action, 
                                final ISelection selection ) {
    this.selection = selection;
  }
  
  
  // helping methods
  //////////////////

  private void applySelection( final ITextSelection textSelection ) {
    info.setOldName( textSelection.getText() );
    info.setNewName( textSelection.getText() );
    info.setOffset( textSelection.getOffset() );
    info.setSourceFile( getFile() );
  }

  private void refuse() {
    String title = UITexts.renameProperty_refuseDlg_title;
    String message = UITexts.renameProperty_refuseDlg_message;
    MessageDialog.openInformation( getShell(), title, message );
  }

  private static boolean saveAll() {
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    return IDE.saveAllEditors( new IResource[] { workspaceRoot }, false );
  }
  
  private void openWizard() {
    RefactoringProcessor processor = new RenamePropertyProcessor( info );
    RenamePropertyRefactoring ref = new RenamePropertyRefactoring( processor );
    RenamePropertyWizard wizard = new RenamePropertyWizard( ref, info );
    RefactoringWizardOpenOperation op 
      = new RefactoringWizardOpenOperation( wizard );
    try {
      String titleForFailedChecks = ""; //$NON-NLS-1$
      op.run( getShell(), titleForFailedChecks );
    } catch( final InterruptedException irex ) {
      // operation was cancelled
    }
  }

  private Shell getShell() {
    Shell result = null;
    if( targetEditor != null ) {
      result = targetEditor.getSite().getShell();
    } else {
      result = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    }
    return result;
  }
  
  private final IFile getFile() {
    IFile result = null;
    if( targetEditor instanceof ITextEditor )  {
      ITextEditor editor = ( ITextEditor )targetEditor;
      IEditorInput input = editor.getEditorInput();
      if( input instanceof IFileEditorInput ) {
        result = ( ( IFileEditorInput )input ).getFile();
      }
    }
    return result;
  }
}
