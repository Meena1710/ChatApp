import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ChatServer extends Frame implements Runnable,ActionListener{
    TextField textField;
    TextArea textArea;
    Button send;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;
    ChatServer(){
        textField=new TextField();
        textArea=new TextArea();
        send=new Button("Send");
        send.addActionListener(this);
        try {
            serverSocket = new ServerSocket(1000);
            socket = serverSocket.accept();

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception e){

        }

        add(textField);
        add(textArea);
        add(send);

        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();


        setSize(500,500);
        setTitle("Server");
        setLayout(new FlowLayout());
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        String msg=textField.getText();
        textArea.append("ChatServer : "+msg+"\n");
        textField.setText("");
        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        }
        catch(IOException e1){

        }
    }

    public static void main(String[] args) {
        new ChatServer();
    }
    public void run(){
        while(true){
            try{
                String msg=dataInputStream.readUTF();
                textArea.append("ChatClient : "+msg+"\n");
            }
            catch(Exception e){

            }
        }
    }



}
