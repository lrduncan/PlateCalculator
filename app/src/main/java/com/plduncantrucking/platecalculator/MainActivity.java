package com.plduncantrucking.platecalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Method for calculating number of each plates for barbell.
     * @param view
     */
    public void calculatePlates(View view) {

        try {
            // Find total weight number, convert to string
            TextView showTotal = findViewById(R.id.editWeight);
            String totalString = showTotal.getText().toString();

            final Double[] AVAILABLE_WEIGHTS = new Double[]{45.0, 35.0, 25.0, 10.0, 5.0, 2.5};
            final TextView[] VIEWS = new TextView[]{findViewById(R.id.XXL), findViewById(R.id.XL),
                    findViewById(R.id.L), findViewById(R.id.M),
                    findViewById(R.id.S), findViewById(R.id.XS)};

            double total = Double.parseDouble(totalString);

            // check that weight is at least the bar
            if (total < 45) {
                Toast notValid = Toast.makeText(this, "That's less than the bar!", Toast.LENGTH_SHORT);
                notValid.show();
                return;
            }

            // Subtract the bar weight
            total -= 45;

            // Divide by weights, divide again by 2 to get number for each side
            for (int i = 0; i < AVAILABLE_WEIGHTS.length; i++) {
                // find how many plates that can be used for this weight
                double plates = Math.floor((total / AVAILABLE_WEIGHTS[i]) / 2);
                // subtract weight from total
                if (plates >= 1) {
                    total = total - (2 * plates * AVAILABLE_WEIGHTS[i]);
                }
                // convert to int to display as whole number
                int conversion = (int) plates;

                VIEWS[i].setText(String.format(java.util.Locale.US, "%d", conversion));
            }
            //Button button = (EditText) findViewById(R.id.edit)
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        // catch exception for blank entries
        } catch (NumberFormatException num) {
            Toast error = Toast.makeText(this, "Please enter a number!", Toast.LENGTH_SHORT);
            error.show();
        // catch all other exceptions
        } catch (Exception e) {
            Toast error = Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
            error.show();
        }
    }

}
