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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.sound.sampled.LineUnavailableException;
import netcontrol.Commands.BaseCommands;

public class NetworkControl {

    private static final Logger log = Logger.getLogger(NetworkControl.class.getName());

    private ServerSocket              server;
    private Socket                    clientConnection;
    private int                       port = 4444;
    

    private static final int                ERROR_CMD       = 0;
    private static final int                TEST_CONNECTION = 1;
    private static final int                TEST_ACCEPT = 1;
    private static final int                PLAY_SOUND = 2;
    private static final int                SET_VOLUME = 3;
    private static final int                GET_VOLUME = 4;

    public static void main(String[] args) throws InterruptedException, IOException, LineUnavailableException {

        NetworkControl net;
        net = new NetworkControl();
        
        /**********Инициализация начальных параметров************/
        Sound.Sound("files.wav");   // Проигрывание начального звука для инициализации Clip
         
        while (true) {
            net.ListenSocket();
        }
    }
    
    public NetworkControl() {                
        try {
        server = new ServerSocket(port);
        log.info("Server started");
        } catch (IOException ex) {
            Logger.getLogger(NetworkControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* !!!!!!!!!!!!!!Очень нужны таймауты!!!!!!!!!!!! */
    private void ListenSocket() {
        InputStream inStream = null;
        OutputStream outStream = null;
        BaseCommands packet;
        BaseCommands.Builder packet_return = BaseCommands.newBuilder();
        try {
            log.info("Wait client");
            clientConnection = server.accept();
            
            //inStream = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            inStream = clientConnection.getInputStream();
            packet = BaseCommands.parseFrom(inStream);
            outStream = new DataOutputStream(clientConnection.getOutputStream());    
            
            
            switch(packet.getType()) {
                case DEVICE_STATE :                                  // Обработка общих параметров устройства
                    log.info("Command: DEVICE_STATE");
                    DeviceStateProcess dsp = new DeviceStateProcess(packet.getDeviceState());
                    packet_return.setDeviceState(dsp.GetAllValue());
                    packet_return.setType(BaseCommands.Type.DEVICE_STATE);
                    packet_return.build().writeTo(outStream);
                    break;
//                case PLAY_SOUND :                                       // Проигрывание звука
//                    Sound snd = new Sound(new File("gamestart.wav"));
                    //BufferedInputStream bufferedIn = new BufferedInputStream(strm);
//                    Sound snd = new Sound(clientConnection.getInputStream());
//                    snd.setVolume((float) 0.6);     // Проверять, если объект не создался!!!!!!!!!!
//                    snd.play();
//                    outStream.write(PLAY_SOUND);
//                    break;
//                case GET_VOLUME :
//                    float vol = Sound.getVolume();
//                    int svol = Float.floatToIntBits(vol);
//                    outStream.writeInt(svol);
//                    break;
//                case SET_VOLUME:
//                    byte[] volu = new byte[4];
//                    inStream.read(volu);
//                    int volm = ByteBuffer.wrap(volu).getInt();
//                    float set_vol = Float.intBitsToFloat(volm);
//                    Sound.setVolume(set_vol);
//                    break;
                    
                default : outStream.write(ERROR_CMD);
            }
            
            clientConnection.close();
            
        } catch (IOException e) {
            log.log(Level.SEVERE, null, e);
        }
    }
}
