package com.gafner.jwb.api;

import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class is the client representation at the server
 */
@SuppressWarnings("RedundantThrows")
@Component
public class ClientListener extends UnicastRemoteObject implements CanvasListenerInterface {

    private CanvasController canvasController;

    public ClientListener(CanvasController canvasController) throws RemoteException {
        super();
        this.canvasController = canvasController;
    }
    @Override
    public void updateClient() throws RemoteException {
        if (canvasController != null) {
            canvasController.update();
        }
    }

    @Override
    public void updateChat() throws RemoteException {
        if (canvasController != null) {
            canvasController.loadConversationFromDB();
        }
    }

    @Override
    public void updatePaint() throws RemoteException {
        if (canvasController != null) {
            canvasController.loadPaintFromDB();
        }
    }
}
