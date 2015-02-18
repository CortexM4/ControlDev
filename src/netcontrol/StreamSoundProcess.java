/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import netcontrol.Commands.StreamSound;

/**
 *
 * @author Crtx
 */
public class StreamSoundProcess implements Runnable {

    private static final Logger log = Logger.getLogger(StreamSoundProcess.class.getName());
    
    private ServerSocket              server;
    private Socket                    clientConnection;
    private int                       port;
    
    private final Thread              StreamThread;
    
    private static final int          ERROR_PORT = -1;
    
    public StreamSoundProcess(StreamSound streamSound) {
        StreamThread = new Thread(this, "StreamThread");
        port = streamSound.getPort();
        
        try {
        server = new ServerSocket(port);
        } catch (IOException ex) {
            port = ERROR_PORT;
            log.log(Level.SEVERE, null, ex);
        }
    }
    
    public StreamSound start() {
        StreamSound.Builder ss = StreamSound.newBuilder();
        ss.setPort(port);
        StreamThread.start();
        return ss.build();
    } 
    
    @Override
    public void run() {
        
        log.info("StreamSound thread start");
        try {
            clientConnection = server.accept();
            Sound snd = new Sound(clientConnection.getInputStream());
            snd.play();
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(StreamSoundProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
