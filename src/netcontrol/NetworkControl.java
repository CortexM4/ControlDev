package netcontrol;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Crtx
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;

public class NetworkControl implements Runnable {

    private static final Logger log = Logger.getLogger(NetworkControl.class.getName());

    private final Thread                    serverThread;
    private final ServerSocket              server;
    private Socket                          clientConnection;

    private static final int                ERROR_CMD       = 0;
    private static final int                TEST_CONNECTION = 1;
    private static final int                TEST_ACCEPT = 1;
    private static final int                PLAY_SOUND = 2;
    private static final int                SET_VOLUME = 3;
    private static final int                GET_VOLUME = 4;

    public NetworkControl() throws IOException {                // Разобраться с параметрами
        server = new ServerSocket(4444);
        serverThread = new Thread(this, "NetworkControl");
    }
    
    public void start() {
        serverThread.start();
        log.info("Thread started");
    }
    
    private void ListenSocket() {
        BufferedReader inStream = null;
        DataOutputStream outStream = null;
        int requestAction;
        try {
            System.out.println("Wait client");
            clientConnection = server.accept();
            
            inStream = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            outStream = new DataOutputStream(clientConnection.getOutputStream());
            
            requestAction = inStream.read();
            
            switch(requestAction) {
                case TEST_CONNECTION :                                  // Присутствует ли устройство в сети
                    outStream.write(TEST_ACCEPT);            
                    break;
                case PLAY_SOUND :                                       // Проигрывание звука
                    //Sound snd = new Sound(new File("gamestart.wav"));
                    InputStream audioSrc = clientConnection.getInputStream();
                    //add buffer for mark/reset support
                    InputStream bufferedIn = new BufferedInputStream(audioSrc);
                    Sound snd = new Sound(bufferedIn);
                    snd.setVolume((float) 0.6);
                    snd.play();
                    outStream.write(PLAY_SOUND);
                    break;
                case GET_VOLUME :
                    float vol = Sound.getVolume();
                    int bits = Float.floatToRawIntBits(vol);
                    outStream.writeInt(bits);
                    break;
                default : outStream.write(ERROR_CMD);
            }
            
            System.out.println("Socket close");
            clientConnection.close();
            
        } catch (IOException e) {
            log.log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            ListenSocket();
        }
    }
}
