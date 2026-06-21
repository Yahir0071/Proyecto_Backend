package pe.edu.pe.Grupo02;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pe.edu.pe.Grupo02.model.Producto;
import pe.edu.pe.Grupo02.repository.ProductoRepository;
import pe.edu.pe.Grupo02.service.ProductoService;

@SpringBootTest
class ProductoServiceTest {


    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void probarRegistroEntradaMercancia() {
        System.out.println("--- INICIANDO TEST ---");


        Producto laptop = new Producto();
        laptop.setNombre("Laptop Lenovo ThinkPad");
        laptop.setCategoria("Tecnología");
        laptop.setPrecio(4200.0);
        laptop.setStockActual(10);
        laptop.setActivo(true);
        productoRepository.save(laptop);

        System.out.println("Llamando al servicio para registrar 5 unidades más...");
        productoService.registrarEntradaMercancia(laptop.getId(), 5);

        Producto productoActualizado = productoRepository.findById(laptop.getId()).get();
        System.out.println("Stock final verificado en BD: " + productoActualizado.getStockActual());
        System.out.println("--- FIN DEL TEST ---");
    }
}