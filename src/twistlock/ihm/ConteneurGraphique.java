package twistlock.ihm;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ConteneurGraphique
{
    private String stringValeur;
    
    private StackPane stackPane;
    
    public ConteneurGraphique(String stringValeur)
    {
        this.stringValeur = stringValeur;
        
        int    largeur   = 300;
        int    hauteur   = 300;
        double rayonCoin = - 10;
        int    marge     = 1;
        Color  couleurAngle1, couleurAngle2, couleurAngle3, couleurAngle4;
        Color  couleurFond = Color.GRAY;
        Color  couleurBord;
        
        stackPane = new StackPane( );
        stackPane.setPrefSize( largeur , hauteur );
        
        Rectangle rectangle = new Rectangle( );
        rectangle.setStroke( couleurFond );
        rectangle.setArcHeight( rayonCoin );
        rectangle.setArcWidth( rayonCoin );
        
        Text textNumero = new Text( this.stringValeur );
        textNumero.setX( rectangle.getWidth( ) / 2 );
        textNumero.setY( rectangle.getArcHeight( ) / 2 );
        
        stackPane.getChildren( ).addAll( rectangle , textNumero );
    }
    
    public StackPane getStackPane( )
    {
        return stackPane;
    }
    
    public void setStringValeur( String stringValeur )
    {
        this.stringValeur = stringValeur;
    }
}
