sealed class SongGuessUIState {

    object Loading: SongGuessUIState()

    data class Success(val songs: List<Song>): SongGuessUIState()

    data class Error(val message: String): SongGuessUIState()

}