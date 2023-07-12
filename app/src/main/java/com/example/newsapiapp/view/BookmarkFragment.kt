package com.example.newsapiapp.view

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.times
import androidx.core.view.get
import androidx.core.view.marginEnd
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiapp.R
import com.example.newsapiapp.adapter.BookmarkAdapter
import com.example.newsapiapp.databinding.FragmentBookmarkBinding
import com.example.newsapiapp.extensions.Extensions.findNavControllerSafely
import com.example.newsapiapp.utils.Utils
import com.example.newsapiapp.viewmodel.BookmarkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBookmarks()
        swipeItems()
        backButtonPressed()
    }

    private fun getBookmarks() {
        lifecycleScope.launch {
            bookmarkViewModel.getAllBookmarks()
            bookmarkViewModel.bookmarkFlow.collect {
                binding.bookmarkRecyclerView.adapter = BookmarkAdapter(it)
            }
        }
    }

    private fun swipeItems() {
        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                println("layoutPos ${viewHolder.layoutPosition}")
                val bg = ContextCompat.getColor(requireContext(), R.color.error_message_color)
                val iconDelete =
                    ContextCompat.getDrawable(requireContext(), R.drawable.delete_icon)!!
                val width = iconDelete.intrinsicWidth
                val height = iconDelete.intrinsicHeight
                val hMargin = (itemView.left - itemView.right) / 20
                val vMargin = (itemView.bottom - itemView.top - height) / 2
                val offset = Utils.convertDpToPx(15f).toInt()
                val paint = Paint()
                val rect = Rect(
                    (itemView.right + hMargin - width),
                    (itemView.top + vMargin),
                    (itemView.right + hMargin), (itemView.bottom - vMargin)
                )
                val rectBig = Rect(
                    (itemView.right + hMargin - width - offset),
                    (itemView.top + vMargin - offset),
                    (itemView.right + hMargin + offset),
                    (itemView.bottom - vMargin + offset)
                )
                paint.color = (bg)
                paint.style = Style.FILL
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (dX > -itemView.right) {
                        c.drawRect(
                            (itemView.right - dX).toInt().toFloat(),
                            itemView.top.toFloat(),
                            (itemView.right + dX).toInt().toFloat(),
                            itemView.bottom.toFloat(),
                            paint
                        )
                        if (dX < hMargin) {
                            iconDelete.bounds = rect
                            iconDelete.draw(c)
                        }
                        else if (dX < hMargin - width - Utils.convertDpToPx(20f)) {
                            iconDelete.bounds = rectBig
                            iconDelete.draw(c)
                        }
                    }
                    else{
                        val article = bookmarkViewModel.bookmarkFlow.value[viewHolder.layoutPosition]
                        bookmarkViewModel.deleteBookmark(article)
                    }


                }
                super.onChildDraw(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.bookmarkRecyclerView)
    }

    fun backButtonPressed() {
        binding.backButton.setOnClickListener {
            findNavControllerSafely(R.id.bookmarkFragment)?.navigate(R.id.action_bookmarkFragment_to_main_fragment)
        }
    }


}