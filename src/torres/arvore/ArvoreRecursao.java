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
            No n1 = obtemNoInterceptado( x, y, no.noBase );
            if ( n1 != null ) {
                return n1;
            }
            No n2 = obtemNoInterceptado( x, y, no.noPasso1 );
            if ( n2 != null ) {
                return n2;
            }
            No n3 = obtemNoInterceptado( x, y, no.noPasso2 );
            if ( n3 != null ) {
                return n3;
            }
            return obtemNoInterceptado( x, y, no.noPasso3 );
        }
        return null;
    }
    
    public void desenhar( EngineFrame e ) {
        desenharArestas( e, raiz );
        desenhar( e, raiz );
        if ( noInterceptado != null && noInterceptado.tipo == Tipo.MOVIMENTO )  {
            //e.fillCircle( noInterceptado.pos, 10, e.RED );
        }
    }
    
    private void desenhar( EngineFrame e, No no ) {
        if ( no != null ) {
            no.desenhar( e );
            desenhar( e, no.noBase );
            desenhar( e, no.noPasso1 );
            desenhar( e, no.noPasso2 );
            desenhar( e, no.noPasso3 );
        }
    }
    
    private void desenharArestas( EngineFrame e, No no ) {
        if ( no != null ) {
            if ( no.noBase != null ) {
                e.drawLine( no.pos, no.noBase.pos, e.BLACK );
            }
            if ( no.noPasso1 != null ) {
                e.drawLine( no.pos, no.noPasso1.pos, e.BLACK );
            }
            if ( no.noPasso2 != null ) {
                e.drawLine( no.pos, no.noPasso2.pos, e.BLACK );
            }
            if ( no.noPasso3 != null ) {
                e.drawLine( no.pos, no.noPasso3.pos, e.BLACK );
            }
            desenharArestas( e, no.noBase );
            desenharArestas( e, no.noPasso1 );
            desenharArestas( e, no.noPasso2 );
            desenharArestas( e, no.noPasso3 );
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

            organizarEspacialmente( no.noBase, nivel+1 );
            organizarEspacialmente( no.noPasso1, nivel+1 );
            organizarEspacialmente( no.noPasso2, nivel+1 );
            organizarEspacialmente( no.noPasso3, nivel+1 );
            
        }
        
    }
    
    private int ranqueCalculo;
    
    private void calcularRanque() {
        ranqueCalculo = 0;
        calcularRanque( raiz );
    }
    
    private void calcularRanque( No no ) {
        if ( no != null ) {
            if ( no.tipo == Tipo.RESOLUCAO ) {
                calcularRanque( no.noPasso1 );
                no.ranque = ranqueCalculo++;
                if ( no.noBase != null ) {
                    no.noBase.ranque = ranqueCalculo - 1;
                }
                calcularRanque( no.noPasso2 );
                calcularRanque( no.noPasso3 );
            } else {
                no.ranque = ranqueCalculo - 1;
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

            mostrar( no.noBase, true);
            mostrar( no.noPasso1, false );
            mostrar( no.noPasso2, true );
            mostrar( no.noPasso3, false );
            
        }
        
    }
    
}
