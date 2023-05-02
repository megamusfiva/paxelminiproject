package com.example.paxelminiproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paxelminiproject.data.remote.model.UserResponse
import com.example.paxelminiproject.databinding.CardItemBinding


class MainAdapter (private val listener: (UserResponse) -> Unit) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var mList: List<UserResponse>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<UserResponse>?) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(mList!![position], listener)

    inner class MyViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserResponse, listener: (UserResponse) -> Unit) = with(itemView) {
            binding.tvName.text = item.fullName
            binding.tvDescription.text = item.description
            binding.tvLogin.text = item.owner.login
            Glide.with(this).load(item.owner.avatarUrl).into(binding.imgPhoto)
            setOnClickListener {
                listener(item)
            }
        }
    }
}
