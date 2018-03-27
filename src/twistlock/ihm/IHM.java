package twistlock.ihm;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import twistlock.Controleur;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class IHM extends Application
{
    
    private Controleur controleur;
    
    /**
     * possède les éléments graphiques
     */
    private Group group = new Group( );
    
    /**
     * possède la fenêtre graphique
     */
    private Scene scene = new Scene( group , 1200 , 800 , Color.LIGHTBLUE );
    
    private BorderPane borderPaneJeu;
    
    private ScrollPane scrollPanePlateau;
    
    private StackPane stackPaneRouge, stackPaneVert, stackPaneBleu, stackPaneJaune;
    
    private ArrayList< Circle > circlesTwistLocks;
    
    @Override
    public void start( Stage stage ) throws Exception
    {
        stage.setTitle( "Twist-Lock" ); // nom de la fenêtre
        
        for( int i = 0 ; i < 20 ; i++ ) {
            Circle c = new Circle( 1 );
            circlesTwistLocks.add( c );
        }
        
        borderPaneJeu = new BorderPane( );
        
        stackPaneRouge = new StackPane( );
        
        Image    image              = new Image( "img/boueeRouge.jpg" );
        Text     textNom            = new Text( );
        GridPane gridPaneTwistLocks = new GridPane( );
        
        //        stackPaneRouge.getChildren().addAll( image, textNom, gridPaneTwistLocks )
        
        borderPaneJeu.setCenter( scrollPanePlateau );
        
        stage.setScene( scene ); // configuration des éléments graphiques
        stage.centerOnScreen( ); // centre sur l'écran
        stage.setOnCloseRequest( event -> System.exit( 0 ) ); // permet l'arrêt du programme lors du clic sur la croix de la fenêtre
        stage.show( ); // rend la fenêtre visible
        
        // permet la mise à jour des éléments graphiques
        new Timer( ).scheduleAtFixedRate( new TimerTask( )
        {
            public void run( )
            {
            
            }
        } , 100 , 100 ); // raffraichissement de la fenêtre chaque dixième de seconde
    }
    
    public void setControleur( Controleur controleur )
    {
        this.controleur = controleur;
    }
}
