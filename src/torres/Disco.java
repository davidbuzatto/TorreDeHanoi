package torres;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Representação de um disco do jogo Torres de Hanoi.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Disco {
    
    private int id;
    private Vector2 pos;
    private Vector2 dim;
    private Color cor;

    public Disco( int id, double x, double y, double largura, double altura, Color cor ) {
        this.id = id;
        this.pos = new Vector2( x, y );
        this.dim = new Vector2( largura, altura );
        this.cor = cor;
    }

    public void draw( EngineFrame e ) {
        e.fillRectangle( pos.x - dim.x / 2, pos.y - dim.y, dim.x, dim.y, cor );
        e.drawText( String.valueOf( id ), pos.x - 5, pos.y - 15, 20, e.BLACK );
    }
    
    public Vector2 getPos() {
        return pos;
    }

    public void setPos( Vector2 pos ) {
        this.pos = pos;
    }

    public Vector2 getDim() {
        return dim;
    }

    public void setDim( Vector2 dim ) {
        this.dim = dim;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor( Color cor ) {
        this.cor = cor;
    }
    
}
