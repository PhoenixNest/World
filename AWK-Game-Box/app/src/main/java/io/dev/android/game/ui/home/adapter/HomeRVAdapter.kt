package io.dev.android.game.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.dev.android.game.databinding.ListItemHomeBinding
import io.dev.android.game.ui.home.model.GameModel

class HomeRVAdapter : RecyclerView.Adapter<HomeRVAdapter.HomeRVVH>() {

    private var dataList: List<GameModel> = emptyList()

    fun setData(list: List<GameModel>) {
        this.dataList = list
    }

    class HomeRVVH(
        private val binding: ListItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(model: GameModel) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRVVH {
        return HomeRVVH(ListItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeRVVH, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}