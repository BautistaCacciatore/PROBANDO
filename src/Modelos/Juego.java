package Modelos;
import Enumerados.Forma;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Juego extends ObservableRemoto implements IJuego{
    private ArrayList<Jugador> jugadores;
    private ArrayList<Ronda> rondas;
    private Ronda rondaActual;
    private int numeroRondaActual;
    private Mazo mazo;
    private int turno;
    private String lider;
    private boolean juegoIniciado;

    public Juego(){
        rondas= new ArrayList<>();
        cargarRondas();
        this.rondaActual= rondas.get(0);
        this.numeroRondaActual= 0;
        jugadores= new ArrayList<>();
        juegoIniciado= false;
    }

    /**
     * Mezcla el mazo, otorga 12 cartas a cada jugador.
     * @return void
     **/
    @Override
    public void repartir() throws RemoteException{
        mazo= new Mazo();
        mazo.mezclar();
        for (int i=0; i<jugadores.size(); i++){
            jugadores.get(i).setCartas(mazo.darCartas());
        }
    }

    /**
     * Agrega un jugador al juego.
     * @param nickname: nickname del jugador a agregar
     * @return void
     **/
    @Override
    public void agregarJugador(String nickname) throws RemoteException{
        if (this.jugadores.size()<4){
            Jugador j= new Jugador(nickname);
            if (this.jugadores.size()==0){
                lider= nickname;
            }
            this.jugadores.add(j);
            notificarObservadores(Evento.NUEVO_JUGADOR_AGREGADO);
        }
    }

    @Override
    public void iniciarJuego() throws RemoteException {
        //ARREGLAR ESTO, VERIFICAR QUE NO HAYA ALGUNA VIST QUE NO INICIO SESION
        if (jugadores.size()>=2){
            notificarObservadores(Evento.INICIAR_JUEGO);
            nuevaRonda();
            juegoIniciado= true;
        }
        else{
            notificarObservadores(Evento.FALTAN_JUGADORES);
        }
    }

    /**
     * Asigna un turno, genera un numero aleatorio entre la cantidad de jugadores.
     * @return void
     **/
    @Override
    public void asignarTurno() throws RemoteException{
        Random random= new Random();
        int indiceTurnoAleatorio;
        indiceTurnoAleatorio= random.nextInt(jugadores.size());
        this.turno= indiceTurnoAleatorio;
        notificarObservadores(Evento.NUEVO_TURNO);
    }

    /**
     * Cambia el turno al siguiente jugador.
     * @return void
     **/
    @Override
    public void cambiarTurno() throws RemoteException{
        if (this.turno==jugadores.size()-1){
            this.turno= 0;
        }
        else{
            this.turno+=1;
        }
        notificarObservadores(Evento.NUEVO_TURNO);
    }

    /**
     * Retorna el jugador actual, es decir, el jugador que posee el turno actual.
     * @return void
     **/
    @Override
    public Jugador jugadorActual() throws RemoteException{
        return jugadores.get(turno);
    }

    @Override
    public int cantidadCartasJugadorActual() throws RemoteException{
        Jugador j= jugadorActual();
        return j.getCantidadCartas();
    }

    /**
     * Inicializa y carga todas las rondas para comenzar con el juego.
     *
     * @return void
     **/
    private void cargarRondas() {
        //Inicializo todas las rondas y las cargo en el juego
        Ronda ronda;
        for (int i = 0; i < 10; i++) {
            switch (i) {
                case 0:
                    ronda = new Ronda(1);
                    ronda.añadirForma(Forma.TRIO, 2);
                    rondas.add(ronda);
                    break;
                case 1:
                    ronda = new Ronda(2);
                    ronda.añadirForma(Forma.ESCALA, 1);
                    ronda.añadirForma(Forma.TRIO, 1);
                    rondas.add(ronda);
                    break;
                case 2:
                    ronda = new Ronda(3);
                    ronda.añadirForma(Forma.ESCALA, 2);
                    rondas.add(ronda);
                    break;
                case 3:
                    ronda = new Ronda(4);
                    ronda.añadirForma(Forma.TRIO, 3);
                    rondas.add(ronda);
                    break;
                case 4:
                    ronda = new Ronda(5);
                    ronda.añadirForma(Forma.TRIO, 2);
                    ronda.añadirForma(Forma.ESCALA, 1);
                    rondas.add(ronda);
                    break;
                case 5:
                    ronda = new Ronda(6);
                    ronda.añadirForma(Forma.ESCALA, 2);
                    ronda.añadirForma(Forma.TRIO, 1);
                    rondas.add(ronda);
                    break;
                case 6:
                    ronda = new Ronda(7);
                    ronda.añadirForma(Forma.ESCALA, 3);
                    rondas.add(ronda);
                    break;
                case 7:
                    ronda = new Ronda(8);
                    ronda.añadirForma(Forma.TRIO, 4);
                    rondas.add(ronda);
                    break;
                case 8:
                    ronda = new Ronda(9);
                    ronda.añadirForma(Forma.ESCALERASUCIA, 1);
                    rondas.add(ronda);
                    break;
                case 9:
                    ronda = new Ronda(10);
                    ronda.añadirForma(Forma.ESCALERAREAL, 1);
                    rondas.add(ronda);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Indica si finalizo la ronda, es decir, si el jugador actual no posee cartas.
     * @param actual: jugador actual
     * @return void
     **/
    @Override
    public boolean finRonda(Jugador actual) throws RemoteException{
        if (actual.sinCartas()){
            cargarPuntos();
            notificarObservadores(Evento.TERMINO_RONDA);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Recorre los jugadores, calcula los puntos de cada uno y los carga.
     *
     * @return void
     **/
    private void cargarPuntos() {
        for (int i = 0; i < jugadores.size(); i++) {
            int puntos = jugadores.get(i).calcularPuntosRonda();
            jugadores.get(i).incrementarPuntos(puntos);
        }
    }

    /**
     * Inicializa una nueva ronda siempre y cuando el juego no haya terminado,
     * limpia las cartas bajadas de cada jugador y vuelve a repartir.
     * @return void
     **/
    @Override
    public void nuevaRonda() throws RemoteException{
        this.numeroRondaActual+=1;
        if (numeroRondaActual<=10){
            this.rondaActual= rondas.get(this.numeroRondaActual-1);
            for (int i=0; i<jugadores.size(); i++){
                jugadores.get(i).limpiarCartasBajadas();
                jugadores.get(i).setSeBajo(false);
            }
            repartir();
            notificarObservadores(Evento.NUEVA_RONDA);
            asignarTurno();
        }
        else{
            finJuego();
        }
    }

    /**
     * Controla si el juego termino, si es asi, determina el ganador del juego.
     * @return boolean: indica si el juego finalizo
     **/
    @Override
    public boolean finJuego() throws RemoteException{
        if (this.numeroRondaActual+1 > 10){
            notificarObservadores(Evento.TERMINO_JUEGO);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Verifica si algun jugador se quedo sin cartas y lo retorna.
     * @return Jugador: ganador de la ronda
     **/
    @Override
    public Jugador ganadorRonda() throws RemoteException{
        Jugador j= null;
        for (int i=0; i<jugadores.size(); i++){
            if (jugadores.get(i).sinCartas()){
                j= jugadores.get(i);
            }
        }
        return j;
    }

    /**
     * Verifica e indica quien fue el ganador del juego, controlando quien es el que posee
     * la menor cantidad de puntos.
     * @return Jugador: ganador del juego
     **/
    @Override
    public Jugador ganadorJuego() throws RemoteException{
        int minimoPuntos=0;
        Jugador ganador= null;
        int flag= 0;
        for (int i=0; i<jugadores.size(); i++){
            if (flag==0){
                ganador= jugadores.get(i);
                minimoPuntos= jugadores.get(i).getPuntos();
                flag= 1;
            }
            else{
                if (jugadores.get(i).getPuntos() < minimoPuntos){
                    ganador= jugadores.get(i);
                    minimoPuntos= jugadores.get(i).getPuntos();
                } else if (jugadores.get(i).getPuntos() == minimoPuntos) {
                    return null;
                }
            }
        }
        return ganador;
    }

    /**
     * Verifica si el jugador actual se bajo, es decir, formo las cartas indicadas
     * en la ronda.
     * @return boolean: indica si el jugador se bajo
     **/
    @Override
    public boolean jugadorActualSeBajo() throws RemoteException{
        return jugadorActual().getSeBajo();
    }

    /**
     * Hace que el jugador actual recoja una carta del mazo.
     * @return void
     **/
    @Override
    public void jugadorActualTomarCartaMazo() throws RemoteException{
        jugadorActual().recogerCarta(this.mazo, true);
        notificarObservadores(Evento.JUGADOR_TOMO_CARTA_MAZO);
    }

    /**
     * Hace que el jugador actual recoja una carta del pozo.
     * @return void
     **/
    @Override
    public void jugadorActualTomarCartaPozo() throws RemoteException{
        jugadorActual().recogerCarta(this.mazo, false);
        notificarObservadores(Evento.JUGADOR_TOMO_CARTA_POZO);
    }

    /**
     * Hace que el jugador actual deje una carta en el pozo.
     * @param indice: indica la posicion de carta a dejar
     * @return void
     **/
    @Override
    public void jugadorActualDejarCarta(int indice) throws RemoteException{
        if (indice>=1 && indice<=cantidadCartasJugadorActual()){
            jugadorActual().dejarCarta(this.mazo, indice);
            notificarObservadores(Evento.JUGADOR_DEJO_CARTA);
        }
    }

    /**
     * Verifica si el jugador actual puede bajarse, es decir, formar las formas que
     * indica la ronda.
     * @return boolean: determina si el jugador pudo bajarse
     **/
    @Override
    public boolean jugadorActualPuedeBajarse() throws RemoteException{
        boolean puedeBajarse= jugadorActual().bajarse(this.rondaActual);
        if (puedeBajarse==true){
            notificarObservadores(Evento.JUGADOR_SE_BAJO);
        }
        else{
            notificarObservadores(Evento.JUGADOR_NO_PUDO_BAJARSE);
        }
        return puedeBajarse;
    }

    /**
     * Verifica si el jugador actual pudo bajar la carta indicada.
     * @param indice: indica la posicion de la carta a dejar
     * @return boolean: indica si la carta pudo ser bajada
     **/
    @Override
    public boolean jugadorActualPudoBajarCarta(int indice) throws RemoteException{
        if (indice>=1 && indice<=cantidadCartasJugadorActual()){
            boolean pudoBajarCarta= jugadorActual().bajarCarta(indice, this.jugadores);
            if (pudoBajarCarta==true){
                notificarObservadores(Evento.JUGADOR_BAJO_CARTA);
            }
            else{
                notificarObservadores(Evento.JUGADOR_NO_PUDO_BAJAR_CARTA);
            }
            return pudoBajarCarta;
        }
        else{
            notificarObservadores(Evento.JUGADOR_NO_PUDO_BAJAR_CARTA);
            return false;
        }
    }

    /**
     * Verifica si el jugador actual no posee cartas.
     * @return boolean: indica si el jugador actual no posee cartas
     **/
    @Override
    public boolean jugadorActualSinCartas() throws RemoteException{
        return jugadorActual().sinCartas();
    }

    /**
     * Obtiene el tope del pozo.
     * @return String: tostring de la carta que se encuentra en el tope del pozo
     **/
    @Override
    public String obtenerTopePozo() throws RemoteException{
        return this.mazo.obtenerTopePozo();
    }

    @Override
    public int obtenerNumeroRondaActual() throws RemoteException{
        return rondaActual.getNumero();
    }

    @Override
    public HashMap<Forma, Integer> obtenerFormasRondaActual() throws RemoteException{
        return rondaActual.getFormas();
    }

    @Override
    public ArrayList<Bajada> formasArmadas(int indice) throws RemoteException{
        return jugadores.get(indice).getFormasArmadas();
    }

    @Override
    public ArrayList<Bajada> formasArmadas(Jugador jugador) throws RemoteException{
        return jugador.getFormasArmadas();
    }

    @Override
    public String obtenerNickname(Jugador jugador) throws RemoteException{
        return jugador.getNickname();
    }

    @Override
    public Forma obtenerNombreForma(Bajada bajada) throws RemoteException{
        return bajada.getNombreForma();
    }

    @Override
    public ArrayList<Carta> cartasQueFormanLaBajada(Bajada bajada) throws RemoteException{
        return bajada.getCartasQueLaForman();
    }

    @Override
    public int obtenerPuntosJugador(Jugador jugador) throws RemoteException{
        return jugador.getPuntos();
    }

    @Override
    public ArrayList<Carta> obtenerCartasJugador(String nickname) throws RemoteException{
        for (int i=0; i<jugadores.size(); i++){
            if (jugadores.get(i).getNickname().equals(nickname)){
                return jugadores.get(i).getCartas();
            }
        }
        return null;
    }

    @Override
    public String nicknameJugadorActual() throws RemoteException{
        return jugadorActual().getNickname();
    }

    @Override
    public int cantidadTotalFormasArmadas() throws RemoteException{
        int total=0;
        for (int i=0; i<jugadores.size(); i++){
            total+= jugadores.get(i).getFormasArmadas().size();
        }
        return total;
    }

    @Override
    public void continuarRonda() throws RemoteException{
        Jugador actual = jugadorActual();
        boolean terminoRonda = finRonda(actual);
        if (terminoRonda == true) {
            nuevaRonda();
            return;
        }
        cambiarTurno();
    }

    @Override
    public int cantidadJugadores() throws RemoteException{
        return this.jugadores.size();
    }

    @Override
    public Jugador obtenerUltimoJugadorAgregado() throws RemoteException {
        return jugadores.get(jugadores.size()-1);
    }

    /*Getters*/
    @Override
    public ArrayList<Jugador> getJugadores() throws RemoteException{
        return jugadores;
    }

    @Override
    public String getLider() throws RemoteException{
        return this.lider;
    }

    @Override
    public boolean getJuegoIniciado() throws RemoteException{
        return this.juegoIniciado;
    }

}
