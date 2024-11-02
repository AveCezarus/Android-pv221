package itstep.learning.android_pv221;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.DecimalFormat;
public class CalcActivity extends AppCompatActivity {
    private static final int maxDigits = 10;
    private TextView tvResult;
    private String zeroSign;
    private String currentOperand = "";
    private double result = 0;
    private String operator = "";
    private boolean newOperation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvResult = findViewById(R.id.calc_tv_result);
        zeroSign = getString(R.string.calc_btn_digit_0);
        for (int i = 0; i < 10; i++) {
            String btnIdName = "calc_btn_digit_" + i;
            @SuppressLint("DiscouragedApi") int btnId = getResources().getIdentifier(btnIdName, "id", getPackageName());
            findViewById(btnId).setOnClickListener(this::btnClickDigit);
        }
        findViewById(R.id.calc_btn_c).setOnClickListener(this::btnClickC);
        findViewById(R.id.calc_btn_backspace).setOnClickListener(this::btnClickBackspace);
        findViewById(R.id.calc_btn_plus).setOnClickListener(this::btnClickPlus);
        findViewById(R.id.calc_btn_equal).setOnClickListener(this::btnClickEquals);
        findViewById(R.id.calc_btn_minus).setOnClickListener(this::btnClickMinus);
        findViewById(R.id.calc_btn_multiply).setOnClickListener(this::btnClickMultiplicate);
        findViewById(R.id.calc_btn_divide).setOnClickListener(this::btnClickDivision);
        findViewById(R.id.calc_btn_inverse).setOnClickListener(this::btnClickInverse);
        findViewById(R.id.calc_btn_square).setOnClickListener(this::btnClickSquare);
        findViewById(R.id.calc_btn_sqrt).setOnClickListener(this::btnClickSqrt);
        findViewById(R.id.calc_btn_percent).setOnClickListener(this::btnClickPercent);
        findViewById(R.id.calc_btn_coma).setOnClickListener(this::btnClickComa);
        btnClickC(null);
    }

    private void btnClickBackspace(View view) {
        String resText = tvResult.getText().toString();
        if (resText.length() > 1) {
            resText = resText.substring(0, resText.length() - 1);
        } else {
            resText = zeroSign;
        }
        tvResult.setText(resText);
    }

    private void btnClickC(View view) {
        tvResult.setText(R.string.calc_btn_digit_0);
        currentOperand = "";
        result = 0;
        operator = "";
        newOperation = false;
    }

    private void btnClickPlus(View view) {
        performPendingOperation();
        operator = "+";
        currentOperand = "";
        tvResult.setText(zeroSign);
    }

    private void btnClickMinus(View view) {
        performPendingOperation();
        operator = "-";
        currentOperand = "";
        tvResult.setText(zeroSign);
    }

    private void btnClickMultiplicate(View view) {
        performPendingOperation();
        operator = "*";
        currentOperand = "";
        tvResult.setText(zeroSign);
    }

    private void btnClickDivision(View view) {
        performPendingOperation();
        operator = "/";
        currentOperand = "";
        tvResult.setText(zeroSign);
    }

    private void performPendingOperation() {
        if (!currentOperand.isEmpty()) {

            if (currentOperand.equals(".")) {
                currentOperand = "0";
            }

            double operandValue;
            try {
                operandValue = Double.parseDouble(currentOperand);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid operand", Toast.LENGTH_SHORT).show();
                return;
            }

            switch (operator) {
                case "+":
                    result += operandValue;
                    break;
                case "-":
                    result -= operandValue;
                    break;
                case "*":
                    result *= operandValue;
                    break;
                case "/":
                    if (operandValue != 0) {
                        result /= operandValue;
                    } else {
                        Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                default:
                    result = operandValue;
            }


            DecimalFormat decimalFormat = new DecimalFormat("#.##########");
            String formattedResult = decimalFormat.format(result);
            tvResult.setText(formattedResult);
            currentOperand = formattedResult;
            newOperation = true;
        }
    }



    private void btnClickEquals(View view) {
        performPendingOperation();
        operator = "";
    }


    private void btnClickInverse(View view) {
        if (currentOperand.isEmpty()) {
            Toast.makeText(this, "No operand to invert", Toast.LENGTH_SHORT).show();
            return;
        }
        double operand = Double.parseDouble(currentOperand);
        if (operand == 0) {
            Toast.makeText(this, "Cannot invert zero", Toast.LENGTH_SHORT).show();
        } else {
            result = 1 / operand;
            DecimalFormat decimalFormat = new DecimalFormat("#.##########");
            String formattedResult = decimalFormat.format(result);

            tvResult.setText(formattedResult);
            currentOperand = formattedResult;
            newOperation = true;
        }
    }
    private void btnClickSquare(View view) {
        if (currentOperand.isEmpty()) {
            Toast.makeText(this, "No operand to square", Toast.LENGTH_SHORT).show();
            return;
        }
        double operand = Double.parseDouble(currentOperand);
        result = operand * operand;
        DecimalFormat decimalFormat = new DecimalFormat("#.##########");
        String formattedResult = decimalFormat.format(result);

        tvResult.setText(formattedResult);
        currentOperand = formattedResult;
        newOperation = true;
    }
    private void btnClickSqrt(View view) {
        if (currentOperand.isEmpty()) {
            Toast.makeText(this, "No operand to square", Toast.LENGTH_SHORT).show();
            return;
        }
        double operand = Double.parseDouble(currentOperand);
        result = Math.sqrt(operand);
        DecimalFormat decimalFormat = new DecimalFormat("#.##########");
        String formattedResult = decimalFormat.format(result);

        tvResult.setText(formattedResult);
        currentOperand = formattedResult;
        newOperation = true;
    }
    private void btnClickDigit(View view) {
        String digit = ((Button) view).getText().toString();
        if (currentOperand.equals(zeroSign)) {
            currentOperand = digit;
        } else {
            if (currentOperand.length() < maxDigits) {
                currentOperand += digit;
            } else {
                Toast.makeText(this, R.string.calc_msg_too_long, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        tvResult.setText(currentOperand);
    }
    private void btnClickPercent(View view) {
        if (!currentOperand.isEmpty()) {
            try {
                double operand = Double.parseDouble(currentOperand);
                result = operand / 100;


                DecimalFormat decimalFormat = new DecimalFormat("#.##########");
                String formattedResult = decimalFormat.format(result);

                tvResult.setText(formattedResult);
                currentOperand = formattedResult;
                newOperation = true;
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No operand to calculate percent", Toast.LENGTH_SHORT).show();
        }
    }
    private void btnClickComa(View view) {
        if (!currentOperand.contains(".")) {
            if (currentOperand.isEmpty()|| currentOperand.equals(zeroSign)) {
                currentOperand = "0.";
            } else {
                currentOperand += ".";
            }
            tvResult.setText(currentOperand);
        }
    }
}
