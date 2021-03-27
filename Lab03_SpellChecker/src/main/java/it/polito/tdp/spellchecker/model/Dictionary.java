package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * svolge il ruolo di model
 * @author Leo Volpe
 *
 */
public class Dictionary {
	
	private Set<String> dizionario;
	private List<String> dizLista;
	
	public Dictionary() {
		this.dizionario=new HashSet<>();
		this.dizLista=new ArrayList<>();
	}
	
	public void reset() {
		this.dizionario.clear();
		this.dizLista.clear();
	}
	
	public void loadDictionary(String language) {
		if (language.equals("English")) {
			try {
				FileReader fr= new FileReader("src/main/resources/English.txt");
				BufferedReader br= new BufferedReader(fr);
				String word;
				while ( (word=br.readLine()) !=null ) {
					dizionario.add(word);
					this.dizLista.add(word);
				}
				br.close();
				fr.close();
			} catch (IOException ioe) {
				System.out.println("Errore nella lettura del file");
			}
		} 
		if (language.equals("Italiano")) {
			try {
				FileReader fr= new FileReader("src/main/resources/Italian.txt");
				BufferedReader br= new BufferedReader(fr);
				String word;
				while ( (word=br.readLine()) !=null ) {
					dizionario.add(word);
					this.dizLista.add(word);
				}
				br.close();
				fr.close();
			} catch (IOException ioe) {
				System.out.println("Errore nella lettura del file");
			}
		}
	}
	
	public List<RichWord> spellCheckText(List<String> inputText){
		List<RichWord> temp= new ArrayList<RichWord>();
		for (String w : inputText) {
			RichWord word = new RichWord(w);
			if (this.dizionario.contains(w)) {
				word.setStato(true);;
			}else
				temp.add(word);
		}
		return temp;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputText) {
		List<RichWord> temp= new ArrayList<RichWord>();
		for (String w : inputText) {
			RichWord word = new RichWord(w);
			String s = this.cercaInLista(w);
			if (s!=null) {
				word.setStato(true);
			}else
				temp.add(word);
		}
		return temp;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputText) {
		List<RichWord> temp= new ArrayList<RichWord>();
		for (String w : inputText) {
			RichWord word = new RichWord(w);
			int inizio=0;
			int fine=dizLista.size();
			while(inizio!=fine) {
				int medio=inizio+(fine-inizio)/2;
				if (w.compareTo(this.dizLista.get(medio))==0) {
					word.setStato(true);
					break;
				}
				else if (w.compareTo(this.dizLista.get(medio))>0)
					inizio=medio+1;
				else
					fine=medio;
			}
			if (word.isStato()==false)
				temp.add(word);
		}
		return temp;
	}
	
	public List<String> filtraTesto(String text) {
		String s="";
		s=text.toLowerCase().replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_'~()\\[\\]\"]", "");
		List<String> testo = new ArrayList<String>();
		String[] campi= s.split(" ");
		for (String x : campi)
			testo.add(x);
		return testo;
	}
	
	public String paroleSbagliateString(List<RichWord> errori) {
		String s="";
		for (RichWord w : errori) {
			s=s+w.toString()+"\n";
		}
		return s;
	}
	
	public String cercaInLista(String w) {
		for (String s : this.dizLista)
			if (w.equals(s))
				return s;
		return null;
	}
	
}
