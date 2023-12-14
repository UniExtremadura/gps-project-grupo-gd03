package es.unex.giiis.asee.spotifilter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    val name: String,
    val password: String
) : Serializable