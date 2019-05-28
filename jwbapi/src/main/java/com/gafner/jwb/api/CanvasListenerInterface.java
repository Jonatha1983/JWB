package com.gafner.jwb.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CanvasListenerInterface extends Remote {
    void updateClient() throws RemoteException;
    void updateChat() throws RemoteException;
    void updatePaint() throws RemoteException;

}
