package servidor;

import bbdd.Conexion;
import models.Empleado;

import java.io.*;
import java.net.Socket;

public class HiloServidor extends Thread {
    InputStream entrada;
    OutputStream salida;
    Conexion conexion = new Conexion();

    private Socket cliente;

    public HiloServidor(Socket cliente) throws IOException {
        this.cliente = cliente;
        // CREO FLUJO DE ENTRADA DEL CLIENTE
        entrada = cliente.getInputStream();
        // CREO FLUJO DE SALIDA AL CLIENTE
        salida = cliente.getOutputStream();
    }

    @Override
    public void run() {
        try {

            while (true) {
                // LEER ENTRADA DEL CLIENTE
                DataInputStream flujoEntrada = new DataInputStream(entrada);
                String stringFlujoEntrada = flujoEntrada.readUTF();
                String[] splitFlujoEntrada = stringFlujoEntrada.split(";");
                String variable1 = splitFlujoEntrada[0];

                if (variable1.equals("Login")) {
                    login(splitFlujoEntrada);
                } else if (variable1.equals("Cobro")) {
                    cobro(splitFlujoEntrada);
                } else if (variable1.equals("Caja")) {
                    caja();
                }
                //flujoEntrada.close();
            }

        } catch (IOException e) {
            e.printStackTrace();


            try {
                // CERRAR STREAMS Y SOCKETS
                entrada.close();
                salida.close();
                cliente.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    private void caja() throws IOException {
        new DataOutputStream(salida).writeUTF(conexion.caja());
    }

    private void cobro(String[] splitFlujoEntrada) throws IOException {
        int idProducto = Integer.parseInt(splitFlujoEntrada[1]);
        int cantidad = Integer.parseInt(splitFlujoEntrada[2]);
        conexion.cobro(idProducto, cantidad);

    }


    private void login(String[] splitFlujoEntrada) throws IOException {
        Empleado empleado = conexion.login(Integer.parseInt(splitFlujoEntrada[1]));
        new ObjectOutputStream(salida).writeObject(empleado);

    }

}// fin de Ejemplo1Servidor