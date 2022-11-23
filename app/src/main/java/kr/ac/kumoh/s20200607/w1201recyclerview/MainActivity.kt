package kr.ac.kumoh.s20200607.w1201recyclerview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumoh.s20200607.w1201recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    /**
     * viewModel 사용방법
     * 1) build.gradle dependencies implement 추가
     */
    //private val model : ListViewModel by viewModels()

    /**
     * 2) lateinit var 사용
     */
    private lateinit var model : ListViewModel

    private val songAdaptor = SongAdapter()

    @SuppressLint("NotifyDataSetChanged") //에러 나는 거 알고 있으니까 걍 쓸게
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[ListViewModel::class.java]

        //어떤 방식으로 recyclerView가 동작하는지 설정해줘야함

        /*
        songAdaptor()과 songAdaptor은 다른 객체임!
         */

        //블록 함수 사용
        binding.list.apply {
            layoutManager = LinearLayoutManager(applicationContext) //this는 recyclerAdapter를 가리킴
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true) //에러나는 경우를 막기 위해 사용
            adapter = songAdaptor
        }

        model.list.observe(this) {
            //songAdaptor.notifyDataSetChanged() //warning 발생, 데이터가 바뀌면 뭐가 바뀐지는 모르기 때문에 아예 reset 시키고 전부 다 다시 보여주니까 비효율적임
            songAdaptor.notifyItemMoved(0, model.list.value?.size ?: 0) //nullable인지 체크 해줘야 함, elvis 연산자 사용
        }

        for (i in 1..3) {
            model.add("love me like that")
        }
        model.add("bye bye my blue")

    }

    inner class SongAdapter : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
        inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            val txSong : TextView = itemView.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            TODO("inflation")
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
            // fasle 만들자마자 parent에 바로 붙일거냐? 우리는 어뎁터 사용하니까 false
            // 이 클래스가 activity가 아닌 songAdaptor이기 때문에 this 못 넣음 context switching

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            TODO("텍스트뷰 값을 세팅하도록 함 - vh가 텍스트뷰 가짐")
            holder.txSong.text = model.list.value?.get(position) ?: "" //model.list.value[position] 이 제일 깔끔하지만 value가 nullable이기 때문에 이렇게 사용할 수 없음
        }

        override fun getItemCount() = model.list.value?.size ?: 0
    }
}