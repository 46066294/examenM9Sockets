import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller {

    public static void main(String[] args)  {
        System.out.println("Examen M9 :: SERVIDOR :: Marc Cano ");

        try  {
            //Es crea el socket de servidor
            ServerSocket serverSocket = new ServerSocket();

            InetSocketAddress add = new InetSocketAddress("0.0.0.0", 9090);
            serverSocket.bind(add);
            System.out.println("...escuchando");

            //Es crea el socket i es posa a l'espera de peticions
            Socket socket = serverSocket.accept();
            System.out.println("...connect ok");
            //es crea el fil i s'inicia
            Hilo hilo = new Hilo(socket);
            hilo.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}