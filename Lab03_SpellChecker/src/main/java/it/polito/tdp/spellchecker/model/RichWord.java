package it.polito.tdp.spellchecker.model;

public class RichWord {
	String parola;
	boolean stato;
	
	public RichWord(String parola) {
		this.parola = parola;
		this.stato = false;
	}

	public void setStato(boolean stato) {
		this.stato = stato;
	}

	@Override
	public String toString() {
		return parola;
	}

}
