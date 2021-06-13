package models;

public class Producto {
    int ID_Producto;
    String Nombre_Producto;
    float Precio_Venta;
    float Precio_Proveedor;
    int Cantidad_Stock;

    public Producto(int ID_Producto, String nombre_Producto, float precio_Venta, float precio_Proveedor, int cantidad_Stock) {
        this.ID_Producto = ID_Producto;
        Nombre_Producto = nombre_Producto;
        Precio_Venta = precio_Venta;
        Precio_Proveedor = precio_Proveedor;
        Cantidad_Stock = cantidad_Stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "Nombre_Producto='" + Nombre_Producto + '\'' +
                '}';
    }

    public String getNombre_Producto() {
        return Nombre_Producto;
    }

    public float getPrecio_Proveedor() {
        return Precio_Proveedor;
    }

    public int getCantidad() {
        return Cantidad_Stock;
    }
}
