import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Hilo extends Thread {

    Socket socket;
    final String RUTA = "/home/46066294p/exm09html.html";
    private Path pathRuta = Paths.get(RUTA);

    //constr
    public Hilo(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try  {
            /*
            //P2 FALTA CODI
            //Aqui va el codi per a modificar l'arxiu
            //html afegint-hi la IP que ha fet la peticio
            String ruta = "/home/46066294p/exm09html.html";
            try{
                FileWriter w = new FileWriter(ruta);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.append(" <p>IP que fa la peticio: " + socket.getInetAddress() + "</p>"); //concatenamos en el archivo sin borrar lo existente
                //ahora cerramos los flujos de canales de datos, al cerrarlos el archivo quedará guardado con información escrita
                //de no hacerlo no se escribirá nada en el archivo
                wr.close();
                bw.close();
            }catch(IOException e){}

            /////////////////////////////////////////////
            */

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            //Emmagatzemament de l'arxiu
            byte[] archivo = new byte[100000000];
            inputStream.read(archivo);

            //s'escriuen els bytes de l'arxiu que hi ha al socket
            //dins el outputStream
            outputStream.write(Files.readAllBytes(pathRuta));
            System.out.println("...arxiu publicat ok (" + archivo.length + " bytes)");
            System.out.println("IP que fa la peticio: " + socket.getInetAddress());


            inputStream.close();
            outputStream.close();
            socket.close();

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }


}//172.31.83.42:9090