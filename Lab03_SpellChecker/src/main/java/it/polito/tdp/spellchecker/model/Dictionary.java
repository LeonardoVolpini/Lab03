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
	
	public Dictionary() {
		this.dizionario=new HashSet<>();
	}
	
	public void reset() {
		this.dizionario.clear();
	}
	
	public void loadDictionary(String language) {
		if (language.equals("English")) {
			try {
				FileReader fr= new FileReader("src/main/resources/English.txt");
				BufferedReader br= new BufferedReader(fr);
				String word;
				while ( (word=br.readLine()) !=null ) {
					dizionario.add(word);
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
	
	public List<String> filtraTesto(String text) {
		String s="";
		s=text.toLowerCase().replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_'~()\\[\\]\"]", "");
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
	
}
