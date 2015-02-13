/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

import java.util.logging.Logger;

/**
 *
 * @author Crtx
 */
public class StreamSoundProcess implements Runnable {

    private static final Logger log = Logger.getLogger(StreamSoundProcess.class.getName());
    private Thread StreamThread;
    private NetworkControl net;
    
    public StreamSoundProcess() {
        StreamThread = new Thread(this, "StreamThread");
        net = new NetworkControl();
    }
    
    public void start() {
        StreamThread.start();
    } 
    
    @Override
    public void run() {
        
    }
    
}
