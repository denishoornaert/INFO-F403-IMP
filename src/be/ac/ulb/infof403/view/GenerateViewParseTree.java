package be.ac.ulb.infof403.view;

import be.ac.ulb.infof403.Utils.FileController;


/**
 * 
 */
public abstract class GenerateViewParseTree extends FileController {
    
    protected static final String ASSET_FOLDER = "assets";
    
    protected abstract String getFooter();
    
    protected abstract String getHeader();
    
}
