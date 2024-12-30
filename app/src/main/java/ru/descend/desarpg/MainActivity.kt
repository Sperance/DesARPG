package ru.descend.desarpg

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.descend.desarpg.databinding.ActivityMainBinding
import ru.descend.desarpg.logic.Mob
import ru.descend.desarpg.room.AppDatabase
import ru.descend.desarpg.room.datas.RoomUsers

class MainActivity : AppCompatActivity() {

    private val vm: MainActivityVM by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDatabase(this@MainActivity)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView() = binding.apply {
        textSimple.text = "SimeleText"
        buttonTest.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                vm.addUser(RoomUsers(0, password = "1234552"))
                Log.e("#TAG", "ALL: ${db.daoUsers().getAll().joinToString("\n")}")
            }
        }
        roomToMob.setOnClickListener {
            vm.getMobFromRoom(1)
        }
        mobToRoom.setOnClickListener {
            val pers1 = Mob("TestHero1")
            pers1.battleStats.attackPhysic.set(25)
            pers1.battleStats.attackPhysic.setPercent(10)
            pers1.battleStats.health.set(12.5)
            pers1.battleStats.strength.set(3)
            pers1.battleStats.health.setPercent(10.2)
            vm.addMobToRoom(pers1)
        }
        allMobsRoom.setOnClickListener {
            vm.getAllMobs()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}