package com.example.dialogwithzoom

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var zoomDialog: Dialog
    private lateinit var imageView: ImageView
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Obtén una referencia al ImageView de tu diseño principal
        imageView = findViewById(R.id.imageViewMain)

        // Configura el detector de gestos de escala
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        // Agrega el evento táctil al ImageView

        imageView.setOnClickListener {
            openZoomDialog(it)
        }
        // Configura el diálogo de zoom
        setupZoomDialog()
    }

    private fun setupZoomDialog() {
        // Crea el diálogo personalizado
        zoomDialog = Dialog(this)
        zoomDialog.setContentView(R.layout.dialog_zoom)
        zoomDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )


        // Configura la imagen en el diálogo
        imageView=zoomDialog.findViewById(R.id.imageView)
        val dialogImageView: ImageView = zoomDialog.findViewById(R.id.imageView)
        dialogImageView.setImageDrawable(imageView.drawable)
        dialogImageView.setOnTouchListener { v, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = Math.max(1.0f, Math.min(scaleFactor, 5.0f)) // Establece límites de zoom

            // Actualiza la escala de la imagen
            imageView.scaleX = scaleFactor
            imageView.scaleY = scaleFactor
            return true
        }
    }

    fun openZoomDialog(view: View) {
        zoomDialog.show()
    }

    fun closeZoomDialog(view: View) {
        zoomDialog.dismiss()
    }
}