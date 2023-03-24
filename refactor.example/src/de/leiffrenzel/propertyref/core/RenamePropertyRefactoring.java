// Copyright (c) 2005 by Leif Frenzel. All rights reserved.
// See http://leiffrenzel.de
package de.leiffrenzel.propertyref.core;

import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;

/** <p>Refactoring for renaming properties in Java Properties files.</p>
  *
  * <p>All the actual work is done in the processor, so we just have to 
  * keep a reference to one here.<p>
  *
  * @author Leif Frenzel
  */
public class RenamePropertyRefactoring extends ProcessorBasedRefactoring {

  private final RefactoringProcessor processor;

  public RenamePropertyRefactoring( final RefactoringProcessor processor ) {
    super( processor );
    this.processor = processor;
  }

  
  // interface methods of ProcessorBasedRefactoring
  /////////////////////////////////////////////////
  
  public RefactoringProcessor getProcessor() {
    return processor;
  }
}
