package com.example.smartphoneapp.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartphoneapp.Adapter.ListItemAdapter
import com.example.smartphoneapp.R
import com.example.smartphoneapp.ViewModel.MainViewModel
import com.example.smartphoneapp.databinding.ActivityListBinding

class ListActivity : BaseActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityListBinding
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        context = this@ListActivity
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)

        binding.menuBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        // Set category title from intent
        val title = intent.getStringExtra("title")
        binding.titleTxt.text = title

        // Fetch categoryId as String from intent
        val categoryId = intent.getStringExtra("id") ?: ""

        // Set category image based on the categoryId
        when (categoryId) {
            "1" -> binding.catlmg.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_4
                )
            )

            "2" -> binding.catlmg.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_4
                )
            )

            "3" -> binding.catlmg.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_2
                )
            )

            "4" -> binding.catlmg.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_4
                )
            )

            "5" -> binding.catlmg.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_4
                )
            )

            "6" -> binding.catlmg.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_3
                )
            )
        }

        // Show loading progress while data is being fetched
        binding.progressBar.visibility = View.VISIBLE

        // Observe filtered items from the ViewModel
        viewModel.loadFiltered(categoryId).observe(this, Observer { items ->
            val mutableItems = items.toMutableList()

            if (mutableItems.isEmpty()) {
                binding.emptyTxt.visibility = View.VISIBLE
            } else {
                binding.emptyTxt.visibility = View.GONE
                binding.view.layoutManager =
                    LinearLayoutManager(this@ListActivity, LinearLayoutManager.VERTICAL, false)
                binding.view.adapter = ListItemAdapter(mutableItems)
            }
            binding.progressBar.visibility = View.GONE
        })
    }
}
