import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.ItemLayoutTaskBinding
import com.example.todolist.model.TaskModel
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

//class TaskListAdapter(private val model: List<TaskModel>) :
//    RecyclerView.Adapter<TaskListAdapter.MyViewHolder>() {
//    inner class MyViewHolder(val binding: ItemLayoutTaskBinding) :
//        RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): TaskListAdapter.MyViewHolder {
//        val binding =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_task, parent, false)
//        return MyViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: TaskListAdapter.MyViewHolder, position: Int) {
//        with(holder) {
//            with(model[position]) {
//                binding.tvTitle.text = name
//                binding.tvDate.text = date
//
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return model.size
//    }
//
//
//}


