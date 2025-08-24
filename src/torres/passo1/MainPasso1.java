package torres.passo1;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.math.Vector2;
import torres.Disco;
import torres.Haste;

/**
 * Simulador do Jogo Torres de Hanói.
 * 
 * Material complementar da disciplina de estruturas de dados para ensino de
 * recursão e aplicação de pilhas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class MainPasso1 extends EngineFrame {
    
    /**
     * Implementar movimentação de discos e botões.
     */
    
    private Haste h1;
    private Haste h2;
    private Haste h3;
    private int quantidadeDiscos;
    
    public MainPasso1() {
        super( 800, 450, "Jogo Torres de Hanói", 60, true );
    }
    
    @Override
    public void create() {
        
        int margem = 50;
        Vector2 dim = new Vector2( 20, 200 );
        Vector2 centro = new Vector2( getScreenWidth() / 2, getScreenHeight() - margem );
        
        int espacamento = 250;
        
        h1 = new Haste( centro.x - espacamento, centro.y, dim.x, dim.y, BLACK );
        h2 = new Haste( centro.x, centro.y, dim.x, dim.y, BLACK );
        h3 = new Haste( centro.x + espacamento, centro.y, dim.x, dim.y, BLACK );
        
        preparar();
        
    }
    
    @Override
    public void update( double delta ) {
        
        h1.atualizar();
        h2.atualizar();
        h3.atualizar();
        
        if ( isMouseButtonDown( MOUSE_BUTTON_LEFT ) ) {
            moverDisco( h2, h1 );
        }
        
        if ( isMouseButtonDown( MOUSE_BUTTON_RIGHT ) ) {
            moverDisco( h2, h3 );
        }
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        h1.desenhar( this );
        h2.desenhar( this );
        h3.desenhar( this );
        
        fillRectangle( 50, h1.getPos().y, getScreenWidth() - 100, 20, BLACK );
        
    }
    
    private void preparar() {
        
        quantidadeDiscos = 9;
        
        h1.limpar();
        h2.limpar();
        h3.limpar();
        
        int ini = 2;
        int fim = quantidadeDiscos + ini - 1;
        
        for ( int i = fim; i >= ini; i-- ) {
            double porc = ( (double) ( fim - i ) / quantidadeDiscos );            
            h2.empilhar( 
                new Disco( 
                    i - ini + 1,
                    0, 0, i * 20, 20, 
                    ColorUtils.colorFromHSV( porc * 360, 1, 1 )
                )
            );
        }
        
        h1.atualizar();
        h2.atualizar();
        h3.atualizar();
        
    }
    
    /**
     * Move um disco da haste de origem para a haste de destino.
     * 
     * A haste de origem não pode estar vazia e o disco que vai ser movido
     * não pode ter diâmetro (largura do retângulo) maior que o que está no
     * topo da haste de destino. Se a hasta de destino não possuir discos,
     * qualquer um pode ser movido para a mesma.
     */
    private void moverDisco( Haste origem, Haste destino ) {
        
    }
    
    public static void main( String[] args ) {
        new MainPasso1();
    }
    
}
