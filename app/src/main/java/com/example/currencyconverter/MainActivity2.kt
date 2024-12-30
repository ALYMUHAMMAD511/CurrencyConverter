package com.example.currencyconverter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    private lateinit var textView : TextView
    private lateinit var callButton: Button
    private lateinit var messageButton: Button
    private lateinit var browserButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        textView = findViewById(R.id.destination_textview)
        callButton = findViewById(R.id.call_button)
        messageButton = findViewById(R.id.message_button)
        browserButton = findViewById(R.id.browser_button)

        //Explicit Intent
        val myData = intent.getIntExtra("My Key", 0)
        textView.text = String.format(myData.toString())

        //Implicit Intents
        callButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:01129630050")
            startActivity(callIntent)
        }

        messageButton.setOnClickListener {
            val sendMessageIntent = Intent(Intent.ACTION_SENDTO)
            sendMessageIntent.data = Uri.parse("mailto:")
            sendMessageIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("example@gmail.com"))
            sendMessageIntent.putExtra(Intent.EXTRA_SUBJECT, "Invitation")
            sendMessageIntent.putExtra(Intent.EXTRA_TEXT, "Invitation Body")
            startActivity(sendMessageIntent)
        }

        browserButton.setOnClickListener {
            val openBrowserIntent = Intent(Intent.ACTION_VIEW)
            openBrowserIntent.data = Uri.parse("https://www.google.com")
            openBrowserIntent.setPackage("com.android.chrome")
            if (openBrowserIntent.resolveActivity(packageManager) != null)
            {
                startActivity(openBrowserIntent)
            }
            else
            {
                Toast.makeText(this, "No Activity Found to handle this action", Toast.LENGTH_SHORT).show()
            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}