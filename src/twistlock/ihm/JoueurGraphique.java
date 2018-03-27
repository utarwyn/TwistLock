package twistlock.ihm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class JoueurGraphique
{
    private String[] URL_IMAGES = { "./twistlock/ihm/img/boueeRouge.jpg" , "./twistlock/ihm/img/boueeVerte.jpg" ,
                                    "./twistlock/ihm/img/boueeBleue.jpg" , "./twistlock/ihm/img/boueeJaune.jpg" };
    
    private int                 id;
    private String              nom;
    private int                 score;
    private Text                textScore;
    private ArrayList< Circle > circleArrayList;
    private HBox                hBox;
    private VBox                vBox;
    
    public JoueurGraphique( int id , String nom )
    {
        this.id = id;
        this.nom = nom;
        this.score = 0;
        
        circleArrayList = new ArrayList<>( );
        textScore = new Text( "0" );
        
        for( int i = 0 ; i < 20 ; i++ ) {
            Circle c = new Circle( 1 );
            c.setRadius( 4.5 );
            circleArrayList.add( c );
        }
    }
    
    public String getURL_IMAGES( )
    {
        return URL_IMAGES[ id ];
    }
    
    public int getId( )
    {
        return id;
    }
    
    public String getNom( )
    {
        return nom;
    }
    
    private GridPane colorTwistLock( int i )
    {
        GridPane gridPane = new GridPane( );
        gridPane.setHgap( 4 );
        gridPane.setVgap( 4 );
        for( int j = 0 ; j < 4 ; j++ ) {
            for( int k = 0 ; k < 5 ; k++ ) {
                if( 5 * j + k < i ) circleArrayList.get( 5 * j + k ).setFill( Color.RED );
                else circleArrayList.get( 5 * j + k ).setFill( Color.TRANSPARENT );
                gridPane.add( circleArrayList.get( 5 * j + k ) , k , j );
            }
        }
        return gridPane;
    }
    
    public VBox stackPaneVertical( )
    {
        ImageView image = new ImageView( URL_IMAGES[ id ] );
        image.setFitWidth( 100 );
        Text textNom = new Text( nom );
        textNom.setTextAlignment( TextAlignment.CENTER );
        GridPane gridPaneTwistLocks = colorTwistLock( 20 );
        
        vBox = new VBox( );
        vBox.setSpacing( 10 );
        vBox.setAlignment( Pos.CENTER );
        vBox.setFillWidth( true );
        vBox.getChildren( ).addAll( image , textNom , gridPaneTwistLocks );
        return vBox;
    }
    
    public HBox stackPaneHorizontal( )
    {
        ImageView image = new ImageView( URL_IMAGES[ id ] );
        image.setFitHeight( 100 );
        Text textNom = new Text( nom );
        textNom.setTextAlignment( TextAlignment.CENTER );
        GridPane gridPaneTwistLocks = colorTwistLock( 20 );
        
        hBox = new HBox( );
        hBox.setSpacing( 10 );
        hBox.setAlignment( Pos.CENTER );
        hBox.setFillHeight( true );
        hBox.getChildren( ).addAll( image , textNom ,textScore, gridPaneTwistLocks );
        return hBox;
    }
    
    public HBox gethBox( )
    {
        return hBox;
    }
    
    public VBox getvBox( )
    {
        return vBox;
    }
    
    public void setScore( int score )
    {
        this.score = score;
    }
}
