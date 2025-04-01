package viewmodel

// viewmodel/PhilosophyViewModel.kt
class PhilosophyViewModel(private val repository: PhilosophyRepository) : ViewModel() {
    private val _topics = MutableLiveData<List<PhilosophyTopic>>()
    val topics: LiveData<List<PhilosophyTopic>> get() = _topics

    private val _userProgress = MutableLiveData<UserProgress>()
    val userProgress: LiveData<UserProgress> get() = _userProgress

    init {
        viewModelScope.launch {
            repository.getAllTopics().collect { _topics.value = it }
            repository.getUserProgress().collect { _userProgress.value = it }
        }
    }

    fun completeTopic(topicId: Int) {
        viewModelScope.launch { repository.completeTopic(topicId) }
    }
}