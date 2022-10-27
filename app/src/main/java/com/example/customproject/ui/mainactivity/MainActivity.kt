package com.example.customproject.ui.mainactivity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.customproject.R
import com.example.customproject.controller.NotificationController
import com.example.customproject.controller.TagController
import com.example.customproject.controller.TransactionController
import com.example.customproject.databinding.ActivityMainBinding
import com.example.customproject.model.TransactionType
import com.example.customproject.ui.home.HomeFragment
import com.example.customproject.ui.home.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.QueryDocumentSnapshot


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val rotateOpen: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.fab_open)}
    private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.fab_close)}
    private var mode = "light"
    private var clicked: Boolean = false
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var mainActivityViewModel: MainActivityViewModel
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_nav_menu, menu)
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
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
            R.id.setmode ->{
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
                R.id.navigation_home,
                R.id.navigation_transaction_type,
                R.id.navigation_calendar,
                R.id.navigation_notifications,
                R.id.navigation_account,
                R.id.navigation_transaction,
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        Log.d("Resume","Resume")
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


    @SuppressLint("ResourceType")
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


        val transtype = arrayOf("Income","Spending")
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
                    mainActivityViewModel.getLabel(taglists,tagAdapter, typeinput.selectedItem.toString())
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
                if (typeinput.selectedItem.toString() == "Income")
                {
                mainActivityViewModel.createTransaction(TransactionType.Income, valueinput.text.toString().toInt(),descinput.text.toString(),taginput.selectedItem.toString())
                mainActivityViewModel.createNotification("You have earn " + valueinput.text.toString() + " for " + descinput.text.toString())
                val myToast = Toast.makeText(this, "Add Successfully", LENGTH_SHORT)
                myToast.setGravity(Gravity.START, 200, 200)
                myToast.show()
                    val currentFragment = supportFragmentManager.fragments.last()
                    val frag =currentFragment.childFragmentManager.fragments[0]
                    frag.getFragmentManager()?.beginTransaction()?.detach(frag)?.commit();
                    frag.getFragmentManager()?.beginTransaction()?.attach(frag)?.commit();
                }else{
                    mainActivityViewModel.createTransaction(TransactionType.Spending, valueinput.text.toString().toInt(),descinput.text.toString(),taginput.selectedItem.toString())
                    mainActivityViewModel.createNotification("You have spend " + valueinput.text.toString() + " for " + descinput.text.toString())
                    val myToast = Toast.makeText(this, "Add Successfully", LENGTH_SHORT)
                    myToast.setGravity(Gravity.START, 200, 200)
                    myToast.show()
                    val currentFragment = supportFragmentManager.fragments.last()
                    val frag =currentFragment.childFragmentManager.fragments[0]
                    frag.getFragmentManager()?.beginTransaction()?.detach(frag)?.commit();
                    frag.getFragmentManager()?.beginTransaction()?.attach(frag)?.commit();

                }
            }.setNegativeButton("Cancel") { _, _ ->

            }
            .setView(lp)
        val alertdialog = builder.create()
        alertdialog.show()
        if (valueinput.text.isEmpty() and descinput.text.isEmpty()) {
            alertdialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        }

        valueinput.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (valueinput.text.isNotEmpty() and descinput.text.isNotEmpty())
                    alertdialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
            }
        })

        descinput.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (valueinput.text.isNotEmpty() and descinput.text.isNotEmpty()) {
                    alertdialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                }
            }

        })
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
                mainActivityViewModel.createLabel( nameinput.text.toString(), colorinput.text.toString(),transactionType)
                mainActivityViewModel.createNotification("You have created new tag: " + nameinput.text.toString() + " for " + typeinput.selectedItem.toString())
                val myToast = Toast.makeText(this, "Add Successfully", LENGTH_SHORT)
                myToast.setGravity(Gravity.START, 200, 200)
                myToast.show()
                val currentFragment = supportFragmentManager.fragments.last()
                val frag =currentFragment.childFragmentManager.fragments[0]
                frag.getFragmentManager()?.beginTransaction()?.detach(frag)?.commit();
                frag.getFragmentManager()?.beginTransaction()?.attach(frag)?.commit();
            }.setNegativeButton("Cancel") { _, _ ->


            }
            .setView(lp)
        val alertdialog = builder.create()
        alertdialog.show()
        if (nameinput.text.isEmpty() and colorinput.text.isEmpty()) {
            alertdialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        }
        nameinput.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (nameinput.text.isNotEmpty() and colorinput.text.isNotEmpty()) {
                    alertdialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                }
            }
        })
    }

}