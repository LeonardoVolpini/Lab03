package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Dictionary dizionario;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> BoxLang;

    @FXML
    private TextArea txtTesto;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextArea txtParoleSbagliate;

    @FXML
    private Label lblErrori;

    @FXML
    private Button btnClear;

    @FXML
    private Label lblTempo;

    @FXML
    void handleClearText(ActionEvent event) {
    	this.txtParoleSbagliate.clear();
    	this.txtTesto.clear();
    	this.dizionario.reset();
    	this.lblErrori.setText("");
    	this.lblTempo.setText("");
    	this.BoxLang.setValue(null);
    }

    @FXML
    void handleSpellCheck(ActionEvent event) {
    	String lang = this.BoxLang.getValue();
    	long tempoI = System.nanoTime();
    	if (lang==null) {
    		this.txtParoleSbagliate.setText("Selezionare una lingua");
    		return;
    	} else {
    		this.dizionario.loadDictionary(lang);
    		String text = this.txtTesto.getText();
    		List<String> temp=this.dizionario.filtraTesto(text);
    		List<RichWord> errori=this.dizionario.spellCheckText(temp);
    		this.txtParoleSbagliate.setText(this.dizionario.paroleSbagliateString(errori));
    		this.lblErrori.setText("The text contains "+errori.size()+" errors");
    	}
    	long tempoF= System.nanoTime();
    	long differenza= tempoF-tempoI;
    	this.lblTempo.setText("Spell Check completed in "+differenza+" seconds");
    }

    @FXML
    void initialize() {
        assert BoxLang != null : "fx:id=\"BoxLang\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTesto != null : "fx:id=\"txtTesto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtParoleSbagliate != null : "fx:id=\"txtParoleSbagliate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrori != null : "fx:id=\"lblErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblTempo != null : "fx:id=\"lblTempo\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel (Dictionary model) {
    	this.dizionario=model;
    	//List<String> languages=new ArrayList<String>();
    	String[] languages = {"English","Italiano"};
    	BoxLang.getItems().addAll(languages);
    }
}

