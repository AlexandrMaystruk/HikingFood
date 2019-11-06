package com.gmail.maystruks08.hikingfood.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject
import androidx.core.content.ContextCompat
import com.gmail.maystruks08.hikingfood.App
import com.gmail.maystruks08.hikingfood.ConfigToolbar
import com.gmail.maystruks08.hikingfood.utils.PRESS_TWICE_INTERVAL
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.core.navigation.AppNavigator
import kotlinx.android.synthetic.main.activity_main.*
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.view.stepprogress.StepProgressView

class MainActivity : AppCompatActivity(), ConfigToolbar {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private var lastBackPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        setToolbar()
        router.newRootScreen(Screens.AllMenuScreen())
    }


    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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

    override fun enableToolbar() {
        supportActionBar?.show()
    }

    override fun disableToolbar() {
        supportActionBar?.hide()
    }

    override fun setBackgroundColor(color: Int) {
        ContextCompat.getColor(this, color).let {
            toolbar.setBackgroundColor(it)
        }
    }

    override fun setToolbarTitle(title: String) {
        toolbarTitle.text = title
    }

    override fun disableOverlay() {
        collapseView.visibility = View.VISIBLE
    }

    override fun enableOverlay() {
        collapseView.visibility = View.GONE
    }

    override fun setBackground(icon: Int) {
        toolbar.background = getDrawable(icon)
    }

    override fun setNavigationIcon(@DrawableRes icon: Int) {
        toolbar.setNavigationIcon(icon)
        toolbar.setNavigationOnClickListener {
            navigateBack()
        }
    }

    override fun removeNavigationIcon() {
        toolbar.navigationIcon = null
    }

    override fun enableBottomBar() {
    }

    override fun disableBottomBar() {
    }
}