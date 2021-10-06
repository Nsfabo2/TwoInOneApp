package com.example.twoinoneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

/*
Use the Number Guessing Game and Guess The Phrase to create a 2 in 1 Application with a Main Menu
- Each game should be its own activity #Numbers and #Phrases
- Change the Main Activity to hold two buttons
- Each button should launch the corresponding activity/game
- Create a drop-down menu to allow easier navigation
- in the main menu the options should be 'Numbers Game' and 'Guess the Phrase' to launch the respective activity
- in each activity, the options should allow the user to start a new game, switch to the other game, and go back to the main menu (can you reuse the same menu xml
 */

class MainActivity : AppCompatActivity(){

lateinit var TitleTextView: TextView
lateinit var NumbersGameButton: Button
lateinit var PhrasesGameButton: Button
lateinit var ConsLO: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Change the Main Activity to hold two buttons
        TitleTextView = findViewById(R.id.GameTV)
        NumbersGameButton = findViewById(R.id.Numberbtn)
        PhrasesGameButton = findViewById(R.id.Phrasebtn)

        //Each button should launch the corresponding activity/game
        NumbersGameButton.setOnClickListener {
            val intent = Intent(this, NumbersActivity::class.java)
            startActivity(intent)
        }

        PhrasesGameButton.setOnClickListener {
            val intent = Intent(this, PhrasesActivity::class.java)
            startActivity(intent)
        }
        title = "Main Activity"
    }//end oncreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }//end oncreateoptionsmenu

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.numbers_game_mainmenu -> {
                startActivity(Intent(this, NumbersActivity::class.java))
                return true
            }
            R.id.phrase_game_mainmenu ->{
                startActivity(Intent(this, PhrasesActivity::class.java))
                return true
            }

        }//end when

        return super.onOptionsItemSelected(item)
    }//end onoptionsitem

}//end class