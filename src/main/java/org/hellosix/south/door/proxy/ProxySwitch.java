package org.hellosix.south.door.proxy;

/**
 *
 * @author lzz
 * @date 2018/2/2
 */
public class ProxySwitch {

    boolean status = true;

    public ProxySwitch(){}

    public ProxySwitch(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
