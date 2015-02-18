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
import com.google.protobuf.CodedInputStream;
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

    public static void main(String[] args) throws InterruptedException, IOException, LineUnavailableException {

        NetworkControl net;
        net = new NetworkControl();
        
        /**********Инициализация начальных параметров************/
        Sound.InitGain(0.5F);        // Вообще борода
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
            log.log(Level.SEVERE, null, ex);
        }
    }
    
    /* !!!!!!!!!!!!!!Очень нужны таймауты!!!!!!!!!!!! */
    private void ListenSocket() {
        byte[] bytes      = new byte[1024];
        BaseCommands packet;
        BaseCommands.Builder packet_return = BaseCommands.newBuilder();
        try {
            clientConnection = server.accept();
 
            //inStream = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            InputStream inStream = clientConnection.getInputStream();
            
            int  bytesRead = inStream.read(bytes);                                          
            CodedInputStream stream = CodedInputStream.newInstance(bytes, 0, bytesRead);    // Тут гребаная магия, т.к. parseFrom если принимает InputStream
            packet = BaseCommands.parseFrom(stream);                                        // то надо разорвать соединение, чтобы он отпустил, иначе висит в ожидании.
            OutputStream outStream = new DataOutputStream(clientConnection.getOutputStream());    
            
            
            switch(packet.getType()) {
                case DEVICE_STATE :                                  // Обработка общих параметров устройства
                    log.info("Command: DEVICE_STATE");
                    DeviceStateProcess dsp = new DeviceStateProcess(packet.getDeviceState());
                    packet_return.setDeviceState(dsp.GetAllValue());
                    packet_return.setType(BaseCommands.Type.DEVICE_STATE);
                    packet_return.build().writeTo(outStream);
                    break;
                case STREAM_SOUND :                                       // Проигрывание звука
                    log.info("Command: STREAM_SOUND");
                    StreamSoundProcess ssp = new StreamSoundProcess(packet.getStreamSound());
                    packet_return.setStreamSound(ssp.start());                  // Значит так, ssp.start() запускает потоки и
                    packet_return.setType(BaseCommands.Type.STREAM_SOUND);      // возвращает StreamSound. Если поле port -1 то,
                    packet_return.build().writeTo(outStream);                   // произошла какая-то ошибка.
//                    Sound snd = new Sound(new File("gamestart.wav"));
                    //BufferedInputStream bufferedIn = new BufferedInputStream(strm);
//                    Sound snd = new Sound(clientConnection.getInputStream());
//                    snd.setVolume((float) 0.6);     // Проверять, если объект не создался!!!!!!!!!!
//                    snd.play();
//                    outStream.write(PLAY_SOUND);
                    break;
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
