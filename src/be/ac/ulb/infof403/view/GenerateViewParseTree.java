package be.ac.ulb.infof403.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public abstract class GenerateViewParseTree {
    
    protected static final String ASSET_FOLDER = "assets";
    
    protected abstract String getFooter();
    
    protected abstract String getHeader();
    
    protected String readFile(final String fileName) {
        String result = "";
        try {
            final FileReader fileReader = new FileReader(fileName);
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while((line = bufferedReader.readLine()) != null) {
                result += line + "\n";
            }   
            bufferedReader.close();
        } catch(IOException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        
        return result;
    }
    
    protected void writeFile(final String fileOutput, final String content) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileOutput, "UTF-8");
            writer.print(content);
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(GenerateGojsParseTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(writer != null) {
            writer.close();
        }
    }
    
}
