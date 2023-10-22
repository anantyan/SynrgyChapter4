package id.anantyan.synrgychapter4.common

sealed class UIState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : UIState<T>(data = data)
    class Error<T>(message: String) : UIState<T>(message = message)
}