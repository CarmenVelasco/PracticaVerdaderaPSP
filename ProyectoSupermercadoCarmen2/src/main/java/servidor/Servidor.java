package servidor;

import models.Configuracion;
import servidor.HiloServidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String args[]) throws IOException {

        ServerSocket servidor;
        servidor = new ServerSocket(6000);
        System.out.println("Servidor iniciado...");

        try {
            Configuracion.iniciar();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        while (true) {
            Socket cliente = new Socket();
            cliente = servidor.accept();//esperando cliente
            HiloServidor hilo = new HiloServidor(cliente);
            hilo.start(); //Se atiende al cliente

        }// Fin de while
    }// Fin de main
}// Fin de Servidor