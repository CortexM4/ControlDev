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
import au.edu.jcu.v4l4j.exceptions.V4L4JException;
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
import javax.sound.sampled.UnsupportedAudioFileException;
import netcontrol.Commands.BaseCommands;

public class NetworkControl {

    private static final Logger log = Logger.getLogger(NetworkControl.class.getName());

    private ServerSocket              server;
    private Socket                    clientConnection;
    private int                       port = 4444;
    
    private static VideoStreamProcess vsp;
    

    private static final int          ERROR_CMD       = 0;

    public static void main(String[] args) throws InterruptedException, IOException, LineUnavailableException {

        NetworkControl net;
        net = new NetworkControl();
        
        /**********Инициализация начальных параметров************/
        Sound.playMP3file("file.mp3", true);                                         // Проигрывание начального звука для инициализации Clip
        
        /*try {
            vsp = new VideoStreamProcess("/dev/video0", 640, 480, 8080, 15);    // Подготовка для стриминга видео с камеры
                                                                                // dev, width, height, port, fps
        } catch (V4L4JException ex) {
            log.log(Level.SEVERE, null, ex);
        }*/

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
                    break;
                case FAIRY_TALE :                                           // Сказки
                    log.info("Command: FAIRY_TALE");
                    Commands.FairyTale ft = packet.getFairyTale();
                    FairyTaleProcess ftp = new FairyTaleProcess(ft); 
                    switch(ft.getCmd()) {
                        case PLAY:
                            packet_return.setFairyTale(ftp.play());
                            break;
                        case GET_POSITION:
                            packet_return.setFairyTale(ftp.get_position());
                            break;
                        case SET_POSITION:
                            packet_return.setFairyTale(ftp.set_position());
                            break;
                    }
                    packet_return.setType(BaseCommands.Type.FAIRY_TALE);
                    packet_return.build().writeTo(outStream);
                    break;
                case VIDEO_STREAM:                                              // Запуск/останов передачи видео
                    log.info("Command: VIDEO_STREAM");
                    Commands.VideoStream vs = packet.getVideoStream();
                    if(vs.getPlay())
                        vsp.start();
                    else if(!vs.getPlay())
                        vsp.stop();
                    packet_return.setVideoStream(vs);
                    packet_return.setType(BaseCommands.Type.VIDEO_STREAM);
                    packet_return.build().writeTo(outStream);
                    break;
                    
                default : outStream.write(ERROR_CMD);
            }
            
            clientConnection.close();
            
        } catch (IOException e) {
            log.log(Level.SEVERE, null, e);
        }
    }
}
