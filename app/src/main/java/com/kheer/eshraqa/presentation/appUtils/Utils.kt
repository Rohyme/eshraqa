package com.kheer.eshraqa.presentation.appUtils

import android.content.Intent
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.kheer.eshraqa.R


/**
 *
 * @Auther Rohyme
 */


fun ImageButton.toggleFavourite(isFavourite: Boolean, isWhite: Boolean) {
    if (isFavourite) {
        setImageResource(if (isWhite) R.drawable.ic_favorite_heart_full_white else R.drawable.ic_favorite_heart_full)
    } else {
        setImageResource(if (isWhite) R.drawable.ic_favorite_heart_white else R.drawable.ic_favorite_heart)
    }
}


fun Fragment.shareEshraqa(title: String, content: String) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title)
    sharingIntent.putExtra(Intent.EXTRA_TEXT, content)
    startActivity(sharingIntent)
}