package lesson_5.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final  String name;
    private final Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public Client(Socket socket, String userName) {
        this.socket = socket;
        this.name = userName;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
            closeEverything(socket,bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (socket.isConnected()) {
                    try {
                        message = bufferedReader.readLine();
                        System.out.println(message);
                    } catch (IOException e) {
                        closeEverything(socket,bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void sendMessage() {
        Scanner scanner = new Scanner(System.in);
        try{
            bufferedWriter.write(name);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            while (socket.isConnected()) {
                String message = scanner.nextLine();
                bufferedWriter.write(name + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (IOException e) {
            closeEverything(socket,bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket socket,
                                 BufferedReader bufferedReader,
                                 BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
