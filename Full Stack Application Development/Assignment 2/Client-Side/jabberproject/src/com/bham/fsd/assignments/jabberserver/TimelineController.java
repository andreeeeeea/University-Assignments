package com.bham.fsd.assignments.jabberserver;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.*;

public class TimelineController implements Initializable {


    ///////////////////////////////////////////// Declare variables //////////////////////////////////////////////////

    private static final int PORT_NUMBER = 44444;
    private Socket clientSocket;
    private ObjectOutputStream OOS;
    private ObjectInputStream OIS;
    @FXML private VBox P2;
    @FXML private ListView P3;
    @FXML TextField T2;
    private String user;

    ////////////////////////////////////////// End of declare variables //////////////////////////////////////////////





    ///////////////////////////////////////// Connect to the database/server /////////////////////////////////////////

    public TimelineController() {

        try {
            clientSocket = new Socket("localhost", PORT_NUMBER);
            OOS = new ObjectOutputStream(clientSocket.getOutputStream());
            OIS = new ObjectInputStream(clientSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////// End of connect to the database/server //////////////////////////////////////





    //////////////////////////////////////////// Setter & Initialize /////////////////////////////////////////////////

    public void setUser(String user)
    {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //TO-DO
    }

    ///////////////////////////////////////// End of Setter & Initialize /////////////////////////////////////////////





    /////////////////////////////// Setting the timeline & follow suggestions ////////////////////////////////////////

    public void SetTimeline(ArrayList<ArrayList<String>> follow)
    {
        for(int i=0; i<follow.size(); i++)
        {
            String sb = follow.get(i).get(0) +" : " + follow.get(i).get(1);  //get the username + post
            Label post = new Label(sb);
            P2.setSpacing(5);
            Button B4 = new Button(); //add a button
            B4.setPrefHeight(5);
            B4.setPrefSize(60, 0);
            B4.setText("Like Jab"); //set the button's text to "Like"
            int finalI = i;
            Label likes = new Label(String.valueOf(follow.get(i).get(3))); //set the label's text with the number of likes
            likes.setFont(new Font("System", 15));

            ////////////////////////////// Whenever the likes button is clicked //////////////////////////
            B4.setOnAction(ActionEvent ->
            {
                likeButtonAction(follow.get(finalI).get(2));
                int OGLikes = Integer.parseInt(follow.get(finalI).get(3));
                int updatedLikes = OGLikes + 1;
                String strUpdatedLikes = String.valueOf(updatedLikes);
                follow.get(finalI).get(3).replaceAll(follow.get(finalI).get(3), strUpdatedLikes);

                ////////////////// For updating the number of likes label //////////////////
                Timeline TL = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent)
                                    {
                                        likes.setText(strUpdatedLikes);
                                    }
                                }
                        ),
                        new KeyFrame(Duration.seconds(2))
                );
                TL .play();
                /////////////////////// end of updating the number of likes label ///////////////////////////////////
            });
            ///////////////////////// end of whenever the likes button is clicked /////////////////////////

            ///////////////////////////////open new stage/ window ///////////////////////////////



            ///////////////////// Set the username, text, like button and number of likes in the listview /////////////////////
            //P2.getChildren().add(post);
            //P2.getChildren().add(B4);
            //P2.getChildren().add(likes);
            HBox hbox = new HBox(post);
            hbox.setPadding(new Insets(0, 0, 0, 5));


            P2.getChildren().add(hbox);

            HBox hbox2 = new HBox(B4, likes);
            hbox2.setSpacing(10);

            P2.getChildren().add(hbox2);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        }
    }
    // Sets the timeline for the user
    // code to update the label
    // taken from https://stackoverflow.com/questions/44060204/javafx-label-will-not-continuously-update



    public void followPeople(ArrayList<ArrayList<String>> notFollowing) //works
    {
        for(int i=0; i<notFollowing.size(); i++)
        {
            String nF = notFollowing.get(i).get(0);
            P3.getItems().add(nF);
            Button B5 = new Button();
            B5.setText("Follow");
            B5.setPrefHeight(5);
            int finalI = i;
            B5.setOnAction(ActionEvent ->
            {
                followButtonAction(notFollowing.get(finalI).get(0));
                B5.setText("User has been followed!");
            });
            P3.getItems().add(B5);
        }
    }
    // Sets the follow suggestions for the user

    /////////////////////////////// End of setting the timeline & follow suggestions /////////////////////////////////





    //////////////////////////////////////////// Button actions //////////////////////////////////////////////////////

   public void followButtonAction(String userNotFollowed)
   {
       try {
           OOS.writeObject(new JabberMessage("signin" + " " + user));
           OOS.flush();
           OOS.writeObject(new JabberMessage("follow" + " " + userNotFollowed));
           OOS.flush();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   // Used in followPeople

    public void likeButtonAction (String post)
    {
        try {
            OOS.writeObject(new JabberMessage("signin" + " " + user));
            OOS.flush();
            OOS.writeObject(new JabberMessage("like" + " " + post));
            OOS.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Used in SetTimeline whenever the like button is clicked


    public void enterButtonOnAction(ActionEvent event) throws IOException {

        OOS.writeObject(new JabberMessage("signin" + " " + user));
        OOS.flush();
        OOS.writeObject(new JabberMessage("post" + " " + T2.getText()));
        OOS.flush();
        String text = user + " : " + T2.getText();
        Label textL = new Label(text);
        P2.getChildren().add(textL);
        T2.clear();
    }
    // Used when user wants to post something


    public void signoutButton(ActionEvent event) throws IOException {

        OOS.writeObject(new JabberMessage("signout"));
        OOS.flush();
        clientSocket.close();
        Platform.exit();
        System.exit(0);

    }
    // Used when user signs out

    ///////////////////////////////////// End of button actions //////////////////////////////////////////////////////

}
