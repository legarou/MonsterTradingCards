package at.technikum.Server;


import at.technikum.Server.QueryHandler.QueryHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class SocketHandler extends Thread {

    private final Socket clientConnection;
    private final BufferedReader bufferedReader;
    private final ResponseHandler responseHandler;
    private final HeaderReader headerReader = new HeaderReader();
    private final ObjectMapper objectMapper = new ObjectMapper();
    ConcurrentHashMap<String, BattleRoom> concurrentMap;

    public SocketHandler(Socket clientConnection, ConcurrentHashMap<String, BattleRoom> concurrentMap) throws IOException {
        this.clientConnection = clientConnection;
        bufferedReader = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
        responseHandler = new ResponseHandler(new BufferedWriter(new OutputStreamWriter(clientConnection.getOutputStream())));
        this.concurrentMap = concurrentMap;
    }

    @Override
    public void run()  {
        try {
            final String httpMethodWithPath = bufferedReader.readLine();
            if((httpMethodWithPath == null) || httpMethodWithPath.isEmpty())
                return;
            System.out.println(httpMethodWithPath);

            while (bufferedReader.ready()) {
                final String input = bufferedReader.readLine();
                if ("".equals(input)) {
                    break;
                }
                headerReader.ingest(input);
            }

            headerReader.print();
            System.out.println("In thread: " + Thread.currentThread().getName());

            char[] charBuffer = new char[headerReader.getContentLength()];
            if (headerReader.getContentLength() > 0) {
                bufferedReader.read(charBuffer, 0, headerReader.getContentLength());
            }
            QueryHandler queryHandler = new QueryHandler(httpMethodWithPath, new String(charBuffer), headerReader.getHeader("Authorization"), concurrentMap);
            queryHandler.processQuery();
            responseHandler.reply(queryHandler.getResponseObject());
            responseHandler.reply();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
