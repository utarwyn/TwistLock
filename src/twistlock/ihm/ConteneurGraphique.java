package twistlock.ihm;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ConteneurGraphique
{
    private String stringValeur;
    
    public ConteneurGraphique( )
    {
        int   largeur     = 10;
        int   hauteur     = 8;
        int   rayonCoin   = 2;
        int   marge       = 1;
        Color couleurAngle1, couleurAngle2, couleurAngle3, couleurAngle4;
        Color couleurFond;
        Color couleurBord;
        
        
        
        StackPane stackPane = new StackPane( );
        stackPane.setPrefSize( largeur , hauteur );
        
        
        Line haut, droite, bas, gauche;
        
        haut = new Line( 4 , 2 , largeur - 4 , 2 );
        droite = new Line( 2 , 2 , largeur - 4 , 2 );
        bas = new Line( 4 , 2 , largeur - 4 , 2 );
        gauche = new Line( 4 , 2 , largeur - 4 , 2 );
    
        Text textNumero = new Text( );
        
        stackPane.getChildren().addAll( haut, droite, bas, gauche );
    }
}
