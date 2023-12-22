import Controladores.Controlador;
import Modelos.Juego;
import Vistas.consolagrafica.ConsolaGrafica;
import Vistas.interfazGrafica.VistaJugador;

public class cariocaApp {
    public static void main(String[] args) {
        /*Juego modelo= new Juego();*/
        //CONSOLA GRAFICA
        /*for (int i=0; i<2; i++){
            if (modelo.cantidadJugadores()<4){
                ConsolaGrafica vistaConsolaGrafica= new ConsolaGrafica();
                Controlador controlador= new Controlador(vistaConsolaGrafica);
                vistaConsolaGrafica.setControlador(controlador);
                controlador.setModelo(modelo);
                modelo.addObserver(controlador);
                vistaConsolaGrafica.comenzarJuego();
            }
        }*/

        //INTERFAZ GRAFICA
        /*for (int i=0; i<2; i++){
            if (modelo.cantidadJugadores() < 4) {
                VistaJugador vistaJugador= new VistaJugador();
                Controlador controlador= new Controlador(vistaJugador);
                vistaJugador.setControlador(controlador);
                controlador.setModelo(modelo);
                modelo.addObserver(controlador);
            }
        }*/

        /*VistaJugador vistaJugador= new VistaJugador();
        Controlador controlador= new Controlador(vistaJugador);
        vistaJugador.setControlador(controlador);
        controlador.setModelo(modelo);
        modelo.addObserver(controlador);

        ConsolaGrafica vistaConsolaGrafica= new ConsolaGrafica();
        Controlador controlador1= new Controlador(vistaConsolaGrafica);
        vistaConsolaGrafica.setControlador(controlador1);
        controlador1.setModelo(modelo);
        modelo.addObserver(controlador1);
        vistaConsolaGrafica.comenzarJuego();*/


    }
}
