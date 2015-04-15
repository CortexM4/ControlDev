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
    FairyTale ft;
    FairyTale.Builder ft_ret = FairyTale.newBuilder();
    
    private static final int          UNDEF_POSITION = -1;
    
    public FairyTaleProcess(FairyTale ft){
        this.ft = ft;
        ft_ret.setName(ft.getName());
        ft_ret.setCmd(FairyTale.Type.PLAY);
    }
    
    public FairyTale play() {
        Sound.playMP3file(ft.getName(), true);
        ft_ret.setMaxPosition(Sound.GetMaxPosition());
        ft_ret.setPosition(Sound.getPosition());
        return ft_ret.build();
    }
    
    public FairyTale get_position() {
        ft_ret.setPosition(Sound.getPosition());
        return ft_ret.build();
    }
    
    public FairyTale set_position() {
        Sound.setPosition(ft.getPosition());
        ft_ret.setPosition(Sound.getPosition());
        return ft_ret.build();
    }
    
}
