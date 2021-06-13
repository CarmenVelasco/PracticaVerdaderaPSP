package models;

import java.io.Serializable;
import java.util.Date;

public class Empleado implements Serializable { //Serializable es para que funcione el ObjectOutputStream
    int ID_Empleado;
    Date Ultima_Sesion;
    Date Fecha_Contratacion;

    public Empleado(int ID_Empleado, Date ultima_Sesion, Date fecha_Contratacion) {
        this.ID_Empleado = ID_Empleado;
        Ultima_Sesion = ultima_Sesion;
        Fecha_Contratacion = fecha_Contratacion;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "ID_Empleado=" + ID_Empleado +
                '}';
    }
}
