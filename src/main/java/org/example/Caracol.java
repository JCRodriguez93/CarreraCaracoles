package org.example;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para iniciar la carrera de caracoles en diferentes hilos de ejecución.
 * @author Jorge Campos Rodríguez
 */
public class Caracol extends Thread { //importante, que extienda de thread

    //atributos que pide la tarea
    String nombre;
    int meta;
    int total_distancia;
    int dias_carrera;
    int retroceso;

    // Atributos de tipo listas (estos ya son un poco kekw, adivina):
    private LinkedList<Integer> desplazamientos;
    //lista de enteros que almacena los 10 desplazamientos que tiene cada caracol.
    private ArrayList<Integer> distancias_diarias;
    //es una lista de enteros para almacenar el desplazamiento diurno y nocturno del caracol.
    private ArrayList<List<Integer>> distancias_recorridas;
    //una lista de listas que almacena las distancias recorridas por el caracol cada día.
    private LinkedList<Integer> copia;
    //lista de enteros que almacena los 10 desplazamientos que tiene cada caracol.

    /**
     * Atributos para colorear los mensajes de la terminal.
     */
    public static String black = "\033[30m";
    public static String cyan = "\033[36m";
    public static String red = "\033[31m";
    public static String green = "\033[32m";
    public static String blue = "\033[34m";
    public static String purple = "\033[35m";
    public static String reset = "\u001B[0m";


    /**
     * Constructor de la clase caracol. Asigna el nombre, total distancia,
     * retroceso, meta, dias de carrera. Instancia las listas de
     * desplazamientos, distancias recorridas y diarias. Crea una copia de los
     * desplazamientos.
     *
     * @param nombre
     * @param meta
     * @param retroceso
     */
    Caracol(String nombre, int meta, int retroceso) {

        this.nombre = nombre; //nombre del caracol (viene por parámetro en el constructor)
        this.total_distancia = 0; //total distancia recorrida
        this.retroceso = retroceso; //retroceso del caracol (viene por parámetro en el constructor)
        this.meta = meta; //meta (viene por parámetro en el constructor)
        this.dias_carrera = 1; //dias de carrera que empiezan en el día 1

        //instanciamos las listas de los desplazamientos
        this.desplazamientos = new LinkedList();
        this.distancias_recorridas = new ArrayList();
        this.distancias_diarias = new ArrayList();

        Random r = new Random(); //objeto de la clase random para numeros aleatorios

        //bucle que añade los 10 desplazamientos diurnos del caracol entre 6 y 4
        for (int a = 0; a < 10; a++) {
            int numAleatorio = r.nextInt(4) + 3;
            desplazamientos.add(numAleatorio);
        }

        //instanciamos la copia y clonamos los desplazamientos para una vez se acaben recargarlos
        this.copia = new LinkedList();
        this.copia = (LinkedList) this.desplazamientos.clone();

    }


    /**
     * Método que devuelve el nombre del caracol.
     *
     * @return String con el nombre del caracol.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para asignar el nombre al caracol.
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que devuelve el la meta.
     *
     * @return int con el valor a donde está situada la meta.
     */
    public int getMeta() {
        return meta;
    }

    /**
     * Método que asigna el valor de la meta.
     *
     * @param meta
     */
    public void setMeta(int meta) {
        this.meta = meta;
    }

    /**
     * Método que devuelve el total distancia recorrida por el caracol.
     *
     * @return int con el valor del total de las distancias.
     */
    public int getTotal_distancia() {
        return total_distancia;
    }

    /**
     * Método que asigna el valor total de la distancias recorridas.
     *
     * @param total_distancia
     */
    public void setTotal_distancia(int total_distancia) {
        this.total_distancia = total_distancia;
    }

    /**
     * Método que devuelve el valor del retroceso.
     *
     * @return int con el valor del retroceso.
     */
    public int getRetroceso() {
        return retroceso;
    }

    /**
     * Método que asigna el valor al retroceso.
     *
     * @param retroceso
     */
    public void setRetroceso(int retroceso) {
        this.retroceso = retroceso;
    }

    /**
     * Método que obtiene el valor de los días de carrera.
     *
     * @return int del valor de los días de carrera
     */
    public int getDias_carrera() {
        return dias_carrera;
    }

    /**
     * Método que asigna el valor a los días de carrera.
     *
     * @param dias_carrera
     */
    public void setDias_carrera(int dias_carrera) {
        this.dias_carrera = dias_carrera;
    }


    /**
     * Método que genera un resumen simple de la carrera de caracoles.
     *
     * @return String con el nombre, distancia y días en acabar la carrera de
     * cada caracol.
     */
    public String resumen_caracol_simple() {

        String resumen = nombre + ". Distancia recorrida: " + this.total_distancia + ". Dias en acabar la carrera: " + dias_carrera;
        return resumen;
    }

    @Override
    public void run() {
        int jornada = 0; //integer con el valor de la jornada.
        int llegada = this.getMeta(); //valor int de la meta para salir del bucle.

        //bucle do-while que se repite hasta que llegada sea 0
        do {

            try {
                comprobar_lista(); //llamamos al método comprobar_lista
                int palante = desplazamientos_diurnos(); //obtenemos el valor del desplazamiento diurno
                int patras = desplazamientos_nocturnos(); //obtenemos el valor del desplazamiento diurno

                //añado los valores de la lista a la distancia recorrida
                this.distancias_recorridas.add(distancias_diarias);

                this.dias_carrera++; //sumo un dia de carrera
                ++jornada; //sumo en 1 la jornada
                //el valor de llegada (que tenía el valor de la meta) sera la resta entre lo que se ha desplazado hoy
                llegada = (llegada - (palante + patras));
                this.setMeta(llegada); //alcualiza el valor de la meta.
                resumen_diario(palante, patras, jornada); //llamada al resumen diario.

            } catch (InterruptedException ex) {
                Logger.getLogger(Caracol.class.getName()).log(Level.SEVERE, null, ex);
            }

        } while (llegada > 0); //cuando el valor de llegada sea 0, el caracol ha llegado a meta
        //salida cuando los caracoles han llegado a meta.
        System.out.println(this.green + "El caracol " + this.getNombre() + " ha llegado a la meta");
    }

    /**
     * Método resumen diario.
     * Crea un resumen de la jornada por cada competidor.
     * @param palante
     * @param patras
     * @param jornada
     */
    private void resumen_diario(int palante, int patras, int jornada) {

        this.total_distancia += (palante + patras);

        System.out.println(this.blue + "RESUMEN DE LA JORNADA " + jornada + "\n"
                + this.blue + "Caracol: " + this.getNombre() + "\n"
                + this.blue + "Dias de competicion: " + this.getDias_carrera() + "\n"
                + this.blue + "Meta en: " + this.getMeta() + "\n"
                + this.blue + "Distancias recorridas: " + this.distancias_diarias + "\n"
                + this.blue + "Distancia reorrida hoy: \n"
                + this.blue + "\t- Distancia diurna: " + palante + "\n"
                + this.blue + "\t- Distancia nocturna: " + patras + "\n"
                + this.blue + "Total distancia recorrida hoy: " + (palante + patras) + "\n"
                + this.blue + "TOTAL DISTANCIA RECORRIDA: " + this.total_distancia + "\n"
                + this.red + "########################################");

    }

    /**
     * Método para los desplazamientos nocturnos.
     * @return int con el valor del retroceso del caracol.
     * @throws InterruptedException
     */
    private int desplazamientos_nocturnos() throws InterruptedException {
        System.out.println(this.purple + "Movimiento nocturno. Caracol: " + this.getNombre());
        //obtengo el retroceso en una variable "patras"
        int patras = this.retroceso;
        this.distancias_diarias.add(patras); //se lo añado a las distancias diarias
        Thread.sleep(1000); //duermo el hilo un poco
        return patras; //devuelvo el retroceso
    }

    /**
     * Método para los desplazamientos diurnos.
     * @return int con el valor del avance del caracol.
     * @throws InterruptedException
     */
    private int desplazamientos_diurnos() throws InterruptedException {
        System.out.println(this.purple + "Movimiento diurno. Caracol: " + this.getNombre());
        int palante = this.desplazamientos.get(0); //obtengo el primer desplazamiento en una variable "palante"
        this.distancias_diarias.add(palante); //se lo añado a las distancias diarias
        Thread.sleep(1000); //duermo un poco el hilo
        this.desplazamientos.remove(0); //borro el primer desplazamiento
        return palante; //devuelvo el valor "palante
    }

    /**
     * Metodo comprobar lisa.
     * Comprueba si la lista de desplazamientos se ha
     * agotado. En caso de agotarse si el caracol no ha llegado a meta la
     * volverá a cargar.
     */
    private void comprobar_lista() {
        if (this.desplazamientos.isEmpty()) { //si la lista está vacia
            this.desplazamientos.addAll(copia); //añado a la lista de desplazamiento todo lo que hay en "copia"
        }
    }

    /**
     * Crea una String con los datos de los caracoles para la carrera.
     * @return String con los datos de la carrera.
     */
    @Override
    public String toString() {
        return "Nombre caracol:" + nombre + "\n"
                + "Desplazamientos diurnos: " + desplazamientos + "\n"
                + "Desplazamientos nocturnos: " + retroceso + "\n"
                + "Distancia a recorrer: " + meta + "\n";
    }

    public static void main(String[] args) {

        Carrera carrera = new Carrera(); //instancio un objeto carrera
        System.out.println(carrera); //llama al toString de la clase Carrera
        carrera.Iniciar_Carrera(); //llamo al método iniciar carrera

    }
}
/**
 * RESUMEN DEL EXAMEN.
 *
 * Tengo los atributos y las listas.
 * El constructor crea los caracoles con sus datos (nombre, meta, desplazamientos, etc)
 * En el Run()
 *      Compruebo que la lista no esté vacia
 *      Hago los desplazamientos, diurno y nocturno
 *      Añado las distancias diarias a la distancia recorrida
 *      sumo en (1) la jornada y dias_carrera
 *      obtengo la llegada (que será el resultado de lo que avanza y retrocede el caracol)
 *      le cambio el valor de la meta (ejm: 25) por el de llegada (ejm: 20)
 *      muestro el resumen de la jornada
 *      si el valor de llegada es menor que 0, sale del bucle, el caracol llegó
 */