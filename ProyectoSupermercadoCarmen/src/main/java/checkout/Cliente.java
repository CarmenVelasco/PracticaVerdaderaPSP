package checkout;

import models.Empleado;
import models.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Cliente {
    static Scanner sc = new Scanner(System.in);
    static Socket Cliente;
    static DataInputStream flujoEntrada;
    static DataOutputStream flujoSalida;

    static {
        try {
            Cliente = new Socket("localhost", 6000);
            // CREO FLUJO DE ENTRADA AL SERVIDOR
            flujoEntrada = new DataInputStream(Cliente.getInputStream());

            // CREO FLUJO DE SALIDA AL SERVIDOR
            flujoSalida = new DataOutputStream(Cliente.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        //El cliente le mandará al servidor un mensaje del tipo
        //“Login;[ID_Empleado]”. El servidor, descompondrá el mensaje y
        //buscará el empleado en la BBDD.
        System.out.println("Escribe el login: ");
        int ID_Empleado = Integer.parseInt(sc.nextLine());
        new DataOutputStream(flujoSalida).writeUTF("Login;" + ID_Empleado);
        Object objeto = new ObjectInputStream(flujoEntrada).readObject();

        //En caso de no encontrar nada, el servidor le contestará al
        //cliente y devolverá un OBJETO Empleado NULL.
        if (objeto == null) {
            System.out.println("No se ha encontrado el empleado");
            return;
        }

        Empleado empleado = (Empleado) objeto; //casteo
        System.out.println("Bienvenido " + empleado);
        menu();
    }// fin de main


    public static void menu() {
        System.out.println("Seleccione la opción deseada: ");
        System.out.println("1: Cobrar compra. ");
        System.out.println("2: Obtener caja del dia. ");
        System.out.println("3: Salir. ");

        //int opcionEmpleado = Integer.parseInt(sc.nextLine());
        int opcionEmpleado = Utils.validarNumero(sc, 3);

        try {
            if (opcionEmpleado == 1) {
                cobrarCompra();
            } else if (opcionEmpleado == 2) {
                obtenerCaja();
            } else if (opcionEmpleado == 3) {
                salir();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //metodo de salida
    private static void salir() {

        try {
            // CERRAR STREAMS Y SOCKETS
            flujoEntrada.close();
            flujoSalida.close();
            Cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("¡Hasta luego!");
        System.exit(0); //status normal
    }

    static private void obtenerCaja() throws IOException {
        new DataOutputStream(flujoSalida).writeUTF("Caja");
        String total = new DataInputStream(flujoEntrada).readUTF();
        System.out.println("¡El total de la caja es (redoble de tambores): " + total + "!");
        menu();

    }

    static private void cobrarCompra() throws IOException {
        System.out.println("1: Disco duro. ");
        System.out.println("2: USB. ");
        System.out.println("3: Monitor. ");
        System.out.println("4: Ratón. ");

        System.out.println("Seleccione el artículo que desea: ");
        //int opcionCompra = Integer.parseInt(sc.nextLine());
        int opcionCompra = Utils.validarNumero(sc, 4);


        System.out.println("¿Cuántas unidades?: ");
        //int numeroUnidades = Integer.parseInt(sc.nextLine());
        int numeroUnidades = Utils.validarNumero(sc);
        System.out.println("De la opción: " + opcionCompra + " ha elegido la cantidad: " + numeroUnidades + ".");
        new DataOutputStream(flujoSalida).writeUTF("Cobro;" + opcionCompra + ";" + numeroUnidades);
        System.out.println("¡Su pedido ha sido recogido con éxito!");
        System.out.println("");
        menu();

    }
}
