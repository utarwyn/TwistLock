package twistlock;

import twistlock.ihm.IHM;
import twistlock.metier.Metier;

public class Controleur
{
    
    private Metier metier;
    
    private IHM ihm;
    
    private Controleur( )
    {
        IHM application = new IHM( );
        application.launch( IHM.class );
        application.setControleur( this );
    }
    
    public static void main( String[] args )
    {
        new Controleur( );
    }
}
