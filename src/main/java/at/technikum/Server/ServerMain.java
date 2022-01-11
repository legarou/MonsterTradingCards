package at.technikum.Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {

        final ExecutorService executorService = Executors.newFixedThreadPool(10);

        try(ServerSocket serverSocket = new ServerSocket(10001, 5)) {
            while(true) {
                final Socket clientConnection = serverSocket.accept();

                final SocketHandler socketHandler = new SocketHandler(clientConnection);
                executorService.submit(socketHandler);
            }
        }

    }
}
