package application;

import java.time.LocalDate;
import java.util.Arrays;

import org.controlsfx.ControlsFXSample;
import org.controlsfx.samples.HelloDecorator;
import org.controlsfx.samples.Utils;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

//my first commit..........keyur bhanderi

public class HelloValidation extends ControlsFXSample {
	public String getSampleName() {
		return "Component Validation";
	}

	public String getJavaDocURL() {
		return Utils.JAVADOC_BASE + "org/controlsfx/validation/ValidationSupport.html";
	}

	public String getSampleDescription() {
		return "Component Validation";
	}

	ValidationSupport validationSupport = new ValidationSupport();

	public Node getPanel(final Stage stage) {
		GridPane root = new GridPane();
		root.setVgap(10);
		root.setHgap(10);
		root.setPadding(new Insets(30, 30, 0, 30));

		root.sceneProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable o) {
				if (root.getScene() != null) {
					root.getScene().getStylesheets()
							.add(HelloDecorator.class.getResource("validation.css").toExternalForm());
				}
			}
		});

		// final ListView<ValidationMessage> messageList = new ListView<>();
		// validationSupport.validationResultProperty().addListener( (o,
		// oldValue, validationResult) -> {
		// messageList.getItems().setAll(validationResult.getMessages());
		// }
		// );

		int row = 0;

		// text field
		TextField textField = new TextField();
		validationSupport.registerValidator(textField, Validator.createEmptyValidator("Text is required"));
		root.add(new Label("TextField"), 0, row);
		root.add(textField, 1, row);
		GridPane.setHgrow(textField, Priority.ALWAYS);

		// combobox
		row++;
		ComboBox<String> combobox = new ComboBox<String>();
		combobox.getItems().addAll("Item A", "Item B", "Item C");
		validationSupport.registerValidator(combobox, Validator.createEmptyValidator("ComboBox Selection required"));

		root.add(new Label("ComboBox"), 0, row);
		root.add(combobox, 1, row);
		GridPane.setHgrow(combobox, Priority.ALWAYS);

		// choicebox
		row++;
		ChoiceBox<String> choiceBox = new ChoiceBox<String>();
		choiceBox.getItems().addAll("Item A", "Item B", "Item C");
		validationSupport.registerValidator(choiceBox, Validator.createEmptyValidator("ChoiceBox Selection required"));

		root.add(new Label("ChoiceBox"), 0, row);
		root.add(choiceBox, 1, row);
		GridPane.setHgrow(combobox, Priority.ALWAYS);

		// checkbox
		row++;
		CheckBox checkBox = new CheckBox();
		validationSupport.registerValidator(checkBox, (Control c, Boolean newValue) -> ValidationResult.fromErrorIf(c,
				"Checkbox should be checked", !newValue));
		root.add(new Label("CheckBox"), 0, row);
		root.add(checkBox, 1, row);
		GridPane.setHgrow(checkBox, Priority.ALWAYS);

		// slider
		row++;
		Slider slider = new Slider(-50d, 50d, -10d);
		slider.setShowTickLabels(true);
		validationSupport.registerValidator(slider, (Control c, Double newValue) -> ValidationResult.fromErrorIf(slider,
				"Slider value should be > 0", newValue <= 0));

		root.add(new Label("Slider"), 0, row);
		root.add(slider, 1, row);
		GridPane.setHgrow(checkBox, Priority.ALWAYS);

		// color picker
		row++;
		ColorPicker colorPicker = new ColorPicker(Color.RED);
		validationSupport.registerValidator(colorPicker, Validator
				.createEqualsValidator("Color should be WHITE or BLACK", Arrays.asList(Color.WHITE, Color.BLACK)));

		root.add(new Label("Color Picker"), 0, row);
		root.add(colorPicker, 1, row);
		GridPane.setHgrow(checkBox, Priority.ALWAYS);

		// date picker
		row++;
		DatePicker datePicker = new DatePicker();
		validationSupport.registerValidator(datePicker, false, (Control c, LocalDate newValue) -> ValidationResult
				.fromWarningIf(datePicker, "The date should be today", !LocalDate.now().equals(newValue)));

		root.add(new Label("Date Picker"), 0, row);
		root.add(datePicker, 1, row);
		GridPane.setHgrow(checkBox, Priority.ALWAYS);

		// // validation results
		// row++;
		// TitledPane pane = new TitledPane("Validation Results", messageList);
		// pane.setCollapsible(false);
		// root.add(pane, 0, row, 2, 1);
		// GridPane.setHgrow(pane, Priority.ALWAYS);

		// root.setTop(grid);
		ScrollPane scrollPane = new ScrollPane(root);
		return scrollPane;
	}

	public Node getControlPanel() {
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(30, 30, 0, 30));

		ValidationDecoration iconDecorator = new GraphicValidationDecoration();
		ValidationDecoration cssDecorator = new StyleClassValidationDecoration();
		ValidationDecoration compoundDecorator = new CompoundValidationDecoration(cssDecorator, iconDecorator);

		int row = 0;

		// --- validation decorator
		Callback<ListView<ValidationDecoration>, ListCell<ValidationDecoration>> cellFactory = listView -> new ListCell<ValidationDecoration>() {
			@Override
			protected void updateItem(ValidationDecoration decorator, boolean empty) {
				super.updateItem(decorator, empty);

				if (empty) {
					setText("");
				} else {
					if (decorator instanceof StyleClassValidationDecoration) {
						setText("Style Class Validation Decorator");
					} else if (decorator instanceof GraphicValidationDecoration) {
						setText("Graphic Validation Decorator");
					} else if (decorator instanceof CompoundValidationDecoration) {
						setText("Compound Validation Decorator");
					} else {
						setText("Unknown decorator type!");
					}
				}
			}
		};
		ComboBox<ValidationDecoration> decoratorBox = new ComboBox<>();
		decoratorBox.getItems().addAll(iconDecorator, cssDecorator, compoundDecorator);
		decoratorBox.setCellFactory(cellFactory);
		decoratorBox.setButtonCell(cellFactory.call(null));
		decoratorBox.getSelectionModel().selectedItemProperty().addListener((o, old, decorator) -> {
			validationSupport.setValidationDecorator(decorator);
		});
		decoratorBox.getSelectionModel().select(0);

		Label validationDecoratorLabel = new Label("Validation Decorator: ");
		validationDecoratorLabel.getStyleClass().add("property");
		grid.add(validationDecoratorLabel, 0, row);
		grid.add(decoratorBox, 1, row);
		GridPane.setHgrow(decoratorBox, Priority.ALWAYS);

		return grid;
	}

	public static void main(String[] args) {
		launch(args);
	}

}