package com.example.gamesbooks.presentation.bookscreen

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesbooks.R
import com.google.android.material.navigation.NavigationView
import com.example.gamesbooks.adapter.characters.CharactersAdapter
import com.example.gamesbooks.data.local.entity.BooksEntity
import com.example.gamesbooks.data.local.entity.CharactersEntity
import com.example.gamesbooks.data.remote.ResponseState
import com.example.gamesbooks.databinding.ActivityGameBookBinding
import com.example.gamesbooks.util.NetworkUtils
import com.example.gamesbooks.util.toastMessage

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.min

@AndroidEntryPoint
class GameBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBookBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var charListItemView: RecyclerView
    private val bookViewModel: GameBookViewModel by viewModels()
    private var charListAdapter: CharactersAdapter? = null

    private lateinit var charactersUrlList: ArrayList<String>
    private var startIndex = 0
    private var patchSize = 0
    private var charUrlListSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        drawerLayout = binding.drawerLayout
        initializeSideDrawer()
        charListItemView = binding.appBarMain.characterContentMain.charRvView

        if (savedInstanceState == null) {
            fetchBookData()
        }
        bookViewModel.liveDataBooks.observe(this) { result ->
            handleNetworkState(result) {
                bindNavigationItem(it)
            }
        }

        bookViewModel.liveDataChars.observe(this) { result ->
            handleNetworkState(result) {
                updateCharacterListAdapter(it)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        charListItemView.layoutManager = layoutManager

        charListItemView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                handleScroll(layoutManager)
            }
        })

    }

    private inline fun <T> handleNetworkState(
        result: ResponseState<T>,
        onSuccess: (data: T) -> Unit
    ) {
        when (result) {
            is ResponseState.Loading -> showProgressBar(true)
            is ResponseState.Success -> {
                showProgressBar()
                result.data?.let { onSuccess(it) }
            }

            is ResponseState.Failure -> {
                showProgressBar()
                toastMessage("${result.message}")
            }
        }
    }

    private fun handleScroll(layoutManager: LinearLayoutManager) {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 0) {
            startIndex = patchSize + 1
            patchSize += 14
            validateCharacterSlice(startIndex, patchSize)
        }
    }

    private fun bindNavigationItem(data: List<BooksEntity>?) {
        val navigationView: NavigationView = binding.navView
        val menu = navigationView.menu

        data?.forEachIndexed { index, menuItemData ->
            val menuItem = menu.add(Menu.NONE, index, Menu.NONE, menuItemData.bookName)

            if (index == 0) {
                data[0].let {
                    menuItem.isChecked = true
                    fetchCharacterData(it)
                }
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            val position = menuItem.itemId

            for (i in 0 until menu.size()) {
                menu.getItem(i).isChecked = false
            }

            data?.get(position)?.let {
                menuItem.isChecked = true
                fetchCharacterData(it)
            }

            toastMessage(menuItem.title.toString())
            true
        }
    }

    private fun fetchCharacterData(books: BooksEntity) {
        closeDrawer()
        updateTitle(books)
        charactersUrlList = ArrayList()
        charactersUrlList.addAll(books.charsUrl)
        charUrlListSize = charactersUrlList.size - 1
        startIndex = 0
        patchSize = 20
        configureCharacterListAdapter()
        validateCharacterSlice(startIndex, patchSize)
    }

    private fun initializeSideDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun showProgressBar(isVisible: Boolean = false) {
        findViewById<ProgressBar>(R.id.progressBar)?.visibility =
            if (isVisible) View.VISIBLE else View.GONE
    }

    private fun fetchBookData() {
        if (NetworkUtils.isNetWorkAvailable(applicationContext))
            bookViewModel.callBookApi()
        else
            toastMessage(getString(R.string.internetMessage))
    }

    private fun updateTitle(book: BooksEntity?) {
        if (book != null) {
            binding.appBarMain.toolbar.title = book.bookName
        }
    }

    private fun configureCharacterListAdapter() {
        charListAdapter = CharactersAdapter()
        charListItemView.adapter = charListAdapter
    }

    private fun launchCharacterApi(sliceData: List<String>) {
        bookViewModel.viewModelScope.launch {
            bookViewModel.callCharacterApi(sliceData)
        }
    }


    private fun updateCharacterListAdapter(data: List<CharactersEntity>?) {
        charListAdapter?.setCharacters(data)
    }


    private fun validateCharacterSlice(initialCount: Int, patchCount: Int) {
        if (patchCount <= charUrlListSize) {
            launchCharacterApi(charactersUrlList.slice(initialCount..patchCount))
        } else {
            if (patchSize < charUrlListSize) {
                val remainingItems = charUrlListSize - patchSize
                val newPatchCount = patchSize + min(PAGE_SIZE, remainingItems)
                launchCharacterApi(charactersUrlList.slice(patchSize..newPatchCount))
                patchSize = newPatchCount
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
