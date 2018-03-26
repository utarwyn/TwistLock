package twistlock;

import javafx.application.Application;
import twistlock.ihm.IHM;
import twistlock.metier.Metier;

public class Controleur
{
    
    private Metier metier;
    
    private IHM ihm;
    
    public Controleur( )
    {
        Application.launch( IHM.class );
    }
    
    public static void main( String[] args )
    {
        new Controleur( );
    }
}
