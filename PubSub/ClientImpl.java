// java -classpath classes -Djava.rmi.server.codebase=file:classes/ pubsub.ClientImpl [aqui um nome pro cliente] [inteiro A] [inteiro B]

package pubsub;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientImpl implements Serializable, Client{

    private static final long serialVersionUID = 7526472295622776147L;
    private String serverName;

    public void receive(Cell o){
        System.err.println("Recebendo aviso de "+o.getNome()+": \n"+o.getMensagem()+"\n");
    }

    public String getName(){
        return this.serverName;
    }

    public void setName(String name){
        this.serverName = name;
    }

    public static void main(String args[]){

        int B = 0; // key for publishing
        int A = 0; // key for subscribing
        String host, name,mensagem="";
        Scanner leMensagem= new Scanner(System.in);
        Scanner leNumero= new Scanner(System.in);

        if (args.length == 4){
            host = args[0];
            name = args[1];
            A = Integer.parseInt(args[2]);
            B = Integer.parseInt(args[3]);
        }
        else if (args.length == 3){
            host = ""; 
            name = args[0];
            A = Integer.parseInt(args[1]);
            B = Integer.parseInt(args[2]);
        }
        else{
            host = "";
            name = "";
            System.err.println ("Usage: java Client [[host] port] sub_key pub_key");
            System.exit(1);
        }

        try{
            Client c = new ClientImpl();
            c.setName(name);

            Client stubC = (Client)UnicastRemoteObject.exportObject(c, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(name, stubC);

            registry = LocateRegistry.getRegistry(host);
            Server stub = (Server) registry.lookup("PubSub");
            
            System.err.println("Subscribind to: " + A + "\nPublishing: " + B + "\n");

            if (A != 0){          
                Integer sub_key;
                sub_key = A;
                stub.subscribe(c, sub_key);
            }
           
            if (B != 0){
                Integer pub_key;
                pub_key = B;
                System.err.println("Deseja enviar uma mensagem?(1-Sim,2-Nao)");
                int resposta=leNumero.nextInt();
                if(resposta==1){
                    System.err.println("Digite a mensagem:");
                    mensagem=leMensagem.nextLine();}
                Cell o = new Cell(); // Create a fresh object
                o.set(A,name,mensagem);  // Qual valor irá para a Cell? o valor da key dela ou da key do sub?
                stub.publish(pub_key, o);  // Publish the object
            }

        } catch(Exception e){

        }
    }
}