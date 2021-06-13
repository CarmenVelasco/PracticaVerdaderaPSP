package bbdd;

import models.EmailSender;
import models.Empleado;
import models.Producto;

import javax.mail.MessagingException;
import java.sql.*;
import java.util.Calendar;

public class Conexion {

    private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/bdsupermercado?useSSL=false";
    private static final String USUARIO = "root";
    private static final String CLAVE = "1234";
    Connection conexion;
    EmailSender enviadorMensaje = new EmailSender();


    public Conexion() {
        try {
            Class.forName(CONTROLADOR);
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("Conexion OK");

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Error en la conexion");
            e.printStackTrace();
        }
    }

    public Empleado login(int ID_Empleado) {
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT * from empleado where ID_Empleado = ?");
            pst.setInt(1, ID_Empleado);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Empleado(rs.getInt("ID_Empleado"), rs.getDate("Ultima_Sesion"), rs.getDate("Fecha_Contratacion"));
            }
            return null;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    public void cobro(int ID_Producto, int Cantidad) {

        try {
            var hoy = Calendar.getInstance();
            PreparedStatement pst = conexion.prepareStatement("INSERT into compra(ID_Producto, Cantidad, Fecha) values(?,?,?)");
            pst.setInt(1, ID_Producto);
            pst.setInt(2, Cantidad);
            pst.setDate(3, new Date(hoy.getTime().getTime()));
            pst.executeUpdate();

            PreparedStatement pst2 = conexion.prepareStatement("SELECT * from productos where ID_Producto = ?");
            pst2.setInt(1, ID_Producto);
            ResultSet rs = pst2.executeQuery();
            rs.next();
            Producto producto = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4), rs.getInt(5));
            if (producto.getCantidad() <= Cantidad) {
                String asunto = "El producto se ha agotado";
                String cuerpo = "Se ha agotado el producto: " + producto.getNombre_Producto() + "; A la hora: " + hoy.get(Calendar.HOUR_OF_DAY) + ":" + hoy.get(Calendar.MINUTE) + "; Precio de proveedor: " + producto.getPrecio_Proveedor();
                enviadorMensaje.sendEmail("LaQueSeHaLiado@SOS.com", asunto, cuerpo);
            }

            PreparedStatement pst3 = conexion.prepareStatement("UPDATE productos set Cantidad_Stock = ? where ID_Producto = ? ");
            pst3.setInt(1, (producto.getCantidad() - Cantidad));
            pst3.setInt(2, ID_Producto);
            pst3.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public String caja() {
        try {
            PreparedStatement pst = conexion.prepareStatement("SELECT SUM(compra.Cantidad * (productos.Precio_Venta - productos.Precio_Proveedor)) Total_Ganancias" +
                    " FROM compra" +
                    " LEFT JOIN productos ON compra.ID_Producto = productos.ID_Producto" +
                    " WHERE Fecha = CURDATE()");
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getString("Total_Ganancias");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

}
