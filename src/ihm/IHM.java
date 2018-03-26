package ihm;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class IHM extends Application
{
    
    /**
     * possède les éléments graphiques
     */
    private Group group = new Group( );
    
    /**
     * possède la fenêtre graphique
     */
    private Scene scene = new Scene( group , 800 , 800 , Color.LIGHTBLUE );
    
    public IHM( )
    {
    
    }
    
    @Override
    public void start( Stage primaryStage ) throws Exception
    {
    
    }
    
    public static void main( String[] args )
    {
        Application.launch( IHM.class , args );
    }
}
