package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*;


class MainActivity : AppCompatActivity() {
    enum class ACTIONS
    {
        PLUS, MINUS, DIVIDE, MULTIPLY, MODULE, CLEAR, NOTHING, EQUAL, COMMA, DELETE
    }

    var currAction = ACTIONS.NOTHING;

    var history = 0.0;
    var currNumber = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0.setOnClickListener{addNumber("0")}
        button1.setOnClickListener{addNumber("1")}
        button2.setOnClickListener{addNumber("2")}
        button3.setOnClickListener{addNumber("3")}
        button4.setOnClickListener{addNumber("4")}
        button5.setOnClickListener{addNumber("5")}
        button6.setOnClickListener{addNumber("6")}
        button7.setOnClickListener{addNumber("7")}
        button8.setOnClickListener{addNumber("8")}
        button9.setOnClickListener{addNumber("9")}

        buttonDelete.setOnClickListener{addAction(ACTIONS.DELETE)}
        buttonAC.setOnClickListener{addAction(ACTIONS.CLEAR)}
        buttonComma.setOnClickListener{addAction(ACTIONS.COMMA)}
        buttonModule.setOnClickListener{addAction(ACTIONS.MODULE)}
        buttonMinus.setOnClickListener{addAction(ACTIONS.MINUS)}
        buttonPlus.setOnClickListener{addAction(ACTIONS.PLUS)}
        buttonMultiply.setOnClickListener{addAction(ACTIONS.MULTIPLY)}
        buttonDivide.setOnClickListener{addAction(ACTIONS.DIVIDE)}
        buttonEqual.setOnClickListener{addAction(ACTIONS.EQUAL)}
    }


    private fun addNumber(number: String)
    {
        if (currNumber == "0")
        {
            currNumber = number
        }
        else
        {
            currNumber += number;
        }

        inputField.text = currNumber
    }

    private fun addAction(action: ACTIONS)
    {
        var displayOperation = ""
        if (action == ACTIONS.PLUS) { displayOperation = "+" }
        if (action == ACTIONS.MINUS) { displayOperation = "-" }
        if (action == ACTIONS.MULTIPLY) { displayOperation = "*" }
        if (action == ACTIONS.DIVIDE) { displayOperation = "/" }
        if (action == ACTIONS.EQUAL) { displayOperation = "=" }
        if (action == ACTIONS.MODULE) { displayOperation = "%" }

        operationField.text = displayOperation

        if (action == ACTIONS.CLEAR)
        {
            history = 0.0;
            currNumber = "0";
            currAction = ACTIONS.NOTHING;
            historyField.text = "0"
            inputField.text = "0"
            return;
        }
        if (action == ACTIONS.COMMA)
        {
            if (currNumber == "")
            {
                currNumber = "0.";
            }
            if (!currNumber.contains('.', true))
            {
                currNumber += ".";
            }
            inputField.text = currNumber
            return;
        }
        if (action == ACTIONS.DELETE)
        {
            if(currNumber.length == 0) return
            currNumber = currNumber.substring(0, currNumber.lastIndex)
            inputField.text = currNumber
            return;
        }
        if (currAction == ACTIONS.NOTHING)
        {
            currAction = action
            if (history == 0.0)
            {
                history = currNumber.toDouble()
                historyField.text = history.toString()
                currNumber = "0"
                inputField.text = currNumber
            }
            else
            {
                calculate()
            }
            return

        }

        if(currAction != ACTIONS.NOTHING) {
            calculate()
            currAction = action
            if (action == ACTIONS.EQUAL)
            {
                currAction = ACTIONS.NOTHING
                inputField.text = history.toString()
                currNumber = history.toString()
                history = 0.0
                historyField.text = ""
            }
        }

    }

    private fun calculate()
    {

        var number = 0.0
        val n = currNumber.toDoubleOrNull()
        if (n == null || n == 0.0) return
        number = n
        var result = 0.0;

        if (currAction == ACTIONS.DIVIDE)
        {
            result = history / number;
        }
        if (currAction == ACTIONS.MULTIPLY)
        {
            result = history * number;
        }
        if (currAction == ACTIONS.PLUS)
        {
            result = history + number;
        }
        if (currAction == ACTIONS.MINUS)
        {
            result = history - number;
        }
        if (currAction == ACTIONS.MODULE)
        {
            result = history % number;
        }

        if (currAction == ACTIONS.EQUAL)
        {
            history = 0.0
            historyField.text = "History : 0"

            currNumber = result.toString()
            inputField.text = currNumber

        }
        else
        {
            history = result;
            currNumber = "0";

            inputField.text = "0"
            historyField.text = history.toString()
        }

        currAction = ACTIONS.NOTHING;


    }
}