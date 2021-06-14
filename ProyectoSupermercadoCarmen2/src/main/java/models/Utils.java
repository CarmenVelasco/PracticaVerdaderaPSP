package models;

import java.util.Scanner;

public class Utils {

    /***
     * Este método valida que lo que escriba el empleado sea un número y no un string
     * @param sc
     * @return
     */
    public static int validarNumero(Scanner sc) {
        boolean seguir = false;
        int numero = 0;

        do {
            try {
                numero = Integer.parseInt(sc.nextLine());
                seguir = true;
            } catch (Exception e) {
                System.err.println("Lo siento pero no has introducido un número");
            }

        }
        while (!seguir);

        return numero;
    }


    public static int validarNumero(Scanner sc, int isValido) {
        boolean seguir = true;
        int numero;
        do {
            numero = validarNumero(sc);
            seguir = false;

            if (numero <= 0 || numero > isValido) {
                System.out.println("Lo siento pero no has introducido una opción válida");
                seguir = true;
            }

        } while (seguir);

        return numero;
    }
}
