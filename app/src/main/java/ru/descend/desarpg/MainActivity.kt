package ru.descend.desarpg

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.descend.desarpg.databinding.ActivityMainBinding
import ru.descend.desarpg.room.AppDatabase
import ru.descend.desarpg.room.DatabaseBuilder
import ru.descend.desarpg.room.DatabaseHelperImpl
import ru.descend.desarpg.room.datas.RoomUsers

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView() = binding.apply {
        textSimple.text = "SimeleText"
        buttonTest.setOnClickListener { v ->
            CoroutineScope(Dispatchers.IO).launch {
                val courses = DatabaseHelperImpl(DatabaseBuilder.getInstance(v.context))
                courses.usersInsert(RoomUsers(courses.usersGetAll().size + 1, password = "1234552"))
                Log.e("#TAG", "ALL: ${courses.usersGetAll().joinToString("\n")}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}