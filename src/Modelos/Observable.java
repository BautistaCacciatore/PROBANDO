package Modelos;

import Controladores.Controlador;

public interface Observable {
    void addObserver(Controlador controlador);
    void deleteObserver(Controlador controlador);
    void notificar(Evento evento, Jugador jugador);

    void notificar(Evento evento);
}
