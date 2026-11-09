package ec.edu.espe.productosapi.service;

import ec.edu.espe.productosapi.exception.ProductoInvalidoException;
import ec.edu.espe.productosapi.exception.ProductoNoEncontradoException;
import ec.edu.espe.productosapi.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {
    private List<Producto> productos = new ArrayList<>();
    private Long contadorId = 1L;

    public List<Producto> obtenerTodos() {
        return productos;
    }

    public Producto obtenerPorId(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado."));
    }

    public Producto guardar(Producto producto) {
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new ProductoInvalidoException("El precio debe ser mayor a cero.");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new ProductoInvalidoException("El stock no puede ser negativo.");
        }
        producto.setId(contadorId++);
        productos.add(producto);
        return producto;
    }

    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        productos.remove(producto);
    }
}
