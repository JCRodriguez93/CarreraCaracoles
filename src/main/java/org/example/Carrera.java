package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para el control y gestión de una carrera de caracoles.
 * @author Saul Lázaro Hermoso
 */
public class Carrera { //nombre clase, solucionado

    /**
     * Array de nombres. Los usaremos para crear una lista con los nombres de los caracoles
     */
    String[] nombres = {"Raudo", "Veloz", "Acelera y luego pregunta", "Mr Frenazo", "Maya la Rapida",
            "Pepito el babas", "Rompetechos derrape", "Gloria Escurridiza", "Mr Lechugas", "Sebastian el Pegajoso"};

    /**
     * Lista de Strings con los nombres de los caracoles.
     */
    List<String> listaNombres = Arrays.asList(nombres);

    /**
     * Número entero, es la distancia a la que se situa la meta.
     */
    private int meta;
    /**
     * Mapa, donde la clave es el nombre del caracol y el valor son los datos del mismo.
     */
    private HashMap<String, Caracol> caracoles; //nombre de la clase Caracol, solucionado
    /**
     * Numero entero con el Nº de participantes de la carrera.
     */
    private int num_participantes;
    /**
     * Atributo para la generación de elementos aleatorios.
     */
    Random rand;

    /**
     * Lista de caracoles. Estará ordenada de mejor a peor
     */
    List<Caracol> mejores;//lista para ordenar los caracoles
    /**
     * Lista de caracoles. Estará ordenada de peor a mejor.
     */
    List<Caracol> peores;//lista para ordenar los caracoles

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
     * Constructor de la clase Carrera. Asigna el número de participantes, genera la meta y rellena el mapa de participantes.
     *
     */
    public Carrera() {
        this.num_participantes = 5;
        this.rand = new Random();
        caracoles = new HashMap<>();

        //Vamos a crear caracoles:
        String nombre = "";
        this.meta = rand.nextInt(40 - 20 + 1) + 20;
        int i = 0;
        while (i < num_participantes) {
            //Hasta que no encuentre un nombre que no está en el Map de caracoles, sigue recuperando nombres
            nombre = this.listaNombres.get(rand.nextInt(this.listaNombres.size()));//ontenemos un nombre de forma aleatoria.
            if (!this.caracoles.containsKey(nombre)) {//si el nombre no está en el mapa, salimos del bucle infinito
                //max - min + 1) + min
                int retroceso = -1 * (rand.nextInt(3 - 1 + 1) + 1);//generamos el retroceso y lo multiplicamos por -1
                Caracol c = new Caracol(nombre, this.meta, retroceso);//creamos el caracol
                //constructor caracol, solucionado

                //añadimos el nombre y datos del caracol al Map
                caracoles.put(nombre, c);
                i++;
            }
        }
    }

    /**
     * Situa la meta al valor que recibe por parámetro
     * @param meta Será la distancia a la que se quiere situar la meta
     */
    public void setMeta(int meta) {
        this.meta = meta;
    }

    /**
     * Asigna un valor al número de participantes
     * @param num_participantes Valor con los participantes a asignar
     */
    public void setNum_participantes(int num_participantes) {
        this.num_participantes = num_participantes;
    }

    /**
     * Recupera el valor del atributo meta
     * @return Entero con el valor de la meta
     */
    public int getMeta() {
        return meta;
    }

    /**
     * Recupera el valor del numero de participantes.
     * @return Entero con el valor del numero de participantes
     */
    public int getNum_participantes() {
        return num_participantes;
    }

    /**
     * Muestra en pantalla los datos de los participantes de la carrera.
     */
    public void Mostrar_datos_participantes() {
        System.out.println(this.blue + "####################################################\n");
        System.out.println(this.blue + "Participantes de la carrera\n");

        //Mostramos los datos de cada caracol
        for (Entry<String, Caracol> c : this.caracoles.entrySet()) {

            System.out.println(c.getValue().toString());
        }
        System.out.println(this.blue + "####################################################\n" + this.reset);
    }

    /**
     * Inicia la carrera de caracoles. Lleva a cabo los siguientes pasos:
     * 1- Muestra los participantes
     * 2- Muestra un mensaje de aviso
     * 3- Hace una cuenta atrás
     * 4- Pone a correr a cada participante
     * 5- Espera a que acaben todos los participantes
     * 6- Muestra el fin de carrera
     * 7- Muestra el podio
     * 8- Saca la clasificación general
     */
    public void Iniciar_Carrera() {
        try {

            Caracol ca = new Caracol(listaNombres.get(0),meta,-1);
            Mostrar_datos_participantes(); //añadido
            System.out.println(this.red + "-----ATENCIÓN!! Va a empezar la carrera de caracoles-------");
            Thread.sleep(2 * 1000);

            for (int i = 3; i > 0; i--) {
                System.out.println(i);
                Thread.sleep(1000);
            }
            System.out.println("YA!!");

            for (Entry<String, Caracol> c : this.caracoles.entrySet()) {
                //obtenemos el caracol

                // Añade código para iniciar la carrera de cada caracol...

                //[con estos dos va, pero el último parece mejor, así que dejo el ultimo]
                // ca = c.getValue();
                // ca.start(); //
                c.getValue().start(); //esto me parece más suculemto, lo dejo así.
            }

            for (Entry<String, Caracol> c : this.caracoles.entrySet()) {
                //Añade código para esperar a que todos los participantes acaben la carrera...

                c.getValue().join(); //corregido 25/07/2022
            }

            //añadidos
            FinCarrera();
            Podio();
            Clasificacion_General();
            //Añade aquí tu código para completar el método.

        } catch (InterruptedException ex) {
            Logger.getLogger(Carrera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Muestra un mensaje indicando que ha acabado la carrera, seguidamente ordena por el más rápido y lento.
     */
    public void FinCarrera() {

        System.out.println(this.green + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(this.green + "FIN DE CARRERA");
        System.out.println(this.green + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + this.reset);
        this.Ordenar_Mas_rapido();
        this.Ordenar_Mas_lento();
    }

    /**
     * Muestra los 3 primeros clasificados de la carrera
     */
    public void Podio() {
        System.out.println(this.green + "------------------------------------------------------------------------");
        System.out.println(this.green + "PODIO DE LA CARRERA:\n");
        for (int i = 0; i < 3; i++) {
            System.out.println(this.blue + (i + 1) + ". " + this.mejores.get(i).resumen_caracol_simple());
        }
        System.out.println(this.green + "------------------------------------------------------------------------" + this.reset);
    }

    /**
     * Muestra la clasificación general
     */
    public void Clasificacion_General() {
        int i = 1;
        System.out.println(this.red + "------------------------------------------------------------------------");
        System.out.println(this.red + "CLASIFICACIÓN GENERAL:\n");
        for (Caracol c : this.mejores) {
            System.out.println(this.blue + i + ". " + c.resumen_caracol_simple());
            i++;
        }
        System.out.println(this.red + "------------------------------------------------------------------------" + this.reset);
    }

    /**
     * Ordena a los participantes del más lento al más rápido
     */
    public void Ordenar_Mas_lento() {
        //obtengo los valores del mapa
        this.peores = new ArrayList<>(this.caracoles.values());
        this.peores.sort(Comparator.comparing(Caracol::getDias_carrera).reversed());//lo ordeno indicandole que use la distancia total recorrida
    }

    /**
     * Ordena a los participantes del más rápido al más lento
     */
    public void Ordenar_Mas_rapido() {
        //obtengo los valores del mapa
        this.mejores = new ArrayList<>(this.caracoles.values());
        this.mejores.sort(Comparator.comparing(Caracol::getDias_carrera));//lo ordeno indicandole que use la distancia total recorrida
    }



    /**
     * Crea una String con un resumen de los datos de la carrera.
     * @return String con los datos de la carrera.
     */
    @Override
    public String toString() {
        String cadena = this.blue + "*************************************************\n";
        cadena += this.blue + "DATOS DE LA CARRERA:\n";
        cadena += this.blue + "*************************************************\n";
        cadena += this.blue + "Meta situada a: " + this.meta + "\n";
        cadena += this.blue + "Nº de participantes: " + this.num_participantes + "\n\n";
        cadena += this.blue + "*****************************************************\n" + this.reset;
        return cadena;
    }

}
