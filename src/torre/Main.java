package torre;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.interfaces.Stack;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 * Simulador de Torre de Hanói.
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
    private int quantidadeDiscos;
    
    private Stack<AcaoHaste> acoesDesfazer;
    private Stack<AcaoHaste> acoesRefazer;
    
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
    
    private List<PassoAnimacao> passosAnimacao;
    private double tempoPassoAnimacao;
    private int passoAtual;
    private boolean executandoAnimacao;
    
    public Main() {
        super( 800, 450, "Jogo Torre de Hanói", 60, true );
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
        
        passosAnimacao = new ArrayList<>();
        tempoPassoAnimacao = 0.1;
        
        quantidadeDiscos = 9;
        preparar( quantidadeDiscos );
        
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
            desfazer();
        }
        
        if ( btnRefazer.isMousePressed() ) {
            refazer();
        }
        
        if ( btnReiniciar.isMousePressed() ) {
            preparar( quantidadeDiscos );
            alterarEstadoGUI( true );
        }
        
        if ( btnResolver.isMousePressed() ) {
            resolver();
            alterarEstadoGUI( false );
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
        
        // se não está havendo animação, a posição dos discos
        // é controlada pelas hastes
        if ( !executandoAnimacao ) {
            h1.atualizar();
            h2.atualizar();
            h3.atualizar();
        } else {
            PassoAnimacao p = passosAnimacao.get( passoAtual );
            p.update( delta );
            if ( p.finalizada ) {
                passoAtual++;
                if ( passoAtual == passosAnimacao.size() ) {
                    executandoAnimacao = false;
                }
            }
        }
        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );

        for ( GuiComponent c : componentes ) {
            c.draw();
        }
        
        h1.desenhar( this );
        h2.desenhar( this );
        h3.desenhar( this );
        
        fillRectangle( 50, h1.getPos().y, getScreenWidth() - 100, 20, BLACK );
        
    }
    
    private void preparar( int quantidade ) {
        
        executandoAnimacao = false;
        
        h1.limpar();
        h2.limpar();
        h3.limpar();
        
        acoesDesfazer.clear();
        acoesRefazer.clear();
        
        passosAnimacao.clear();
        passoAtual = 0;
        
        int ini = 2;
        int fim = quantidade + ini - 1;
        
        for ( int i = fim; i >= ini; i-- ) {
            double porc = ( (double) ( fim - i ) / quantidade );            
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
        preparar( quantidadeDiscos );
        resolver( h2.getTamanho(), h2, h3, h1 );
        executandoAnimacao = true;
    }
    
    private void resolver( int n, Haste origem, Haste destino, Haste auxiliar ) {
        
        // base: mover apenas um disco
        if ( n == 1 ) {
            moverDisco( origem, destino, false );
            salvarPassoAnimacao( origem, destino, tempoPassoAnimacao );
            return;
            
        }
        
        // recursão:
        // passo 1: mover n-1 discos da origem para o auxiliar
        resolver( n-1, origem, auxiliar, destino );
        
        // passo 2: mover o maior disco da origem para o destino
        moverDisco( origem, destino, false );
        salvarPassoAnimacao( origem, destino, tempoPassoAnimacao );
        
        // passo 3: mover n-1 discos do auxiliar para o destino
        resolver( n-1, auxiliar, destino, origem );
        
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
    
    private void salvarPassoAnimacao( Haste origem, Haste destino, double tempo ) {
        
        double x1 = origem.getPos().x;
        double y1 = origem.getPos().y - origem.getTamanho() * destino.verTopo().getDim().y;
        double x2 = destino.getPos().x;
        double y2 = destino.getPos().y - ( destino.getTamanho() - 1 ) * destino.verTopo().getDim().y;
        double yFinal = origem.getPos().y - origem.getDim().y - destino.verTopo().getDim().y;
        
        passosAnimacao.add( new PassoAnimacao( destino.verTopo(), x1, y1, x1, yFinal, tempo ) );
        passosAnimacao.add( new PassoAnimacao( destino.verTopo(), x1, yFinal, x2, yFinal, tempo  ) );
        passosAnimacao.add( new PassoAnimacao( destino.verTopo(), x2, yFinal, x2, y2, tempo  ) );
        
    }
    
    private void alterarEstadoGUI( boolean habilitado ) {
        btn12.setEnabled( habilitado );
        btn13.setEnabled( habilitado );
        btn21.setEnabled( habilitado );
        btn23.setEnabled( habilitado );
        btn31.setEnabled( habilitado );
        btn32.setEnabled( habilitado );
        btnDesfazer.setEnabled( habilitado );
        btnRefazer.setEnabled( habilitado );
    }
    
    private record AcaoHaste( Haste origem, Haste destino ) {
    }
    
    private class PassoAnimacao {
        
        Disco disco;
        double x;
        double y;
        
        double largura;
        double altura;
        
        double tempo;
        double tempoAtual;
        boolean finalizada;
        
        public PassoAnimacao( Disco disco, double x1, double y1, double x2, double y2, double tempo ) {
            this.disco = disco;
            this.x = x1;
            this.y = y1;
            this.largura = x2 - x1;
            this.altura = y2 - y1;
            this.tempo = tempo;
        }
        
        void update( double delta ) {
            
            if ( !finalizada ) {
                
                tempoAtual += delta;
                
                if ( tempoAtual >= tempo ) {
                    tempoAtual = tempo;
                    finalizada = true;
                }
                
                double porc = tempoAtual / tempo;
                
                disco.getPos().x = x + porc * largura;
                disco.getPos().y = y + porc * altura;
                
            }
            
        }
        
    }
    
    public static void main( String[] args ) {
        new Main();
    }
    
}
