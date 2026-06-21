package pe.edu.pe.Grupo02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.pe.Grupo02.model.Producto;
import pe.edu.pe.Grupo02.model.Ubicacion;
import pe.edu.pe.Grupo02.repository.ProductoRepository;
import pe.edu.pe.Grupo02.repository.UbicacionRepository;
import pe.edu.pe.Grupo02.service.impl.ProductoServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private UbicacionRepository ubicacionRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto productoMock;
    private Ubicacion ubicacionMock;

    @BeforeEach
    void setUp() {
        ubicacionMock = new Ubicacion();
        ubicacionMock.setId(1);
        ubicacionMock.setNombre("Estante A");

        productoMock = new Producto(
                1,
                "Teclado Mecánico",
                "Tecnología",
                150.0,
                15,
                5,
                "UNIDAD",
                ubicacionMock,
                true
        );
    }

    // --- TEST 1 ---
    @Test
    @DisplayName("Debe retornar un producto cuando se busca por un ID existente")
    void debeRetornarProductoCuandoIdExiste() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(productoMock));

        Producto resultado = productoService.obtenerPorId(1);

        assertNotNull(resultado);
        assertEquals("Teclado Mecánico", resultado.getNombre());
        verify(productoRepository, times(1)).findById(1);
    }

    // --- TEST 2 ---
    @Test
    @DisplayName("Debe lanzar RuntimeException cuando el producto no existe")
    void debeLanzarExcepcionCuandoProductoNoExiste() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            productoService.obtenerPorId(99);
        });

        assertEquals("Producto no encontrado con ID: 99", excepcion.getMessage());
        verify(productoRepository, times(1)).findById(99);
    }

    // --- TEST 3 ---
    @Test
    @DisplayName("Debe filtrar y retornar solo los productos con stock menor o igual al mínimo")
    void debeRetornarAlertasDeStockBajo() {
        Producto productoOk = new Producto(2, "Mouse", "Tecnología", 50.0, 20, 5, "UNIDAD", ubicacionMock, true);
        Producto productoCritico = new Producto(3, "Cable HDMI", "Tecnología", 20.0, 2, 5, "UNIDAD", ubicacionMock, true);

        when(productoRepository.findAll()).thenReturn(List.of(productoMock, productoOk, productoCritico));

        List<Producto> alertas = productoService.obtenerAlertasDeStockBajo();

        assertNotNull(alertas);
        assertEquals(1, alertas.size());
        assertEquals("Cable HDMI", alertas.get(0).getNombre());
    }

    // --- TEST 4 (NUEVO) ---
    @Test
    @DisplayName("Debe guardar y retornar el producto cuando se crea con una ubicación válida")
    void debeCrearProductoCorrectamente() {
        // Arrange
        when(ubicacionRepository.findById(1)).thenReturn(Optional.of(ubicacionMock));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoMock);

        // Act
        Producto resultado = productoService.crear(productoMock);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getUbicacion().getId());
        verify(ubicacionRepository, times(1)).findById(1);
        verify(productoRepository, times(1)).save(productoMock);
    }

    // --- TEST 5 (NUEVO) ---
    @Test
    @DisplayName("Debe actualizar los datos de un producto existente de manera correcta")
    void debeActualizarProductoExistente() {
        // Arrange
        Producto detallesNuevos = new Producto(1, "Teclado Pro", "Tecnología", 180.0, 10, 5, "UNIDAD", ubicacionMock, true);

        when(productoRepository.findById(1)).thenReturn(Optional.of(productoMock));
        when(ubicacionRepository.findById(1)).thenReturn(Optional.of(ubicacionMock));
        when(productoRepository.save(any(Producto.class))).thenReturn(detallesNuevos);

        // Act
        Producto resultado = productoService.actualizar(1, detallesNuevos);

        // Assert
        assertNotNull(resultado);
        assertEquals("Teclado Pro", resultado.getNombre());
        assertEquals(180.0, resultado.getPrecio());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    // --- TEST 6 (NUEVO) ---
    @Test
    @DisplayName("Debe llamar al método delete del repositorio al eliminar un producto existente")
    void debeEliminarProductoCorrectamente() {
        // Arrange
        when(productoRepository.findById(1)).thenReturn(Optional.of(productoMock));
        doNothing().when(productoRepository).delete(productoMock);

        // Act
        productoService.eliminar(1);

        // Assert
        verify(productoRepository, times(1)).findById(1);
        verify(productoRepository, times(1)).delete(productoMock);
    }

    // --- TEST 7 (NUEVO) ---
    @Test
    @DisplayName("Debe incrementar el stock actual del producto al registrar una entrada de mercancía")
    void debeRegistrarEntradaMercancia() {
        // Arrange
        int cantidadEntrada = 10;
        int stockInicial = productoMock.getStockActual(); // 15

        when(productoRepository.findById(1)).thenReturn(Optional.of(productoMock));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Producto resultado = productoService.registrarEntradaMercancia(1, cantidadEntrada);

        // Assert
        assertNotNull(resultado);
        assertEquals(stockInicial + cantidadEntrada, resultado.getStockActual(), "El stock debió subir a 25");
        verify(productoRepository, times(1)).save(productoMock);
    }
}