package id.anantyan.synrgychapter4.presentation.home

import id.anantyan.synrgychapter4.data.local.entities.Product

interface HomeInteraction {
    fun onClick(position: Int, item: Product)
    fun onLongClick(position: Int, item: Product)
}