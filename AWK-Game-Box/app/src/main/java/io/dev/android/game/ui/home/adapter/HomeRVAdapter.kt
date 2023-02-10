package io.dev.android.game.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import io.dev.android.game.R
import io.dev.android.game.databinding.ItemHomeBinding
import io.dev.android.game.ui.home.model.GameModel
import io.dev.android.game.util.LogUtil

class HomeRVAdapter : RecyclerView.Adapter<HomeRVAdapter.HomeRVVH>() {

    private var dataList: List<GameModel> = emptyList()

    fun setData(list: List<GameModel>) {
        this.dataList = list
    }

    class HomeRVVH(
        private val binding: ItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(model: GameModel) {
            binding.model = model
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRVVH {
        return HomeRVVH(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomeRVVH, position: Int) {
        val gameModel = dataList[position]
        holder.bindData(gameModel)

        val itemView = holder.itemView
        itemView.setOnClickListener {
            when (gameModel.title) {
                itemView.resources.getString(R.string.game_2048_title) -> {
                    LogUtil.verbose(message = "Navigate to 2048")
                    itemView.findNavController().navigate(R.id.action_homeFragment_to_block2048Fragment)
                }
                itemView.resources.getString(R.string.game_one_line_finish_title) -> {
                    LogUtil.verbose(message = "Navigate to One Line Finish")
                    itemView.findNavController().navigate(R.id.action_homeFragment_to_oneLineFinishFragment)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}