package com.fm.modules.models;

public class PlatilloSeleccionado {

    private Long platilloSeleccionadoId;
    private Platillo platillo;
    private Pedido pedido;
    private String nombre;
    private double precio;

    public PlatilloSeleccionado() {
    }

    public PlatilloSeleccionado(Long platilloSeleccionadoId, Platillo platillo, Pedido pedido, String nombre,
                                double precio) {
        this.platilloSeleccionadoId = platilloSeleccionadoId;
        this.platillo = platillo;
        this.pedido = pedido;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Long getPlatilloSeleccionadoId() {
        return platilloSeleccionadoId;
    }

    public void setPlatilloSeleccionadoId(Long platilloSeleccionadoId) {
        this.platilloSeleccionadoId = platilloSeleccionadoId;
    }

    public Platillo getPlatillo() {
        return platillo;
    }

    public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{platilloSeleccionadoId:'");
        builder.append(platilloSeleccionadoId);
        builder.append("',platillos:'");
        builder.append(platillo);
        builder.append("',pedidos:'");
        builder.append(pedido);
        builder.append("',nombre:'");
        builder.append(nombre);
        builder.append("',precio:'");
        builder.append(precio);
        builder.append("'}");
        return builder.toString();
    }

}
