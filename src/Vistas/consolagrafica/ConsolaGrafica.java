package Vistas.consolagrafica;
import Controladores.Controlador;
import Enumerados.Forma;
import Modelos.Bajada;
import Modelos.Carta;
import Modelos.Jugador;
import Vistas.IVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ConsolaGrafica implements IVista{
    Controlador controlador;
    private final JFrame frame;
    private JPanel contentPane;
    private JTextField txtEntrada;
    private JButton btnEnter;
    private JTextArea txtSalidaConsola;

    private int jugadoresRestantes;
    private int estadoMenuJuego;
    private int estadoIndiceCarta;
    private int estadoSubmenuJuego;
    private int esperandoInicioJuego;
    String nickname;

    public ConsolaGrafica() {
        frame = new JFrame("<CARIOCA>");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        txtSalidaConsola.setBackground(Color.BLACK);
        txtSalidaConsola.setForeground(Color.GREEN);
        txtSalidaConsola.setEditable(false);
        jugadoresRestantes=0;

        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSalidaConsola.append(txtEntrada.getText() + "\n");
                if (jugadoresRestantes > 0){
                    procesarNicknameJugador(txtEntrada.getText());
                }
                else if (esperandoInicioJuego==1) {
                    procesarInicioJuego();
                }
                else if (controlador.jugadorActual().getNickname().equals(nickname)) {
                    if (estadoMenuJuego==1) {
                        procesarMenu1(txtEntrada.getText());
                    } else if (estadoIndiceCarta==1) {
                        procesarJugadorActualDejarCarta(txtEntrada.getText());
                    } else if (estadoMenuJuego==2) {
                        procesarMenu2(txtEntrada.getText());
                    } else if (estadoSubmenuJuego==1) {
                        //procesarSubmenuJuego(txtEntrada.getText());
                    } else if (estadoIndiceCarta==2) {
                        procesarJugadorActualDejarCarta2(txtEntrada.getText());
                    }
                }else{
                    mostrarMensaje("------------------------------------------------------------");
                    mostrarMensaje("NO ES TU TURNO");
                    mostrarMensaje("------------------------------------------------------------");
                }
                txtEntrada.setText("");
            }
        });
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void mostrarMensaje(String texto) {
        txtSalidaConsola.append(texto + "\n");
    }

    @Override
    public void iniciar() {
        mostrar();
        mostrarMensaje("-----BIENVENIDO A CARIOCA-----");
        mostrarMensaje("INGRESE SU NOMBRE: ");
        jugadoresRestantes+=1;
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    private void procesarNicknameJugador(String nickname) {
        this.nickname= nickname;
        jugadoresRestantes=0;
        controlador.agregarJugadores(nickname);
        esperandoInicioJuego=1;
        if (this.nickname==controlador.getLider()){
            mostrarMensaje("INGRESE UNA TECLA PARA INICIAR LA PARTIDA: ");
        }
        else{
            mostrarMensaje("DEBES ESPERAR A QUE EL LIDER INICIE LA PARTIDA");
            this.mostrarMensaje("------------------------------------------------------------");
        }
    }

    private void procesarInicioJuego(){
        this.mostrarMensaje("------------------------------------------------------------");
        if (controlador.getJuegoIniciado()==false){
            if (this.nickname==controlador.getLider()){
                if (controlador.cantidadJugadores()>=2 && controlador.cantidadJugadores()<=4){
                    controlador.iniciarJuego();
                }
                else{
                    mostrarMensaje("LA CANTIDAD DE JUGADORES NO ES VALIDA PARA INICAR EL JUEGO");
                    this.mostrarMensaje("------------------------------------------------------------");
                }
            }
            else{
                mostrarMensaje("EL LIDER DEBE INICIAR LA PARTIDA.");
                this.mostrarMensaje("------------------------------------------------------------");
            }
        }
        else{
            mostrarMensaje("EL JUEGO YA ESTA INICIADO");
            this.mostrarMensaje("------------------------------------------------------------");
        }
    }

    public void mostrarMenuJuego() {
        if (controlador.jugadorActualSeBajo()==false){
            this.mostrarMensaje("1- RECOGER CARTA DEL MAZO");
            this.mostrarMensaje("2- RECOGER CARTA DEL POZO");
            this.mostrarMensaje("3- BAJARSE (DEBE PODER FORMAR LAS FORMAS CORRESPONDIENTES)");
            this.mostrarMensaje("INGRESE UNA OPCION: ");
            estadoMenuJuego=1;
        }
        else {
            this.mostrarMensaje("1- RECOGER CARTA DEL MAZO");
            this.mostrarMensaje("2- RECOGER CARTA DEL POZO");
            this.mostrarMensaje("3- DESHACER CARTA");
            this.mostrarMensaje("INGRESE UNA OPCION: ");
            estadoMenuJuego = 2;
        }
    }

    public void procesarJugadorActualDejarCarta(String input){
        try {
            int indiceCarta = Integer.parseInt(input);
            if ((controlador.jugadorActualSeBajo()==false) || (controlador.jugadorActualSeBajoRecien())){
                if (indiceCarta>=1 && indiceCarta<= controlador.cantidadCartasJugadorActual()){
                    controlador.jugadorActualDejarCarta(indiceCarta);
                    estadoIndiceCarta=0;
                    if (controlador.jugadorActualSeBajoRecien()){
                        controlador.setJugadorActualSeBajoRecien(false);
                    }
                    controlador.continuarRonda();
                }
                else{
                    this.mostrarMensaje("Error: Debes ingresar un número valido.");
                    this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                }
            }
            else{
                if (indiceCarta >= 1 && indiceCarta <= controlador.cantidadCartasJugadorActual()) {
                    boolean pudoBajarCarta = controlador.jugadorActualPudoBajarCarta(indiceCarta);
                    if (pudoBajarCarta == true) {
                        if (controlador.jugadorActualSinCartas()) {
                            estadoIndiceCarta = 0;
                            controlador.continuarRonda();
                        } else {
                            mostrarCartasJugador();
                            estadoIndiceCarta=0;
                            mostrarMenuJuego();
                        }
                    } else {
                        estadoIndiceCarta = 0;
                    }
                } else {
                    this.mostrarMensaje("Error: Debes ingresar un número valido.");
                    this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                }
            }
        } catch (NumberFormatException e) {
            this.mostrarMensaje("Error: Debes ingresar un número valido.");
            this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
        }
    }

    public void procesarJugadorActualDejarCarta2(String input){
        try {
            int indiceCarta = Integer.parseInt(input);
            if (indiceCarta>=1 && indiceCarta<= controlador.cantidadCartasJugadorActual()){
                controlador.jugadorActualDejarCarta(indiceCarta);
                controlador.continuarRonda();
            }
            else{
                this.mostrarMensaje("Error: Debes ingresar un número valido.");
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
            }
        } catch (NumberFormatException e) {
            this.mostrarMensaje("Error: Debes ingresar un número valido.");
            this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
        }
    }

    public void procesarMenu1(String input){
        boolean puedeBajarse;
        switch (input) {
            case "1":
                controlador.jugadorActualTomarCartaMazo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoMenuJuego=0;
                estadoIndiceCarta=1;
                break;
            case "2":
                controlador.jugadorActualTomarCartaPozo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoMenuJuego=0;
                estadoIndiceCarta=1;
                break;
            case "3":
                puedeBajarse = controlador.jugadorActualPuedeBajarse();
                if (puedeBajarse == true) {
                    if (controlador.obtenerNumeroRondaActual()!= 9 && controlador.obtenerNumeroRondaActual()!=10){
                        this.mostrarMensaje("DEBE DEJAR UNA CARTA EN EL POZO, INGRESE EL NUMERO DE CARTA A DEJAR: ");
                        controlador.setJugadorActualSeBajoRecien(true);
                        estadoMenuJuego=0;
                        estadoIndiceCarta=1;
                    }
                    else{
                        controlador.continuarRonda();
                    }
                }
                break;
            default:
                mostrarMensaje("Error: Opcion invalida");
                break;
        }
    }

    public void procesarMenu2(String input){
        switch (input){
            case "1":
                controlador.jugadorActualTomarCartaMazo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoMenuJuego=0;
                estadoIndiceCarta=2;
                break;
            case "2":
                controlador.jugadorActualTomarCartaPozo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoMenuJuego=0;
                estadoIndiceCarta=2;
                break;
            case "3":
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A BAJAR: ");
                estadoMenuJuego=0;
                estadoIndiceCarta=1;
                break;
            default:
                mostrarMensaje("Error: Opcion invalida");
                break;
        }
    }

    //public void mostrarSubmenuJuego() {
    //    estadoMenuJuego=0;
    //    estadoSubmenuJuego=1;
    //    this.mostrarMensaje("1- RECOGER CARTA DEL MAZO");
    //    this.mostrarMensaje("2- RECOGER CARTA DEL POZO");
    //    this.mostrarMensaje("INGRESE UNA OPCION: ");
    //}

    /*public void procesarSubmenuJuego(String input){
        switch (input){
            case "1":
                controlador.jugadorActualTomarCartaMazo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoSubmenuJuego=0;
                estadoIndiceCarta=2;
                break;
            case "2":
                controlador.jugadorActualTomarCartaPozo();
                this.mostrarMensaje("INGRESE EL NUMERO DE CARTA A DEJAR: ");
                estadoSubmenuJuego=0;
                estadoIndiceCarta=2;
                break;
            default:
                mostrarMensaje("Error: Opcion invalida");
                break;
        }
    }*/

    public void mostrarRondaActual(){
        this.mostrarMensaje("------------------------------------------------------------");
        this.mostrarMensaje("RONDA: " + controlador.obtenerNumeroRondaActual());
        this.mostrarMensaje("SE DEBE FORMAR: ");
        for (HashMap.Entry<Forma, Integer> entry : controlador.obtenerFormasRondaActual().entrySet()) {
            Forma forma = entry.getKey();
            int cantidad = entry.getValue();
            this.mostrarMensaje("Forma: " + forma + ", Cantidad: " + cantidad);
        }
        this.mostrarMensaje("------------------------------------------------------------");
    }

    public void mostrarCartasJugador(){
        mostrarMensaje("TUS CARTAS: ");
        ArrayList<Carta> cartas= controlador.obtenerCartasJugador(this.nickname);
        for (int i=0; i<cartas.size(); i++){
            this.mostrarMensaje((i+1) + " - " + cartas.get(i).toString());
        }
        this.mostrarMensaje("------------------------------------------------------------");
    }

    public void mostrarFormasArmadasDeCadaJugador(){
        int totalFormasArmadas= controlador.cantidadTotalFormasArmadas();
        if (totalFormasArmadas>0){
            ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
            for (int i=0; i<controlador.cantidadJugadores(); i++){
                if (controlador.formasArmadasJugador(i).size()!=0){
                    mostrarCartasBajadas(jugadores.get(i));
                }
            }
            this.mostrarMensaje("------------------------------------------------------------");
        }
    }

    public void mostrarCartasBajadas(Jugador jugador){
        this.mostrarMensaje("CARTAS BAJADAS DE: " + controlador.obtenerNickname(jugador));
        for (int i=0; i<controlador.formasArmadasJugador(jugador).size(); i++){
            mostrarForma(controlador.formasArmadasJugador(jugador).get(i));
        }
    }

    public void mostrarForma(Bajada bajada){
        if (controlador.obtenerNombreForma(bajada).equals(Forma.TRIO)){
            if (controlador.cartasQueFormanLaBajada(bajada).get(0).getValor().equals("$")){
                if (controlador.cartasQueFormanLaBajada(bajada).get(1).getValor().equals("$")){
                    this.mostrarMensaje("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(2).getValor());
                }
                else{
                    this.mostrarMensaje("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(1).getValor());
                }
            }
            else{
                this.mostrarMensaje("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor());
            }
        } else if (controlador.obtenerNombreForma(bajada).equals(Forma.ESCALA)) {
            this.mostrarMensaje("ESCALA " + controlador.cartasQueFormanLaBajada(bajada).get(0).getColor() + ", comienza con " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor() + " ,termina con " + controlador.cartasQueFormanLaBajada(bajada).get(controlador.cartasQueFormanLaBajada(bajada).size()-1).getValor());
        }
    }

    public void mostrarTurno(Jugador jugador) {
        this.mostrarMensaje("ES EL TURNO DE: " + controlador.obtenerNickname(jugador));
        this.mostrarMensaje("------------------------------------------------------------");
        mostrarFormasArmadasDeCadaJugador();
        mostrarCartasJugador();
        mostrarMazo();
        mostrarTopePozo();
        if (controlador.nicknameJugadorActual().equals(this.nickname)){
            mostrarMenuJuego();
        }
    }

    public void mostrarTopePozo(){
        String a= controlador.obtenerTopePozo();
        this.mostrarMensaje("TOPE POZO: " + a);
        this.mostrarMensaje("------------------------------------------------------------");
    }

    public void mostrarMazo(){
        this.mostrarMensaje("MAZO...");
    }

    public void mostrarJugadorTomoCartaMazo(Jugador jugador){
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarMensaje("TOMASTE UNA CARTA DEL MAZO CON EXITO!");
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje(controlador.obtenerNickname(jugador) + " TOMO UNA CARTA DEL MAZO CON EXITO!");
            this.mostrarMensaje("------------------------------------------------------------");
        }
    }

    public void mostrarJugadorDejoCarta(Jugador jugador){
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarMensaje("DEJASTE LA CARTA CON EXITO!");
        }
        else{
            this.mostrarMensaje(controlador.obtenerNickname(jugador) + " HA DEJADO LA CARTA CON EXITO!");
        }
        this.mostrarMensaje("------------------------------------------------------------");
    }

    public void mostrarJugadorTomoCartaPozo(Jugador jugador){
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarMensaje("TOMASTE UNA CARTA DEL POZO CON EXITO!");
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje(controlador.obtenerNickname(jugador) + " TOMO UNA CARTA DEL POZO CON EXITO!");
            this.mostrarMensaje("------------------------------------------------------------");
        }
    }

    public void mostrarJugadorSeBajo(Jugador jugador){
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarMensaje("TE BAJASTE CON EXITO!");
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje( controlador.obtenerNickname(jugador) + " SE HA BAJADO CON EXITO!");
            this.mostrarMensaje("------------------------------------------------------------");
        }
    }

    public void mostrarJugadorNoPudoBajarse(Jugador jugador){
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarMensaje("NO PUEDES BAJARTE");
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarMenuJuego();
        }
        else{
            this.mostrarMensaje(controlador.obtenerNickname(jugador) + " INTENTO BAJARSE Y NO PUDO");
            this.mostrarMensaje("------------------------------------------------------------");
        }
    }

    public void mostrarJugadorBajoCarta(Jugador jugador){
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarMensaje("HAS BAJADO LA CARTA CON EXITO!");
            this.mostrarMensaje("------------------------------------------------------------");
        }
        else{
            this.mostrarMensaje( controlador.obtenerNickname(jugador) + " HA BAJADO UNA CARTA CON EXITO!");
            this.mostrarMensaje("------------------------------------------------------------");
        }
    }

    public void mostrarJugadorNoPudoBajarCarta(Jugador jugador){
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            this.mostrarMensaje("------------------------------------------------------------");
            this.mostrarMensaje(" NO ES POSIBLE BAJAR LA CARTA.");
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarMenuJuego();
        }
        else{
            this.mostrarMensaje("------------------------------------------------------------");
            this.mostrarMensaje( controlador.obtenerNickname(jugador) + " INTENTO BAJAR UNA CARTA Y NO PUDO.");
            this.mostrarMensaje("------------------------------------------------------------");
        }
    }

    public void mostrarTerminoRonda(){
        txtSalidaConsola.setText("");
        this.mostrarMensaje("------------------------------------------------------------");
        Jugador j= controlador.ganadorRonda();
        this.mostrarMensaje("EL GANADOR DE LA RONDA ES: " + controlador.obtenerNickname(j));
        this.mostrarMensaje("------------------------------------------------------------");
        mostrarPuntosJugadores();
    }

    public void mostrarPuntosJugadores(){
        this.mostrarMensaje("------------------------------------------------------------");
        ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
        for (int i=0; i<jugadores.size(); i++){
            this.mostrarMensaje(controlador.obtenerNickname(jugadores.get(i)) + ", PUNTOS: " + controlador.obtenerPuntosJugador(jugadores.get(i)));
        }
        this.mostrarMensaje("------------------------------------------------------------");
    }

    public void mostrarFinJuego(){
        txtSalidaConsola.setText("");
        this.mostrarMensaje("------------------------------------------------------------");
        this.mostrarMensaje("EL JUEGO HA TERMINADO");
        Jugador j= controlador.ganadorJuego();
        this.mostrarMensaje("GANADOR: " + controlador.obtenerNickname(j));
        mostrarPuntosJugadores();
    }

    @Override
    public void mostrarInicioJuego() {
        esperandoInicioJuego=0;
        txtSalidaConsola.setText("");
        this.mostrarMensaje("------------------------------------------------------------");
        mostrarMensaje("EL JUEGO FUE INICIADO, A JUGAR!");
    }

    @Override
    public void mostrarNuevoJugadorAgregado(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            this.mostrarMensaje("------------------------------------------------------------");
            this.mostrarMensaje("INGRESASTE AL JUEGO.");
            this.mostrarMensaje("------------------------------------------------------------");
            //mostrarMenuJuego();
        }
        else{
            this.mostrarMensaje("------------------------------------------------------------");
            this.mostrarMensaje( controlador.obtenerNickname(jugador) + " HA INGRESADO AL JUEGO.");
            this.mostrarMensaje("------------------------------------------------------------");
            if (this.nickname==null){
                mostrarMensaje("INGRESE SU NOMBRE: ");
            }
            if (this.nickname==controlador.getLider()){
                mostrarMensaje("INGRESE UNA TECLA PARA INICIAR LA PARTIDA: ");
            }
        }
    }

    @Override
    public void mostrarFaltanJugadores() {
        if (nickname==controlador.getLider()){
            mostrarMensaje("NO ES POSIBLE INICAR EL JUEGO YA QUE FALTA QUE ALGUN JUGADOR INGRESE");
            this.mostrarMensaje("------------------------------------------------------------");
        }
        else{
            this.mostrarMensaje("------------------------------------------------------------");
            mostrarMensaje("EL LIDER INTENTO INICIAR EL JUEGO PERO NO FUE POSIBLE");
            this.mostrarMensaje("------------------------------------------------------------");
            if (this.nickname==null){
                mostrarMensaje("INGRESE SU NOMBRE: ");
            }
        }
    }

}
