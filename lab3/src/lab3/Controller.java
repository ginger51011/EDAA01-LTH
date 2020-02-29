package lab3;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import textproc.GeneralWordCounter;
import textproc.TextProcessor;

public class Controller extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		
		Scene scene = new Scene(root, 500, 500);
		primaryStage.setTitle("BookReader");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// Copy-paste
		Scanner s = new Scanner(new File("nilsholg.txt"));
		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning
		
		// Undantagsord
		Scanner scan = new Scanner(new File("undantagsord.txt"));
		Set<String> stopwords = new HashSet<String>(); //Vilket set som funkar
		while (scan.hasNext()) {
			stopwords.add(scan.next().toLowerCase());
		}
		
		GeneralWordCounter gwc = new GeneralWordCounter(stopwords); // Kan ej vara TextProcessor för getWords() är egen metod för GWC

		while (s.hasNext()) {
			String word = s.next().toLowerCase();

			gwc.process(word);
		}
		// Slut på Copy-paste
		
		ObservableList<Map.Entry<String, Integer>> words = FXCollections.observableArrayList(gwc.getWords());
		ListView<Map.Entry<String, Integer>> listView = new ListView<Map.Entry<String, Integer>>(words);
		root.setCenter(listView);
		
		// New box längst ner med knappar för i vilken ordning sakerna ska sorteras
		HBox buttonBox = new HBox();
		
		// Skapar knappar
		Button alphabeticButton = new Button("Alfabetisk");
		Button frequencyButton = new Button("Förekomst");
		
		// Lägger till knappar i hbox och hbox i root
		buttonBox.getChildren().addAll(alphabeticButton);
		buttonBox.getChildren().addAll(frequencyButton);
		root.setBottom(buttonBox);
		
		// Oläslig kod
		alphabeticButton.setOnAction(event -> words.sort((p1, p2) -> p1.getKey().compareTo(p2.getKey()))); // Om knappen trycks, sortera words i bokstavsordning
		frequencyButton.setOnAction(event -> words.sort((p1, p2) -> p1.getValue() - p2.getValue()));
		
		// UI för sökfunktionen
		HBox searchBox = new HBox();
		
		Button searchButton = new Button("Sök");
		searchButton.setDefaultButton(true); // Så när man trycker enter används denna per automatik
		
		TextField searchTextField = new TextField();
		searchTextField.setPromptText("Skriv sökord här...");
		
		searchBox.getChildren().addAll(searchTextField);
		searchBox.getChildren().addAll(searchButton);
		
		// Den faktiska sökfunktionen, då man trycker på sök
		searchButton.setOnAction(event -> {
			String searchWord = searchTextField.getText().toLowerCase(); // Sparar sökordet
			searchWord = searchWord.replaceAll(" ", ""); // Tar bort blanksteg
			
			int index = -1;
			// Loopar igenom listan
			for (Entry<String, Integer> e : words) { // Vi beskriver vad Entry har för datayper
				if (e.getKey().equals(searchWord)) {
					index = words.indexOf(e);
					break;
				} 
			}
			if (index == -1){
				Alert alert = new Alert(AlertType.INFORMATION, "Sökordet hittades inte");
				alert.showAndWait();
			} else {
				listView.scrollTo(index);
			}	
		});
		
		
		root.setTop(searchBox);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
