package torres.arvore;

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import torres.Haste;

/**
 * Um nó da árvore de recursão.
 * Precisa ser refatorado!
 * 
 * @author Prof. Dr. David Buzatto
 */
public class No {
    
    private static int contId;
    private int id;
    public int ranque;
    
    public Vector2 pos;
    public Vector2 dim;
    
    // dados do nó
    private int n;
    private Haste origem;
    private Haste destino;
    private Haste auxiliar;
    
    // estrutura
    public No noBase;    // base
    public No noPasso1;  // recursão
    public No noPasso2;  // recursão
    public No noPasso3;  // recursão
    
    public Tipo tipo;
    
    private String texto;
    private int larguraTexto;
    
    public Color cor;
    
    private No() {
        this.id = contId++;
        this.pos = new Vector2();
        this.dim = new Vector2( 110, 20 );
        this.cor = EngineFrame.BLACK;
    }
    
    public No( Haste origem, Haste destino ) {
        this();
        this.origem = origem;
        this.destino = destino;
        this.tipo = Tipo.MOVIMENTO;
    }
    
    public No( int n, Haste origem, Haste destino, Haste auxiliar ) {
        this();
        this.n = n;
        this.origem = origem;
        this.destino = destino;
        this.auxiliar = auxiliar;
        this.tipo = Tipo.RESOLUCAO;
    }
    
    public void desenhar( EngineFrame e ) {
        if ( texto == null ) {
            texto = toString();
            larguraTexto = e.measureText( texto, 20 );
        }
        e.fillRoundRectangle( pos.x - larguraTexto / 2 - 5, pos.y - 5, larguraTexto + 5, dim.y + 5, 10, cor );
        e.drawText( toString(), pos.x - larguraTexto / 2, pos.y, 20, e.WHITE );
    }
    
    public boolean intercepta( int x, int y ) {
        return CollisionUtils.checkCollisionPointRectangle( x, y, new Rectangle( pos.x - larguraTexto / 2 - 5, pos.y - 5, larguraTexto + 5, dim.y + 5 ) );
    }

    public int getRanque() {
        return ranque;
    }

    @Override
    public String toString() {
        if ( tipo == Tipo.MOVIMENTO ) {
            return String.format( "m(%s,%s)", origem.getNome(), destino.getNome() );
        }
        return String.format( "r(%d,%s,%s,%s)", n, origem.getNome(), destino.getNome(), auxiliar.getNome() );
    }
        
}
