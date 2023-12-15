package es.unex.giiis.asee.spotifilter.view.home

import es.unex.giiis.asee.spotifilter.data.model.User

interface UserProvider {
    fun getUser(): User
}