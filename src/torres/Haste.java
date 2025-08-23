package torres;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.interfaces.Stack;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class Haste {
    
    private Vector2 pos;
    private Vector2 dim;
    private Color cor;
    private Stack<Disco> discos;

    public Haste( double x, double y, double largura, double altura, Color cor ) {
        this.pos = new Vector2( x, y );
        this.dim = new Vector2( largura, altura );
        this.cor = cor;
        this.discos = new ResizingArrayStack<>();
    }

    public void update() {
        
        int atual = discos.getSize() - 1;
        double y = pos.y;
        
        for ( Disco d : discos ) {
            d.getPos().x = pos.x;
            d.getPos().y = y - d.getDim().y * atual;
            atual--;
        }
        
    }
    
    public void draw( EngineFrame e ) {
        e.fillRectangle( pos.x - dim.x / 2, pos.y - dim.y, dim.x, dim.y, cor );
        for ( Disco d : discos ) {
            d.draw( e );
        }
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

    public Stack<Disco> getDiscos() {
        return discos;
    }

    public void setDiscos( Stack<Disco> discos ) {
        this.discos = discos;
    }
    
}
