package com.gmail.maystruks08.hikingfood

import android.Manifest.permission.*
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject
import com.gmail.maystruks08.hikingfood.utils.PRESS_TWICE_INTERVAL
import com.gmail.maystruks08.hikingfood.core.navigation.AppNavigator
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gmail.maystruks08.hikingfood.utils.extensions.toast


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private var lastBackPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        router.newRootScreen(Screens.AllMenuScreen())
    }

    private val navigator: Navigator =
        object : AppNavigator(this, supportFragmentManager, R.id.mainContainer) {
            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
        requestPermissions()
    }


    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, RECORD_AUDIO)) {
                toast("Please grant permissions to write external storage ")
                requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
                    PERMISSIONS_READ_WRITE_EXTERNAL_STORAGE
                )
            } else {
                requestPermissions(
                    this,
                    arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
                    PERMISSIONS_READ_WRITE_EXTERNAL_STORAGE
                )
            }
        } else if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
          //do work
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_READ_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    toast("Permissions Denied to write external storage")
                }
                return
            }
        }
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }


    override fun onBackPressed() {
        this.hideSoftKeyboard()
        this.navigateBack()
    }

    private fun navigateBack() {
        when {
            supportFragmentManager.backStackEntryCount > 0 -> router.exit()
            lastBackPressTime < System.currentTimeMillis() - PRESS_TWICE_INTERVAL -> {
                Toast.makeText(this, R.string.toast_exit_app_warning_text, Toast.LENGTH_SHORT).show()
                lastBackPressTime = System.currentTimeMillis()
            }
            else -> router.exit()
        }
    }

    private fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    companion object{

       private const val PERMISSIONS_READ_WRITE_EXTERNAL_STORAGE = 7

    }
}