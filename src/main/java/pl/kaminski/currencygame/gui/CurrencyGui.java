package pl.kaminski.currencygame.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kaminski.currencygame.Service.CurrencyService;

@Route("currency-game")
public class CurrencyGui extends VerticalLayout {
    private Button buttonGuess;
    private String currencyName;
    private Double currencyValue;
    private TextArea textArea;
    private Label labelResult;
    private int counter = 1;

    @Autowired
    public CurrencyGui(CurrencyService currencyService) {
        this.currencyName = currencyService.getRandomCurrency();
        this.currencyValue = currencyService.getCurrencyValue();
        currencyService.initGame();
        initLabel();
        initTextArea();
        initButton();
        compareValues();
    }

    private void initLabel() {
        Label label = new Label("Welcome to the game.");
        add(label);
    }

    private void initTextArea() {
        textArea = new TextArea("Your random currency is: " + currencyName);
        textArea.setPlaceholder("Guess Value here ...");
        add(textArea);
    }

    private void initButton() {
        buttonGuess = new Button("Guess!");
        add(buttonGuess);
    }

    private void compareValues() {
        labelResult = new Label();
        buttonGuess.addClickListener(clickEvent -> {
            comparingLogic();
        });

    }

    private void comparingLogic() {
        try {
            if (currencyValue < Double.parseDouble(textArea.getValue())) {
                labelResult.setText("Guessed Value is too big, try again!");
                counter++;
            } else if (currencyValue > Double.parseDouble(textArea.getValue())) {
                labelResult.setText("Guessed Value is too small, try again!");
                counter++;
            } else if (currencyValue == Double.parseDouble(textArea.getValue())) {
                labelResult.setText("Congratulations, you guessed correctly, it took " + counter + " times");
            }
            add(labelResult);
        } catch (Exception e) {
            labelResult.setText("Wrong format try again!");
            add(labelResult);
        }
    }
}


