package com.bham.fsd.assignments.jabberserver;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {

    ///////////////////////////////////////////// Declare variables //////////////////////////////////////////////////

    private static final int PORT_NUMBER = 44444;
    private Socket clientSocket;
    private ObjectOutputStream OOS;
    private ObjectInputStream OIS;
    private String user;
    @FXML private TextField T1;
    @FXML private Button B1;
    @FXML private Button B2;
    @FXML private Label loginLabel;
    public String text;

    ////////////////////////////////////////// End of declare variables //////////////////////////////////////////////





    ////////////////////////////////// Connect to the database/server & setter ////////////////////////////////////////

    public Controller() {
        try {
            clientSocket = new Socket("localhost", PORT_NUMBER);
            OOS = new ObjectOutputStream(clientSocket.getOutputStream());
            OIS = new ObjectInputStream(clientSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //connects to database/server, works
    public void setUser (String user)
    {
        this.user = user;
    }


    /////////////////////////////// End of Connect to the database/server & setter ///////////////////////////////////





    //////////////////////////////////////////// Button actions //////////////////////////////////////////////////////

    public void signInButton(ActionEvent event) throws SQLException
    {
        try {
            String username = T1.getText();
            OOS.writeObject(new JabberMessage("signin" + " " + username));
            OOS.flush();
            JabberMessage response = (JabberMessage) OIS.readObject();
            System.out.println(response.getMessage());
            if (response.getMessage().equals("unknown-user")) {
                loginLabel.setText("The username you've entered is invalid, please try again or register today.");
                loginLabel.setVisible(true);
            } else {
                loginLabel.setText("Welcome, " + username + ".");
                loginLabel.setVisible(true);
                timelineWindow(username);
                setUser(username);

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    // Used when the user signs in


    public void registerButton(ActionEvent event) throws SQLException //
    {
        try {
            String username = T1.getText();

            OOS.writeObject(new JabberMessage("register" + " " + username));
            OOS.flush();
            JabberMessage response = (JabberMessage) OIS.readObject();
            System.out.println(response.getMessage());
            loginLabel.setText("Your account has been succesfully created." + "Welcome, " + username + ".");
            timelineWindow(username);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Used when the user registers

    ///////////////////////////////////// End of button actions //////////////////////////////////////////////////////





    /////////////////////////////////////// Opening timeline stage ///////////////////////////////////////////////////

    public void timelineWindow(String username) throws IOException {


        try {
            //////////////////////////////////// load timeline's fxml ///////////////////////////////////////////////

            FXMLLoader loader = new FXMLLoader(getClass().getResource("timeline.fxml"));
            Stage loginStage = (Stage) B1.getScene().getWindow();
            loginStage.close();
            Parent root = loader.load();
            TimelineController control = loader.getController();

            /////////////////////////////////////////////////////////////////////////////////////////////////////////

            ///////////////////////////////////// open new stage/ window ////////////////////////////////////////////

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Timeline");
            stage.show();

            /////////////////////////////////////////////////////////////////////////////////////////////////////////

            /////////////////////////////// send timeline & not followed user to other stage/////////////////////////
            OOS.writeObject(new JabberMessage("timeline"));
            OOS.flush();
            JabberMessage timelineResponse = (JabberMessage) OIS.readObject();
            ArrayList<ArrayList<String>> data = timelineResponse.getData();

            OOS.writeObject(new JabberMessage("users"));
            OOS.flush();
            JabberMessage followResponse = (JabberMessage) OIS.readObject();
            ArrayList<ArrayList<String>> notFollowing = followResponse.getData();

            /////////////////////////////////////////////////////////////////////////////////////////////////////////

            /////////////////////////////// Send data to Timeline Controller/////////////////////////////////////////

            control.SetTimeline(data);
            control.setUser(username);
            control.followPeople(notFollowing);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////// End of opening timeline stage ///////////////////////////////////////////////


}
