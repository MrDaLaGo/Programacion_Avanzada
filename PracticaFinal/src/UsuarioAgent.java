import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UsuarioAgent extends Agent {

    @Override
    protected void setup() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.print("¿Desea hacer un viaje? (si/no): ");
                String respuesta = scanner.nextLine();

                if (respuesta.equalsIgnoreCase("si")) {
                    System.out.print("Destino: ");
                    String destino = scanner.nextLine();
                    //Transformamos la primera letra a mayúsculas siempre para poder ser interpretado asi mas tarde
                    destino = destino.substring(0, 1).toUpperCase() + destino.substring(1).toLowerCase();
                    
                    LocalDate fechaIda;
                    LocalDate fechaVuelta;
                while(true) {
                    System.out.print("Fecha Ida (dd/MM/yyyy): ");
                    String fechaIdaStr = scanner.nextLine();
                    fechaIda = LocalDate.parse(fechaIdaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    
                    if(fechaIda.isAfter(LocalDate.of(2025,5,30))) {
                    	System.out.print("La fecha de ida ingresada no puede ser superior al 30/05/2025\n");
                    	continue;
                    }else if(fechaIda.isBefore(LocalDate.of(2025,5,1))){
                    	System.out.println("La fecha de ida no puede ser inferior al 01/05/2025\n");
                    	continue;
                    }
                    	
                    
                    System.out.print("Fecha Vuelta (dd/MM/yyyy): ");
                    String fechaVueltaStr = scanner.nextLine();
                    fechaVuelta = LocalDate.parse(fechaVueltaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    
                    if(fechaVuelta.isBefore(LocalDate.of(2025,5,2))) {
                    	System.out.print("La fecha de vuelta ingresada no puede ser inferior al 02/05/2025\n");
                    	continue;
                    }else if(fechaVuelta.isAfter(LocalDate.of(2025,5,31))){
                    	  System.out.println("La fecha de vuelta no puede ser posterior al 31/05/2025\n");
                    	  continue;
                      }
                if(fechaIda.equals(fechaVuelta)){
                	System.out.print("Ambas fechas no pueden ser la misma\n");
                	continue;
                  }
                break;
                }
           ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);//creamos un mensaje de tipo request para enviar a CorteIngles
                    msg.addReceiver(getAID("CorteInglesAgent"));
                    msg.setContent(destino + ";" + fechaIda.format(formatter) + ";" + fechaVuelta.format(formatter));
                    //System.out.println("Mensaje enviado a CorteInglesAgent: " + msg.getContent());
                    send(msg);

                    System.out.println("Reserva solicitada para " + destino + " desde " + fechaIda + " hasta " + fechaVuelta);

                    // Espera la respuesta de confirmación o fallo
                    ACLMessage respuestaReserva = blockingReceive();
                    if (respuestaReserva != null) {
                        if (respuestaReserva.getPerformative() == ACLMessage.INFORM) {
                            System.out.println("Reserva confirmada: " + respuestaReserva.getContent());
                        } else if (respuestaReserva.getPerformative() == ACLMessage.FAILURE) {
                            System.out.println("No hay disponibilidad para las fechas y destino solicitados.");
                        }
                    }else {
                    	System.out.println("No llegó ningun mensaje de respuesta");
                          }
                } else if (respuesta.equalsIgnoreCase("no")) {
                    System.out.println("Gracias por usar el sistema de reservas. Saliendo...");
                    doDelete();
                } else {
                    System.out.println("Por favor, responda con 'si' o 'no'.");
                }
            }
        });

    }
}