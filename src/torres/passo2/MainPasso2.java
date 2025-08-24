package torres.passo2;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
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
public class MainPasso2 extends EngineFrame {
    
    /**
     * Implemetar desfazer e refazer.
     */
    
    private Haste h1;
    private Haste h2;
    private Haste h3;
    private int quantidadeDiscos;
    
    private GuiButton btnDesfazer;
    private GuiButton btnRefazer;
    
    private GuiButton btn12;
    private GuiButton btn13;
    private GuiButton btn21;
    private GuiButton btn23;
    private GuiButton btn31;
    private GuiButton btn32;
    
    private List<GuiComponent> componentes;
    
    public MainPasso2() {
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
        
        componentes = new ArrayList<>();
    
        btnDesfazer = new GuiButton( h2.getPos().x - 175, 20, 80, 20, "desfazer" );
        btnRefazer = new GuiButton( h2.getPos().x - 85, 20, 80, 20, "refazer" );
        
        btn12 = new GuiButton( h1.getPos().x - 25, 90, 50, 20, "-> 2" );
        btn13 = new GuiButton( h1.getPos().x - 25, 120, 50, 20, "-> 3" );
        btn21 = new GuiButton( h2.getPos().x - 25, 90, 50, 20, "1 <-" );
        btn23 = new GuiButton( h2.getPos().x - 25, 120, 50, 20, "-> 3" );
        btn31 = new GuiButton( h3.getPos().x - 25, 90, 50, 20, "1 <-" );
        btn32 = new GuiButton( h3.getPos().x - 25, 120, 50, 20, "2 <-" );
        
        componentes.add( btnDesfazer );
        componentes.add( btnRefazer );
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
        
        if ( btn12.isMousePressed() ) {
            moverDisco( h1, h2 );
        }
        
        if ( btn13.isMousePressed() ) {
            moverDisco( h1, h3 );
        }
        
        if ( btn21.isMousePressed() ) {
            moverDisco( h2, h1 );
        }
        
        if ( btn23.isMousePressed() ) {
            moverDisco( h2, h3 );
        }
        
        if ( btn31.isMousePressed() ) {
            moverDisco( h3, h1 );
        }
        
        if ( btn32.isMousePressed() ) {
            moverDisco( h3, h2 );
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
    
    private void moverDisco( Haste origem, Haste destino ) {
        if ( !origem.estaVazia() ) {
            if ( destino.estaVazia() ) {
                destino.empilhar( origem.desempilhar() );
            } else if ( destino.verTopo().getDim().x > origem.verTopo().getDim().x ) {
                destino.empilhar( origem.desempilhar() );
            }
        }
    }
    
    private void desfazer() {
        System.out.println( "desfazendo..." );
    }
    
    private void refazer() {
        System.out.println( "refazendo..." );
    }
    
    public static void main( String[] args ) {
        new MainPasso2();
    }
    
}
