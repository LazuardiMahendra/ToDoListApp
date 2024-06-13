package com.example.todolist.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.model.TaskModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class TaskViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _taskAdded = MutableLiveData<Boolean>()
    val taskAdded: LiveData<Boolean> get() = _taskAdded

    fun addTask(taskModel: TaskModel) {
        db.collection("tasks")
            .add(taskModel)
            .addOnSuccessListener { documentReference ->
                Log.d("POST", "DocumentSnapshot added with ID: ${documentReference.id}")
                _taskAdded.value = true
            }
            .addOnFailureListener { e ->
                Log.w("POST", "Error adding document", e)
                _taskAdded.value = false
            }
    }

    fun getTaskByAsc(taskModel: TaskModel) {
        db.collection("tasks").orderBy("date", "asc").get()
            .addOnSuccessListener { result ->
                Log.d("GET", "DocumentSnapshot added with ID: ${result.id}")
                _taskAdded.value = true
            }
            .addOnFailureListener { e ->
                Log.w("GET", "Error adding document", e)
                _taskAdded.value = false
            }
    }

//    fun getTaskByTodo(taskModel: TaskModel) {
//        db.collection("tasks").whereEqualTo("status", "To Do").get()
//            .addOnSuccessListener { result ->
//                Log.d("GET", "DocumentSnapshot added with ID: ${result}")
//                _taskAdded.value = true
//            }
//            .addOnFailureListener { e ->
//                Log.w("GET", "Error adding document", e)
//                _taskAdded.value = false
//            }
//    }

//    fun getTaskByProgress(taskModel: TaskModel) {
//        db.collection("tasks").whereEqualTo("status", "Progress")
//            .get()
//            .addOnSuccessListener { documentReference ->
//                Log.d("GET", "DocumentSnapshot added with ID:")
//                _taskAdded.value = true
//            }
//            .addOnFailureListener { e ->
//                Log.w("GET", "Error adding document", e)
//                _taskAdded.value = false
//            }
//    }


    fun getTaskByProgress(taskModel: TaskModel) {
        db.collection("tasks").whereEqualTo("status", "Done").get()
            .addOnSuccessListener { documentReference ->
                Log.d("GET", "DocumentSnapshot added with ID: ${documentReference}")
                _taskAdded.value = true
            }
            .addOnFailureListener { e ->
                Log.w("GET", "Error adding document", e)
                _taskAdded.value = false
            }


        fun updateTask(taskModel: TaskModel) {
            db.collection("tasks")
                .add(taskModel)
                .addOnSuccessListener { documentReference ->
                    Log.d("PUT", "DocumentSnapshot added with ID: ${documentReference.id}")
                    _taskAdded.value = true
                }
                .addOnFailureListener { e ->
                    Log.w("PUT", "Error adding document", e)
                    _taskAdded.value = false
                }
        }

        fun deleteTask(taskModel: TaskModel) {
            db.collection("tasks")
                .add(taskModel)
                .addOnSuccessListener { documentReference ->
                    Log.d("POST", "DocumentSnapshot added with ID: ${documentReference.id}")
                    _taskAdded.value = true
                }
                .addOnFailureListener { e ->
                    Log.w("POST", "Error adding document", e)
                    _taskAdded.value = false
                }
        }


    }
}