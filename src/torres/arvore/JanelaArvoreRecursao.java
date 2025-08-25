package torres.arvore;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;

/**
 * Uma instância da engine para a apresentação da árvore de recursão.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class JanelaArvoreRecursao extends EngineFrame {

    private ArvoreRecursao arvore;
    
    private int xOrigem;
    private int yOrigem;
    
    private int xDiff;
    private int yDiff;
    
    public JanelaArvoreRecursao() {
        super( 1000, 800, "Visualização da Árvore de Recursão", 60, true, true, false, true, false, false );
        setExtendedState( MAXIMIZED_BOTH );
    }
    
    @Override
    public void create() {
        xOrigem = 100;
        yOrigem = 50;
    }

    @Override
    public void update( double delta ) {
        
        if ( isMouseButtonPressed( MOUSE_BUTTON_LEFT ) ) {
            xDiff = getMouseX() - xOrigem;
            yDiff = getMouseY() - yOrigem;
        } else if ( isMouseButtonDown( MOUSE_BUTTON_LEFT ) ) {
            xOrigem = getMouseX() - xDiff;
            yOrigem = getMouseY() - yDiff;
        }
        
        if ( arvore != null ) {
            arvore.atualizar( this, xOrigem, yOrigem );
        }
        
    }

    @Override
    public void draw() {
        if ( arvore != null ) {
            translate( xOrigem, yOrigem );
            arvore.desenhar( this );
        }
    }

    public void setArvore( ArvoreRecursao arvore ) {
        this.arvore = arvore;
    }
    
}
