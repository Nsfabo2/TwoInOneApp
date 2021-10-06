package com.example.twoinoneapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class PhrasesActivity : AppCompatActivity() {

    lateinit var PhraseTV: TextView
    lateinit var LetterTV: TextView
    lateinit var GuessedTF: EditText
    lateinit var GuessButton: Button
    lateinit var MyRVPhrase: RecyclerView
    lateinit var ConsLOPhrase: ConstraintLayout
    lateinit var Lefted: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phrases)

         PhraseTV = findViewById(R.id.PhraseTV)
         LetterTV = findViewById(R.id.LetterTV)
         GuessedTF = findViewById(R.id.GuessedTF)
         GuessButton = findViewById(R.id.GuessButton)
         MyRVPhrase = findViewById(R.id.MyRVPhrase)
         ConsLOPhrase = findViewById(R.id.ConsLOPhrase)
         Lefted = findViewById(R.id.Lefted)

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        var Chances = sharedPreferences.getInt("Score", 10)
        val Results = mutableListOf<String>()
        val PhrasesList = listOf<String>(
            "HIT THE HAY",
            "COASTS AN ARM AND A LEG",
            "BREAK A LEG",
            "BETTER LATE THAN NEVER",
            "RULE OF THUMB",
        )
        val RandomPhrase = PhrasesList[Random.nextInt(PhrasesList.size)]
        var starPhrase = Regex("[A-Za-z]").replace(RandomPhrase, "*")
        PhraseTV.text = starPhrase
        var enterPhrase = true

        MyRVPhrase.adapter = RecyclerViewAdapter(Results as ArrayList<String>)
        MyRVPhrase.layoutManager = LinearLayoutManager(this)


        GuessButton.setOnClickListener{
            var userinput = GuessedTF.text.toString().trim().toUpperCase()
            if(PhraseTV.text.contains("*")){
                if(userinput.isEmpty()){ Snackbar.make(ConsLOPhrase, "You must enter at least one letter", Snackbar.LENGTH_SHORT).show() }
                else{
                    if(enterPhrase){
                        if(userinput == RandomPhrase){
                            Results.add("Greet job")
                            PhraseTV.text = RandomPhrase
                            PhraseTV.textSize = 18f
                            GuessButton.isClickable = false
                        }
                        else{
                            Results.add("Wrong guess: $userinput")
                        }
                        GuessedTF.setText("")
                        GuessedTF.hint = "Guess a letter"
                        enterPhrase = false
                    }
                    else{
                        LetterTV.text = userinput[0].toString()
                        if(RandomPhrase.contains(userinput[0])){
                            var counter = 0
                            var phraseChar = PhraseTV.text.toString().toCharArray()
                            for(i in 0..RandomPhrase.length-1){
                                if(RandomPhrase[i] == userinput[0]) {
                                    phraseChar[i] = userinput[0]
                                    counter++
                                }
                            }
                            PhraseTV.text = String(phraseChar)
                            Results.add("Found $counter $userinput(s)")
                        }
                        else{
                            Results.add("Wrong guess: $userinput")
                            Results.add("${--Chances} guesses remaining")
                            val editor = sharedPreferences.edit()
                            editor.putInt("Score", Chances)
                            editor.commit()
                            Lefted.text = sharedPreferences.getInt("Score", Chances).toString()
                        }
                        GuessedTF.setText("")
                        GuessedTF.hint = "Guess the full phrase"
                        enterPhrase = true
                    }
                }
            }
            else{
                Results.add("Greet job")
                PhraseTV.text = RandomPhrase
                PhraseTV.textSize = 18f
                GuessButton.isClickable = false
            }

            MyRVPhrase.adapter = RecyclerViewAdapter(Results)
            MyRVPhrase.layoutManager = LinearLayoutManager(this)
            MyRVPhrase.scrollToPosition(Results.size - 1)

            if(Chances == 0){
                Results.add("Game over")
                GuessButton.isClickable = false
            }
        }
        title = "Phrases Game"
    }//end on create

    //Menu stuff
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu, menu)
        return true
    }//end

    /*override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem = menu!!.getItem(1)
        if(item.title == "Other Game"){ item.title = "Guess The Phrase" }
        return super.onPrepareOptionsMenu(menu)
    }//end */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.new_game -> {
                startActivity(Intent(this, PhrasesActivity::class.java))
                return true
            }
            R.id.other_game -> {
                startActivity(Intent(this, NumbersActivity::class.java))
                return true
            }
            R.id.main_menuu -> {
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }//end

}//end class