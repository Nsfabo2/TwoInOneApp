package com.example.twoinoneapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

/*
- ask the user to guess a number between 0 and 10
- generate a random number between 0 and 10
- take user input in the Edit Text field
- log user input and display whether or not the guess was correct
- only allow the user to enter numbers
*/

class NumbersActivity : AppCompatActivity() {

        lateinit var UserGuesses: ArrayList<String>
        lateinit var RemeinngGuesses: TextView
        lateinit var UserInput: EditText
        lateinit var Submitbtn: Button
        lateinit var MyRV: RecyclerView
        lateinit var Conslo: ConstraintLayout
        var Guess: Int = 0
        var counter: Int = 3
        var NewGame = false

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_numbers)
            //ask the user to guess a number between 0 and 10
            UserGuesses = ArrayList()
            RemeinngGuesses = findViewById(R.id.tvTrys)
            UserInput = findViewById(R.id.UserInput)
            Submitbtn = findViewById(R.id.GuessBtn)
            MyRV = findViewById(R.id.MyRV)
            Conslo = findViewById(R.id.Conslo)
            Guess = Random.nextInt(10)
            MyRV.adapter = RecyclerViewAdapter(UserGuesses)
            MyRV.layoutManager = LinearLayoutManager(this)
            Submitbtn.setOnClickListener (){ ButtonClicked() }

            title = "Numbers Game"
        }//end oncreate

        fun ContinueDialog(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Play again?")
            builder.setPositiveButton("Yes") { _: DialogInterface, i: Int -> this.recreate()}
            builder.setNegativeButton("No") { _: DialogInterface, i: Int -> finish()}
            builder.show()
        }//ContinueDialog

        //log user input and display whether or not the guess was correct
        /*fun InputChecked(rando: Int , toCheck: Int){
            if (toCheck == rando) {
                RemeinngGuesses.text = "Correct!!"
                UserGuesses.add(toCheck)
                MyRV.adapter = RecyclerViewAdapter(UserGuesses)
                MyRV.layoutManager = LinearLayoutManager(this)
                UserInput.setText("")
            } else {
                UserGuesses.add(toCheck)
                MyRV.adapter = RecyclerViewAdapter(UserGuesses)
                MyRV.layoutManager = LinearLayoutManager(this)
                UserInput.setText("")
                counter--
                RemeinngGuesses.text = "You Have $counter guesses left"
            }
        }//end InputChecked*/

        //generate a random number between 0 and 10
        fun ButtonClicked(){
            var UserGuess = UserInput.text.toString()
            if(UserGuess.isNotEmpty()){
                if (counter > 0) {
                    if(UserGuess.toInt() == Guess){
                        RemeinngGuesses.text = "Correct!!"
                        Snackbar.make(Conslo, "You win!", Snackbar.LENGTH_LONG).show()
                    }else{
                        counter--
                        UserGuesses.add("You guessed $UserGuess")
                        UserGuesses.add("You have $counter guesses left")
                        RemeinngGuesses.text = "You Have $counter guesses left"
                    }

                    if(counter==0){
                        UserGuesses.add("You lose - The correct answer was $Guess")
                        UserGuesses.add("Game Over")
                        Snackbar.make(Conslo, "You lose...\nThe correct answer was $Guess", Snackbar.LENGTH_LONG).show()
                        ContinueDialog()
                    }
                }
                UserInput.text.clear()
                UserInput.clearFocus()
                MyRV.adapter?.notifyDataSetChanged()
            }else{
                Snackbar.make(Conslo, "Please enter a number", Snackbar.LENGTH_SHORT).show()
            }
        }//end ButtonClicked

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
                startActivity(Intent(this, NumbersActivity::class.java))
                return true
            }
            R.id.other_game -> {
                startActivity(Intent(this, PhrasesActivity::class.java))
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