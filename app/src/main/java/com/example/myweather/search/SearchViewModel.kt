package com.example.myweather.search

import androidx.compose.ui.text.input.TextFieldValue
import com.example.myweather.base.BaseMviViewModel

class SearchViewModel :
    BaseMviViewModel<SearchContract.State, SearchContract.Event, SearchContract.Effect>() {
    override fun createState(): SearchContract.State {
        return SearchContract.State(
            searchTextField = TextFieldValue()
        )
    }

    override fun initialState() {
    }

    override fun loadData() {
    }

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.UpdateTextFieldValue -> {
                setState {
                    copy(searchTextField = event.text)
                }
            }
        }
    }
}