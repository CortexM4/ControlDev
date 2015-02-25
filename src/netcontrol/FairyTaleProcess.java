/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

import java.util.logging.Logger;
import netcontrol.Commands.FairyTale;

/**
 *
 * @author Crtx
 */
public class FairyTaleProcess {
   
    private static final Logger log = Logger.getLogger(FairyTaleProcess.class.getName());
    private String name;
    private int position;
    
    public FairyTaleProcess(FairyTale ft){
        name = ft.getName();
        position = ft.getPosition();
    }
    
    public FairyTale play() {
        FairyTale.Builder ft = FairyTale.newBuilder();
        Sound.Sound(name, true, position);
        ft.setName(name);
        ft.setMaxPosition(Sound.GetMaxPosition());
        ft.setPosition(Sound.getPosition());
        return ft.build();
    }
    
}
