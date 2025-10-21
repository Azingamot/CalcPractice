package com.example.calcpractice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private String prevNumber = "";
    private String curNumber = "";

    private String curOperation = "";

    private TextView curNumberText, prevNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        curNumberText = findViewById(R.id.curNumber);
        prevNumberText = findViewById(R.id.prevNumber);
    }

    public void EnterNumber(View v)
    {
        TextView text = (TextView)v;
        if (curNumber.length() < 8)
        {
            if (Objects.equals(curNumber, "0"))
            {
                curNumber = "";
            }
            curNumber += text.getText();
        }
        else
        {
            Toast.makeText(this, "Слишком большое число", Toast.LENGTH_SHORT).show();
        }

        ReloadText();
    }

    public void MakeOperation(View v)
    {
        if (!Objects.equals(curNumber, ""))
        {
            if (!Objects.equals(curOperation, "")) DoCalculation(null);
            TextView text = (TextView)v;
            prevNumber = String.valueOf(curNumber);
            curNumber = "";
            curOperation = text.getText().toString();
            ReloadText();
        }
    }

    public void Clear(View v)
    {
        prevNumber = "";
        curNumber = "";
        curOperation = "";
        ReloadText();
    }

    public void DoSqrt(View v)
    {
        curNumber = String.valueOf(Math.round(Math.sqrt(Integer.parseInt(curNumber))));
        ReloadText();
    }

    public void ClearLast(View v)
    {
        StringBuilder sb = new StringBuilder(curNumber);
        if (sb.length() > 0)
        {
            sb.deleteCharAt(sb.length() - 1);
            curNumber = sb.toString();
            ReloadText();
        }
        else
        {
            Toast.makeText(this, "Достигнут конец", Toast.LENGTH_SHORT).show();
        }
    }

    public void DoCalculation(View v)
    {
        try
        {
            if (curOperation != "")
            {
                switch (curOperation)
                {
                    case "+": curNumber = String.valueOf(Integer.parseInt(prevNumber) + Integer.parseInt(curNumber)); break;
                    case "-": curNumber = String.valueOf(Integer.parseInt(prevNumber) - Integer.parseInt(curNumber)); break;
                    case "*": curNumber = String.valueOf(Integer.parseInt(prevNumber) * Integer.parseInt(curNumber)); break;
                    case "**": curNumber = String.valueOf(Math.round(Math.pow(Integer.parseInt(prevNumber), Integer.parseInt(curNumber)))); break;
                    case "%": curNumber = String.valueOf(Integer.parseInt(prevNumber) % Integer.parseInt(curNumber)); break;
                    case "/": if (!Objects.equals(curNumber, "0"))
                    {
                        curNumber = String.valueOf(Math.round(Float.parseFloat(prevNumber) / Float.parseFloat(curNumber)));
                    }
                    else
                    {
                        Toast.makeText(this, "Деление на 0 запрещено", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                }
                prevNumber = "";
                ReloadText();
            }
        }
        catch (Exception e)
        {
            Log.d("ERRORCALC", "DoCalculation: " + e.getMessage());
        }

    }

    private void ReloadText()
    {
        curNumberText.setText(curNumber);
        prevNumberText.setText(prevNumber);
    }
}