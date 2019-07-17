/** Author: Helen Lily Hu
 * Date: 7/14/19 - 7/17/19 */

package system;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUI extends Application {

	/** Handles reading and parsing web addresses and writing texts */
	MarkovAuthor author;

	/** Display text to write to */
	Text text;

	/** Launches the application */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// get screen dimensions to create scene
		Rectangle2D screenSize= Screen.getPrimary().getVisualBounds();
		double smolDim= Math.min(screenSize.getHeight(), screenSize.getWidth());

		// initialize fields
		author= new MarkovAuthor();
		text= new Text();

		// create layout manager
		VBox root= new VBox();
		root.setSpacing(20);
		root.setPadding(new Insets(20));
		initGUI(root, smolDim);

		Scene scene= new Scene(root, smolDim, smolDim, Color.WHITE);
		primaryStage.setTitle("Dissociative Author");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		// author.read("http://shakespeare.mit.edu/macbeth/macbeth.1.1.html");
		text.setText("Enter or choose a link to start the writing.");
	}

	/** Helper for start that handles GUI elements that are children of root */
	private void initGUI(VBox root, double smolDim) {
		// toolbar in which to enter web address to write from
		HBox toolbar= new HBox();
		root.getChildren().add(toolbar);
		initToolbar(toolbar);

		// scrollable area where text will be written
		ScrollPane draftArea= new ScrollPane();
		draftArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		root.getChildren().add(draftArea);
		// Customize text and make it scrollable
		text.setWrappingWidth(smolDim - 70);
		text.setFont(Font.font(20));
		draftArea.setContent(text);
	}

	/** Helper for initGUI that handles GUI elements that are children of toolbar */
	private void initToolbar(HBox toolbar) {
		toolbar.setAlignment(Pos.CENTER);

		// label for web address entry box
		Text label= new Text("Link to Writing Inspiration:   ");
		toolbar.getChildren().add(label);

		// web address entry box
		ComboBox<String> box= new ComboBox<>();
		toolbar.getChildren().add(box);
		// allow user to enter own link
		box.setEditable(true);
		box.getItems().addAll("http://shakespeare.mit.edu/macbeth/full.html",
			"http://hca.gilead.org.il/p_stone.html",
			"https://csivc.csi.cuny.edu/history/files/lavender/wallpaper.html",
			"http://shakespeare.mit.edu/romeo_juliet/full.html",
			"http://www.hplovecraft.com/writings/texts/fiction/cc.aspx",
			"http://gutenberg.net.au/ebooks06/0609221h.html#c2");
		box.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String link= box.getValue();
				author.read(link);
				author.write(text);
			}

		});

	}

}
