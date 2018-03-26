package twistlock.ihm;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import twistlock.Controleur;

public class AjoutJoueur extends Application
{
    
    private Controleur controleur;
    
    /**
     * possède les éléments graphiques
     */
    private Group group = new Group( );
    
    /**
     * possède la fenêtre graphique
     */
    private Scene scene = new Scene( group , Color.LIGHTBLUE );
    
    @Override
    public void start( Stage stage )
    {
        stage.setTitle( "Twist-Lock - Ajouter des joueurs" ); // nom de la fenêtre
        
        GridPane gridPane = new GridPane( );
        gridPane.setHgap( 20 );
        gridPane.setVgap( 20 );
        gridPane.setPadding( new Insets( 20 , 20 , 20 , 20 ) );
        
        Text textNbLigne    = new Text( "Nombre de lignes " );
        Text textNbColonnes = new Text( "Nombre de colonnes" );
        textNbColonnes.setTextAlignment( TextAlignment.RIGHT );
        
        TextField textFieldNbLignes = new TextField( );
        textFieldNbLignes.textProperty( ).addListener( new ChangeListener< String >( )
        {
            @Override
            public void changed(
                    ObservableValue< ? extends String > observable , String oldValue , String newValue )
            {
                if( ! newValue.matches( "^\\d$" ) ) {
                    textFieldNbLignes.setText( newValue.replaceAll( "[^\\d?]" , "" ) );
                }
                if( textFieldNbLignes.getText( ).length( ) > 1 )
                    textFieldNbLignes.setText( textFieldNbLignes.getText( ).substring( 0 , textFieldNbLignes.getText( ).length( ) - 1 ) );
            }
        } );
        TextField textFieldNbColonnes = new TextField( );
        textFieldNbColonnes.textProperty( ).addListener( new ChangeListener< String >( )
        {
            @Override
            public void changed(
                    ObservableValue< ? extends String > observable , String oldValue , String newValue )
            {
                if( ! newValue.matches( "^\\d$" ) ) {
                    textFieldNbColonnes.setText( newValue.replaceAll( "[^\\d?]" , "" ) );
                }
                if( textFieldNbColonnes.getText( ).length( ) > 1 )
                    textFieldNbColonnes.setText( textFieldNbColonnes.getText( ).substring( 0 , textFieldNbColonnes.getText( ).length( ) - 1 ) );
            }
        } );
        
        Text textJ1 = new Text( "Nom joueur 1" );
        Text textJ2 = new Text( "Nom joueur 2" );
        Text textJ3 = new Text( "Nom joueur 3 (facultatif)" );
        Text textJ4 = new Text( "Nom joueur 4 (facultatif)" );
        
        TextField textFieldJ1 = new TextField( );
        TextField textFieldJ2 = new TextField( );
        TextField textFieldJ3 = new TextField( );
        TextField textFieldJ4 = new TextField( );
        
        Button buttonValider = new Button( "Valider" );
        buttonValider.setOnAction( new EventHandler< ActionEvent >( )
        {
            @Override
            public void handle( ActionEvent event )
            {
                boolean bOk = true;
                
                if( textFieldNbLignes.getText( ).equals( "" ) ) bOk = false;
                if( textFieldNbColonnes.getText( ).equals( "" ) ) bOk = false;
                if( textFieldJ1.getText( ).equals( "" ) ) bOk = false;
                if( textFieldJ2.getText( ).equals( "" ) ) bOk = false;
                if( textFieldJ4.getText( ).equals( "" ) && ! textFieldJ3.getText( ).equals( "" ) ) bOk = false;
                
                if( bOk ){
                    
                    controleur.ajouterJoueur( textFieldJ1.getText() );
                    controleur.ajouterJoueur( textFieldJ2.getText() );
                    if( !textFieldJ3.getText().equals( "" ) ) controleur.ajouterJoueur( textFieldJ3.getText() );
                    if( !textFieldJ4.getText().equals( "" ) ) controleur.ajouterJoueur( textFieldJ4.getText() );
                }else{
                
                }
            }
        } );
        
        gridPane.add( textNbLigne , 0 , 0 );
        gridPane.add( textFieldNbLignes , 1 , 0 );
        gridPane.add( textNbColonnes , 0 , 1 );
        gridPane.add( textFieldNbColonnes , 1 , 1 );
        gridPane.add( textJ1 , 0 , 2 );
        gridPane.add( textFieldJ1 , 1 , 2 );
        gridPane.add( textJ2 , 0 , 3 );
        gridPane.add( textFieldJ2 , 1 , 3 );
        gridPane.add( textJ3 , 0 , 4 );
        gridPane.add( textFieldJ3 , 1 , 4 );
        gridPane.add( textJ4 , 0 , 5 );
        gridPane.add( textFieldJ4 , 1 , 5 );
        gridPane.add( buttonValider , 1 , 6 );
        
        group.getChildren( ).add( gridPane );
        
        stage.setScene( scene ); // configuration des éléments graphiques
        stage.centerOnScreen( ); // centre sur l'écran
        stage.setOnCloseRequest( event -> System.exit( 0 ) ); // permet l'arrêt du programme lors du clic sur la croix de la fenêtre
        stage.show( ); // rend la fenêtre visible
    }
    
    public void setControleur( Controleur controleur )
    {
        this.controleur = controleur;
    }
}

