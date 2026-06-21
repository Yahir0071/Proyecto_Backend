// archivo: src/main/java/pe/edu/pe/Grupo02/service/impl/PedidoServiceImpl.java
package pe.edu.pe.Grupo02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.pe.Grupo02.model.Pedido;
import pe.edu.pe.Grupo02.repository.PedidoRepository;
import pe.edu.pe.Grupo02.service.PedidoService;
import pe.edu.pe.Grupo02.structure.ColaPedidos;
import pe.edu.pe.Grupo02.structure.PilaHistorialEstados;
import pe.edu.pe.Grupo02.structure.PilaHistorialEstados.EstadoPedido;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ColaPedidos colaPedidos = new ColaPedidos();
    private final Map<Integer, PilaHistorialEstados> historialesPorPedido = new HashMap<>();

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido obtenerPorId(int id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Pedido crear(Pedido pedido) {
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        // Al crear, encolar automáticamente
        encolarPedido(pedidoGuardado);
        // Agregar estado inicial
        agregarEstadoPedido(pedidoGuardado.getId(), "PENDIENTE");
        return pedidoGuardado;
    }

    @Override
    @Transactional
    public Pedido actualizar(int id, Pedido detalles) {
        Pedido pedido = obtenerPorId(id);
        pedido.setEstado(detalles.getEstado());
        pedido.setTotal(detalles.getTotal());
        pedido.setPrioridad(detalles.getPrioridad());
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        pedidoRepository.deleteById(id);
    }

    // ===== MÉTODOS PARA COLA =====
    @Override
    public void encolarPedido(Pedido pedido) {
        colaPedidos.encolar(pedido);
    }

    @Override
    public Pedido procesarSiguientePedido() {
        return colaPedidos.desencolar();
    }

    @Override
    public Pedido obtenerPrimeroEnCola() {
        return colaPedidos.obtenerPrimero();
    }

    @Override
    public int obtenerTamanioCola() {
        return colaPedidos.tamanio();
    }

    // ===== MÉTODOS PARA PILA =====
    @Override
    public void agregarEstadoPedido(int idPedido, String estado) {
        historialesPorPedido.putIfAbsent(idPedido, new PilaHistorialEstados());
        historialesPorPedido.get(idPedido).apilar(estado, idPedido);
    }

    @Override
    public EstadoPedido deshacerEstadoPedido(int idPedido) {
        if (historialesPorPedido.containsKey(idPedido)) {
            return historialesPorPedido.get(idPedido).deshacer();
        }
        return null;
    }

    @Override
    public String obtenerUltimoEstadoPedido(int idPedido) {
        if (historialesPorPedido.containsKey(idPedido)) {
            EstadoPedido estado = historialesPorPedido.get(idPedido).obtenerUltimo();
            return estado != null ? estado.getNombre() : null;
        }
        return null;
    }
}