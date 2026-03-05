import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Map;

public class AlojamientoAgent extends Agent {

    private Hashtable<String, Map<LocalDate, Integer>> calendarioHoteles; //la clave es el nombre del hotel y el valor es un mapa con la disponibilidad de habitaciones para cada fecha
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void setup() {
        calendarioHoteles = new Hashtable<>();
        
        // Inicializar los hoteles y su disponibilidad para el mes de mayo de 2025
        inicializarHotel("Vigo Playa Samil", 2);
        inicializarHotel("Plasencia Parador", 1);
        inicializarHotel("Madrid Plaza Colón", 2);
        inicializarHotel("Madrid Bernabeu", 1);
        inicializarHotel("Madrid Cibeles", 3);

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    if (msg.getPerformative() == ACLMessage.REQUEST) { // Si el mensaje recibido es una solicitud
                        String content = msg.getContent();
                        if (content != null) {
                            String[] detalles = content.split(";");//separamos el contenido del mensaje para manejar sus partes individualmente
                            if (detalles.length == 3) {
                                String destino = detalles[0];
                                LocalDate fechaIda = LocalDate.parse(detalles[1], formatter);
                                LocalDate fechaVuelta = LocalDate.parse(detalles[2], formatter);

                                String hotelDisponible = verificarDisponibilidad(destino, fechaIda, fechaVuelta);

                                ACLMessage reply = msg.createReply();
                                if (hotelDisponible != null) {
                                    ocuparHabitaciones(hotelDisponible, fechaIda, fechaVuelta);
                                    reply.setPerformative(ACLMessage.INFORM);
                                    reply.setContent("Reserva realizada en " + hotelDisponible + " desde " + fechaIda + " hasta " + fechaVuelta);
                                } else {
                                    reply.setPerformative(ACLMessage.FAILURE);
                                    reply.setContent("No hay disponibilidad en " + destino + " para las fechas seleccionadas.");
                                }
                                send(reply);
                            } else {
                                //System.out.println("Formato de mensaje inválido en AlojamientoAgent: " + content);
                                    }
                        } else {
                            //System.out.println("Error: el contenido del mensaje es nulo en AlojamientoAgent.");
                               }
                    } else {
                        //System.out.println("Mensaje ignorado en AlojamientoAgent:");
                            }
                } else {
                    block();
                       }
            }
        });
    }

    private void inicializarHotel(String hotel, int habitaciones) { //crea la disponibilidad de habitaciones para cada hotel en mayo de 2025
        Map<LocalDate, Integer> disponibilidad = new Hashtable<>();
        for (int i = 1; i <= 31; i++) {
            disponibilidad.put(LocalDate.of(2025, 5, i), habitaciones);
        }
        calendarioHoteles.put(hotel, disponibilidad); //guarda el mapa para el hotel
        //System.out.println("Calendario inicializado para " + hotel + ": " + disponibilidad);
    }

    private String verificarDisponibilidad(String destino, LocalDate fechaIda, LocalDate fechaVuelta) {
        for (String hotel : calendarioHoteles.keySet()) { //recorre calendarioHoteles por el valor,es decir por nombre hotel
            if (hotel.startsWith(destino)) {//si nombre del hotel coincide con el destino introducido
                Map<LocalDate, Integer> disponibilidad = calendarioHoteles.get(hotel);
                
                LocalDate fecha = fechaIda;
                boolean disponible = true;
                
                while (!fecha.isAfter(fechaVuelta)) {//verifica que hay habitaciones disponibles para cada dia entre las fechas disponibles
                    Integer habitacionesDisponibles = disponibilidad.get(fecha);
                    //System.out.println("Verificando disponibilidad en " + hotel + " para la fecha " + fecha +
                                       //": " + (habitacionesDisponibles != null ? habitacionesDisponibles : "N/A") + " habitaciones disponibles.");
                    if (habitacionesDisponibles == null || habitacionesDisponibles <= 0) {
                        disponible = false;
                        System.out.println("No hay disponibilidad en " + hotel + " para la fecha " + fecha);
                        break;
                    }
                    fecha = fecha.plusDays(1);
                }

                if (disponible) {
                    //System.out.println("Hotel disponible: " + hotel);
                    return hotel;
                }
            }
        }
        return null;
    }

    private void ocuparHabitaciones(String hotel, LocalDate fechaIda, LocalDate fechaVuelta) {//reduce la disponibilidad de habitaciones en el hotel para cada dia de entre las fechas
        Map<LocalDate, Integer> disponibilidad = calendarioHoteles.get(hotel);

        LocalDate fecha = fechaIda;
        while (!fecha.isAfter(fechaVuelta)) {
            disponibilidad.put(fecha, disponibilidad.get(fecha) - 1);//para cada fecha decrementa el numero de habitaciones disponibles
            //System.out.println("Ocupando habitación en " + hotel + " para " + fecha +". Restantes: " + disponibilidad.get(fecha));
            fecha = fecha.plusDays(1);
        }
    }
}
