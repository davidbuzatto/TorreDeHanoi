package torres;

import aesd.ds.interfaces.Stack;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 * Simulador de Torres de Hanoi.
 * 
 * Material complementar da disciplina de estruturas de dados para ensino de
 * recursão e aplicação de pilhas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Main extends EngineFrame {
    
    private Haste h1;
    private Haste h2;
    private Haste h3;
    
    private GuiButton btnDesfazer;
    private GuiButton btnRefazer;
    private GuiButton btnReiniciar;
    private GuiButton btnResolver;
    
    private GuiButton btn12;
    private GuiButton btn13;
    
    private GuiButton btn21;
    private GuiButton btn23;
    
    private GuiButton btn31;
    private GuiButton btn32;
    
    private List<GuiComponent> componentes;
    
    public Main() {
        super( 800, 450, "Simulador de Torres de Hanoi", 60, true );
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
        
        int ini = 2;
        int fim = 10;
        int quant = fim - ini + 1;
        
        for ( int i = fim; i >= ini; i-- ) {
            double porc = ( (double) ( fim - i ) / quant );            
            h2.getDiscos().push( 
                new Disco( 
                    i - ini + 1,
                    0, 0, i * 20, 20, 
                    ColorUtils.colorFromHSV( porc * 360, 1, 1 )
                )
            );
        }
        
        componentes = new ArrayList<>();
        btnDesfazer = new GuiButton( h2.getPos().x - 175, 20, 80, 20, "desfazer" );
        btnRefazer = new GuiButton( h2.getPos().x - 85, 20, 80, 20, "refazer" );
        btnReiniciar = new GuiButton( h2.getPos().x + 5, 20, 80, 20, "reiniciar" );
        btnResolver = new GuiButton( h2.getPos().x + 95, 20, 80, 20, "resolver" );
    
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
        
        componentes.add( btn12 );
        componentes.add( btn13 );
        componentes.add( btn21 );
        componentes.add( btn23 );
        componentes.add( btn31 );
        componentes.add( btn32 );
        
    }
    
    @Override
    public void update( double delta ) {
        
        for ( GuiComponent c : componentes ) {
            c.update( delta );
        }
        
        if ( btnDesfazer.isMousePressed() ) {
            System.out.println( "desfazer..." );
        }
        
        if ( btnRefazer.isMousePressed() ) {
            System.out.println( "refazer..." );
        }
        
        if ( btnReiniciar.isMousePressed() ) {
            System.out.println( "reiniciar..." );
        }
        
        if ( btnResolver.isMousePressed() ) {
            System.out.println( "resolver..." );
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
        
        h1.update();
        h2.update();
        h3.update();
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );

        for ( GuiComponent c : componentes ) {
            c.draw();
        }
        
        h1.draw( this );
        h2.draw( this );
        h3.draw( this );
        fillRectangle( 50, h1.getPos().y, getScreenWidth() - 100, 20, BLACK );
        
    }
    
    private static void moverDisco( Haste origem, Haste destino ) {
        
        Stack<Disco> po = origem.getDiscos();
        Stack<Disco> pd = destino.getDiscos();
        
        if ( !po.isEmpty() ) {
            if ( pd.isEmpty() ) {
                pd.push( po.pop() );
            } else if ( pd.peek().getDim().x > po.peek().getDim().x ) {
                pd.push( po.pop() );
            }
        }
    }
    
    public static void main( String[] args ) {
        new Main();
    }
    
}
