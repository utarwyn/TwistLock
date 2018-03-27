package twistlock;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import twistlock.ihm.ConteneurGraphique;
import twistlock.ihm.Fenetre;
import twistlock.ihm.JoueurGraphique;
import twistlock.metier.Conteneur;
import twistlock.metier.Joueur;
import twistlock.metier.Metier;
import twistlock.metier.TwistLock;

import java.util.ArrayList;
import java.util.Optional;

public class Controleur extends Application
{
    
    private Metier metier;
    
    private Fenetre fenetre;
    
    /**
     * possède les éléments graphiques
     */
    private Group group = new Group( );
    
    /**
     * possède la fenêtre graphique
     */
    private Scene scene = new Scene( group , Color.LIGHTBLUE );
    
    private Stage stage;
    
    private TextField textFieldNbLignes, textFieldNbColonnes, textFieldJ1, textFieldJ2, textFieldJ3, textFieldJ4;
    
    private int lignes, colonnes;
    
    private int nbJoueurs;
    
    private ArrayList< String > nomJoueurs;
    
    private BorderPane borderPaneJeu;
    
    private StackPane stackPanePlateau;
    
    private StackPane stackPaneRouge, stackPaneVert, stackPaneBleu, stackPaneJaune;
    
    private ArrayList< Circle > circlesTwistLocks;
    
    private ArrayList< ConteneurGraphique > conteneurGraphiqueArrayList;
    
    public static void main( String[] args )
    {
        Application.launch( Controleur.class );
    }
    
    @Override
    public void start( Stage stage )
    {
        this.stage = stage;
        ajoutJoueursGraphique( );
    }
    
    private void ajoutJoueursGraphique( )
    {
        stage.setTitle( "Twist-Lock - Ajouter des joueurs" ); // nom de la fenêtre
        
        GridPane gridPane = new GridPane( );
        gridPane.setHgap( 20 );
        gridPane.setVgap( 20 );
        gridPane.setPadding( new Insets( 20 , 20 , 20 , 20 ) );
        
        Text textNbLigne    = new Text( "Nombre de lignes" );
        Text textNbColonnes = new Text( "Nombre de colonnes" );
        textNbColonnes.setTextAlignment( TextAlignment.RIGHT );
        
        textFieldNbLignes = new TextField( "1" );
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
        textFieldNbColonnes = new TextField( "2" );
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
        
        textFieldJ1 = new TextField( "3" );
        textFieldJ2 = new TextField( "4" );
        textFieldJ3 = new TextField( "5" );
        textFieldJ4 = new TextField( "6" );
        
        Button buttonValider = new Button( "Valider" );
        buttonValider.setOnAction( new EventHandler< ActionEvent >( )
        {
            @Override
            public void handle( ActionEvent event )
            {
                
                if( verification( ) ) {
                    
                    if( ! textFieldJ3.getText( ).equals( "" ) ) {
                        nbJoueurs = 3;
                    }
                    if( ! textFieldJ4.getText( ).equals( "" ) ) {
                        nbJoueurs = 4;
                    }
                    
                    lignes = Integer.parseInt( textFieldNbLignes.getText( ) );
                    colonnes = Integer.parseInt( textFieldNbColonnes.getText( ) );
                    
                    nomJoueurs = new ArrayList<>( );
                    nomJoueurs.add( textFieldJ1.getText( ) );
                    nomJoueurs.add( textFieldJ2.getText( ) );
                    if( nbJoueurs > 2 ) nomJoueurs.add( textFieldJ3.getText( ) );
                    if( nbJoueurs > 3 ) nomJoueurs.add( textFieldJ4.getText( ) );
                    
                    chargerMetier( lignes , colonnes );
                    for( int i = 0 ; i < nbJoueurs ; i++ ) {
                        ajouterJoueur( nomJoueurs.get( i ) );
                    }
                    group.getChildren( ).remove( 0 );
                    plateauDeJeuGraphique( );
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
    
    private void plateauDeJeuGraphique( )
    {
        group = new Group( );
        scene = new Scene( group , 1200 , 800 , Color.LIGHTBLUE );
        stage.setTitle( "Twist-Lock" ); // nom de la fenêtre
        
        circlesTwistLocks = new ArrayList<>( );
        
        for( int i = 0 ; i < 20 ; i++ ) {
            Circle c = new Circle( 1 );
            circlesTwistLocks.add( c );
        }
        
        borderPaneJeu = new BorderPane( );
        
        ArrayList< JoueurGraphique > joueurGraphiqueArrayList = new ArrayList<>( );
        
        for( int i = 0 ; i < nbJoueurs ; i++ ) {
            joueurGraphiqueArrayList.add( new JoueurGraphique( i , nomJoueurs.get( i ) ) );
        }
        
        borderPaneJeu.setLeft( joueurGraphiqueArrayList.get( 0 ).stackPaneVertical( ) );
        borderPaneJeu.setRight( joueurGraphiqueArrayList.get( 1 ).stackPaneVertical( ) );
        borderPaneJeu.setTop( joueurGraphiqueArrayList.get( 2 ).stackPaneHorizontal( ) );
        borderPaneJeu.setBottom( joueurGraphiqueArrayList.get( 3 ).stackPaneHorizontal( ) );
        
        stackPanePlateau = new StackPane( );
        stackPanePlateau.setPrefSize( scene.getWidth( ) - 200 , scene.getHeight( ) - 200 );
        
        conteneurGraphiqueArrayList = new ArrayList<>( );
        for( int i = 0 ; i < lignes ; i++ ) {
            for( int j = 0 ; j < colonnes ; j++ ) {
                ConteneurGraphique conteneurGraphique = new ConteneurGraphique( "test" ,
                                                                                lignes * 40 , colonnes * 50 );
                conteneurGraphiqueArrayList.add( conteneurGraphique );
                stackPanePlateau.getChildren( ).add( conteneurGraphique.getStackPane() );
            }
        }
        
        borderPaneJeu.setCenter( stackPanePlateau );
        
        group.getChildren( ).add( borderPaneJeu );
        
        stage.setScene( scene ); // configuration des éléments graphiques
        stage.centerOnScreen( ); // centre sur l'écran
        stage.setOnCloseRequest( event -> System.exit( 0 ) ); // permet l'arrêt du programme lors du clic sur la croix de la fenêtre
        stage.show( ); // rend la fenêtre visible
    }
    
    private boolean verification( )
    {
        boolean bOk = true;
        
        if( textFieldNbLignes.getText( ).equals( "" ) ) {
            bOk = false;
            erreur( "Saisisser un nombre de lignes entre 1 et 9" );
        }
        if( textFieldNbColonnes.getText( ).equals( "" ) ) {
            bOk = false;
            erreur( "Saisisser un nombre de colonnes entre 1 et 9" );
        }
        if( textFieldJ1.getText( ).equals( "" ) ) {
            bOk = false;
            erreur( "Saisisser le nom du joueur 1" );
        }
        if( textFieldJ2.getText( ).equals( "" ) ) {
            bOk = false;
            erreur( "Saisisser le nom du joueur 2" );
        }
        if( ! textFieldJ4.getText( ).equals( "" ) && textFieldJ3.getText( ).equals( "" ) ) {
            bOk = false;
            erreur( "Saisissez le nom du joueur 3" );
        }
        
        if( textFieldJ1.getText( ).equals( textFieldJ2.getText( ) ) || textFieldJ1.getText( ).equals( textFieldJ3.getText( ) ) ||
            textFieldJ1.getText( ).equals( textFieldJ4.getText( ) ) || textFieldJ2.getText( ).equals( textFieldJ3.getText( ) ) ||
            textFieldJ2.getText( ).equals( textFieldJ4.getText( ) ) ||
            ! textFieldJ3.getText( ).equals( "" ) && textFieldJ3.getText( ).equals( textFieldJ4.getText( ) ) )
        {
            bOk = false;
            erreur( "Noms de joueurs identiques" );
        }
        
        return bOk;
    }
    
    public void erreur( String message )
    {
        Alert alert = new Alert( Alert.AlertType.ERROR );
        alert.setTitle( "Erreur" );
        alert.setHeaderText( message );
        final Optional< ButtonType > result = alert.showAndWait( );
    }
    
    /* ----------------------------- */
    /*  GESTION DE LA GRILLE DE JEU  */
    /* ----------------------------- */
    
    public void chargerMetier( int nbLignes , int nbColonnes )
    {
        this.metier = new Metier( nbLignes , nbColonnes );
    }
    
    public int getNbLig( )
    {
        return this.metier.getNbLig( );
    }
    
    public int getNbCol( )
    {
        return this.metier.getNbCol( );
    }
    
    /* ------------------------ */
    /*  GESTION DES TWISTLOCKS  */
    /* ------------------------ */
    
    public Conteneur getConteneur( int lig , int col )
    {
        return this.metier.getConteneur( lig , col );
    }
    
    public TwistLock[][] getTwistLocks( )
    {
        return this.metier.getTwistLocks( );
    }
    
    public boolean isTwistlock( int lig , int col )
    {
        return this.metier.isTwistlock( lig , col );
    }
    
    /* --------------------- */
    /*  GESTION DES JOUEURS  */
    /* --------------------- */
    
    public boolean jouerTwistlock( int lig , int col , Joueur joueur )
    {
        return this.metier.jouerTwistlock( lig , col , joueur );
    }
    
    public void ajouterJoueur( String nom )
    {
        this.metier.ajouterJoueur( nom );
    }
    
    public ArrayList< Joueur > getJoueurs( )
    {
        return this.metier.getJoueurs( );
    }
    
    public boolean nouveauTour( )
    {
        return this.metier.nouveauTour( );
    }
}
