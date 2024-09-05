import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ChatClient extends Frame implements Runnable,ActionListener{
    TextField textField;
    TextArea textArea;
    Button send;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;
    ChatClient(){
        textField=new TextField();
        textArea=new TextArea();
        send=new Button("Send");
        send.addActionListener(this);
        try {
            socket = new Socket("localhost",1000);

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
        setTitle("Client");
        setLayout(new FlowLayout());
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        String msg=textField.getText();
        textArea.append("ChatClient : "+msg+"\n");
        textField.setText("");
        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        }
        catch(IOException e1){

        }
    }

    public static void main(String[] args) {
          new ChatClient();
    }
    public void run(){
        while(true){
            try{
                String msg=dataInputStream.readUTF();
                textArea.append("ChatServer : "+msg+"\n");
            }
            catch(Exception e){

            }
        }
    }



}
