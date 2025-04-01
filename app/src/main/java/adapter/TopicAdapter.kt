package adapter

// adapter/TopicAdapter.kt
class TopicAdapter(
    private var topics: List<PhilosophyTopic>,
    private val onComplete: (Int) -> Unit
) : RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val btnComplete: Button = itemView.findViewById(R.id.btnComplete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_topic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topic = topics[position]
        holder.tvTitle.text = topic.title
        holder.btnComplete.setOnClickListener { onComplete(topic.id) }
    }

    override fun getItemCount() = topics.size

    fun updateTopics(newTopics: List<PhilosophyTopic>) {
        topics = newTopics
        notifyDataSetChanged()
    }
}