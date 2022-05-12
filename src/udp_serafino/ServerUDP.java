/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp_serafino;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class ServerUDP {
    public static void main(String[] args) {
        
        /* porta del server maggiore di 1024 
		 * oppure la porta 13 standard del protocollo Daytime
		 */
		int port=2000;
		
		//oggetto Socket UDP 
		DatagramSocket dSocket;
		//datagramma UDP ricevuto dal client
		DatagramPacket inputPacket;
		//datagramma UDP di risposta da inviare
		DatagramPacket outputPacket;
		//Buffer per il contenuto del segmento da ricevere
		byte[] buffer;
		//Testo dei messaggi in I/O
		String messageIn, messageOut;
		//Data e ora correnti
		Date d;
            try {
		//si crea il datagramsocket e si associa alla porta specifica
		dSocket = new DatagramSocket(port);
		System.out.println("Server pronto!");
                      
		while(true){
                    System.out.println("Server in ascolto sulla porta " + port + "!\n");
		//quanti byte conterà il nostro array
                    //buffer= array di byte
                    buffer = new byte[256];
		
                    //si crea un datagramma UDP in cui trasportare il buffer di lunghezza length
                    //area di memoria dedicata al ricevere i dati 
                    inputPacket = new DatagramPacket(buffer, buffer.length);
				
                    //si ricevono i byte dal client e si blocca finchè arrivano i pacchetti
                    dSocket.receive(inputPacket);
                    //dall'HEdear del pacchetti, riesco a  capire a chi "rispondere"
                    //si recupera l'indirizzo IP e la porta UDP del client
                    InetAddress clientAddress = inputPacket.getAddress();
                    int clientPort = inputPacket.getPort();
				
                    //si stampa a video il messaggio ricevuto dal client
                    messageIn = new String(inputPacket.getData(), 0, inputPacket.getLength());
                    System.out.println("SONO IL CLIENT " + clientAddress + 
						":" + clientPort + "> " + messageIn);
				
				//si crea un oggetto Date con la data corrente
				d = new Date();
				//si crea il messaggio del server in uscita associandolo alla connessione aperta con il client
				messageOut = d.toString();
				
				//si crea un datagramma UDP in cui trasportare il messaggio di lunghezza length
				outputPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);
				//si invia il messaggio al client
				dSocket.send(outputPacket);
				System.out.println("Risposta inviata!");
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
        
        
    }
}
