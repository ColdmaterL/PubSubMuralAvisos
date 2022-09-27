package pubsub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote{

    public void subscribe(Client c, Integer key) throws RemoteException;
    public void publish(Integer key, Cell o) throws RemoteException;
}