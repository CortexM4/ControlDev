/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author Crtx
 */
public class MainControl {

    private static final Logger log = Logger.getLogger(MainControl.class.getName());
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        try {
        NetworkControl net = new NetworkControl();
        int i = 0;
        while(true) {
            Thread.sleep(1000);
            System.out.print("Sec:" +i+ " ");
            i++;
        }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    
}
