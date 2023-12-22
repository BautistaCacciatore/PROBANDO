package Vistas.interfazGrafica;

import Controladores.Controlador;
import Enumerados.Forma;
import Modelos.Bajada;
import Modelos.Carta;
import Modelos.Jugador;
import Vistas.IVista;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class VistaJugador implements IVista {
    private final JFrame frame;
    private JPanel contentPane;

    private JTabbedPane tabbedPane1;
    private JPanel ingresar;
    private JPanel jugar;
    private JLabel label;
    private JTextField ingresoNickname;
    private JButton enterBtn;
    private JLabel labelBienvenida;

    private JButton pozoBtn;
    private JButton bajarseBtn;
    private JButton dejarBtn;
    private JTextArea cartas;
    private JTextArea notificaciones;
    private JButton bajarBtn;
    private JButton mazoBtn;
    private JSpinner spinner1;
    private JTextArea datosRonda;
    private JButton iniciarPartidaBtn;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;

    private String nickname;
    private Controlador controlador;
    private int tomoCartaMazo;
    private int tomoCartaPozo;
    private int dejoCarta;
    private int seBajo;
    private int  bajoCarta;

    public VistaJugador() {
        //paneles
        contentPane.setBackground(new Color(255, 255, 224)); // Color crema o amarillo claro
        ingresar.setBackground(new Color(255, 255, 224));
        jugar.setBackground(new Color(255, 255, 224));
        Border bordeCompuesto = new CompoundBorder(
                new MatteBorder(10, 10, 10, 10, new Color(200, 255, 224)), // Borde interno
                new LineBorder(new Color(70, 130, 180), 5, true) // Borde externo
        );
        ingresar.setBorder(bordeCompuesto);
        jugar.setBorder(bordeCompuesto);

        //frame
        frame = new JFrame("JUGADOR");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 400);
        frame.setLocationRelativeTo(null); // Centrar en la pantalla

        //label
        //Border borde= BorderFactory.createLineBorder(Color.BLACK, 10, true);
        //label.setBorder(borde);

        //textAreas
        cartas.setBackground(new Color(255, 228, 225));
        cartas.setForeground(new Color(100, 0,0));
        datosRonda.setBackground(new Color(255, 228, 225));
        datosRonda.setForeground(new Color(100, 0,0));
        notificaciones.setBackground(new Color(255, 228, 225));
        notificaciones.setForeground(new Color(100, 0,0));

        //botones
        enterBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        enterBtn.setForeground(Color.WHITE);
        enterBtn.setFont(new Font("Arial", Font.BOLD, 16));

        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarIngresoNickname();
            }
        });


        iniciarPartidaBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        iniciarPartidaBtn.setForeground(Color.WHITE);
        iniciarPartidaBtn.setFont(new Font("Arial", Font.BOLD, 16));

        iniciarPartidaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname!= null){
                    if (controlador.getJuegoIniciado()==false){
                        if (nickname==controlador.getLider()){
                            if (controlador.cantidadJugadores()>=2 && controlador.cantidadJugadores()<=4){
                                controlador.iniciarJuego();
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "La cantidad de jugadores no es valida para iniciar el juego.");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "El lider debe iniciar la partida.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "El juego ya esta iniciado.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Primero debes ingresar tu nickname.");
                }
            }
        });


        //
        mazoBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        mazoBtn.setForeground(Color.WHITE);
        mazoBtn.setFont(new Font("Arial", Font.BOLD, 16));
        mazoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nickname primero.");
                } else if (controlador.getJuegoIniciado()) {
                    if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                        if (tomoCartaMazo==0 && tomoCartaPozo==0 && seBajo==0 && dejoCarta==0 && bajoCarta==0){
                            controlador.jugadorActualTomarCartaMazo();
                            tomoCartaMazo=1;
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "No puedes tomar una carta.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No es tu turno.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes esperar a que el juego inicie.");
                }
            }
        });

        pozoBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        pozoBtn.setForeground(Color.WHITE);
        pozoBtn.setFont(new Font("Arial", Font.BOLD, 16));
        pozoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nickname primero.");
                } else if (controlador.getJuegoIniciado()) {
                    if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                        if (tomoCartaMazo==0 && tomoCartaPozo==0 && seBajo==0 && dejoCarta==0 && bajoCarta==0){
                            controlador.jugadorActualTomarCartaPozo();
                            tomoCartaPozo=1;
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "No puedes tomar una carta.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No es tu turno.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes esperar a que el juego inicie.");
                }
            }
        });

        bajarseBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        bajarseBtn.setForeground(Color.WHITE);
        bajarseBtn.setFont(new Font("Arial", Font.BOLD, 16));
        bajarseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nickname primero.");
                } else if (controlador.getJuegoIniciado()) {
                    if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                        if (controlador.jugadorActualSeBajo()==false){
                            if (tomoCartaMazo == 1 || tomoCartaPozo == 1) {
                                JOptionPane.showMessageDialog(null, "No puede bajarse, primero debe dejar una carta.");
                            } else {
                                boolean puedeBajarse = controlador.jugadorActualPuedeBajarse();
                                if (puedeBajarse == true) {
                                    if (controlador.obtenerNumeroRondaActual() != 9 && controlador.obtenerNumeroRondaActual() != 10) {
                                        JOptionPane.showMessageDialog(null, "Se ha bajado con exito, ahora debe dejar una carta.");
                                        controlador.setJugadorActualSeBajoRecien(true);
                                        seBajo = 1;
                                    } else {
                                        controlador.continuarRonda();
                                    }
                                }
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Ya te has bajado.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No es tu turno.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes esperar a que el juego inicie.");
                }
            }
        });

        dejarBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        dejarBtn.setForeground(Color.WHITE);
        dejarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        dejarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname == null) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nickname primero.");
                } else if (controlador.getJuegoIniciado()) {
                    if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                        if (tomoCartaMazo == 1 || tomoCartaPozo == 1 || seBajo == 1) {
                            Integer indiceCarta = (Integer) spinner1.getValue();
                            if (indiceCarta >= 1 && indiceCarta <= controlador.cantidadCartasJugadorActual()) {
                                controlador.jugadorActualDejarCarta(indiceCarta);
                                dejoCarta = 1;
                                if (controlador.jugadorActualSeBajoRecien()) {
                                    controlador.setJugadorActualSeBajoRecien(false);
                                }
                                controlador.continuarRonda();
                            } else {
                                JOptionPane.showMessageDialog(null, "Debe ingresar un numero valido.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Debe tomar una carta primero.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No es tu turno.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes esperar a que el juego inicie.");
                }
            }
        });

        bajarBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        bajarBtn.setForeground(Color.WHITE);
        bajarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        bajarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nickname primero.");
                } else if (controlador.getJuegoIniciado()) {
                    if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                        if (controlador.jugadorActualSeBajo()){
                            if (tomoCartaPozo==0 && tomoCartaMazo==0 && dejoCarta==0){
                                if (!controlador.jugadorActualSeBajoRecien()){
                                    Integer indiceCarta = (Integer) spinner1.getValue();
                                    if (indiceCarta >= 1 && indiceCarta <= controlador.cantidadCartasJugadorActual()) {
                                        boolean pudoBajarCarta = controlador.jugadorActualPudoBajarCarta(indiceCarta);
                                        if (pudoBajarCarta == true) {
                                            if (controlador.jugadorActualSinCartas()) {
                                                controlador.continuarRonda();
                                            } else {
                                                mostrarCartasJugador();
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, "La carta no pudo ser bajada.");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Debes ingresar un numero valido.");
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Te bajaste recien, en tu proximo turno podras bajar cartas.");
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Tomaste una carta, debes dejar una.");
                            }
                        } else{
                            JOptionPane.showMessageDialog(null, "Primero debes bajarte.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No es tu turno.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes esperar a que el juego inicie.");
                }
            }
        });
    }

    public void procesarIngresoNickname(){
        if (this.nickname==null){
            this.nickname= ingresoNickname.getText();
            controlador.agregarJugadores(nickname);
            JOptionPane.showMessageDialog(null, "Bienvenido " + nickname );
            if (!controlador.getJuegoIniciado()){
                //mostrarMensaje2("-------------------------------------------------------------------");
                mostrarMensaje2("- ESPERANDO A QUE EL JUEGO INICIE...");
                //mostrarMensaje2("-------------------------------------------------------------------");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Ya has ingresado tu nickname.");
        }
    }

    @Override
    public void setControlador(Controlador controlador){
        this.controlador= controlador;
    }

    @Override
    public void iniciar() {
        frame.setVisible(true);
    }

    @Override
    public void mostrarMensaje(String texto) {
        cartas.append(texto + "\n");
    }

    public void mostrarMensaje2(String texto){
        notificaciones.append(texto + "\n");
    }

    public void mostrarMensaje3(String texto){
        datosRonda.append(texto + "\n");
    }

    @Override
    public void mostrarRondaActual() {
        notificaciones.setText("");
        this.mostrarMensaje3("------------------------------------------------------------");
        this.mostrarMensaje3("RONDA: " + controlador.obtenerNumeroRondaActual());
        this.mostrarMensaje3("SE DEBE FORMAR: ");
        for (HashMap.Entry<Forma, Integer> entry : controlador.obtenerFormasRondaActual().entrySet()) {
            Forma forma = entry.getKey();
            int cantidad = entry.getValue();
            this.mostrarMensaje3("Forma: " + forma + ", Cantidad: " + cantidad);
        }
        this.mostrarMensaje3("------------------------------------------------------------");
    }

    public void mostrarRonda() {
        this.mostrarMensaje3("------------------------------------------------------------");
        this.mostrarMensaje3("RONDA: " + controlador.obtenerNumeroRondaActual());
        this.mostrarMensaje3("SE DEBE FORMAR: ");
        for (HashMap.Entry<Forma, Integer> entry : controlador.obtenerFormasRondaActual().entrySet()) {
            Forma forma = entry.getKey();
            int cantidad = entry.getValue();
            this.mostrarMensaje3("Forma: " + forma + ", Cantidad: " + cantidad);
        }
        this.mostrarMensaje3("------------------------------------------------------------");
    }

    @Override
    public void mostrarFormasArmadasDeCadaJugador() {
        int totalFormasArmadas= controlador.cantidadTotalFormasArmadas();
        if (totalFormasArmadas>0){
            ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
            for (int i=0; i<controlador.cantidadJugadores(); i++){
                if (controlador.formasArmadasJugador(i).size()!=0){
                    mostrarCartasBajadas(jugadores.get(i));
                }
            }
            this.mostrarMensaje3("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarTurno(Jugador jugador) {
        tomoCartaPozo=0;
        tomoCartaMazo=0;
        seBajo=0;
        dejoCarta=0;
        bajoCarta=0;
        datosRonda.setText("");
        mostrarRonda();
        this.mostrarMensaje3("ES EL TURNO DE: " + controlador.obtenerNickname(jugador));
        this.mostrarMensaje3("------------------------------------------------------------");
        mostrarFormasArmadasDeCadaJugador();
        mostrarCartasJugador();
        mostrarMazo();
        mostrarTopePozo();
    }

    @Override
    public void mostrarCartasBajadas(Jugador jugador) {
        this.mostrarMensaje3("CARTAS BAJADAS DE: " + controlador.obtenerNickname(jugador));
        for (int i=0; i<controlador.formasArmadasJugador(jugador).size(); i++){
            mostrarForma(controlador.formasArmadasJugador(jugador).get(i));
        }
    }

    @Override
    public void mostrarForma(Bajada bajada) {
        if (controlador.obtenerNombreForma(bajada).equals(Forma.TRIO)){
            if (controlador.cartasQueFormanLaBajada(bajada).get(0).getValor().equals("$")){
                if (controlador.cartasQueFormanLaBajada(bajada).get(1).getValor().equals("$")){
                    this.mostrarMensaje3("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(2).getValor());
                }
                else{
                    this.mostrarMensaje3("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(1).getValor());
                }
            }
            else{
                this.mostrarMensaje3("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor());
            }
        } else if (controlador.obtenerNombreForma(bajada).equals(Forma.ESCALA)) {
            this.mostrarMensaje3("ESCALA " + controlador.cartasQueFormanLaBajada(bajada).get(0).getColor() + ", comienza con " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor() + " ,termina con " + controlador.cartasQueFormanLaBajada(bajada).get(controlador.cartasQueFormanLaBajada(bajada).size()-1).getValor());
        }
    }

    @Override
    public void mostrarCartasJugador() {
        cartas.setText("");
        ArrayList<Carta> cartas= controlador.obtenerCartasJugador(this.nickname);
        for (int i=0; i<cartas.size(); i++){
            this.mostrarMensaje((i+1) + " - " + cartas.get(i).toString());
        }
    }

    @Override
    public void mostrarTopePozo() {
        String a= controlador.obtenerTopePozo();
        this.mostrarMensaje3("TOPE POZO:");
        this.mostrarMensaje3(a);
        this.mostrarMensaje3("------------------------------------------------------------");
    }

    @Override
    public void mostrarMazo() {
        this.mostrarMensaje3("MAZO...");
    }

    @Override
    public void mostrarJugadorTomoCartaPozo(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("- TOMASTE UNA CARTA DEL POZO CON EXITO!");
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje2("- " + controlador.obtenerNickname(jugador) + " TOMO UNA CARTA DEL POZO CON EXITO!");
            //this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarJugadorTomoCartaMazo(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("- TOMASTE UNA CARTA DEL MAZO CON EXITO!");
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje2("- " + controlador.obtenerNickname(jugador) + " TOMO UNA CARTA DEL MAZO CON EXITO!");
            //this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarJugadorSeBajo(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("- TE BAJASTE CON EXITO!");
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje2("- " +  controlador.obtenerNickname(jugador) + " SE HA BAJADO CON EXITO!");
            //this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarJugadorNoPudoBajarse(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            //JOptionPane.showMessageDialog(null, "No puedes bajarte.");
            this.mostrarMensaje2("- NO PUEDES BAJARTE ");
        }
        else{
            this.mostrarMensaje2("- " + controlador.obtenerNickname(jugador) + " INTENTO BAJARSE Y NO PUDO");
            //this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarJugadorDejoCarta(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("- DEJASTE LA CARTA CON EXITO!");
        }
        else{
            this.mostrarMensaje2("- " + controlador.obtenerNickname(jugador) + " HA DEJADO LA CARTA CON EXITO!");
        }
        //this.mostrarMensaje2("------------------------------------------------------------");
    }

    @Override
    public void mostrarJugadorBajoCarta(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("- HAS BAJADO LA CARTA CON EXITO!");
            //this.mostrarMensaje2("------------------------------------------------------------");
        }
        else{
            this.mostrarMensaje2( "- " + controlador.obtenerNickname(jugador) + " HA BAJADO UNA CARTA CON EXITO!");
            //this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarJugadorNoPudoBajarCarta(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("- INTENTASTE BAJAR UNA CARTA Y NO PUDISTE");
            //this.mostrarMensaje2("------------------------------------------------------------");
        }
        else{
            //this.mostrarMensaje2("------------------------------------------------------------");
            this.mostrarMensaje2( "- " + controlador.obtenerNickname(jugador) + " INTENTO BAJAR UNA CARTA Y NO PUDO.");
            //this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarTerminoRonda() {
        //this.mostrarMensaje("------------------------------------------------------------");
        Jugador j= controlador.ganadorRonda();
        JOptionPane.showMessageDialog(null, "EL GANADOR DE LA RONDA ES: " + controlador.obtenerNickname(j));
        //this.mostrarMensaje("EL GANADOR DE LA RONDA ES: " + controlador.obtenerNickname(j));
        //this.mostrarMensaje("------------------------------------------------------------");
        mostrarPuntosJugadores();
    }

    @Override
    public void mostrarPuntosJugadores() {
        //this.mostrarMensaje("------------------------------------------------------------");
        ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
        StringBuilder mensaje = new StringBuilder("Puntos de los jugadores:\n");
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            mensaje.append(controlador.obtenerNickname(jugador))
                    .append(", PUNTOS: ")
                    .append(controlador.obtenerPuntosJugador(jugador))
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, mensaje.toString());
        //for (int i=0; i<jugadores.size(); i++){
        //    this.mostrarMensaje(controlador.obtenerNickname(jugadores.get(i)) + ", PUNTOS: " + controlador.obtenerPuntosJugador(jugadores.get(i)));
        //}
        //this.mostrarMensaje("------------------------------------------------------------");
    }

    @Override
    public void mostrarFinJuego() {
        //this.mostrarMensaje("");
        //this.mostrarMensaje("------------------------------------------------------------");
        //this.mostrarMensaje("EL JUEGO HA TERMINADO");
        Jugador j= controlador.ganadorJuego();
        //this.mostrarMensaje("GANADOR: " + controlador.obtenerNickname(j));
        //this.mostrarMensaje("------------------------------------------------------------");

        // Construye la cadena con los mensajes
        StringBuilder mensajeFinJuego = new StringBuilder("EL JUEGO HA TERMINADO\n");
        mensajeFinJuego.append("GANADOR: ").append(controlador.obtenerNickname(j));

        JOptionPane.showMessageDialog(null, mensajeFinJuego.toString());
        mostrarPuntosJugadores();
    }

    @Override
    public void mostrarInicioJuego() {
        //mostrarMensaje2("---------------------------------------------------------- ");
        mostrarMensaje2("- EL JUEGO FUE INICIADO, A JUGAR!");
        //mostrarMensaje2("----------------------------------------------------------");
    }

    @Override
    public void mostrarNuevoJugadorAgregado(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("- INGRESASTE AL JUEGO");
            //this.mostrarMensaje2("------------------------------------------------------------");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje2("- " + controlador.obtenerNickname(jugador) + " HA INGRESADO AL JUEGO");
            //this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarFaltanJugadores() {
        if (nickname==controlador.getLider()){
            mostrarMensaje2("- NO ES POSIBLE INICAR EL JUEGO YA QUE FALTA QUE ALGUN JUGADOR INGRESE");
        }
        else{
            mostrarMensaje2("- EL LIDER INTENTO INICIAR EL JUEGO PERO NO FUE POSIBLE");
        }
    }
}
