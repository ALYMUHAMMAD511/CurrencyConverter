package com.example.currencyconverter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var amountEditText : TextInputEditText
    private lateinit var resultEditText : TextInputEditText
    private lateinit var convertButton : Button
    private lateinit var fromDropDownMenu : AutoCompleteTextView
    private lateinit var toDropDownMenu : AutoCompleteTextView
    private lateinit var toolBar : Toolbar

    private val usd = "American Dollar (USD)"
    private val egp = "Egyptian Pound (EGP)"
    private val gbp = "English Pound (GBP)"
    private val aed = "United Arab Emirates Dirham (AED)"

    private val currenciesValues = mapOf(
        usd to 1.0,
        egp to 50.84,
        gbp to 0.8,
        aed to 3.67,
    )
    


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initializeViews()

        populateDropDownMenu()

        toolBar.inflateMenu(R.menu.options_menu)
        toolBar.setOnMenuItemClickListener {
            menuItem ->
            if (menuItem.itemId == R.id.share_action) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val message = "${amountEditText.text.toString()} ${fromDropDownMenu.text} = ${resultEditText.text.toString()} ${toDropDownMenu.text}"
                shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                if (shareIntent.resolveActivity(packageManager) != null){
                    startActivity(shareIntent)
                }
                else {
                    Toast.makeText(this, "No Activity Found to handle this action", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        fromDropDownMenu.setOnItemClickListener { _, _, _, _ ->  convertCurrency()}
        toDropDownMenu.setOnItemClickListener { _, _, _, _ ->  convertCurrency()}

        amountEditText.addTextChangedListener{
            convertCurrency()
        }

        convertButton.setOnClickListener {
            val intent = Intent(this, MainActivity2 :: class.java)
            intent.putExtra("My Key", 511)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    private fun convertCurrency() {
            if (amountEditText.text.toString().isNotEmpty()) {
                val amount = amountEditText.text.toString().toDouble()
                val fromCurrencySelectedValue = currenciesValues[fromDropDownMenu.text.toString()]
                val toCurrencySelectedValue = currenciesValues[toDropDownMenu.text.toString()]
                val result = Math.round((amount * toCurrencySelectedValue!!) / fromCurrencySelectedValue!!* 1000.0) / 1000.0
                resultEditText.setText(String.format(result.toString()))
            } else {
                amountEditText.error = "Amount Field is Required"
//                val snackBar = Snackbar.make(fromDropDownMenu, "Amount Field is Required", Snackbar.LENGTH_SHORT)
//                snackBar.show()
//                snackBar.setAction("OK"){
//                    Toast.makeText(this, "Action Trigger", Toast.LENGTH_SHORT).show()
//                }
            }
    }

    private fun initializeViews() {
        amountEditText = findViewById(R.id.amount_text_input_edit_text)
        resultEditText = findViewById(R.id.result_text_input_edit_text)
        convertButton = findViewById(R.id.convert_button)
        fromDropDownMenu = findViewById(R.id.from_auto_complete_textview)
        toDropDownMenu = findViewById(R.id.to_auto_complete_textview)
        toolBar = findViewById(R.id.toolbar)
    }

    private fun populateDropDownMenu() {
        val currencies = listOf(usd, egp, gbp, aed)
        val fromAdapter = ArrayAdapter(this, R.layout.drop_down_list_item, currencies)
        fromDropDownMenu.setAdapter(fromAdapter)
        val toAdapter = ArrayAdapter(this, R.layout.drop_down_list_item, currencies)
        toDropDownMenu.setAdapter(toAdapter)
    }
}