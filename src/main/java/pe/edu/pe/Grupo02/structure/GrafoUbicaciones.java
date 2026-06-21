package pe.edu.pe.Grupo02.structure;

import pe.edu.pe.Grupo02.model.Ubicacion;
import java.util.*;

public class GrafoUbicaciones {

    private Map<Integer, NodoUbicacion> nodos;
    private Map<Integer, List<Arista>> adyacencia;

    public GrafoUbicaciones() {
        this.nodos = new HashMap<>();
        this.adyacencia = new HashMap<>();
    }

    public void agregarNodo(int id, String nombre) {
        NodoUbicacion nodo = new NodoUbicacion(id, nombre);
        nodos.put(id, nodo);
        adyacencia.put(id, new ArrayList<>());
    }

    public void agregarArista(int origen, int destino, double distancia) {
        if (!nodos.containsKey(origen) || !nodos.containsKey(destino)) {
            return;
        }

        adyacencia.get(origen).add(new Arista(destino, distancia));
        adyacencia.get(destino).add(new Arista(origen, distancia));
    }

    // Dijkstra - Ruta más corta
    public List<Integer> rutaMasCorta(int origen, int destino) {
        Map<Integer, Double> distancias = new HashMap<>();
        Map<Integer, Integer> previos = new HashMap<>();
        PriorityQueue<Nodo> pq = new PriorityQueue<>();

        for (Integer nodo : nodos.keySet()) {
            distancias.put(nodo, Double.MAX_VALUE);
        }
        distancias.put(origen, 0.0);
        pq.offer(new Nodo(origen, 0.0));

        while (!pq.isEmpty()) {
            Nodo actual = pq.poll();
            int nodoActual = actual.getId();
            double distActual = actual.getDistancia();

            if (distActual > distancias.get(nodoActual)) continue;

            for (Arista arista : adyacencia.get(nodoActual)) {
                int vecino = arista.getDestino();
                double peso = arista.getDistancia();
                double nuevaDistancia = distancias.get(nodoActual) + peso;

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    previos.put(vecino, nodoActual);
                    pq.offer(new Nodo(vecino, nuevaDistancia));
                }
            }
        }

        List<Integer> ruta = new ArrayList<>();
        Integer actual = destino;
        while (actual != null) {
            ruta.add(0, actual);
            actual = previos.get(actual);
        }

        return ruta;
    }

    // BFS
    public List<Integer> rutaBFS(int origen, int destino) {
        Map<Integer, Integer> padre = new HashMap<>();
        Queue<Integer> cola = new LinkedList<>();
        Set<Integer> visitados = new HashSet<>();

        cola.offer(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            int actual = cola.poll();
            if (actual == destino) break;

            for (Arista arista : adyacencia.get(actual)) {
                int vecino = arista.getDestino();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    padre.put(vecino, actual);
                    cola.offer(vecino);
                }
            }
        }

        List<Integer> ruta = new ArrayList<>();
        Integer actual = destino;
        while (actual != null) {
            ruta.add(0, actual);
            actual = padre.get(actual);
        }

        return ruta;
    }

    private static class NodoUbicacion {
        private int id;
        private String nombre;

        public NodoUbicacion(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
    }

    private static class Arista {
        private int destino;
        private double distancia;

        public Arista(int destino, double distancia) {
            this.destino = destino;
            this.distancia = distancia;
        }

        public int getDestino() {
            return destino;
        }

        public double getDistancia() {
            return distancia;
        }
    }

    private static class Nodo implements Comparable<Nodo> {
        private int id;
        private double distancia;

        public Nodo(int id, double distancia) {
            this.id = id;
            this.distancia = distancia;
        }

        public int getId() {
            return id;
        }

        public double getDistancia() {
            return distancia;
        }

        @Override
        public int compareTo(Nodo otro) {
            return Double.compare(this.distancia, otro.distancia);
        }
    }
    public boolean contiene(int idNodo) {
        return nodos.containsKey(idNodo);
    }
}
