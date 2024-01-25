package com.homework.raiffeisen.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.homework.raiffeisen.classes.User
import com.homework.raiffeisen.databinding.UserItemBinding
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UserViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: User) {
        binding.tvNameUser.text = "${item.name.first} ${item.name.last}"
        binding.tvYearsOfUser.text = "${item.dob.age} years from ${item.nat}"
        Picasso.get().load(item.picture.large).into(binding.imageUser)
        binding.tvDateOfRegister.text = setData(item.registered.date)
    }

    private fun setData(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        val dateTime = LocalDateTime.parse(date, formatter)
        val hourMinut = dateTime.toLocalTime()
        val formatterHourMinut = DateTimeFormatter.ofPattern("HH:mm")
        return hourMinut.format(formatterHourMinut)
    }
}