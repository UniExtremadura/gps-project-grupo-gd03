package es.unex.giiis.asee.spotifilter.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import es.unex.giiis.asee.spotifilter.R
import es.unex.giiis.asee.spotifilter.data.model.User
import es.unex.giiis.asee.spotifilter.databinding.ActivityHomeBinding
import es.unex.giiis.asee.spotifilter.view.home.fragment.albums.AlbumsFragment
import es.unex.giiis.asee.spotifilter.view.home.fragment.tracks.TracksFragment
import es.unex.giiis.asee.spotifilter.view.home.fragment.tracks.TracksFragmentDirections

class HomeActivity : AppCompatActivity(), UserProvider {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment)
            .navController
    }
    private lateinit var user: User

    companion object {

        fun start(context: Context, user: User) {
            val intent = Intent(context, HomeActivity::class.java).apply {
                putExtra("USER", user)
            }
            context.startActivity(intent)
        }

    }

    override fun getUser() = user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        user = intent.getSerializableExtra("USER") as User
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val searchItem = menu?.findItem(R.id.actionSearch)
        val searchView = searchItem?.actionView as SearchView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    when (val currentFragment =
                        navHostFragment?.childFragmentManager?.fragments?.get(0)) {
                        is AlbumsFragment -> {
                            currentFragment.updateRecyclerView(query!!)
                        }
                        is TracksFragment -> {
                            currentFragment.updateRecyclerView(query!!)
                        }
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    when (val currentFragment =
                        navHostFragment?.childFragmentManager?.fragments?.get(0)) {
                        is AlbumsFragment -> {
                            currentFragment.updateRecyclerView(newText!!)
                        }
                        is TracksFragment -> {
                            currentFragment.updateRecyclerView(newText!!)
                        }
                    }
                    return true
                }
            }
        )
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actionSettings -> {
            val action = TracksFragmentDirections.actionHomeToSettingsFragment()
            navController.navigate(action)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setUpUI() {
        binding.bottomNavigationView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.albumsFragment, R.id.playlistsFragment, R.id.tracksFragment)
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.settingsFragment -> {
                    binding.toolbar.menu.findItem(R.id.actionSearch).isVisible = false
                    binding.toolbar.menu.findItem(R.id.actionSettings).isVisible = false
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.playlistsFragment -> {
                    binding.toolbar.menu.findItem(R.id.actionSearch).isVisible = false
                    binding.toolbar.menu.findItem(R.id.actionSettings).isVisible = true
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                else -> {
                    binding.toolbar.menu.findItem(R.id.actionSearch).isVisible = true
                    binding.toolbar.menu.findItem(R.id.actionSettings).isVisible = true
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

}