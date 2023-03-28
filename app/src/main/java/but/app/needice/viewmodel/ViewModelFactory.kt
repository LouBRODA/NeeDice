package but.app.needice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import but.app.needice.dao.DaoColor
import java.security.InvalidParameterException

class ViewModelFactory(val daoColor: DaoColor): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ColorViewModel::class.java)){
            return ColorViewModel(daoColor) as T
        } else {
            throw InvalidParameterException("ERR : Creation of ViewModel Impossible !")
        }
    }
}