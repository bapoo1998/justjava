package com.example.bapusaheb.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.justjava.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;
    public boolean creamCheckBox() {
        CheckBox creamBox = (CheckBox) findViewById(R.id.cream_check_box);
        boolean hasCream = creamBox.isChecked();
        return hasCream;


    }
    public boolean chocoCheckBox() {
        CheckBox chocoBox = (CheckBox) findViewById(R.id.choco_check_box);
        boolean hasChoco = chocoBox.isChecked();
        return hasChoco;
    }
    /**
     * These 2 methods increase and decrease the quantity
     */


    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot order less than one cup of Coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
        displayPrice(calculatePrice(creamCheckBox(), chocoCheckBox()));
    }

    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "You cannot order more than 100 cups of Coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
        displayPrice(calculatePrice(creamCheckBox(), chocoCheckBox()));
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        Toast.makeText(this, "App developed by Bapusaheb Patil", Toast.LENGTH_SHORT).show();
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String theName = nameField.getText().toString();

        String theMessage = createOrderSummary(creamCheckBox(), chocoCheckBox(), theName)
                             + "\n Total Price: "
                             + NumberFormat.getCurrencyInstance().format(calculatePrice(creamCheckBox(), chocoCheckBox()))
                             + "\n Now, pay up, dude!";
        displayMessage(theMessage);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:bapoo1998@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order from " + theName);
        intent.putExtra(Intent.EXTRA_TEXT, theMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        Toast.makeText(this, "App developed by Bapusaheb Patil", Toast.LENGTH_SHORT).show();
    }

    private String createOrderSummary(boolean addCream, boolean addChoco, String someName) {
        String priceMessage = "Name: " + someName;
        priceMessage = priceMessage + "\n Has whipped cream: " + addCream;
        priceMessage = priceMessage + "\n Has chocolate: " + addChoco;
        priceMessage = priceMessage + "\n Quantity: " + quantity;
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(
                R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(
                R.id.price_text_view);
        priceTextView.setText(message);
    }

    private int calculatePrice(boolean addCream, boolean addChoco) {
        if (addCream && addChoco){
            return quantity * (5 + 1 + 2);
        } else if (addCream){
            return quantity * (5 + 1);
        } else if (addChoco) {
            return quantity * (5 + 2);
        }else {
            return quantity * 5;
        }
    }
}