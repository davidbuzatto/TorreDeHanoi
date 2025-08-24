package torres.passo3;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.interfaces.Stack;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.imgui.GuiLabel;
import br.com.davidbuzatto.jsge.imgui.GuiTextField;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.util.ArrayList;
import java.util.List;
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
public class MainPasso3 extends EngineFrame {
    
    /**
     * Implementar a solução recursiva.
     */
    
    private static final int QUANTIDADE_DISCOS = 9;
    
    private Haste h1;
    private Haste h2;
    private Haste h3;
    private int quantidadeDiscos;
    
    private Stack<AcaoHaste> acoesDesfazer;
    private Stack<AcaoHaste> acoesRefazer;
    
    private GuiButton btnDesfazer;
    private GuiButton btnRefazer;
    private GuiButton btnReiniciar;
    private GuiButton btnResolver;
    
    private GuiLabel lblQuantidade;
    private GuiTextField txtQuantidade;
    
    private GuiButton btn12;
    private GuiButton btn13;
    private GuiButton btn21;
    private GuiButton btn23;
    private GuiButton btn31;
    private GuiButton btn32;
    
    private List<GuiComponent> componentes;
    
    public MainPasso3() {
        super( 800, 450, "Jogo Torres de Hanói", 60, true );
    }
    
    @Override
    public void create() {
        
        useAsDependencyForIMGUI();
        
        int margem = 50;
        Vector2 dim = new Vector2( 20, 200 );
        Vector2 centro = new Vector2( getScreenWidth() / 2, getScreenHeight() - margem );
        
        int espacamento = 250;
        
        h1 = new Haste( centro.x - espacamento, centro.y, dim.x, dim.y, BLACK );
        h2 = new Haste( centro.x, centro.y, dim.x, dim.y, BLACK );
        h3 = new Haste( centro.x + espacamento, centro.y, dim.x, dim.y, BLACK );
        
        acoesDesfazer = new ResizingArrayStack<>();
        acoesRefazer = new ResizingArrayStack<>();
        
        componentes = new ArrayList<>();
        btnDesfazer = new GuiButton( h2.getPos().x - 175, 20, 80, 20, "desfazer" );
        btnRefazer = new GuiButton( h2.getPos().x - 85, 20, 80, 20, "refazer" );
        btnReiniciar = new GuiButton( h2.getPos().x + 5, 20, 80, 20, "reiniciar" );
        btnResolver = new GuiButton( h2.getPos().x + 95, 20, 80, 20, "resolver" );
        
        lblQuantidade = new GuiLabel( btnReiniciar.getBounds().x + 2, btnReiniciar.getBounds().y + 25, 45, 20, "Quant:" );
        txtQuantidade = new GuiTextField( lblQuantidade.getBounds().x + lblQuantidade.getBounds().width, btnReiniciar.getBounds().y + 25, 30, 20, String.valueOf( QUANTIDADE_DISCOS ) );
    
        btn12 = new GuiButton( h1.getPos().x - 25, 90, 50, 20, "-> 2" );
        btn13 = new GuiButton( h1.getPos().x - 25, 120, 50, 20, "-> 3" );
        btn21 = new GuiButton( h2.getPos().x - 25, 90, 50, 20, "1 <-" );
        btn23 = new GuiButton( h2.getPos().x - 25, 120, 50, 20, "-> 3" );
        btn31 = new GuiButton( h3.getPos().x - 25, 90, 50, 20, "1 <-" );
        btn32 = new GuiButton( h3.getPos().x - 25, 120, 50, 20, "2 <-" );
        
        componentes.add( btnDesfazer );
        componentes.add( btnRefazer );
        componentes.add( btnReiniciar );
        componentes.add( btnResolver );
        componentes.add( lblQuantidade );
        componentes.add( txtQuantidade );
        
        componentes.add( btn12 );
        componentes.add( btn13 );
        componentes.add( btn21 );
        componentes.add( btn23 );
        componentes.add( btn31 );
        componentes.add( btn32 );
        
        preparar();
        
    }
    
    @Override
    public void update( double delta ) {
        
        for ( GuiComponent c : componentes ) {
            c.update( delta );
        }
        
        if ( btnDesfazer.isMousePressed() ) {
            desfazer();
        }
        
        if ( btnRefazer.isMousePressed() ) {
            refazer();
        }
        
        if ( btnReiniciar.isMousePressed() ) {
            preparar();
        }
        
        if ( btnResolver.isMousePressed() ) {
            resolver();
        }
        
        if ( btn12.isMousePressed() ) {
            moverDisco( h1, h2, true );
            acoesRefazer.clear();
        }
        
        if ( btn13.isMousePressed() ) {
            moverDisco( h1, h3, true );
            acoesRefazer.clear();
        }
        
        if ( btn21.isMousePressed() ) {
            moverDisco( h2, h1, true );
            acoesRefazer.clear();
        }
        
        if ( btn23.isMousePressed() ) {
            moverDisco( h2, h3, true );
            acoesRefazer.clear();
        }
        
        if ( btn31.isMousePressed() ) {
            moverDisco( h3, h1, true );
            acoesRefazer.clear();
        }
        
        if ( btn32.isMousePressed() ) {
            moverDisco( h3, h2, true );
            acoesRefazer.clear();
        }
        
        h1.atualizar();
        h2.atualizar();
        h3.atualizar();
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );

        for ( GuiComponent c : componentes ) {
            c.draw();
            //drawRectangle( c.getBounds(), BLUE );
        }
        
        h1.desenhar( this );
        h2.desenhar( this );
        h3.desenhar( this );
        
        fillRectangle( 50, h1.getPos().y, getScreenWidth() - 100, 20, BLACK );
        
    }
    
    private void preparar() {
        
        try {
            quantidadeDiscos = Integer.parseInt( txtQuantidade.getValue() );
            if ( quantidadeDiscos <= 0 ) {
                throw new NumberFormatException( "negativo" );
            }
        } catch ( NumberFormatException exc ) {
            txtQuantidade.setValue( String.valueOf( QUANTIDADE_DISCOS ) );
            quantidadeDiscos = QUANTIDADE_DISCOS;
        }
        
        h1.limpar();
        h2.limpar();
        h3.limpar();
        
        acoesDesfazer.clear();
        acoesRefazer.clear();
        
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
    
    private void moverDisco( Haste origem, Haste destino, boolean salvarDesfazer ) {
        if ( !origem.estaVazia() ) {
            if ( destino.estaVazia() ) {
                destino.empilhar( origem.desempilhar() );
                if ( salvarDesfazer ) {
                    acoesDesfazer.push( new AcaoHaste( origem, destino ) );
                }
            } else if ( destino.verTopo().getDim().x > origem.verTopo().getDim().x ) {
                destino.empilhar( origem.desempilhar() );
                if ( salvarDesfazer ) {
                    acoesDesfazer.push( new AcaoHaste( origem, destino ) );
                }
            }
        }
    }
    
    private void resolver() {
        preparar();
        resolver( h2.getTamanho(), h2, h3, h1 );
    }
    
    /**
     * Implementação do algoritmo recursivo para a solução do problema das
     * Torres de Hanói.
     * 
     * Move o maior disco (n) da haste de origem para a haste de destino,
     * usando a haste auxiliar como apoio para a movimentação.
     * 
     * @param n Enésimo disco que se quer mover da haste de origem para a haste
     * de destino.
     * @param origem A haste de origem.
     * @param destino A haste de destino.
     * @param auxiliar A haste auxiliar.
     */
    private void resolver( int n, Haste origem, Haste destino, Haste auxiliar ) {
        
        // base: mover apenas um disco
        
        /**
         * recursão:
         *   - passo 1: mover n-1 discos da origem para o auxiliar;
         *   - passo 2: mover o maior disco da origem para o destino;
         *   - passo 3: mover n-1 discos do auxiliar para o destino.
         */
        
    }
    
    private void desfazer() {
        if ( !acoesDesfazer.isEmpty() ) {
            AcaoHaste a = acoesDesfazer.pop();
            acoesRefazer.push( a );
            moverDisco( a.destino, a.origem, false );
        }
    }
    
    private void refazer() {
        if ( !acoesRefazer.isEmpty() ) {
            AcaoHaste a = acoesRefazer.pop();
            acoesDesfazer.push( a );
            moverDisco( a.origem, a.destino, false );
        }
    }
    
    private record AcaoHaste( Haste origem, Haste destino ) {
    }
    
    public static void main( String[] args ) {
        new MainPasso3();
    }
    
}
