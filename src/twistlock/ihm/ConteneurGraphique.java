package twistlock.ihm;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ConteneurGraphique
{
    private String stringValeur;
    
    private StackPane stackPane;
    
    private int largeur, hauteur;
    
    private Rectangle rectangle;
    
    public ConteneurGraphique(String stringValeur)
    {
        this.stringValeur = stringValeur;
        
            largeur   = Integer.MAX_VALUE;
            hauteur   = Integer.MAX_VALUE;
        double rayonCoin = - 10;
        int    marge     = 1;
        Color  couleurAngle1, couleurAngle2, couleurAngle3, couleurAngle4;
        Color  couleurFond = Color.GRAY;
        Color  couleurBord;
        
        stackPane = new StackPane( );
        stackPane.setPrefSize( largeur , hauteur );
    
        rectangle = new Rectangle();
        rectangle.setWidth( stackPane.getWidth() );
        rectangle.setHeight( stackPane.getHeight() );
        rectangle.setFill(Color.GREEN);
        rectangle.setStroke(Color.DARKGREEN);
        rectangle.setStrokeWidth(5);
        rectangle.setArcHeight(30);
        rectangle.setArcWidth(30);
        rectangle.setAccessibleText( this.stringValeur );
        stackPane.getChildren().add( rectangle );
    }
    
    public StackPane getStackPane( )
    {
        return stackPane;
    }
    
    public void setStringValeur( String stringValeur )
    {
        this.stringValeur = stringValeur;
    }
    
    public void setLargeur( int largeur )
    {
        this.largeur = largeur;
        rectangle.setWidth( largeur );
    }
    
    public void setHauteur( int hauteur )
    {
        this.hauteur = hauteur;
        rectangle.setHeight( hauteur );
    }
}
