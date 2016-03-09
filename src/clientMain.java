import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Mat on 09/03/2016.
 */
public class clientMain {
    public static void main(String[] args) {
        System.out.println("Examen M9 :: CLIENT :: Marc Cano ");

        String ip = "";
        int port = 0;

        Scanner input = new Scanner(System.in);

        String menu = "";
        boolean on = true;//condicio de sortida del programa

        while (on) {
            System.out.println("\n");
            System.out.println("MENU:");
            System.out.println(" 1--> Establir direccio IP i port per conectar-se");
            System.out.println(" 2--> Enviar missatge a servidor");
            System.out.println(" 3--> Descarrega un arxiu del servidor");//client ftp
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
                    ip = establirIP();
                    port = establirPort();
                    break;
                }

                case "2": {
                    missatgeServidor(ip, port);

                    break;
                }

                case "3": {
                    try {
                        dowloadFile(ip, port);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }

                case "4": {

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
                    visualitzarDades(ip, port);
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

    private static String establirIP() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introdueix direccio IP:");
        String ip = scanner.nextLine();

        System.out.println("\nIP: " + ip);
        return ip;
    }

    private static int establirPort() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introdueix port:");
        int port = scanner.nextInt();

        System.out.println("PORT: " + port);
        return port;
    }

    private static void missatgeServidor(String ip, int port) {
        Scanner msg = new Scanner(System.in);
        System.out.println("Escriu missatge:");
        String mensaje = msg.nextLine();

        Socket clienteSocket = new Socket();
        System.out.println("...creat socket de client");
        System.out.println("...s'esta realitzant la conexio");

        //InetSocketAddress addr = new InetSocketAddress("172.31.83.42", 5555);
        //InetSocketAddress addr = new InetSocketAddress("192.168.1.102", 5555);
        InetSocketAddress addr = new InetSocketAddress(ip, port);
        try {

            clienteSocket.connect(addr);

            InputStream is = clienteSocket.getInputStream();
            OutputStream os = clienteSocket.getOutputStream();

            System.out.println("\n...enviant missatge");

            /*
            Ponemos el mensaje en el canal
            RECORDAR que hay que ponerlo en bits
             */
            os.write(mensaje.getBytes());

            System.out.println("...missatge enviat");

            clienteSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void visualitzarDades(String ip, int port) {
        //System.out.println("\nCLIENT ACTUAL:");
        System.out.println("\nIP: " + ip);
        System.out.println("PORT: " + port);
    }


    private static void dowloadFile(String ip, int port) throws IOException {
        /*
        Scanner teclat = new Scanner(System.in);
        System.out.println("Entra IP del servidor:");
        String ipServer = teclat.nextLine();
        System.out.println("Entra PORT del servidor:");
        int portServer = teclat.nextInt();
        */

        int bytesRead;
        int actual = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;

        try {
            sock = new Socket(ip, port);
            System.out.println("Conectando...");

            // Recibiendo archivo
            byte [] byteFichero  = new byte [10000000];//tamany inicial del buffer
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream("C:\\Users\\Mat\\Desktop\\DAM2\\testing_exam_M09.txt");//////////////////////////////////
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(byteFichero,0,byteFichero.length);
            actual = bytesRead;

            do {
                bytesRead = is.read(byteFichero, actual, (byteFichero.length - actual));
                if(bytesRead >= 0) actual += bytesRead;
            } while(bytesRead > -1);

            bos.write(byteFichero, 0 , actual);
            bos.flush();
            System.out.println("Recibido " + fos.toString()
                    + " ...Descargado (" + actual + " bytes read)");
        }
        finally {
            //teclat.close();
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
    }



}
