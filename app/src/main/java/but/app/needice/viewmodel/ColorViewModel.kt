package but.app.needice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import but.app.needice.dao.DaoColor
import but.app.needice.entity.ColorEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ColorViewModel(val daoColor: DaoColor) : ViewModel() {

    //fun getColors():LiveData<List<ColorEntity>> = daoColor.getColors().asLiveData

    fun insertColor(colorEntity: ColorEntity){
        viewModelScope.launch {
            daoColor.insertColor(colorEntity)
        }
    }
}