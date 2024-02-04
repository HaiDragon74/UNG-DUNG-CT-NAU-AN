package com.example.btungdungmonan.adapter.AdapterCustom2rcl

import com.example.btungdungmonan.dataclass.Search.SearchMeal
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal

class ListDataParentChild {
    private var type: Int
    private lateinit var docMonAn: MutableList<CategoriMeal>
    private lateinit var conDocMeal: MutableList<SearchMeal>

    constructor(type: Int, docMonAn: MutableList<CategoriMeal>?, conDocMeal: MutableList<SearchMeal>?) {
        this.type = type
        if (docMonAn != null) {
            this.docMonAn = docMonAn
        }
        if (conDocMeal != null) {
            this.conDocMeal = conDocMeal
        }
    }
    fun setType(type: Int){
        this.type=type
    }
    fun getType():Int {
        return type
    }
    fun setDocMonAn(docMonAn: MutableList<CategoriMeal>) {
        this.docMonAn = docMonAn
    }
    fun getDocMonAn():MutableList<CategoriMeal>{
        return docMonAn
    }
    fun setConDocMeal(docMonAn: MutableList<SearchMeal>){
        this.conDocMeal=conDocMeal
    }
    fun getconDocMeal():MutableList<SearchMeal>{
        return conDocMeal
    }
}