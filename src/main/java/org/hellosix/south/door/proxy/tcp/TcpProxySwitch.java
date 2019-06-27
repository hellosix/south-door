package org.hellosix.south.door.proxy.tcp;

/**
 *
 * @author lzz
 * @date 2018/2/2
 */
public class TcpProxySwitch {

    boolean status = true;

    public TcpProxySwitch(){}

    public TcpProxySwitch(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
