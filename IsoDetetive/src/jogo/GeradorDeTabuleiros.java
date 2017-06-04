package jogo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import estruturas.*;

public class GeradorDeTabuleiros {
	//Construtor de GeradorDeTabuleiros
	public GeradorDeTabuleiros() {
		GeradorDeTabuleiros.carregarDoArquivo();
	}
	
	//Carrega o mapa do aquivo "tabuleiro_oficial.txt"
	public static Tabuleiro carregarDoArquivo() {
		return GeradorDeTabuleiros.carregarDoArquivo( "tabuleiro_oficial.txt" );
	}
	
	//Carrega o tabuleiro do aquivo com nome = fileName. retorna um Tabuleiro preenchido
	public static Tabuleiro carregarDoArquivo( String fileName ) {
	    Scanner file;
	    file = null;
	    
	    // Carregar arquivo
	    try {
	        file = new Scanner(Paths.get( "data\\" + fileName ));
	    } catch (FileNotFoundException ex) {
	    	System.out.print("Arquivo "+fileName+" Nao Encontrado!\n");
	        //Logger.getLogger(Skills.class.getName()).log(Level.SEVERE, null, ex);
	    } catch (IOException e) {
	    	System.out.println("Abertura de "+fileName+" falhou!");
			e.printStackTrace();
		}	
	    
	    // Preparar a lista
	    ArrayList<ArrayList<Casa>> casas = new ArrayList<ArrayList<Casa>>();
	    
	    // Variaveis do processo
	    String line;
	    boolean breakFlag = false;
	    
	    // Load Data
	    int y = -1;
    	while( file.hasNextLine() ) {
    		// Ignore empty lines
    		line 		= file.nextLine().trim();
    		breakFlag 	= false;
    		y++;
    		
	    	while( line.length() < 1 ) {
	    		if( file.hasNextLine() == false ) {
	    			breakFlag = true;
	    			break;
	    		} else {
	    			line = file.nextLine().trim();
	    		}
	    	}

	    	
	    	// End of file marker 
	    	if( breakFlag == true ) {
	    		break;
	    	}
	    	
	    	// Create new entry
	    	ArrayList<Casa> novaLinhaDeCasas = new ArrayList<Casa>();
	    	casas.add( novaLinhaDeCasas );
	    	
	    	for( int x = 0 ; x < line.length() ; x++ ) {
	    		Casa novaCasa = new Casa( x , y );
	    		novaLinhaDeCasas.add( novaCasa );
	    		
	    		switch( line.charAt(x) ) {
	    		case '#':
	    			novaCasa.type = CasaType.VACUO;
	    			break;	  
	    			
	    		case '.':
	    			novaCasa.type = CasaType.CORREDOR;
	    			break;	
	    			
	    		case 'Z':
	    			novaCasa.type = CasaType.CENTRO;
	    			break;	
	    		
	    		case 'A':
	    			novaCasa.type = CasaType.COZINHA;
	    			break;
	    			
	    		case 'a':
	    			novaCasa.type = CasaType.COZINHA_PORTA;
	    			break;
	    			
	    		case 'B':
	    			novaCasa.type = CasaType.SL_MUSICA;
	    			break;
	    			
	    		case 'b':
	    			novaCasa.type = CasaType.SL_MUSICA_PORTA;
	    			break;
	    			
	    		case 'C':
	    			novaCasa.type = CasaType.SL_INVERNO;
	    			break;
	    			
	    		case 'c':
	    			novaCasa.type = CasaType.SL_INVERNO_PORTA;
	    			break;
	    			
	    		case 'D':
	    			novaCasa.type = CasaType.SL_JANTAR;
	    			break;
	    			
	    		case 'd':
	    			novaCasa.type = CasaType.SL_JANTAR_PORTA;
	    			break;
	    			
	    		case 'E':
	    			novaCasa.type = CasaType.SL_JOGOS;
	    			break;
	    			
	    		case 'e':
	    			novaCasa.type = CasaType.SL_JOGOS_PORTA;
	    			break;
	    			
	    		case 'F':
	    			novaCasa.type = CasaType.SL_ESTAR;
	    			break;
	    			
	    		case 'f':
	    			novaCasa.type = CasaType.SL_ESTAR_PORTA;
	    			break;
	    			
	    		case 'G':
	    			novaCasa.type = CasaType.ENTRADA;
	    			break;
	    			
	    		case 'g':
	    			novaCasa.type = CasaType.ENTRADA_PORTA;
	    			break;
	    			
	    		case 'H':
	    			novaCasa.type = CasaType.BIBLIOTECA;
	    			break;
	    			
	    		case 'h':
	    			novaCasa.type = CasaType.BIBLIOTECA_PORTA;
	    			break;
	    			
	    		case 'I':
	    			novaCasa.type = CasaType.ESCRITORIO;
	    			break;
	    			
	    		case 'i':
	    			novaCasa.type = CasaType.ESCRITORIO_PORTA;
	    			break;
	    			
	    		case '0':
	    			novaCasa.type = CasaType.INICIO_L;
	    			break;
	    			
	    		case '1':
	    			novaCasa.type = CasaType.INICIO_SHERLOCK;
	    			break;
	    			
	    		case '2':
	    			novaCasa.type = CasaType.INICIO_CARMEN;
	    			break;
	    			
	    		case '3':
	    			novaCasa.type = CasaType.INICIO_PANTERA;
	    			break;
	    			
	    		case '4':
	    			novaCasa.type = CasaType.INICIO_EDMORT;
	    			break;
	    			
	    		case '5':
	    			novaCasa.type = CasaType.INICIO_BATMAN;
	    			break;
	    			
	    		}
	    	} // Great for end
    	} // Great While End
	    file.close();
	    
	    // Criando novo tabueliro
	    return new Tabuleiro( casas );
	}

}
