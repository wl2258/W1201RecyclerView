package kr.ac.kumoh.s20200607.w1201recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {
    private val songs = ArrayList<String>()
    private val _list = MutableLiveData<ArrayList<String>>() //public으로 바꿔서 사용 가능하나 캡슐화를 위해 private
    val list : LiveData<ArrayList<String>>
        get() = _list

    init {
        _list.value = songs
    }

//    fun getList(): LiveData<ArrayList<String>> = _list //mutable이 아닌 그냥 liveData를 주게 함 (값만 가져가도록 읽기 전용)
    fun add(song: String) {
        songs.add(song)
        _list.value = songs
    }

//    fun getSong(i: Int) = songs[i]
//    fun getSize() = songs.size
}