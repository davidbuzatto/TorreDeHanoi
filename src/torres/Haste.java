package torres;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.interfaces.Stack;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 * Representação de uma haste do jogo Torres de Hanói.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Haste {
    
    private String nome;
    private Vector2 pos;
    private Vector2 dim;
    private Color cor;
    private Stack<Disco> pilha;

    public Haste( String nome, double x, double y, double largura, double altura, Color cor ) {
        this.nome = nome;
        this.pos = new Vector2( x, y );
        this.dim = new Vector2( largura, altura );
        this.cor = cor;
        this.pilha = new ResizingArrayStack<>();
    }
    
    public Haste( double x, double y, double largura, double altura, Color cor ) {
        this( "", x, y, largura, altura, cor );
    }

    // atualiza a posição dos discos de acordo com suas posições na pilha
    public void atualizar() {
        
        int atual = pilha.getSize() - 1;
        double y = pos.y;
        
        for ( Disco d : pilha ) {
            d.getPos().x = pos.x;
            d.getPos().y = y - d.getDim().y * atual;
            atual--;
        }
        
    }
    
    public void desenhar( EngineFrame e ) {
        e.fillRectangle( pos.x - dim.x / 2, pos.y - dim.y, dim.x, dim.y, cor );
        for ( Disco d : pilha ) {
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

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public void empilhar( Disco d ) {
        pilha.push( d );
    }
    
    public Disco desempilhar() {
        return pilha.pop();
    }
    
    public Disco verTopo() {
        return pilha.peek();
    }
    
    public boolean estaVazia() {
        return pilha.isEmpty();
    }
    
    public int getTamanho() {
        return pilha.getSize();
    }
    
    public void limpar() {
        pilha.clear();
    }
    
    public Stack<Disco> getPilha() {
        return pilha;
    }
    
}
