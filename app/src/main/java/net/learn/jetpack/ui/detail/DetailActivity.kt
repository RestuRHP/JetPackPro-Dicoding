package net.learn.jetpack.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import net.learn.jetpack.R


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

//        supportActionBar?.title = intent.getStringExtra(EXTRA_TITLE)
//        supportActionBar?.elevation = 0f

        setSupportActionBar(toolbar)
//        toolbar.title=intent.getStringExtra(EXTRA_TITLE)
        collapsing.title = intent.getStringExtra(EXTRA_TITLE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}