import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Mat on 09/03/2016.
 */
public class serverMain {
    public static void main(String[] args) {
        System.out.println("Examen M9 :: SERVIDOR :: Marc Cano ");

        String ip = "0.0.0.0";
        //String ip = "192.168.1.102";
        int port = 5555;
        String path = "C:\\Users\\Mat\\Desktop\\DAM2\\wifi_cole.txt";  // you may change this


        Scanner input = new Scanner(System.in);

        String menu = "";
        boolean on = true;//condicio de sortida del programa

        while (on) {
            System.out.println("\n");
            System.out.println("MENU:");
            System.out.println(" 2--> Enviar missatge a servidor");
            System.out.println(" 3--> Servidor de descarrega");//client ftp
            System.out.println(" 4--> Servidor web");//client ftp
            System.out.println(" 9--> Visualitzar dades");
            System.out.println(" 0--> Sortir del programa");
            System.out.println(" ");
            menu = input.nextLine();

            switch (menu) {
                case "0": {
                    System.out.println("\n...sortir");
                    on = false;
                    input.close();
                    break;
                }

                case "1": {

                    break;
                }

                case "2": {
                    missatgeServidor(ip, port);

                    break;
                }

                case "3": {
                    try {
                        dowloadFile(path, port);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }

                case "4": {
                    servidorWeb(ip, port);
                    break;
                }

                case "5": {
                    break;
                }

                case "6": {
                    break;
                }

                case "7": {
                    break;

                }case "8": {
                    break;
                }

                case "9": {
                    visualitzarDades(ip, port, path);
                    break;
                }

                default: {
                    System.out.println("\n...entrada de menu incorrecta\n");
                    break;
                }

            }//switch
        }//while_menu

    }//main


    //:::::METODES:::::


    private static void missatgeServidor(String ip, int port) {
        System.out.println("...creando servidor");

        try {
            ServerSocket serverSocket = new ServerSocket();
            System.out.println("...realizando bind");//bind = vincular
            InetSocketAddress addr = new InetSocketAddress(ip, port);

            serverSocket.bind(addr);//el servidor escucha en la direccion que le digamos

            System.out.println("...escuchando");

            Socket socketDeEscucha = serverSocket.accept();
            System.out.println("...se ha recibido una llamada");

            InputStream is = socketDeEscucha.getInputStream();
            OutputStream os = socketDeEscucha.getOutputStream();

            byte[] mensaje = new byte[25];
            is.read(mensaje);
            System.out.println(new String(mensaje));

            System.out.println("...cerrando");

            socketDeEscucha.close();
            serverSocket.close();
            is.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void visualitzarDades(String ip, int port, String path) {
        //System.out.println("\nCLIENT ACTUAL:");
        System.out.println("\nIP: " + ip);
        System.out.println("PORT: " + port);
        System.out.println("PATH DESCARREGA: " + path);

    }


    private static void dowloadFile(String path, int port) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;

        boolean escuchando = true;

        try {
            servsock = new ServerSocket(port);
            while (escuchando) {
                System.out.println("Escuchando...");
                try {
                    sock = servsock.accept();
                    System.out.println("Conexion aceptada: " + sock);
                    // send file
                    File myFile = new File(path);
                    byte [] byteFichero  = new byte [(int)myFile.length()];
                    fis = new FileInputStream(myFile);
                    bis = new BufferedInputStream(fis);
                    bis.read(byteFichero,0,byteFichero.length);
                    os = sock.getOutputStream();
                    System.out.println("Enviando " + path + "(" + byteFichero.length + " bytes)");
                    os.write(byteFichero,0,byteFichero.length);
                    os.flush();
                    System.out.println("Enviado.");
                }
                finally {
                    if (bis != null) bis.close();
                    if (os != null) os.close();
                    if (sock!=null) sock.close();
                }
            }
        }
        finally {
            if (servsock != null) servsock.close();
        }
    }


    private static void servidorWeb(String ip, int port) {
        try  {
            // Creamos nuestro ServerSocket y le damos una dirección
            ServerSocket servidorSocket = new ServerSocket();
            System.out.println("...realizando bind");//bind = vincular

            InetSocketAddress address = new InetSocketAddress(ip, port);
            //InetSocketAddress address = new InetSocketAddress("0.0.0.0.", 5555);

            // Acoplamos el InetSocketAdress al socket y los ponemos a escuchar
            servidorSocket.bind(address);
            System.out.println("...escuchando");


            // Bucle en el que se arranca un hilo por cada petición de acceso a la web
            // NOTA: MUY MUY MUY recomendable no hacer más de una conexión a la vez
            // Ya que el buffer es de un tamaño pequeño y se puede sobrecargar, provocando
            // excepciones (java.lang.OutOfMemoryError) y fugas de memoria)
            while (true){
                servidorSocket.bind(address);
                Socket socket = servidorSocket.accept();
                FilWeb filWeb = new FilWeb(socket);
                filWeb.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



}
