import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class CorteInglesAgent extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive(); // Recibe mensajes de UsuarioAgent o AlojamientoAgent
                if (msg != null) {
                    if (msg.getPerformative() == ACLMessage.REQUEST) { // Solicitud de UsuarioAgent
                        // Reenvía la solicitud a AlojamientoAgent
                        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                        request.addReceiver(getAID("AlojamientoAgent"));
                        request.setContent(msg.getContent());//guarda en el contenido del mensaje lo que nos llega de Usuario
                        request.setConversationId(msg.getConversationId()); // Asocia el ID de la conversación de esta manera asegura
                        													//que todos los mensajes estan enlazados
                        request.setReplyWith(msg.getReplyWith()); // obtiene el identificador de respuesta del mensaje recibido
                        send(request);

                    } else if (msg.getPerformative() == ACLMessage.INFORM || msg.getPerformative() == ACLMessage.FAILURE) {
                        // Respuesta de AlojamientoAgent (confirmación o fallo)
                        ACLMessage reply = new ACLMessage(msg.getPerformative());
                        reply.addReceiver(getAID("UsuarioAgent"));
                        reply.setContent(msg.getContent()); // Pasa el contenido de la respuesta de AlojamientoAgent
                        send(reply);
                    }
                } else {
                    block();
                }
            }
        });
    }
}
