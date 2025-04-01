package com.example.nous

import adapter.TopicAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import data.PhilosophyTopic
import data.database.PhilosophyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.produce
import repository.PhilosophyRepository
import viewmodel.PhilosophyViewModel

// MainActivity.kt
class MainActivity<PhilosophyViewModel> : AppCompatActivity() {
    private var viewModel: PhilosophyViewModel = TODO()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel'i başlat
        val dao = PhilosophyDatabase.getDatabase(this).philosophyDao()
        viewModel = ViewModelProvider(
            this,
            PhilosophyViewModelFactory(PhilosophyRepository(dao))
        )[PhilosophyViewModel::class.java]

        // RecyclerView'ı ayarla
        adapter = TopicAdapter(emptyList()) { topicId -> viewModel.completeTopic(topicId) }
        binding.rvTopics.adapter = adapter
        binding.rvTopics.layoutManager = LinearLayoutManager(this)

        // LiveData'yı observe et
        viewModel.topics.observe(this) { adapter.updateTopics(it) }
        viewModel.userProgress.observe(this) {
            binding.tvStreak.text = "Günlük Seri: ${it.dailyStreak}"
        }

        CoroutineScope(Dispatchers.IO).produce<> {  }launch {
            val dao = PhilosophyDatabase.getDatabase(this@MainActivity).philosophyDao()
            if (dao.getAllTopics().first().isEmpty()) {
                dao.insertTopics(
                    listOf(
                        PhilosophyTopic(
                            title = "Stoacılık Temelleri",
                            philosopher = "Epiktetos",
                            difficulty = "Başlangıç"
                        )
                    )
                )
            }
        }
    }

    // ViewModelFactory
    class PhilosophyViewModelFactory(private val repository: PhilosophyRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PhilosophyViewModel(repository) as T
        }
    }
}