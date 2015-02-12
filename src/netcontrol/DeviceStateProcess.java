/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

import java.io.IOException;
import netcontrol.Commands.DeviceState;

/**
 *
 * @author Crtx
 */
public class DeviceStateProcess {
    
    
    private boolean power;
    private float gain;
    DeviceStateProcess(DeviceState deviceState) throws IOException{
        switch(deviceState.getType()) {
            case WRITE :
                power = deviceState.getPower();
                gain = deviceState.getSound();
                Sound.setVolume(gain);
            break;
            default :  
                throw new IOException("Device state ");
        }
    }
    
    public DeviceState GetAllValue(){
        DeviceState.Builder deviceState = DeviceState.newBuilder();
        deviceState.setType(DeviceState.Direction.READ);                // Какая-то лажа
        deviceState.setPower(true);
        deviceState.setSound(Sound.getVolume());
        return deviceState.build();
    }
    
}
