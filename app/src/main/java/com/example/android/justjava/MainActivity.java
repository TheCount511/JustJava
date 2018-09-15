package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This is a global variable accessible to all methods
     */

    int quantity = 0;
    int pricePerCup = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the "+" button is clicked
     */
    public void increment(View view) {
        quantity = quantity + 1;
        if (quantity > 100) {// this code prevents the quantity from going above 100
            quantity = 100;
            //the following code shows a toast on the screen
            Toast.makeText(MainActivity.this, "you can not order above 100 at once", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method is called when the "-" button is clicked
     */

    public void decrement(View view) {
        quantity = quantity - 1;
        if (quantity < 1) {//this code prevents the quantity from going below 1
            quantity = 1;
            //the following code shows a toast on the screen
            Toast.makeText(MainActivity.this, "You can not select a quantity below 1", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }


    /**
     * @param hasWhippedCream is used for storing whether or not the user wants whipped cream
     * @param hasChocolate    is used for storing whether or not the user wants chocolate topping
     * @return (quantity x costWithToppings) returns the total cost as thee name implies
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int costWithToppings = pricePerCup;

        if (hasWhippedCream) {
            costWithToppings = costWithToppings + 1;
        }

        if (hasChocolate) {
            costWithToppings = costWithToppings + 2;
        }


        return (quantity * costWithToppings);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText userName = (EditText) findViewById(R.id.username);
        String usersName = userName.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whippedCream);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();


        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = getString(R.string.customers_name, usersName) + "\n" + getString(R.string.total) + price;
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you) +
                "\n" + getString(R.string.add_whipped_cream) +
                hasWhippedCream +
                "\n" + getString(R.string.add_chocolate) + hasChocolate;

        display(priceMessage);

        Intent placeOrder = new Intent(Intent.ACTION_SEND)
                .setType("*/*")
                .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
                .putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (placeOrder.resolveActivity(getPackageManager()) != null) {
            startActivity(placeOrder);
        }
    }

    public void display (String priceMessage){

        TextView OrderSummary = (TextView) findViewById(R.id.display);
                OrderSummary.setText(priceMessage);
    }


}

