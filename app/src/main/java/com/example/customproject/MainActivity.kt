package com.example.customproject

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.customproject.controller.NotificationController
import com.example.customproject.controller.TagController
import com.example.customproject.controller.TransactionController
import com.example.customproject.databinding.ActivityMainBinding
import com.example.customproject.model.TransactionType
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.QueryDocumentSnapshot


class MainActivity : AppCompatActivity(){
    private val transactionController:TransactionController = TransactionController()
    private val notificationController:NotificationController = NotificationController()
    private val tagController:TagController = TagController()
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var clicked: Boolean = false
    private val rotateOpen: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.fab_open)}
    private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.fab_close)}
    private var mode = "light"
    private lateinit var sharedPreferences:SharedPreferences
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_nav_menu, menu)
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val mode=sharedPreferences.getString("mode","").toString()
        if (menu != null) {
            val button =menu.findItem(R.id.setmode)
            if (mode=="light"){
               button.setIcon(R.drawable.ic_baseline_bedtime_24)
            }
            else{
                button.setIcon(R.drawable.ic_baseline_wb_sunny_24)
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPreferences:SharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()
        return when (item.itemId){
            R.id.setmode->{
                if (mode == "light") {
                    mode="dark"
                    AppCompatDelegate.setDefaultNightMode(  AppCompatDelegate
                        .MODE_NIGHT_YES)
                    item.setIcon(R.drawable.ic_baseline_wb_sunny_24)
                    myEdit.putString("mode",mode)
                    myEdit.apply()
                }
                else{
                    mode="light"
                    AppCompatDelegate.setDefaultNightMode(  AppCompatDelegate
                        .MODE_NIGHT_NO)
                    item.setIcon(R.drawable.ic_baseline_bedtime_24)
                    myEdit.putString("mode",mode)
                    myEdit.apply()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        if (sharedPreferences.getString("mode","") != "")
        {
            mode = sharedPreferences.getString("mode","").toString()
            if (mode=="light"){
                AppCompatDelegate.setDefaultNightMode(  AppCompatDelegate
                    .MODE_NIGHT_NO)

            }
            else{
                AppCompatDelegate.setDefaultNightMode(  AppCompatDelegate
                    .MODE_NIGHT_YES)

            }
        }
        val navView: BottomNavigationView = binding.navView



        val transButton = findViewById<FloatingActionButton>(R.id.trans)
        transButton.setOnClickListener {
            addTrans()
        }


        val labelButton = findViewById<FloatingActionButton>(R.id.lab)
        labelButton.setOnClickListener {
            addLabel()
        }

        val addButton = findViewById<FloatingActionButton>(R.id.fab)
        addButton.setOnClickListener {
            onAddButtonClicked(transButton,labelButton,addButton)
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_transaction_type,R.id.navigation_calendar, R.id.navigation_notifications,R.id.navigation_account,R.id.navigation_transaction
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun onAddButtonClicked(b1:FloatingActionButton,b2:FloatingActionButton,b3:FloatingActionButton) {
        setVisibility(clicked,b1,b2)
        setAnimation(clicked,b1, b2,b3)
        clicked = !clicked
    }

    private fun setVisibility(clicked:Boolean,b1: FloatingActionButton,b2: FloatingActionButton) {
        if (!clicked)
        {
            b1.visibility= View.VISIBLE
            b2.visibility = View.VISIBLE
            b1.isClickable = true
            b2.isClickable = true

        }
        else{
            b1.visibility= View.INVISIBLE
            b2.visibility = View.INVISIBLE
            b1.isClickable = false
            b2.isClickable = false
        }
    }

    private fun setAnimation(clicked: Boolean,b1: FloatingActionButton,b2: FloatingActionButton, b3: FloatingActionButton) {
        if (!clicked)
        {
            b1.startAnimation(fromBottom)
            b2.startAnimation(fromBottom)
            b3.startAnimation(rotateOpen)
        }
        else{
            b1.startAnimation(toBottom)
            b2.startAnimation(toBottom)
            b3.startAnimation(rotateClose)
        }
    }


    private fun addTrans(){

        val layoutParams = LinearLayout.LayoutParams(  LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins( 60,20,60,20)

        val valueinput = EditText(this)
        valueinput.hint = "Value"
        valueinput.gravity = Gravity.CENTER
        valueinput.layoutParams = layoutParams
        valueinput.inputType = InputType.TYPE_CLASS_NUMBER


        val descinput = EditText(this)
        descinput.hint = "Description"
        descinput.gravity = Gravity.CENTER
        descinput.layoutParams = layoutParams


        val typeinput = Spinner(this)
        typeinput.gravity = Gravity.CENTER
        typeinput.layoutParams = layoutParams
        typeinput.textAlignment= View.TEXT_ALIGNMENT_TEXT_START


        val transtype = arrayOf("Spending","Income")
        val transAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,transtype)
        transAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeinput.adapter = transAdapter

        val taginput = Spinner(this)
        taginput.gravity = Gravity.CENTER
        taginput.layoutParams = layoutParams
        taginput.textAlignment= View.TEXT_ALIGNMENT_TEXT_START

        val taglists = arrayListOf<String>()
        val tagAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,taglists)
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        taginput.adapter = tagAdapter
        typeinput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (typeinput.selectedItem == "Income") {
                    taglists.clear()
                    tagController.getAll(TransactionType.Income).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document: QueryDocumentSnapshot in task.result) {
                                val tag = document.getString("desc")
                                if (tag != "") {
                                    taglists.add(tag as String)
                                }
                            }
                            tagAdapter.notifyDataSetChanged()
                        }
                    }

                } else {
                    taglists.clear()
                    tagController.getAll(TransactionType.Spending).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document: QueryDocumentSnapshot in task.result) {
                                val tag = document.getString("desc")
                                if (tag != null) {
                                    taglists.add(tag)
                                }
                            }
                            tagAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val lp = LinearLayout(this)
        lp.orientation = LinearLayout.VERTICAL

        lp.addView( valueinput ,layoutParams)
        lp.addView( descinput,layoutParams )
        lp.addView(typeinput,layoutParams)
        lp.addView(taginput,layoutParams)

        val builder = AlertDialog.Builder(this)

        builder.setMessage(" New transaction")
            .setPositiveButton("Create") { _, _ ->
                val result = transactionController.Create(
                    TransactionType.Income,
                    valueinput.text.toString().toInt(),
                    descinput.text.toString()
                )
                notificationController.Add("You have earn " + valueinput.text + " for " + descinput.toString())
                transactionController.Add(result, TransactionType.Income,taginput.selectedItem.toString())
                val myToast = Toast.makeText(this, "Add Successfully", LENGTH_SHORT)
                myToast.setGravity(Gravity.START, 200, 200)
                myToast.show()
            }.setNegativeButton("Cancel") { _, _ ->
            }
            .setView(lp)
        val alertdialog = builder.create()
        alertdialog.show()
    }

    private fun addLabel(){
        val layoutParams = LinearLayout.LayoutParams(  LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins( 60,20,60,20)

        val nameinput = EditText(this)
        nameinput.hint = "Name"
        nameinput.gravity = Gravity.CENTER
        nameinput.layoutParams = layoutParams


        val colorinput = EditText(this)
        colorinput.hint = "Colour"
        colorinput.gravity = Gravity.CENTER
        colorinput.layoutParams = layoutParams

        val typeinput = Spinner(this)
        typeinput.gravity = Gravity.CENTER
        typeinput.layoutParams = layoutParams
        typeinput.textAlignment= View.TEXT_ALIGNMENT_GRAVITY


        val transtype = arrayOf("Income","Spending")
        val arrayAdapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,transtype)
        arrayAdapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item))
        typeinput.adapter = arrayAdapter

        val lp = LinearLayout(this)
        lp.orientation = LinearLayout.VERTICAL

        lp.addView( nameinput ,layoutParams)
        lp.addView( colorinput,layoutParams )
        lp.addView(typeinput,layoutParams)


        val builder = AlertDialog.Builder(this)


        builder.setMessage(" New transaction's tag")
            .setPositiveButton("Create") { _, _ ->
                var transactionType = TransactionType.Income
                if (typeinput.selectedItem.toString() == "Spending") {
                    transactionType = TransactionType.Spending
                }
                val result = tagController.Create(
                    nameinput.text.toString(),
                    colorinput.text.toString(),
                    transactionType
                )
                notificationController.Add("You have created new tag: " + nameinput.text + " for " + typeinput.selectedItem.toString())
                tagController.Add(result)
                val myToast = Toast.makeText(this, "Add Successfully", LENGTH_SHORT)
                myToast.setGravity(Gravity.START, 200, 200)
                myToast.show()
            }.setNegativeButton("Cancel") { _, _ ->


            }
            .setView(lp)
        val alertdialog = builder.create()
        alertdialog.show()
    }

}