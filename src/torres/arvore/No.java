package torres.arvore;

import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import torres.Disco;
import torres.Haste;

/**
 * Um nó da árvore de recursão.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class No {
    
    private static int contId;
    private static final Color COR_FUNDO_DETALHES = ColorUtils.fade( EngineFrame.WHITE, 0.9 );
    
    private int id;
    
    public Vector2 pos;
    public Vector2 dim;
    
    // dados do nó
    private int n;
    private Haste origem;
    private Haste destino;
    private Haste auxiliar;
    
    private List<Disco> discosOrigem;
    private List<Disco> discosDestino;
    private List<Disco> discosAuxiliar;
    
    // estrutura
    private No noBase;    // base
    private No noPasso1;  // recursão
    private No noPasso2;  // recursão
    private No noPasso3;  // recursão
    private int ranque;
    
    private Tipo tipo;
    
    private String texto;
    private int larguraTexto;
    private Color cor;
    
    public No( int n, Haste origem, Haste destino, Haste auxiliar, Tipo tipo ) {
        
        this.id = contId++;
        
        this.pos = new Vector2();
        this.dim = new Vector2( 110, 20 );
        
        this.discosOrigem = new ArrayList<>();
        this.discosDestino = new ArrayList<>();
        this.discosAuxiliar = new ArrayList<>();
        
        this.cor = ColorUtils.fade( EngineFrame.BLACK, 0.7 );
        
        this.n = n;
        this.origem = origem;
        this.destino = destino;
        this.auxiliar = auxiliar;
        this.tipo = tipo;
        
    }
    
    public No( Haste origem, Haste destino, Haste auxiliar, Tipo tipo ) {
        this( 0, origem, destino, auxiliar, tipo );
    }
    
    public void desenhar( EngineFrame e ) {
        if ( texto == null ) {
            texto = toString();
            larguraTexto = e.measureText( texto, 20 );
        }
        e.fillRoundRectangle( pos.x - larguraTexto / 2 - 5, pos.y - 5, larguraTexto + 5, dim.y + 5, 10, e.WHITE );
        e.fillRoundRectangle( pos.x - larguraTexto / 2 - 5, pos.y - 5, larguraTexto + 5, dim.y + 5, 10, cor );
        e.drawText( toString(), pos.x - larguraTexto / 2, pos.y, 20, e.WHITE );
    }
    
    public boolean intercepta( int x, int y ) {
        return CollisionUtils.checkCollisionPointRectangle( x, y, new Rectangle( pos.x - larguraTexto / 2 - 5, pos.y - 5, larguraTexto + 5, dim.y + 5 ) );
    }
    
    public void desenharDetalhes( EngineFrame e ) {
        
        double x = pos.x + larguraTexto / 2 + 5;
        double y = pos.y - 5;
        double larg = 160;
        double alt = 60;
        double yBase = y + alt - 10;
        int tBarra = 4;
        double xOrigem = x + larg/2 - 50;
        double xAuxiliar = x + larg/2;
        double xDestino = x + larg/2 + 50;
        
        e.fillRoundRectangle( x, y, larg, alt, 10, COR_FUNDO_DETALHES );
        e.drawRoundRectangle( x, y, larg, alt, 10, e.GRAY );
        
        e.setStrokeLineWidth( tBarra );
        
        // assumindo que origem é a haste da esquerda, auxiliar a do meio
        // e destino a da direita
        
        e.drawLine( xOrigem, y + 10, xOrigem, yBase, e.BLACK );
        e.drawLine( xAuxiliar, y + 10, xAuxiliar, yBase, e.BLACK );
        e.drawLine( xDestino, y + 10, xDestino, yBase, e.BLACK );
        e.drawLine( x + 10, yBase, x + larg - 10, yBase, e.BLACK );
        
        desenharDiscos( e, discosOrigem, xOrigem, tBarra, yBase );
        desenharDiscos( e, discosAuxiliar, xAuxiliar, tBarra, yBase );
        desenharDiscos( e, discosDestino, xDestino, tBarra, yBase );
        
    }

    private void desenharDiscos( EngineFrame e, List<Disco> discos, double xOrigem, int tBarra, double yBase ) {
        
        int i = 1;
        int t = discos.size();
        
        for ( Disco d : discos ) {
            e.drawLine(
                xOrigem - d.getNumero() * tBarra / 2,
                yBase - ( t - i + 1 ) * tBarra,
                xOrigem + d.getNumero() * tBarra / 2,
                yBase - ( t - i + 1 ) * tBarra,
                d.getCor()
            );
            i++;
        }
        
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getRanque() {
        return ranque;
    }

    public void setRanque( int ranque ) {
        this.ranque = ranque;
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

    public int getN() {
        return n;
    }

    public void setN( int n ) {
        this.n = n;
    }

    public Haste getOrigem() {
        return origem;
    }

    public void setOrigem( Haste origem ) {
        this.origem = origem;
    }

    public Haste getDestino() {
        return destino;
    }

    public void setDestino( Haste destino ) {
        this.destino = destino;
    }

    public Haste getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar( Haste auxiliar ) {
        this.auxiliar = auxiliar;
    }

    public No getNoBase() {
        return noBase;
    }

    public void setNoBase( No noBase ) {
        this.noBase = noBase;
    }

    public No getNoPasso1() {
        return noPasso1;
    }

    public void setNoPasso1( No noPasso1 ) {
        this.noPasso1 = noPasso1;
    }

    public No getNoPasso2() {
        return noPasso2;
    }

    public void setNoPasso2( No noPasso2 ) {
        this.noPasso2 = noPasso2;
    }

    public No getNoPasso3() {
        return noPasso3;
    }

    public void setNoPasso3( No noPasso3 ) {
        this.noPasso3 = noPasso3;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo( Tipo tipo ) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto( String texto ) {
        this.texto = texto;
    }

    public int getLarguraTexto() {
        return larguraTexto;
    }

    public void setLarguraTexto( int larguraTexto ) {
        this.larguraTexto = larguraTexto;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor( Color cor ) {
        this.cor = ColorUtils.fade( cor, 0.7 );
    }
    
    private void copiar( Haste haste, List<Disco> discos ) {
        for ( Disco d : haste.getPilha() ) {
            discos.add( d );
        }
    }
    
    public void copiar( Haste origem, Haste auxiliar, Haste destino ) {
        copiar( origem, discosOrigem );
        copiar( auxiliar, discosAuxiliar );
        copiar( destino, discosDestino );
    }
    
    @Override
    public String toString() {
        if ( tipo == Tipo.MOVIMENTO ) {
            return String.format( "m(%s,%s)", origem.getNome(), destino.getNome() );
        }
        return String.format( "r(%d,%s,%s,%s)", n, origem.getNome(), destino.getNome(), auxiliar.getNome() );
    }
        
}
