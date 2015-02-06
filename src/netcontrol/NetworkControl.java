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
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Logger;
import java.util.logging.Level;
import netcontrol.DeviceStateProtos.DeviceState;

public class NetworkControl {

    private static final Logger log = Logger.getLogger(NetworkControl.class.getName());

    private ServerSocket              server;
    private Socket                    clientConnection;
    

    private static final int                ERROR_CMD       = 0;
    private static final int                TEST_CONNECTION = 1;
    private static final int                TEST_ACCEPT = 1;
    private static final int                PLAY_SOUND = 2;
    private static final int                SET_VOLUME = 3;
    private static final int                GET_VOLUME = 4;

    public static void main(String[] args) throws InterruptedException, IOException {

        DeviceState.Builder device = DeviceState.newBuilder();
      /*  try {
            device.mergeFrom(new FileInputStream("123.txt"));
        } catch (FileNotFoundException  e) {
            System.out.println(args[0] + ": File not found.  Creating a new file.");
        }*/
        
        device.setSound(0.1F);
        device.setPower(true);
        
        FileOutputStream output = new FileOutputStream("123.txt");
        
        device.build().writeTo(output);
        output.close();
        
      /*  NetworkControl net;
        net = new NetworkControl();
        
        
        while (true) {
            net.ListenSocket();
        }*/
    }
    
    public NetworkControl() {                
        try {
        server = new ServerSocket(4444);
        log.info("Server started");
        } catch (IOException ex) {
            Logger.getLogger(NetworkControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* !!!!!!!!!!!!!!Очень нужны таймауты!!!!!!!!!!!! */
    private void ListenSocket() {
        InputStream inStream = null;
        DataOutputStream outStream = null;
        int requestAction;
        try {
            log.info("Wait client");
            clientConnection = server.accept();
            
            //inStream = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            inStream = clientConnection.getInputStream();
            outStream = new DataOutputStream(clientConnection.getOutputStream());
            
            requestAction = inStream.read();
            
            log.info("Client cmd:"+ requestAction);
            
            switch(requestAction) {
                case TEST_CONNECTION :                                  // Присутствует ли устройство в сети
                    outStream.write(TEST_ACCEPT);            
                    break;
                case PLAY_SOUND :                                       // Проигрывание звука
//                    Sound snd = new Sound(new File("gamestart.wav"));
                    //BufferedInputStream bufferedIn = new BufferedInputStream(strm);
                    Sound snd = new Sound(clientConnection.getInputStream());
                    snd.setVolume((float) 0.6);     // Проверять, если объект не создался!!!!!!!!!!
                    snd.play();
                    outStream.write(PLAY_SOUND);
                    break;
                case GET_VOLUME :
                    float vol = Sound.getVolume();
                    int svol = Float.floatToIntBits(vol);
                    outStream.writeInt(svol);
                    break;
                case SET_VOLUME:
                    byte[] volu = new byte[4];
                    inStream.read(volu);
                    int volm = ByteBuffer.wrap(volu).getInt();
                    float set_vol = Float.intBitsToFloat(volm);
                    Sound.setVolume(set_vol);
                    break;
                    
                default : outStream.write(ERROR_CMD);
            }
            
            clientConnection.close();
            
        } catch (IOException e) {
            log.log(Level.SEVERE, null, e);
        }
    }
}
