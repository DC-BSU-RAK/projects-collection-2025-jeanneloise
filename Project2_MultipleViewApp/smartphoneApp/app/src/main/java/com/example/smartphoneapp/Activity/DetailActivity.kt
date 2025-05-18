package com.example.smartphoneapp.Activity


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.smartphoneapp.Model.ItemsModel
import com.example.smartphoneapp.PrefsHelper
import com.example.smartphoneapp.R
import com.example.smartphoneapp.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item:ItemsModel
    private lateinit var context:Context



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context=this

        bundle()

        binding.favBtn.setOnClickListener {
            val favorites = PrefsHelper.getFavoriteList(context).toMutableList()
            val alreadyFavorited = favorites.any { it.title == item.title }

            if (alreadyFavorited) {
                Toast.makeText(context, "Already in favorites", Toast.LENGTH_SHORT).show()
            } else {
                favorites.add(item)
                PrefsHelper.saveFavoriteList(context, favorites)
                Toast.makeText(context, "Added to favorites!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun bundle() {
        binding.apply {

            item=intent.getParcelableExtra("object")!!
            titleTxt.text=item.title
            subtitleTxt.text=item.extra
            descriptionTxt.text=item.description
            priceTxt.text="AED"+item.price

            val imageName = item.picUrl[0]
            val resourceId = resources.getIdentifier(imageName, "drawable", packageName)

            Glide.with(context)
                .load(resourceId)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
                .into(binding.img)
            backBtn.setOnClickListener {finish() }

                sizeBtn1.setOnClickListener{
                    sizeBtn1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.orange_stroke_bg))
                    sizeBtn2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dark_grey_bg2))
                    sizeBtn3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dark_grey_bg2))
                    sizeBtn1.setTextColor(getResources().getColor(R.color.orange))
                    sizeBtn2.setTextColor(getResources().getColor(R.color.white))
                    sizeBtn3.setTextColor(getResources().getColor(R.color.white))
                }

            sizeBtn2.setOnClickListener{
                sizeBtn1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dark_grey_bg2))
                sizeBtn2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.orange_stroke_bg))
                sizeBtn3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dark_grey_bg2))
                sizeBtn1.setTextColor(getResources().getColor(R.color.white))
                sizeBtn2.setTextColor(getResources().getColor(R.color.orange))
                sizeBtn3.setTextColor(getResources().getColor(R.color.white))
            }

            sizeBtn3.setOnClickListener{
                sizeBtn1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dark_grey_bg2))
                sizeBtn2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dark_grey_bg2))
                sizeBtn3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.orange_stroke_bg))
                sizeBtn1.setTextColor(getResources().getColor(R.color.white))
                sizeBtn2.setTextColor(getResources().getColor(R.color.white))
                sizeBtn3.setTextColor(getResources().getColor(R.color.orange))
            }

        }
    }
}