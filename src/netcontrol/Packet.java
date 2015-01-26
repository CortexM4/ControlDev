/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

/**
 *
 * @author Crtx
 */
public class Packet {
    
    private static final int                ERROR_CMD       = 0;
    private static final int                TEST_CONNECTION = 1;
    private static final int                TEST_ACCEPT = 1;
    private static final int                PLAY_SOUND = 2;
    private static final int                SET_VOLUME = 3;
    private static final int                GET_VOLUME = 4;
    
    private int cmd;
    private byte[] data;
    
    public Packet(int cmd, byte[] data){
        this.cmd = cmd;
        this.data = data;
    }
    
}
