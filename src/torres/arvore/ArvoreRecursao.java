package torres.arvore;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import torres.Haste;

/**
 * Armazena os dados da árvore de recursão da solução recursiva das Torres de
 * Hanói.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ArvoreRecursao {
    
    private No raiz;
    private No noInterceptado;
    
    public ArvoreRecursao( int n, Haste origem, Haste destino, Haste auxiliar ) {
        raiz = new No( n, origem, destino, auxiliar );
    }
    
    public No getRaiz() {
        return raiz;
    }
    
    public void atualizar( EngineFrame e, int xDiff, int yDiff ) {
        noInterceptado = obtemNoInterceptado( e.getMouseX() - xDiff, e.getMouseY() - yDiff, raiz );
    }
    
    private No obtemNoInterceptado( int x, int y, No no ) {
        if ( no != null ) {
            if ( no.intercepta( x, y ) ) {
                return no;
            }
            No n1 = obtemNoInterceptado( x, y, no.getNoBase() );
            if ( n1 != null ) {
                return n1;
            }
            No n2 = obtemNoInterceptado( x, y, no.getNoPasso1() );
            if ( n2 != null ) {
                return n2;
            }
            No n3 = obtemNoInterceptado( x, y, no.getNoPasso2() );
            if ( n3 != null ) {
                return n3;
            }
            return obtemNoInterceptado( x, y, no.getNoPasso3() );
        }
        return null;
    }
    
    public void desenhar( EngineFrame e ) {
        desenharArestas( e, raiz );
        desenhar( e, raiz );
        if ( noInterceptado != null && noInterceptado.getTipo() == Tipo.MOVIMENTO )  {
            //e.fillCircle( noInterceptado.pos, 10, e.RED );
        }
    }
    
    private void desenhar( EngineFrame e, No no ) {
        if ( no != null ) {
            no.desenhar( e );
            desenhar( e, no.getNoBase() );
            desenhar( e, no.getNoPasso1() );
            desenhar( e, no.getNoPasso2() );
            desenhar( e, no.getNoPasso3() );
        }
    }
    
    private void desenharArestas( EngineFrame e, No no ) {
        if ( no != null ) {
            if ( no.getNoBase() != null ) {
                e.drawLine( no.pos, no.getNoBase().pos, e.BLACK );
            }
            if ( no.getNoPasso1() != null ) {
                e.drawLine( no.pos, no.getNoPasso1().pos, e.BLACK );
            }
            if ( no.getNoPasso2() != null ) {
                e.drawLine( no.pos, no.getNoPasso2().pos, e.BLACK );
            }
            if ( no.getNoPasso3() != null ) {
                e.drawLine( no.pos, no.getNoPasso3().pos, e.BLACK );
            }
            desenharArestas( e, no.getNoBase() );
            desenharArestas( e, no.getNoPasso1() );
            desenharArestas( e, no.getNoPasso2() );
            desenharArestas( e, no.getNoPasso3() );
        }
    }
    
    public void organizarEspacialmente() {
        calcularRanque();
        organizarEspacialmente( raiz, 0 );
    }
    
    private void organizarEspacialmente( No no, int nivel ) {
        
        if ( no != null ) {
            
            no.pos.x = no.getRanque() * no.dim.x;
            no.pos.y = nivel * ( no.dim.y + 40 );

            organizarEspacialmente( no.getNoBase(), nivel+1 );
            organizarEspacialmente( no.getNoPasso1(), nivel+1 );
            organizarEspacialmente( no.getNoPasso2(), nivel+1 );
            organizarEspacialmente( no.getNoPasso3(), nivel+1 );
            
        }
        
    }
    
    private int ranqueCalculo;
    
    private void calcularRanque() {
        ranqueCalculo = 0;
        calcularRanque( raiz );
    }
    
    private void calcularRanque( No no ) {
        if ( no != null ) {
            if ( no.getTipo() == Tipo.RESOLUCAO ) {
                calcularRanque( no.getNoPasso1() );
                no.setRanque( ranqueCalculo++ );
                if ( no.getNoBase() != null ) {
                    no.getNoBase().setRanque( ranqueCalculo - 1 );
                }
                calcularRanque( no.getNoPasso2() );
                calcularRanque( no.getNoPasso3() );
            } else {
                no.setRanque( ranqueCalculo - 1 );
            }
        }
    }
    
    public void mostrar() {
        mostrar( raiz, false );
    }
    
    private void mostrar( No no, boolean base ) {
        
        if ( no != null ) {
            
            if ( base ) {
                System.out.println( no );
            }

            mostrar( no.getNoBase(), true);
            mostrar( no.getNoPasso1(), false );
            mostrar( no.getNoPasso2(), true );
            mostrar( no.getNoPasso3(), false );
            
        }
        
    }
    
}
